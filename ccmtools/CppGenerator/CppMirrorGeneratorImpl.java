/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * copyright (c) 2002, 2003 Salomon Automation
 *
 * $Id$
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.CppGenerator;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MFixedDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CppMirrorGeneratorImpl
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    {
        "MComponentDef"
    };

    // test values for attributes, operations, and parameters.

    private Map test_values;
    private Map value_names;
    private Map used_value_names;
    private final static String[][] local_test_values =
    {
        // in general these follow a pattern. also, wide literals are all
        // capital letters, and non-wide are all lower case, just so they're
        // visually different.
        // min (or generic)         max (or min positive)    0        1
        { "",                      "",                      "",      "",    },
        { "NULL",                  "\"test any value\"",    "1.5",   "'a'", },
        { "TRUE",                  "FALSE",                 "",      "",    },
        { "'a'",                   "'z'",                   "'\0'",  "'1'", },
        { "-1e308",                "1e-308",                "0.0",   "1.0", },
        { "(fixed data type not implemented", "", "", "", },
        { "-1e38",                 "1e-38",                 "0",     "1",   },
        { "-2147483648L",          "2147483647L",           "0L",    "1L",  },
        { "-1e308",                "1e-308",                "0.0",   "1.0", },
        { "-9223372036854775808L", "9223372036854775807L",  "0L",    "1L",  },
        { "NULL",                  "",                      "",      "",    },
        { "NULL",                  "",                      "",      "",    },
        { "7",                     "255",                   "0",     "1",   },
        { "(principal data type not implemented", "", "", "", },
        { "-32768",                "32767",                 "0",     "1",   },
        { "\"test string\"",       "\"s\"",                 "\"\"",  "",    },
        { "NULL",                  "",                      "",      "",    },
        { "7",                     "4294967295L",           "0L",    "1L",  },
        { "7",                     "18446744073709551615L", "0L",    "1L",  },
        { "7",                     "65535",                 "0",     "1",   },
        { "NULL",                  "",                      "",      "",    },
        { "'A'",                   "'Z'",                   "'\0'",  "'1'", },
        { "\"TEST WSTRING\"",      "\"W\"",                 "\"\"",  "",    },
    };

    /**************************************************************************/

    public CppMirrorGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppMirror", d, out_dir, local_output_types, null, null);

        String[] kinds = MPrimitiveKind.getLabels();

        value_names = new Hashtable();
        used_value_names = new Hashtable();

        test_values = new Hashtable();
        for (int i = 0; i < local_test_values.length; i++) {
            List tmp = new ArrayList();
            for (int j = 0; j < local_test_values[i].length; j++)
                if (! local_test_values[i][j].equals(""))
                    tmp.add(local_test_values[i][j]);
            test_values.put(kinds[i], tmp);
        }
    }

    /**
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException
    {
        String generated_code = template.substituteVariables(output_variables);

        if (generated_code.trim().equals("")) return;

        String node_name = ((MContained) current_node).getIdentifier();
        String file_dir = "CCM_Test";
        String file_name = "_check_" +
            handleNamespace("FileNamespace", node_name) + ".cc";

        writeFinalizedFile(file_dir, file_name, generated_code);

        File makefile = new File(file_dir, "Makefile.py");
        if (! makefile.isFile())
            writeFinalizedFile(file_dir, "Makefile.py", "");
    }

    /**
     * Finalize the output files. This function's implementation does nothing;
     * it serves only to override the inherited function from CppGeneratorImpl.
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files) { return; }

    /**************************************************************************/

    /**
     * Get a local value for the given variable name.
     *
     * @param variable The variable name to get a value for.
     * @return the value of the variable available based on information in the
     *         current state of graph traversal.
     */
    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);

        if (current_node instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        } else if (current_node instanceof MComponentDef) {
            return data_MComponentDef(variable, value);
        }

        return value;
    }

    protected String data_MAttributeDef(String data_type, String data_value)
    {
        if (data_type.equals("TestValue")) {
            // FIXME : implement some sort of loop so we can use all test
            // values.
            return getTestVariable(current_node, 0);
        }
        // there isn't a data_MAttribute function in the superclass ...
        return data_value;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    {
        if (data_type.equals("UsingNamespace")) {
            String id = ((MComponentDef) current_node).getIdentifier();
            String temp = handleNamespace(data_type, id);
            return temp+"using namespace CCM_Session_"+id+"_mirror;\n";
        }
        return super.data_MComponentDef(data_type, data_value);
    }

    /**************************************************************************/

    /**
     * Get information about the parameters for the given function. The type of
     * information returned depends on the type parameter.
     *
     * @param op the operation to investigate.
     * @param type the type of information to gather. If the type is "value"
     *        this function builds up a string of test values for each
     *        parameter. Otherwise the parent class' function is called.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    protected String getOperationParams(MOperationDef op, String type)
    {
        Iterator params = op.getParameters().iterator();
        if (type.equals("value")) {
            List ret = new ArrayList();
            while (params.hasNext())
                ret.add(getTestVariable(params.next(), 0));
            return join(", ", ret);
        } else if (type.equals("complex")) {
            StringBuffer values = new StringBuffer();
            while (params.hasNext())
                values.append(createTestVariable((MParameterDef) params.next()));
            return values.toString();
        } else
            return super.getOperationParams(op, type);
    }

    /**
     * Load an appropriate template (based on the value in the template_name
     * argument) for the given child, and fill out its variable information.
     *
     * @param child MInterfaceDef node to gather information from.
     * @param template_name the name of the template to load for variable
     *        substitution.
     * @return a string containing the variable-substituted template requested.
     */
    protected Map getTwoStepVariables(MInterfaceDef iface,
                                      MOperationDef operation,
                                      MContained container)
    {
        String lang_type = getLanguageType(operation);
        String values = getOperationParams(operation, "value");

        Map local_vars = new Hashtable();

        local_vars.put("Object",       container.getIdentifier());
        local_vars.put("Identifier",   operation.getIdentifier());
        local_vars.put("ProvidesType", iface.getIdentifier());

        local_vars.put("MParameterDefValue", values);
        local_vars.put("MParameterDefValueString", values.replaceAll("\"", "'"));
        local_vars.put("MParameterDefComplexValue",
                       getOperationParams(operation, "complex"));

        if (! lang_type.equals("void")) {
            local_vars.put("PrintReturnStart", "cout << \">> returned \" << ");
            local_vars.put("PrintReturnEnd", " << endl");
        } else {
            local_vars.put("PrintReturnStart", "");
            local_vars.put("PrintReturnEnd", "");
        }

        return local_vars;
    }

    /**************************************************************************/

    /**
     * Create a variable name that can be used to hold an instance of a test
     * value for this object.
     */
    private String getTestVariable(Object node, int var_index)
    {
        MIDLType type = null;
        String kind = null;

        if (node instanceof MTyped)
            type = ((MTyped) node).getIdlType();
        else
            return "";

        if (type instanceof MPrimitiveDef)
            kind = ((MPrimitiveDef) type).getKind().toString();
        else if (type instanceof MStringDef)
            kind = ((MStringDef) type).getKind().toString();
        else if (type instanceof MWstringDef)
            kind = ((MWstringDef) type).getKind().toString();
        else if (type instanceof MFixedDef)
            kind = ((MFixedDef) type).getKind().toString();

        if (kind != null) {
            List values = (List) test_values.get(kind);
            return (String) values.get(var_index);
        }

        if (type instanceof MTypedefDef) {
            MTypedefDef typedef = (MTypedefDef) type;
            String id = typedef.getIdentifier();

            Integer index = new Integer(-1);
            if (used_value_names.containsKey(id))
                index = (Integer) used_value_names.get(id);
            index = new Integer(index.intValue() + 1);
            used_value_names.put(id, index);

            return id+"Instance"+index;
        }

        return "NULL";
    }

    /**
     * Create a test variable for the given parameter, if the parameter is a
     * compound (typedef) type. This will, for example, create a new struct
     * instance, and fill in some example values. The resulting variable name
     * can be retrieved with a call to 'getTestVariable'.
     */
    private String createTestVariable(MParameterDef param)
    {
        MIDLType type = param.getIdlType();

        if (! (type instanceof MTypedefDef)) return "";

        MTypedefDef typedef = (MTypedefDef) type;
        String id = typedef.getIdentifier();

        Integer index = new Integer(-1);
        if (value_names.containsKey(id))
            index = (Integer) value_names.get(id);
        index = new Integer(index.intValue() + 1);
        value_names.put(id, index);

        String result = "    "+id+" "+id+"Instance"+index+";\n";

        // FIXME : implement member variable initializiation.

        return result;
    }
}

