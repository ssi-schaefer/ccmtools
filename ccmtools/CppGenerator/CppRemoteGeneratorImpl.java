/* CCM Tools : C++ Code Generator Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

/*====================================================================
             File structure of remote C++ prototype:

Calculator
|-- Calculator.idl
|
|-- CCM_Session_Calculator_remote
|   |-- CalculatorHome_remote.cc
|   |-- CalculatorHome_remote.h
|   |-- Calculator_remote.cc
|   |-- Calculator_remote.h
|   |-- Makefile.py             
|
|
|-- CCM_Session_Calculator_stubs  // created from IDL2 generator 
|   |-- Calculator.idl
|       => Calculator.cc            // from IDL generated   
|       => Calculator.h             // from IDL generated
|   |-- Makefile.py
|


|-- CCM_Session_Container         // Environment files
|   |-- CCMContainer.cc
|   |-- CCMContainer.h
|   |-- Makefile.py
====================================================================*/




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

	System.out.println("CppRemoteGeneratorImpl.CppRemoteGeneratorImpl()");

	// fill the CORBA_mappings with IDL to C++ Mapping types 
	String[] labels = MPrimitiveKind.getLabels();
	CORBA_mappings = new Hashtable();
	for (int i = 0; i < labels.length; i++)
	    CORBA_mappings.put(labels[i], remote_language_map[i]);
    }


    //====================================================================
    // Handle traverser events
    //====================================================================

    /**
     * Acknowledge the start of the given node during graph traversal. If the
     * node is a MContainer type and is not defined in anything, assume it's the
     * global parse container, and push "CCM_Local" onto the namespace stack,
     * indicating that this code is for local CCM components.
     *
     * @param node the node that the GraphTraverser object is about to
     *        investigate.
     * @param scope_id the full scope identifier of the node. This identifier is
     *        a string containing the names of parent nodes, joined together
     *        with double colons.
     */
    public void startNode(Object node, String scope_id)
    {
	System.out.println("CppRemoteGeneratorImpl.startNode("+scope_id+")");
        super.startNode(node, scope_id);

        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null))
            namespace.push("CCM_Remote");
    }

    /**
     * Acknowledge and process a closing node during graph traversal. If the
     * node is an MContainer type, pop the namespace (this will remove our
     * CCM_Local that we pushed, in theory (tm)). If the node is of the correct
     * type and defined in the original parsed file, write code for this node.
     *
     * @param node the node that the graph traverser object just finished
     *        investigating.
     * @param scope_id the full scope identifier of the node. This identifier is
     *        a string containing the names of ancestor nodes, joined together
     *        with double colons.
     *
     * Note: this method is only a logging wrapper for the super class method.  
     */
    public void endNode(Object node, String scope_id)
    {
	System.out.println("CppRemoteGeneratorImpl.endNode("+scope_id+")");
        super.endNode(node, scope_id);
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
	}
	else if (current_node instanceof MAttributeDef) { 
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

        if (data_type.equals("CORBAType")) {
	    data_value =  getCORBALanguageType((MTyped)current_node);
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
     * Return the C++ language mapping for the given object. 
     *
     * @param object the node object to use for type finding.
     */
    protected String getLanguageType(MTyped object)
    {
	System.out.println("CppRemoteGeneratorImpl.getLanguageType("+object+")");

        MIDLType idl_type = object.getIdlType();

        String base_type = getBaseIdlType(object);
        if (language_mappings.containsKey(base_type))
            base_type = (String) language_mappings.get(base_type);

	// handling of operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();

            String prefix = "";
            String suffix = "";

            if (direction == MParameterMode.PARAM_IN) {
		prefix = "const ";
	    }
		
            if ((idl_type instanceof MTypedefDef) ||
                (idl_type instanceof MStringDef) ||
                (idl_type instanceof MFixedDef)) {
		suffix = "&";
	    }

            return prefix + base_type + suffix;
        } 
	
	else if ((object instanceof MAliasDef) &&
		 (idl_type instanceof MTyped)) {
            return getLanguageType((MTyped) idl_type);
        } 
	// handle IDL sequence mapping
	else if (object instanceof MSequenceDef) {
            // FIXME : can we implement bounded sequences in C++ ?
            return "std::"+sequence_type+"<"+base_type+"> ";
        } 
	// handle IDL array mapping
	else if (object instanceof MArrayDef) {
            Iterator i = ((MArrayDef) object).getBounds().iterator();
            Long bound = (Long) i.next();
            String result = base_type + "[" + bound;
            while (i.hasNext()) result += "][" + (Long) i.next();
            return result + "]";
        }

        return base_type;
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
        vars.put("MParameterDefConvertParameterToCpp",  
		 getParameterConvertParameterToCpp(operation)); 
        vars.put("MParameterDefConvertMethodToCpp", 
		 getParameterConvertMethodToCpp(operation));
	vars.put("MParameterDefConvertResultToCpp", 
		 getParameterConvertResultToCpp(operation));

	if (! lang_type.equals("void")) 
	    vars.put("Return", "return ");
	else 
	    vars.put("Return", "");
	
        return vars;
    }



    //====================================================================
    // Handle the CORBA Types 
    //====================================================================
    
    protected String getParameterConvertResultToCpp(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBAOperationParams()");

	String ret_string = "";

	if(op.getIdlType() instanceof MPrimitiveDef) {
	    String base_type = (String)language_mappings.get((String)getBaseIdlType(op));
	    if(!base_type.equals("void")) {
		ret_string = "return CCM::" + base_type + "_to_CORBA" + base_type + "(result);";
	    }
	}
 
	return ret_string;
    }


    protected String getParameterConvertMethodToCpp(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBAOperationParams()");

	String ret_string = null;

	if(op.getIdlType() instanceof MPrimitiveDef) {
	    ret_string = (String)language_mappings.get((String)getBaseIdlType(op)) +
		" result = local_facet->" + op.getIdentifier() + "(";
	}
 
	List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    String base_type = (String)language_mappings.get((String)getBaseIdlType(p));
            ret.add(" parameter_" + p.getIdentifier()); 
        }

        return ret_string + join(", ", ret) + ");";
    }


    /**
     * Converts the parameter list of an operation into C++ parameters.  
     *
     * TODO
     *
     */ 
    protected String getParameterConvertParameterToCpp(MOperationDef op)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBAOperationParams()");

        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();

	    String base_type = (String)language_mappings.get((String)getBaseIdlType(p));
	    ret.add(base_type + " parameter_" + p.getIdentifier() 
		    + " = CCM::CORBA" + base_type + "_to_" + 
		    base_type + "(" +p.getIdentifier() + ");");
	}
	return join("\n", ret) + "\n";
    }
    


    /**
     * Transforms the local C++ language type to the corresponding CORBA type.
     *
     */
    protected String getCORBALanguageType(MTyped object)
    {
	System.out.println("CppRemoteGeneratorImpl.getCORBALanguageType()");

	MIDLType idl_type = object.getIdlType();

	String base_type = getBaseIdlType(object);
        if (CORBA_mappings.containsKey(base_type))
            base_type = (String) CORBA_mappings.get(base_type);
	
	// handling of operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();

            String prefix = "";
            String suffix = "";

	    // Henning/Vinoski P296
	    // simple IDL types are passed as IN parameter witout const 
            if (direction == MParameterMode.PARAM_IN) {
		if(!(idl_type instanceof MPrimitiveDef)) 
		    prefix = "const ";
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



    //====================================================================
    // Write out generated files
    //====================================================================

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
	String node_name = ((MContained) current_node).getIdentifier();
        String[] out_files = { node_name + "_remote.h",
                               node_name + "_remote.cc" };

        for (int i = 0; i < out_strings.length; i++) {
            String generated_code = out_strings[i];
	    String file_name = out_files[i];
	    String file_dir;
  	    if (current_node instanceof MComponentDef) {
		// ComponentDef node
		file_dir = handleNamespace("FileNamespace", node_name);
		if (generated_code.trim().equals("")) continue;
		writeFinalizedFile(file_dir, file_name, generated_code);
	    }
	    else if (current_node instanceof MHomeDef)  {
		// HomeDef node
		MHomeDef home = (MHomeDef)current_node;
		node_name = ((MContained)home.getComponent()).getIdentifier();
		file_dir = handleNamespace("FileNamespace", node_name);
		if (generated_code.trim().equals("")) continue;
		writeFinalizedFile(file_dir, file_name, generated_code);
	    }
        }
    }



    /**
     * Finalize the output files. This function's implementation writes two
     * global files, a global Remote value conversion header, and a global
     * user_types.h file based on the individual <file>_user_types.h files.
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files)
    {
	System.out.println("CppRemoteGeneratorImpl.finalize()");

	//...
    }
}



