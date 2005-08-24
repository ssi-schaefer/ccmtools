/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.UI.Driver;

public class CppPythonGenerator
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    {
        "MComponentDef", "MInterfaceDef", "MHomeDef",
        "MStructDef", "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef"
    };

    /**************************************************************************/

    public CppPythonGenerator(Driver d, File out_dir)
        throws IOException
    {
        super("CppPython", d, out_dir, local_output_types);

        baseNamespace.add("CCM_Test");
        baseNamespace.add("Python");
        baseNamespace.add("CCM_Local");
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
        String node_name = ((MContained) currentNode).getIdentifier();

        String file_dir = join("_", slice(namespaceStack, 2));

        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
        String[] out_files = { node_name + "_python.h",
                               node_name + "_python.cc" };

        for (int i = 0; i < out_strings.length; i++) {
            String generated_code = out_strings[i];
            String file_name = out_files[i];

            if (generated_code.trim().equals("")) continue;

            writeFinalizedFile(file_dir, file_name, generated_code);
        }
    }

    /**************************************************************************/

    /**
     * Return the language type for the given object. This returns the value
     * given by getLanguageType, but it replaces non-word characters with
     * underscores and such.
     *
     * @param object the node object to use for type finding.
     */
    protected String getLanguageType(MTyped object)
    {
        String lang_type = super.getLanguageType(object);
        lang_type = lang_type.replaceAll("[*]", "_ptr");
        lang_type = lang_type.replaceAll("&", "_ref");
        lang_type = lang_type.replaceAll("const ", "const_");
        lang_type = lang_type.replaceAll("std::", "");
        lang_type = lang_type.replaceAll(CPP_SEQUENCE_TYPE, "");
        return      lang_type.replaceAll("[ ><]", "");
    }

    /**************************************************************************/

    protected String data_MAliasDef(String data_type, String data_value)
    {
        MIDLType idl_type = ((MAliasDef) currentNode).getIdlType();

        if (data_type.equals("CppLanguageType")) {
            return super.getLanguageType((MTyped) idl_type);

        } else if (data_type.equals("AliasType")) {
            String type = "MAliasDefNormal";
            if (idl_type instanceof MSequenceDef) type = "MAliasDefSequence";
            else if (idl_type instanceof MArrayDef) type = "MAliasDefArray";
            Template t = template_manager.getTemplate(type, current_name);
            data_value = t.substituteVariables(output_variables);

        } else if (data_type.equals("LoopConvertTo")) {
        } else if (data_type.equals("LoopConvertFrom")) {
        }

        return data_value;
    }

    /**************************************************************************/

    /**
     * Get C++ information about the parameters for the given operation. This is
     * essentially a way to circumvent the getLanguageType function to get
     * access to the parent class getLanguageType implementation.
     *
     * @param op the operation to investigate.
     * @return a comma separated string of the parameter information requested
     *         for this operation.
     */
    private String getOperationCppParams(MOperationDef op)
    {
        List ret = new ArrayList();
        for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); ) {
            MParameterDef p = (MParameterDef) ps.next();
            ret.add(super.getLanguageType(p) + " " + p.getIdentifier());
        }
        return join(", ", ret);
    }

    /**
     * Get Python parameter conversion code for the given operation.
     *
     * @param op the operation to investigate.
     * @return a string containing code for converting the given operation's parameters
     *         to Python values and adding them to the pyArgs tuple.
     */
    private String getOperationConvertTo(MOperationDef op)
    {
        StringBuffer ret = new StringBuffer("");
        int pos = 0;
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
            String lang_type = getLanguageType(p);
            String id = p.getIdentifier();

            ret.append("  PyObject *python_" + id + " = convert_" + lang_type);
            ret.append("_to_python ( " + id + " );\n");

            ret.append("  if ( PyTuple_SetItem ( args, " + pos++ + ", python_");
            ret.append(id + " ) ) return;\n");
        }
        return ret.toString();
    }

    /**
     * Get Python parameter conversion code for the given operation.
     *
     * @param op the operation to investigate.
     * @return a string containing code for converting the given operation's parameters
     *         to Python values and adding them to the pyArgs tuple.
     */
    private String getOperationConvertFrom(MOperationDef op)
    {
        StringBuffer ret = new StringBuffer("");
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
            ret.append("  Py_DECREF ( python_" + p.getIdentifier() + " );\n");
        }
        return ret.toString();
    }

    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function.
     *
     * @param operation the particular interface operation that we're filling in
     *        a template for.
     * @param container the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepOperationVariables(MOperationDef operation,
                                               MContained container)
    {
        String lang_type = super.getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object",                   container.getIdentifier());
        vars.put("Identifier",               operation.getIdentifier());
        vars.put("LanguageType",             lang_type);
        vars.put("MExceptionDef",            getOperationExcepts(operation));
        vars.put("MParameterDefAll",         getOperationCppParams(operation));
        vars.put("MParameterDefName",        getOperationParamNames(operation));
        vars.put("MParameterDefConvertTo",   getOperationConvertTo(operation));
        vars.put("MParameterDefConvertFrom", getOperationConvertFrom(operation));

        vars.put("NumParams", new Integer(operation.getParameters().size()));

        if (! lang_type.equals("void")) {
            vars.put("Return",
                     "  result = convert_" + getLanguageType(operation) +
                     "_from_python ( python_result );\n" +
                     "  Py_DECREF ( python_result );\n" +
                     "  return result;\n");
            vars.put("ReturnVar", "  " + lang_type + " result;");
        } else {
            vars.put("Return", "  Py_DECREF ( python_result );\n");
            vars.put("ReturnVar", "");
        }

        return vars;
    }
}

