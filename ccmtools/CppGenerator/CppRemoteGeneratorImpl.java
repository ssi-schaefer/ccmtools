/* CCM Tools : C++ Code Generator Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003 Salomon Automation
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

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.*;
import ccmtools.Metamodel.ComponentIDL.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class CppRemoteGeneratorImpl
    extends CppGenerator
{
    //====================================================================
    // Definition of arrays that determine the generator's behavior
    //====================================================================

    /**
     * Top level node types:
     * Types for which we have a global template; that is, a template that is
     * not contained inside another template.
     */
    private final static String[] local_output_types =
    {
	//"MContainer",
        "MHomeDef",
	"MComponentDef"
    };

    /**
     * Environment files:
     * Output locations and templates for "environment files", the files that we
     * need to output once per project. the length of this list needs to be the
     * same as the length of the following list ; this list provides the file
     * names, and the next one provides the templates to use for each file.
     */
    private final static File[] local_environment_files =
    {
        new File("CCM_Session_Container", "CCMContainer.h"),
	new File("CCM_Session_Container", "CCMContainer.cc"),
	new File("CCM_Session_Container", "Makefile.py"),

        new File("remoteComponents", "Components.idl"),
	new File("remoteComponents", "Components.h"),
	new File("remoteComponents", "Components.cc"),
        new File("remoteComponents", "Makefile.py")
    };

    /**
     * Template files:
     * Defines the templates for the files in local_environment_files.
     * Note: order and length of both array must be the same.
     */
    private final static String[] local_environment_templates =
    {
        "CCMContainerHeader",     // Template for CCMContainer.h
	"CCMContainerImpl",       // Template for CCMContainer.cc
	"Blank",                  // Template for Makefile.py

	"ComponentsIdl",          // Template for Components.idl
	"ComponentsHeader",       // (mico 2.3.10 specific)
	"ComponentsImpl",         // (mico 2.3.10 specific)
	"MakefilePy"              // Template for Makefile.py
    };

    /**
     * Language type mapping:
     * Defines the IDL to C++ Mappings for primitive types.
     * Note: order and length of the array must be the same as used by the
     * MPrimitiveKind enumeration of the CCM metamodel.
     */
    private final static String[] remote_language_map =
    {
	"",
	"CORBA::Any",         // PK_ANY
	"CORBA::Boolean",     // PK_BOOLEAN
	"CORBA::Char",        // PK_CHAR
	"CORBA::Double",      // PK_DOUBLE
	"",                   // PK_FIXED
	"CORBA::Float",       // PK_FLOAT
	"CORBA::Long",        // PK_LONG
	"CORBA::LongDouble",  // PK_LONGDOUBLE
	"CORBA::LongLong",    // PK_LONGLONG
	"",                   // PK_NULL
	"",                   // PK_OBJREF
	"CORBA::Octet",       // PK_OCTET
	"",                   // PK_PRINCIPAL
	"CORBA::Short",       // PK_SHORT
	"char*",              // PK_STRING
	"",                   // PK_TYPECODE
	"CORBA::ULong",       // PK_ULONG
	"CORBA::ULongLong",   // PK_ULONGLONG
	"CORBA::UShort",      // PK_USHORT
	"",                   // PK_VALUEBASE
	"void",               // PK_VOID
	"CORBA::WChar",       // PK_WCHAR
	"CORBA::WChar*"       // PK_WSTRING
    };

    private Map CORBA_mappings;

    /**
     * The generator constructor calls the constructor of the base class
     * and sets up the map for the CORBA to C++ mapping.
     *
     * @param d
     * @param out_dir
     *
     * @exception IOException
     */
    public CppRemoteGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppRemote", d, out_dir, local_output_types,
              local_environment_files, local_environment_templates);

        base_namespace = "CCM_Remote";

	System.out.println("CppRemoteGeneratorImpl.CppRemoteGeneratorImpl()");

	// fill the CORBA_mappings with IDL to C++ Mapping types

	String[] labels = MPrimitiveKind.getLabels();
	CORBA_mappings = new Hashtable();
	for (int i = 0; i < labels.length; i++)
	    CORBA_mappings.put(labels[i], remote_language_map[i]);
    }

    /**
     * Get a local value for the given variable name.
     *
     * This function performs some common value parsing in the CCM MOF library.
     * More specific value parsing needs to be provided in the subclass for a
     * given language, in the subclass' getLocalValue function.
     *
     * Note that the method calls the super class method.
     *
     * @param variable The variable name to get a value for.
     *
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
	System.out.println("CppRemoteGeneratorImpl.getLocalValue("+ variable+ ")");

        String value = super.getLocalValue(variable);

	if (current_node instanceof MUsesDef) {
            return data_MUsesDef(variable, value);
        } else if (current_node instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        } else if (current_node instanceof MFactoryDef) {
            return data_MFactoryDef(variable, value);
	}

	return value;
    }

   /**
    * Navigates in the metamodel from MUsesDef to MInterfaceDef to make
    * the template substitution for the MOperationDef possible.
    * @param data_type
    * @param data_value
    * @return
    */
    protected String data_MUsesDef(String data_type, String data_value)
    {
	System.out.println("CppRemoteGeneratorImpl.data_MUsesDef("+data_type+", "+ data_value +")");

        if (data_type.startsWith("MOperation")) {
            MUsesDef uses = (MUsesDef) current_node;
            return fillTwoStepTemplates(uses.getUses(), data_type);
        }
        return data_value;
    }


    protected String data_MAttributeDef(String data_type, String data_value)
    {
	System.out.println("CppRemoteGeneratorImpl.data_MAttributeDef("
			   + data_type + "," + data_value+ ")");

	// current_node is MAttributeDef
	MTyped object = (MTyped)current_node;
	String base_type = getBaseLanguageType(object);

	// handle %(CORBAType)s Template in %(MAttributeDef*)s
        if (data_type.equals("CORBAType")) {
	    data_value =  getCORBALanguageType((MTyped)current_node);
        }
	// handle %(MAttributeDefConvertResultType)s Template in %(MAttributeDef*)s
	else if(data_type.equals("MAttributeDefConvertResultType")) {
	    data_value = base_type + "_to_" + "CORBA" + base_type;
	}
	// handle %(MAttributeDefConvertParameter)s Template in %(MAttributeDef*)s
	else if(data_type.equals("MAttributeDefConvertParameter")) {
	    data_value = "CORBA" + base_type + "_to_" + base_type;
	}
        return data_value;
    }


    /**
     * Overwrites the superclass method to support standard IDL2C++ mapping
     * of parameters.
     * MFactoryDef is a MOperationDef so we can use the getCORBAOperationParams()
     * method to convert the parameter list.
     * Note that the MFactoryDef templates contains the %(MParameterCORBA)s 
     * placeholder to indicate the CORBA parameter list.
     */
    protected String data_MFactoryDef(String data_type, String data_value)
    {
	System.out.println("CppRemoteGeneratorImpl.data_MFactoryDef("
			   + data_type + "," + data_value+ ")");

	if(data_type.startsWith("MParameterCORBA")) {
	    return getCORBAOperationParams((MOperationDef)current_node);
	}
	return data_value;
    }

    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function.
     *
     * @param iface the interface from which we're starting the two step
     *        operation.
     * @param operation the particular interface operation that we're filling in
     *        a template for.
     * @param container the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepVariables(MInterfaceDef iface,
                                      MOperationDef operation,
                                      MContained container)
    {
	System.out.println("CppRemoteGeneratorImpl.getTwoStepVariables()");

	// only calls the overwritten method of the base class
	//        String lang_type = super.getLanguageType(operation);
	String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

	vars.put("Object",              container.getIdentifier());
        vars.put("Identifier",          operation.getIdentifier());
	vars.put("ProvidesType",        iface.getIdentifier());
        vars.put("SupportsType",        iface.getIdentifier());
	vars.put("UsesType",            iface.getIdentifier());
        vars.put("LanguageType",        lang_type);
	vars.put("CORBAType",           getCORBALanguageType(operation));
        vars.put("MExceptionDef",       getOperationExcepts(operation));
        vars.put("MParameterDefAll",    getOperationParams(operation));
	vars.put("MParameterDefCORBA",  getCORBAOperationParams(operation));
        vars.put("MParameterDefName",   getOperationParamNames(operation));

	// for adapter generation
        vars.put("MParameterDefConvertParameter",
		 getParameterConvertParameter(operation));
        vars.put("MParameterDefConvertMethod",
                 getParameterConvertMethod(operation));
	vars.put("MParameterDefConvertResult",
		 getParameterConvertResult(operation));
	vars.put("Return", (lang_type.equals("void")) ? "" : "return ");

        return vars;
    }

    //====================================================================
    // Handle the CORBA Types
    //====================================================================

    /**
     * Map a base language type into an idenfitier suitable for function names.
     *
     */
    protected String mapBaseLanguageType(MTyped object)
    {
        MIDLType idl_type = object.getIdlType();

        if (idl_type instanceof MPrimitiveDef)
            return ((MPrimitiveDef) idl_type).getKind().toString();
        else if (idl_type instanceof MStringDef)
            return ((MStringDef) idl_type).getKind().toString();
        else if (idl_type instanceof MWstringDef)
            return ((MWstringDef) idl_type).getKind().toString();
        else if (idl_type instanceof MFixedDef)
            return ((MFixedDef) idl_type).getKind().toString();
        else if (idl_type instanceof MContained) {
            List scope = getScope((MContained) idl_type);
            scope.add(((MContained) idl_type).getIdentifier());
            return join("_", scope);
        }

        return "";
    }

    /**
     * Converts the parameter list of an operation into C++ parameters.
     *
     * TODO
     *
     */
    protected String getParameterConvertParameter(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getParameterConvertParameter()");

        MIDLType idl_type = op.getIdlType();
        String cpp_type = getBaseLanguageType(op);
        String base_type = mapBaseLanguageType(op);

        List ret = new ArrayList();

        for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); ) {
            MParameterDef p = (MParameterDef) ps.next();
	    MParameterMode direction = p.getDirection();
            String p_id = p.getIdentifier();

	    // Note that "out" parameters must not be converted to C++

            String value = "  " + cpp_type + " parameter_" + p_id;
	    if (direction != MParameterMode.PARAM_OUT)
		value +=
                    " = CCM::CORBA"+base_type+"_to_"+base_type+" ( "+p_id+" )";
            ret.add(value + ";");
	}

	return join("\n", ret) + "\n";
    }

    protected String getParameterConvertMethod(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getParameterConvertMethod()");

        String op_string = "  "+mapBaseLanguageType(op)+
            " result = local_adapter->"+op.getIdentifier();

	List ret = new ArrayList();
        for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); )
	    ret.add(" parameter_"+((MParameterDef) ps.next()).getIdentifier());

        return op_string+" ("+join(",", ret)+" );";
    }

    protected String getParameterConvertResult(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getParameterConvertResult()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType();
        String base_type = idl_type.toString();

	if (idl_type instanceof MPrimitiveDef || idl_type instanceof MStringDef) {
            List ret = new ArrayList();

	    // Note that inout and out parameter must also be converted
	    for (Iterator ps = op.getParameters().iterator(); ps.hasNext(); ) {
		MParameterDef p = (MParameterDef) ps.next();
		MParameterMode direction = p.getDirection();

		if (direction != MParameterMode.PARAM_IN) {
                    String p_id = p.getIdentifier();
                    String p_base = p.getIdlType().toString();
                    String p_cpp = getBaseLanguageType(p);
                    ret.add("  " + p_id + "= CCM::" + p_base +
                            "_to_CORBA" + p_base + "(parameter_" + p_id + ");");
		}
	    }

	    ret_string += join("\n", ret) + "\n";

	    if (! base_type.equals("void"))
		ret_string += "  return CCM::" + base_type +
                    "_to_CORBA" + base_type + "(result);";
	}

	return ret_string;
    }

    /**
     * Transforms the local C++ language type to the corresponding CORBA type.
     *
     */
    protected String getCORBALanguageType(MTyped object)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBALanguageType()");

	MIDLType idl_type = object.getIdlType();
        String base_type = getBaseLanguageType(object);

        if (CORBA_mappings.containsKey(base_type))
            base_type = (String) CORBA_mappings.get(base_type);

	// handling of operation parameter types and passing rules
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();

            String prefix = "";
            String suffix = "";

	    if (direction == MParameterMode.PARAM_IN) {
		if(!(idl_type instanceof MPrimitiveDef)) {
		    prefix = "const ";
		    // Henning/Vinoski P296 :
		    // simple IDL types are passed as IN parameter witout const 
		}
	    }
	    else if(direction == MParameterMode.PARAM_OUT) {
		if(idl_type instanceof MStringDef) {
		    return "CORBA::String_out";  // special case !!!
		}
		else {
		    suffix = "_out";
		}
	    }
	    else if(direction == MParameterMode.PARAM_INOUT) {
		suffix = "&";
	    }

            if ((idl_type instanceof MTypedefDef) ||
                (idl_type instanceof MFixedDef)) {
		suffix = "&";
	    }

            return prefix + base_type + suffix;
        }

        return base_type;
    }

    protected String getCORBAOperationParams(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBAOperationParams()");

        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
            ret.add(getCORBALanguageType(p) + " " + p.getIdentifier());
        }
        return join(", ", ret);
    }

    /**
     * Write generated code to an output file.
     * Each global template consists of two sections separated by
     * "<<<<<<<SPLIT>>>>>>>" that are written in two different files
     * node_name + "_remote.h" and node_name + "_remote.cc"
     *
     * @param template the template object to get the generated code structure
     *        from; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException
    {
	System.out.println("CppRemoteGeneratorImpl.writeOutput()");

        String out_string = template.substituteVariables(output_variables);

        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
	String[] out_file_types = { ".h", ".cc" };

	String node_name = ((MContained) current_node).getIdentifier();

        String dir_name = new String(node_name);
        if (current_node instanceof MHomeDef)
            dir_name = ((MHomeDef) current_node).getComponent().getIdentifier();

	String file_dir = handleNamespace("IncludeNamespace", dir_name);
        file_dir = file_dir.replaceAll("[^\\w]", "_");

        for (int i = 0; i < out_strings.length; i++) {
            if (out_strings[i].trim().equals("")) continue;

            String file_name = node_name + out_file_types[i];
            writeFinalizedFile(file_dir, file_name, out_strings[i]);

            // write an empty Confix Makefile.py if there's not one here.

            File confix_file = new File(output_dir, file_dir);
            confix_file = new File(confix_file, "Makefile.py");
            if (! confix_file.isFile())
                writeFinalizedFile(file_dir, "Makefile.py", "");
	}
    }
}



