/* CCM Tools : IDL Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

package ccmtools.IDLGenerator;

import ccmtools.CodeGenerator.CodeGenerator;
import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MParameterMode;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;

import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract public class IDLGenerator
    extends CodeGenerator
{
    private String fileSuffix = "";

    private final static String[] local_output_types =
    {
        "MComponentDef", "MInterfaceDef", "MHomeDef",
        "MStructDef", "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef"
    };

    private final static String[] local_reserved_words =
    {
        "abstract", "array", "attribute", "boolean", "char", "const", "double",
        "enum", "exception", "fixed", "float", "in", "inout", "interface",
        "long", "module", "native", "null", "Object", "octet", "out",
        "sequence", "short", "string", "struct", "union", "unsigned", "void",
        "wchar", "wstring", "component", "factory", "finder", "home",
    };

    private final static String[] local_language_map =
    {
        "",
        "Any",
        "boolean",
        "char",
        "double",
        "fixed",
        "float",
        "long",
        "long double",
        "long long",
        "null",
        "Object",
        "octet",
        "principal",
        "short",
        "string",
        "typecode",
        "unsigned long",
        "unsigned long long",
        "unsigned short",
        "ValueBase",
        "void",
        "wchar",
        "wstring"
    };

    /**************************************************************************/

    public IDLGenerator(String suffix, Driver d, File out_dir)
        throws IOException
    {
        super("IDL" + suffix, d, out_dir, local_output_types,
              local_reserved_words, null, null, local_language_map);

        fileSuffix = new String(suffix.toLowerCase());
    }

    /**
     * Acknowledge and process a closing node during graph traversal. If the
     * node is of the correct type and defined in the original parsed file,
     * write code for this node.
     *
     * @param node the node that the graph traverser object just finished
     *        investigating.
     * @param scope_id the full scope identifier of the node. This identifier is
     *        a string containing the names of ancestor nodes, joined together
     *        with double colons.
     */
    public void endNode(Object node, String scope_id)
    {
        super.endNode(node, scope_id);
        writeOutputIfNeeded();
    }

    /**
     * Finalize the output files. This function's implementation writes a global
     * user_types.h file based on the individual <file>_user_types.h files.
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
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    protected void writeOutput(Template template)
        throws IOException
    {
        String[] pieces =
            template.substituteVariables(output_variables).split("\n");

        List code_pieces = new ArrayList();
        for (int i = 0; i < pieces.length; i++)
            if (! pieces[i].trim().equals(""))
                code_pieces.add(pieces[i]);

        String name = join("_", namespace);
        if (! name.equals("")) name += "_";
        name += ((MContained) current_node).getIdentifier();
        name += ".idl" + this.fileSuffix;

        String code = join("\n", code_pieces).replaceAll("};", "};\n");

        writeFinalizedFile("", name, code + "\n\n");
    }

    /**
     * Build a string containing appropriately formatted namespace information
     * based on the given data type and local namespace component. This is
     * aimed at languages with C-like syntax (perl, C, C++, Java, IDL) and
     * should be overridden for others (Python, Prolog :-).
     *
     * @param data_type a string referring to a desired type of namespace
     *        information. This is normally a variable name from a template.
     * @param local a string giving the name of the current namespace component.
     * @return a string containing the appropriately formatted namespace
     *         information.
     */
    protected String handleNamespace(String data_type, String local)
    {
        if (data_type.equals("OpenNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = namespace.iterator(); i.hasNext(); )
                tmp.add("module "+i.next()+" {\n");
            return join("", tmp);
        } else if (data_type.equals("CloseNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = namespace.iterator(); i.hasNext(); i.next())
                tmp.add("};\n");
            return join("", tmp);
        } else {
            return super.handleNamespace(data_type, local);
        }
    }

    /**
     * Create an #include statement sufficient for including the given node's
     * header file.
     *
     * @param node the node to use for gathering include statement information.
     * @return a string containing an #include statement.
     */
    protected String getScopedInclude(MContained node)
    {
        List scope = getScope(node);
        scope.add(node.getIdentifier());
        return "#include <" + join("_", scope) + ".idl" + fileSuffix + ">";
    }
    /**
     * Get a local value for the given variable name.
     *
     * This function performs some common value parsing in the CCM MOF library.
     * More specific value parsing needs to be provided in the subclass for a
     * given language, in the subclass' getLocalValue function. Subclasses
     * should call this function first and then perform any subclass specific
     * value manipulation with the returned value.
     *
     * @param variable The variable name to get a value for.
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);

        if (current_node instanceof MComponentDef) {
            return data_MComponentDef(variable, value);
        } else if (current_node instanceof MHomeDef) {
            return data_MHomeDef(variable, value);
        } else if (current_node instanceof MInterfaceDef) {
            return data_MInterfaceDef(variable, value);
        } else if (current_node instanceof MOperationDef) {
            return data_MOperationDef(variable, value);
        } else if (current_node instanceof MEnumDef)      {
            return data_MEnumDef(variable, value);
        } else if (current_node instanceof MFieldDef) {
            return data_MFieldDef(variable, value);
        } else if (current_node instanceof MUnionFieldDef) {
            return data_MUnionFieldDef(variable, value);
        } else if (current_node instanceof MAliasDef) {
            return data_MAliasDef(variable, value);
        }

        return value;
    }

    /**
     * Return the language type for the given object.
     *
     * @param object the node object to use for type finding.
     * @return a string describing the IDL language type.
     */
    protected String getLanguageType(MTyped object)
    {
        String base_type = getBaseIdlType(object);
	MIDLType idl_type = object.getIdlType();

        if (language_mappings.containsKey(base_type))
            base_type = (String) language_mappings.get(base_type);

	// We have to check the direction of the operation's parameter

	if (object instanceof MParameterDef) {
	    MParameterDef param = (MParameterDef) object;
	    MParameterMode direction = param.getDirection();
	    String parameter_direction = "";

	    if (direction == MParameterMode.PARAM_IN) {
		parameter_direction = "in ";
	    } else if  (direction == MParameterMode.PARAM_INOUT) {
		parameter_direction = "inout ";
	    } else if  (direction == MParameterMode.PARAM_OUT) {
		parameter_direction = "out ";
	    }

	    return parameter_direction + base_type;
	}

	// We have to check the kind of typedef definition to create correct
	// IDL2 code

	if (object instanceof MAliasDef) {
	    String typedef_identifier = ((MAliasDef)object).getIdentifier();
	    if (idl_type instanceof MArrayDef) {
		// creates a 'typedef type name[n][m]..' statement
		Iterator i = ((MArrayDef) idl_type).getBounds().iterator();
		Long bound = (Long) i.next();
		String result = base_type + " "+ typedef_identifier + "[" + bound;
		while (i.hasNext()) result += "][" + (Long) i.next();
		return result + "]";
	    } else if (idl_type instanceof MSequenceDef) {
		// creates a 'typedef sequence<type>' name statement
		String result = "sequence<" + base_type;
		Long bound = ((MSequenceDef) idl_type).getBound();
		if (bound != null) result += "," + bound;
		return result + "> " + typedef_identifier;
	    } else {
		// creates a 'typedef type name' statement
		String result = base_type + " "+ typedef_identifier;
		return result;
	    }
	}

	return base_type;
    }

    /**************************************************************************/

    protected String data_MAliasDef(String data_type, String data_value)
    {
        MIDLType idl_type = ((MAliasDef) current_node).getIdlType();
        if (data_type.equals("LanguageTypeInclude")) {
            if (idl_type instanceof MContained) {
                MContained node = (MContained) idl_type;
                System.out.println("idl type "+node.getIdentifier()+" is mcontained ...");
                if (node.getSourceFile().equals("")) {
                    System.out.println("source file is not original");
                    return getScopedInclude(node);
                }
            }
        }
        return data_value;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    { return data_MInterfaceDef(data_type, data_value); }

    protected String data_MEnumDef(String data_type, String data_value)
    {
        if (data_type.equals("Members")) {
            List b = new ArrayList();
            MEnumDef enum = (MEnumDef) current_node;
            for (Iterator i = enum.getMembers().iterator(); i.hasNext(); )
                b.add((String) i.next());
            return join(", ", b);
        }
        return data_value;
    }

    protected String data_MFieldDef(String data_type, String data_value)
    {
        MIDLType idl_type = ((MFieldDef) current_node).getIdlType();
        if (data_type.equals("LanguageTypeInclude")) {
            if (idl_type instanceof MContained) {
                MContained node = (MContained) idl_type;
                if (node.getSourceFile().equals(""))
                    return getScopedInclude(node);
            }
        }
        return data_value;
    }

    protected String data_MHomeDef(String data_type, String data_value)
    {
        if (data_type.equals("ComponentInclude")) {
        }
        return data_MInterfaceDef(data_type, data_value);
    }

    protected String data_MInterfaceDef(String data_type, String data_value)
    {
        if (data_type.equals("BaseTypes")) {
            String base = joinBases(", ");
            if (base.length() > 0) return ": " + base;
        } else if (data_type.equals("ExternInclude")) {
            return collectExternIncludes();
        } else if (data_type.startsWith("MSupportsDef") &&
                   data_value.endsWith(", ")) {
            return "supports " +
                data_value.substring(0, data_value.length() - 2);
        }
        return data_value;
    }

    protected String data_MOperationDef(String data_type, String data_value)
    {
        if (data_type.startsWith("MExceptionDef") && data_value.endsWith(", "))
            return "raises ( " +
                data_value.substring(0, data_value.length() - 2) + " )";
        else if (data_type.startsWith("MParameterDef") &&
                 data_value.endsWith(", "))
            return data_value.substring(0, data_value.length() - 2);
        return data_value;
    }

    protected String data_MUnionFieldDef(String data_type, String data_value)
    {
        MIDLType idl_type = ((MUnionFieldDef) current_node).getIdlType();
        if (data_type.equals("LanguageTypeInclude")) {
            if (idl_type instanceof MContained) {
                MContained node = (MContained) idl_type;
                if (node.getSourceFile().equals(""))
                    return getScopedInclude(node);
            }
        }
        return data_value;
    }
}






