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

import ccmtools.utils.Debug;
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
import java.util.Collection;
import java.util.Collections;

/**
 * Remote C++ component generator
 * 
 *
 */
public class CppRemoteGeneratorImpl
    extends CppGenerator
{
    //====================================================================
    // Definition of arrays that determine the generator's behavior 
    //====================================================================

    private Map CORBA_mappings;

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

        new File("remoteComponents", "CCM.idl"),
	new File("remoteComponents", "CCM.h"),
	new File("remoteComponents", "CCM.cc"),
        new File("remoteComponents", "Makefile.py")
    };

    /**
     * Template files:
     * Defines the templates for the files in local_environment_files.
     *
     * Note: order and length of both array must be the same.
     *
     * TODOs: 
     * - The ComponentsHeader and the ComponentsImpl files are
     *   generated from ComponentsIdl using the mico (2.3.10) idl compiler.
     *   The idl compiler call should be done from the ccmtools while
     *   creating the environment.
     * - The CCM.idl file should be installed with the environment's
     *   header files => ccm-install/include/CCM.idl
     */
    private final static String[] local_environment_templates =
    {
        "CCMContainerHeader",     // Template for CCMContainer.h
	"CCMContainerImpl",       // Template for CCMContainer.cc
	"Blank",                  // Template for Makefile.py

	"ComponentsIdl",          // Template for Components.idl
	"ComponentsHeader",       // (mico 2.3.10 specific) !!!
	"ComponentsImpl",         // (mico 2.3.10 specific) !!!
	"MakefilePy"              // Template for Makefile.py
    };

    /**
     * Language type mapping:
     * Defines the IDL to C++ Mappings for primitive types.
     *
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
    

    

    /**
     * The generator constructor calls the constructor of the base class
     * and sets up the map for the CORBA to C++ mappings.
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

	Debug.setDebugLevel(Debug.NONE);
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.CppRemoteGeneratorImpl()");

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
        super.startNode(node, scope_id);

	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.startNode()");

        if ((node instanceof MContainer) 
	    && (((MContainer) node).getDefinedIn() == null)) {
            namespace.push("CCM_Remote");
	}
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
        super.endNode(node, scope_id);

	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.endNode()");
    }
    


    //====================================================================
    // Code generator core functions
    //====================================================================


    /**
     * Overwrites the CppGenerator's method to change between CCM_Local
     * CCM_Remote.
     */ 
    protected String handleNamespace(String data_type, String local)
    {
	Debug.println(Debug.METHODS,"CppRemoteGenerator.handleNamespace()");

        List names = new ArrayList(namespace);
	
	// ShortNamespace corresponds with the module hierarchy in the IDL file,
	// there is no CCM_Local, CCM_Remote or CCM_Session_ included.
	if (data_type.equals("ShortNamespace")) {
	    if(names.size() > 2)
		return join("::", slice(names, 2));
	    else
		return "";
        } 

	if (!local.equals("")) { 
	    names.add("CCM_Session_" + local); 
	}

	if (data_type.equals("Namespace")) {
            return join("::", slice(names, 2));
        } 
	else if (data_type.equals("FileNamespace")) {
            return join("_", slice(names, 1));
        } 
	else if (data_type.equals("IncludeNamespace")) {
            return join("/", slice(names, 2));
        } 
	else if (data_type.equals("UsingNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("using namespace "+i.next()+";\n");
            return join("", tmp);
        } 
	else if (data_type.equals("OpenNamespace")) {
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("namespace "+i.next()+" {\n");
            return join("", slice(tmp,1));
        } 
	else if (data_type.equals("CloseNamespace")) {
            Collections.reverse(names);
            List tmp = new ArrayList();
            for (Iterator i = names.iterator(); i.hasNext(); )
                tmp.add("} // /namespace "+i.next()+"\n");
	    return join("", slice(tmp,-1));
        }
        return "";
    }


    /** 
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This version of the function fills in
     * operation information from the given interface.
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
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getTwoStepVariables()");

	String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();
 
	vars.put("Object",              container.getIdentifier());
        vars.put("Identifier",          operation.getIdentifier());
        vars.put("LanguageType",        lang_type);
	vars.put("CORBAType",           getCORBALanguageType(operation));
        vars.put("MExceptionDef",       getOperationExcepts(operation));
        vars.put("MParameterDefAll",    getOperationParams(operation));
	vars.put("MParameterDefCORBA",  getCORBAOperationParams(operation));
        vars.put("MParameterDefName",   getOperationParamNames(operation));
        
	// Used for supports and facet adapter generation 
        vars.put("MParameterDefConvertParameter" , convertParameterToCpp(operation)); 
	vars.put("MParameterDefDeclareResult"    , declareCppResult(operation));
        vars.put("MParameterDefConvertMethod"    , convertMethodToCpp(operation));
	vars.put("MParameterDefConvertResult"    , convertResultToCorba(operation));
	vars.put("MParameterDefConvertExceptions", convertExceptionsToCorba(operation));
	vars.put("MParameterDefConvertParameterFromCppToCorba", convertParameterToCorba(operation));

	// Used for receptacle adapter generation 
	vars.put("MParameterDefConvertReceptacleParameterToCorba",
		 convertReceptacleParameterToCorba(operation)); 
	vars.put("MParameterDefDeclareReceptacleCorbaResult",
		 declareReceptacleCorbaResult(operation));
	vars.put("MParameterDefConvertReceptacleMethodToCorba"   ,
		 convertReceptacleMethodToCorba(operation, container.getIdentifier()));
	vars.put("MParameterDefConvertReceptacleResultToCpp",
		 convertReceptacleResultToCpp(operation));
	vars.put("MParameterDefConvertReceptacleExceptionsToCpp",
		 convertReceptacleExceptionsToCpp(operation));

	if (! lang_type.equals("void")) 
	    vars.put("Return", "return ");
	else 
	    vars.put("Return", "");
	
        return vars;
    }


    /**
     * Handles the different template names found for a particular model node
     * and returns the generated code.
     * The current model node is represented in the current_node variable.
     * The string parameter contains the name of the found template
     *
     * Note that the method calls the super class method.
     *
     * @param variable The variable name (tag name) to get a value 
     *                 (generated code) for.
     *
     * @return The value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getLocalValue()"); 

        String value = super.getLocalValue(variable);

	if (current_node instanceof MAttributeDef) { 
            return data_MAttributeDef(variable, value);
	}

	Debug.println(Debug.VALUES,value);
	return value;
    }
    

    protected String data_MUsesDef(String data_type, String data_value)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.data_MUsesDef()");

        if (data_type.startsWith("MOperation")) {
            MUsesDef uses = (MUsesDef) current_node;
            return "";//fillTwoStepTemplates(uses.getUses(), data_type);!!!!!!!!!!!!!!
        }
        return data_value;
    }


    protected String data_MAttributeDef(String data_type, String data_value)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.data_MAttributeDef()"); 

	// current_node is MAttributeDef
	MTyped object = (MTyped)current_node;
	MIDLType idl_type = object.getIdlType(); 
	String base_type = getBaseIdlType(object);

	// Handle %(CORBAType)s tag in %(MAttributeDef*)s templates
        if (data_type.equals("CORBAType")) {
	    data_value =  getCORBALanguageType((MTyped)current_node);
        }
	// Handle %(MAttributeDefConvertResultType)s tag in %(MAttributeDef*)s 
	// templates
	else if(data_type.equals("MAttributeDefConvertResultType")) {
	    data_value = base_type + "_to_" + "CORBA" + base_type;
	}
	// Handle %(MAttributeDefConvertParameter)s tag in %(MAttributeDef*)s
	// templates
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
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.data_MFactoryDef()");

	if(data_type.startsWith("MParameterCORBA")) {
	    return getCORBAOperationParams((MOperationDef)current_node);
	}
	return data_value; // super.data_MFactoryDef() wegen , am Ende
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
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.writeOutput()");
	
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
	String[] out_file_types = { "_remote.h", "_remote.cc" };

        for (int i = 0; i < out_strings.length; i++) {
	    // If the out_string is empty, skip the file creation
	    if (out_strings[i].trim().equals("")) 
		continue;

	    // If the current node is a ComponentDef, create the component's files
  	    if (current_node instanceof MComponentDef) {
		String component_name = ((MContained) current_node).getIdentifier();
		String file_dir = handleNamespace("FileNamespace", component_name);

		writeFinalizedFile(file_dir + "_remote",  
				   component_name + out_file_types[i], 
				   out_strings[i]);
	    }
	    // If the current node is a HomeDef, create the home's files
	    else if (current_node instanceof MHomeDef)  {

		MHomeDef home = (MHomeDef)current_node;
		String component_name = ((MContained)home.getComponent()).getIdentifier();  
		String home_name = home.getIdentifier();
		String file_dir = handleNamespace("FileNamespace", component_name);

		writeFinalizedFile(file_dir + "_remote", 
				   home_name + out_file_types[i], 
				   out_strings[i]);

		// generate an empty Makefile.py in the CCM_Session_*_remote 
		// directory - needed by Confix
		writeFinalizedFile(file_dir + "_remote","Makefile.py","");
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
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.finalize()");
	// do nothing...
    }





    //====================================================================
    // Handle the C++ data types 
    //====================================================================

    /**
     * Converts the CCM model type information (MTyped) to the corresponding 
     * local C++ types. If the MTyped type is used as an operation parameter,
     * the local C++ parameter passing rules take place.
     *
     * @param object Reference to a MTyped element of the CCM model.
     * @return Generated code for the local C++ type as string.
     */
    protected String getLanguageType(MTyped object)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getLanguageType()"); 

        MIDLType idl_type = object.getIdlType();
        String base_type = getBaseIdlType(object);
	String cpp_type;
        if (language_mappings.containsKey(base_type)) {
	    // Primitive data types are mapped via map.
            cpp_type = (String) language_mappings.get(base_type);
	}
	else {
	    // Not primitive types are mapped as they are
	    cpp_type = base_type;
	}

	// Handle operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();
            String prefix = "";
            String suffix = "";

            if (direction == MParameterMode.PARAM_IN) {
		// in
		prefix = "const ";
		if ((idl_type instanceof MTypedefDef)
		    || (idl_type instanceof MStringDef)
		    || (idl_type instanceof MFixedDef)) {
		    suffix = "&";
		}
	    }
	    else { 
		// inout, out
		prefix = "";
		suffix = "&";
	    }

	    return prefix + cpp_type + suffix;
        } 
	
	else if ((object instanceof MAliasDef) &&
		 (idl_type instanceof MTyped)) {
            return getLanguageType((MTyped) idl_type);
        } 

	// Handle IDL sequence mapping
	else if (object instanceof MSequenceDef) {
            // FIXME : can we implement bounded sequences in C++ ?
	    //            return "std::"+sequence_type+"<"+base_type+"> ";
            return "std::" + sequence_type + "<" + cpp_type + "> ";
        } 

	// Handle IDL array mapping
	else if (object instanceof MArrayDef) {
            Iterator i = ((MArrayDef) object).getBounds().iterator();
            Long bound = (Long) i.next();
	    //            String result = base_type + "[" + bound;
            String result = cpp_type + "[" + bound;
            while (i.hasNext()) result += "][" + (Long) i.next();
            return result + "]";
        }

        return cpp_type;
    }


    //====================================================================
    // Handle the CORBA data types 
    //====================================================================

    /**
     * Converts the CCM model type information (MTyped) to the corresponding 
     * CORBA types. If the MTyped type is used as an operation parameter,
     * the CORBA to C++ mapping parameter passing rules take place.
     *
     * @param object Reference to a MTyped element of the CCM model.
     * @return Generated code for the CORBA type as string.
     */
    protected String getCORBALanguageType(MTyped object)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getCORBALanguageType()"); 

	MIDLType idl_type = object.getIdlType();
	String base_type = getBaseIdlType(object);
	String corba_type;

        if (CORBA_mappings.containsKey(base_type)) {
	    // Primitive data types are mapped via map.
	    corba_type = (String) CORBA_mappings.get(base_type);
	}
	else {
	    corba_type = base_type; 
	}

	// Handle operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();
            String prefix = "";
            String suffix = "";

	    if (direction == MParameterMode.PARAM_IN) {
		if(!(idl_type instanceof MPrimitiveDef)) {
		    // Simple IDL types are passed as IN parameter witout const 
		    prefix = "const ";
		}
	    }
	    else if(direction == MParameterMode.PARAM_OUT) {
		if(idl_type instanceof MStringDef) {
		     // OUT string is a special case in the CORBA to C++ mapping!
		    return "CORBA::String_out"; 
		}
		else {
		    return corba_type + "_out";
		}
	    }
	    else if(direction == MParameterMode.PARAM_INOUT) {
		suffix = "&";
	    }
		
            if ((idl_type instanceof MTypedefDef) ||
                (idl_type instanceof MFixedDef)) {
		suffix = "&";
	    }
	    return prefix + corba_type + suffix;
        }
	else if(object instanceof MOperationDef) { 
	    // Handle operation return types
	    if(idl_type instanceof MStructDef ||
	       idl_type instanceof MAliasDef) {
		return corba_type + "*";
	    } 
	}

	return corba_type;
    }



    //====================================================================
    // Handle the CORBA to C++ adption on operation level
    //====================================================================

    /**
     * Creates the code that describes the parameter list of the operation
     * using CORBA data types.
     *
     * The %(MParameterDefCORBA)s  tag forces a call to this method
     * via getTwoStepVariables().
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String getCORBAOperationParams(MOperationDef op)
    {
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.getCORBAOperationParams()"); 

        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
            ret.add(getCORBALanguageType(p) + " " + p.getIdentifier());
        }
        return join(", ", ret);
    }


    /**
     * Creates the code that declares the exceptions to the given operation
     * in CORBA style.
     * Note that every CORBA operation can throw the CORBA::SystemException.
     *
     * The %(MExceptionDef)s tag foreces a call to this method via
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String getOperationExcepts(MOperationDef op)
    {
	Debug.println(Debug.METHODS,"CppRemoteGenerator.getOperationExcepts()"); 
	
        List ret = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext(); )
            ret.add(((MExceptionDef) es.next()).getIdentifier());
	
        if (ret.size() > 0) 
	    return "throw(CORBA::SystemException, " + join(", ", ret) + " )";
        else                
	    return "";
    }


    /**
     * Creates the code that converts the CORBA parameters to local C++ types.
     * Note that only the in and inout parameters are converted.
     *
     * The %(MParameterDefConvertParameter)s tag forces a call to this method
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */ 
    protected String convertParameterToCpp(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.getParameterConvertParameter()");

        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    MIDLType idl_type = ((MTyped)p).getIdlType();

	    if(idl_type instanceof MPrimitiveDef 
	       || idl_type instanceof MStringDef) {
		ret.add(convertPrimitiveParameterFromCorbaToCpp(p));
	    }
	    else if(idl_type instanceof MStructDef) { 
		MTypedefDef idl_typedef = (MTypedefDef)idl_type;
		MStructDef idl_struct = (MStructDef)idl_typedef;
		ret.add(convertStructParameterFromCorbaToCpp(p, idl_struct, false));
	    }
	    else if(idl_type instanceof MAliasDef) {
		MAliasDef alias = (MAliasDef)idl_type;
		if(alias.getIdlType() instanceof MSequenceDef) {
		    ret.add(convertSequenceParameterFromCorbaToCpp(p, alias));
		}
		else {
		    // all other alias types
		    // TODO
		    return "// unhandled idl alias type in convertParameterToCpp()";
		}
	    }
	    else { 
		// all other idl types
		// TODO
		return "// unhandled idl type in convertParameterToCpp()";
	    }
	}
	return join("\n", ret) + "\n";
    }

    

    protected String convertPrimitiveParameterFromCorbaToCpp(MParameterDef p)
    {
	MParameterMode direction = p.getDirection();
	String base_type = getBaseIdlType(p);
	String cpp_type = (String)language_mappings.get(base_type);

        List ret = new ArrayList();
	ret.add("  " 
		+ cpp_type + " parameter_" 
		+ p.getIdentifier() + ";"); 
	
	if(direction != MParameterMode.PARAM_OUT) {
	    ret.add("  " + "parameter_" + p.getIdentifier()
		    + " = CCM::CORBA" + base_type + "_to_" + 
		    base_type + "(" +p.getIdentifier() + ");");  
	}
	return join("\n", ret) + "\n";
    }

    protected String convertStructParameterFromCorbaToCpp(MParameterDef p, 
							  MStructDef idl_struct,
							  boolean isSequenceItem) 
    {
	MParameterMode direction = p.getDirection();
	
	String item ="";
	String index = "";
	String indent = "";
	if(isSequenceItem) {
	    item = "_item";
	    index = "[i]";
	    indent = "  ";
	}

	List ret = new ArrayList();
	ret.add(indent + "  CCM_Local::" 
		+ idl_struct.getIdentifier() + " parameter_" 
		+ p.getIdentifier() + item +";"); 
	
	if(direction != MParameterMode.PARAM_OUT) {
	    for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
		MFieldDef member = (MFieldDef)members.next();
		MIDLType member_idl = ((MTyped)member).getIdlType();
		String base_type = getBaseIdlType((MTyped)member);

		if(member_idl instanceof MPrimitiveDef 
		   || member_idl instanceof MStringDef) {
		    ret.add(indent + "  " 
			    + "parameter_" + p.getIdentifier() + item + "." 
			    + member.getIdentifier() + " = CCM::CORBA" 
			    + base_type + "_to_" + base_type + "(" 
			    + p.getIdentifier() + index  + "." 
			    + member.getIdentifier() + ");");
		}
		else { 
		    // all other idl_types
		    // TODO
		    ret.add(indent + "  // unhandled idl type in convertStructFromCorbaToCpp()");
		} 
	    }    
	}
	return join("\n", ret) + "\n";
    }

    protected String convertSequenceParameterFromCorbaToCpp(MParameterDef p, 
							    MAliasDef alias) 
    {
	MSequenceDef idl_sequence = (MSequenceDef)alias.getIdlType();
	MIDLType sequence_type = ((MTyped)idl_sequence).getIdlType();
	String base_type = getBaseIdlType((MTyped)idl_sequence);
	List ret = new ArrayList();

	if(sequence_type instanceof MPrimitiveDef
	   || sequence_type instanceof MStringDef) {
	    ret.add("  CCM_Local::" + alias.getIdentifier() + " parameter_"
		    + p.getIdentifier() + ";");

	    if(p.getDirection() != MParameterMode.PARAM_OUT) {	
		ret.add("  for(unsigned long i=0; i< " + p.getIdentifier() + ".length(); i++) {");
		ret.add("    parameter_" + p.getIdentifier() 
			+ ".push_back(CCM::CORBA" + base_type + "_to_" + base_type 
			+ "(" + p.getIdentifier() + "[i]));");
		ret.add("  }");
	    }
	}
	else if(sequence_type instanceof MStructDef) {
	    MStructDef idl_struct = (MStructDef)sequence_type;

	    ret.add("  CCM_Local::" + alias.getIdentifier() + " parameter_"
		    + p.getIdentifier() + ";");

	    if(p.getDirection() != MParameterMode.PARAM_OUT) {	
		ret.add("  for(unsigned long i=0; i< " + p.getIdentifier() + ".length(); i++) {");
		ret.add(convertStructParameterFromCorbaToCpp(p,idl_struct, true)); 
		ret.add("    parameter_" + p.getIdentifier() 
			+ ".push_back(parameter_" + p.getIdentifier() + "_item);");
		ret.add("  }");
	    }
	}
	else {
	    // all other idl types
	    // TODO
	    return "// unhandled idl type in convertSequenceParameterFromCorbaToCpp()";
	}
	return join("\n", ret);
    }
    
    /**
     * Create the code that declares the variable (C++ type and name) in which the 
     * result value will be stored.
     *
     * The %(MParameterDefDeclareResult)s tag forces a call to this method
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String declareCppResult(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.getParameterDeclareResult()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType(); 

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = "  " + (String)language_mappings.get((String)getBaseIdlType(op)) +
		         " result;";
	}
	else if(idl_type instanceof MStructDef) { 
	    MTypedefDef idl_typedef = (MTypedefDef)idl_type;
	    MStructDef idl_struct = (MStructDef)idl_typedef;
	    ret_string = "  CCM_Local::" + idl_struct.getIdentifier() + " result;"; 
	}
	else if(idl_type instanceof MAliasDef) {
	    MAliasDef alias = (MAliasDef)idl_type;
	    if(alias.getIdlType() instanceof MSequenceDef) {
		ret_string = "  CCM_Local::" + alias.getIdentifier() + " result;";
	    }
	    else {
		// all other idl alias types
		// TODO
	    return "// unhandled idl alias type in declareCppResult()";
	    }
	}
	else {  
	    // all other idl_types
	    // TODO
	    ret_string = "// unhandled idl type in declareCppResult()";
	}
	return ret_string;
    }


    /**
     * Create the code that makes the local method call, with all of the
     * local parameters.
     * Note that the local method must be part of the object local_adapter 
     * points to.
     *
     * The %(MParameterDefConvertMethod)s tag forces a call to this method
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertMethodToCpp(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.getParameterConvertMethod()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType(); 

	if(idl_type instanceof MPrimitiveDef 
	   || idl_type instanceof MStringDef
	   || idl_type instanceof MStructDef
	   || idl_type instanceof MAliasDef) {
	    ret_string = "  " + "  result = local_adapter->" 
		+ op.getIdentifier() + "(";
	    
	    List ret = new ArrayList(); 
	    for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
		MParameterDef p = (MParameterDef) params.next();
		String base_type = (String)language_mappings.get((String)getBaseIdlType(p));
		ret.add(" parameter_" + p.getIdentifier()); 
	    }
	    return ret_string + join(", ", ret) + ");";
	}
	else {  
	    // all other idl_types
	    // TODO
	    return "// unhandled idl type in convertMethodToCpp()";
	}
    }




    /**
     * Create the code for the remote facet and supports adapter.
     *
     * The %(MParameterDefConvertParameterFromCppToCorba)s in the template
     * %(MOperstaionFacetAdapterImpl)s tag forces a call to this method
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     **/
    protected String convertParameterToCorba(MOperationDef op)
    {
	List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    MIDLType idl_type = ((MTyped)p).getIdlType();
	    
	    if(idl_type instanceof MPrimitiveDef 
	       || idl_type instanceof MStringDef) {
		ret.add(convertPrimitiveParameterFromCppToCorba(p));
	    }
	    else if(idl_type instanceof MStructDef) { 
		MTypedefDef idl_typedef = (MTypedefDef)idl_type;
		MStructDef idl_struct = (MStructDef)idl_typedef;
		ret.add(convertStructParameterFromCppToCorba(p, idl_struct));
	    }
	    else if(idl_type instanceof MAliasDef) {
		MAliasDef alias = (MAliasDef)idl_type;
		if(alias.getIdlType() instanceof MSequenceDef) {
		    ret.add(convertSequenceParameterFromCppToCorba(p, alias));
		}
		else {
		    // all other alias types
		    // TODO
		    return "// unhandled idl alias type in convertParameterToCorba()";
		}
	    }
	    else { 
		
		// all other idl types
		// TODO
		return "// unhandled idl type in convertParameterToCorba()";
	    }
	}
	return join("\n", ret) + "\n";
    }

    protected String convertPrimitiveParameterFromCppToCorba(MParameterDef p)
    {
	List ret = new ArrayList();
	MParameterMode direction = p.getDirection();
	if(direction != MParameterMode.PARAM_IN) {
	    String parameter_base_type = getBaseIdlType(p);
	    String parameter_cpp_type = (String)language_mappings.get(parameter_base_type);
	    ret.add("  " 
		    + p.getIdentifier() 
		    + " = CCM::" + parameter_base_type 
		    + "_to_CORBA" + parameter_base_type 
		    + "(parameter_" + 
		    p.getIdentifier() + ");"); 
	}
	return join("\n", ret);
    }

    protected String convertStructParameterFromCppToCorba(MParameterDef p, 
							  MStructDef idl_struct)
    {
	MParameterMode direction = p.getDirection();
	String ItemAccess="";
	
	List ret = new ArrayList();
	if(direction == MParameterMode.PARAM_IN) {
	    return "";
	}
	else if(direction == MParameterMode.PARAM_OUT) {
	    ret.add("  " + p.getIdentifier() + " = new " + idl_struct.getIdentifier() + ";");
	    ItemAccess = "->";
	}
	else if(direction == MParameterMode.PARAM_INOUT) {
	    ItemAccess = ".";
	}
	
	for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
	    MFieldDef member = (MFieldDef)members.next();
	    MIDLType member_idl = ((MTyped)member).getIdlType();
	    String base_type = getBaseIdlType((MTyped)member);
	    
	    if(member_idl instanceof MPrimitiveDef 
	       || member_idl instanceof MStringDef) {
		ret.add("  " 
			+ p.getIdentifier() + ItemAccess + member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(parameter_" + p.getIdentifier() + "." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructParameterFromCppToCorba()");
	    } 
	}    
	return join("\n", ret);
    }

    protected String convertStructParameterFromCppToCorba2(MParameterDef p,
							   MStructDef idl_struct)
    {
	MParameterMode direction = p.getDirection();
	List ret = new ArrayList();

	for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
	    MFieldDef member = (MFieldDef)members.next();
	    MIDLType member_idl = ((MTyped)member).getIdlType();
	    String base_type = getBaseIdlType((MTyped)member);
	
	    String corba_parameter ="";
	    String cpp_parameter = "";

	    if(direction == MParameterMode.PARAM_IN) {
		return "";
	    }
	    else if(direction == MParameterMode.PARAM_OUT) {
		corba_parameter = "(*" +  p.getIdentifier() + ")[i]";
		cpp_parameter = "parameter_" + p.getIdentifier() + "[i]";
	    }
	    else {
		corba_parameter = p.getIdentifier() + "[i]";
		cpp_parameter = "parameter_" + p.getIdentifier() + "[i]";
	    }	   

	    if(member_idl instanceof MPrimitiveDef 
		       || member_idl instanceof MStringDef) {
		ret.add("  " 
			+ corba_parameter + "." +  member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(" + cpp_parameter + "." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructParameterFromCppToCorba2()");
	    } 
	}    
	return join("\n", ret);
    }

    protected String convertSequenceParameterFromCppToCorba(MParameterDef p, 
							    MAliasDef alias) 
    {
	MSequenceDef idl_sequence = (MSequenceDef)alias.getIdlType();
	MIDLType sequence_type = ((MTyped)idl_sequence).getIdlType();
	String base_type = getBaseIdlType((MTyped)idl_sequence);
	List ret = new ArrayList();
	
	if(p.getDirection() == MParameterMode.PARAM_IN) {
	    return ""; 
	}
	
	if(sequence_type instanceof MPrimitiveDef
	   || sequence_type instanceof MStringDef) {
	    if(p.getDirection() == MParameterMode.PARAM_OUT) {	
		ret.add("  " + p.getIdentifier() + " = new " + alias.getIdentifier() + ";"); 
		ret.add("  " + p.getIdentifier() + "->length(parameter_" 
			+ p.getIdentifier() + ".size());");
	        ret.add("  for(unsigned long i=0; i< parameter_" + p.getIdentifier() 
			+ ".size(); i++) {");
		ret.add("    (*" +  p.getIdentifier() + ")[i] = CCM::" 
			+ base_type + "_to_CORBA" + base_type + "(parameter_" + p.getIdentifier()
			+ "[i]);"); 
		ret.add("  }");
	    }
	    else {
		ret.add("  " + p.getIdentifier() + ".length(parameter_" 
			+ p.getIdentifier() + ".size());");
		ret.add("  for(unsigned long i=0; i< parameter_" + p.getIdentifier() 
			+ ".size(); i++) {");
		ret.add("    " +  p.getIdentifier() + "[i] = CCM::" 
			+ base_type + "_to_CORBA" + base_type + "(parameter_" + p.getIdentifier()
			+ "[i]);"); 
		ret.add("  }");
	    }
	}
	else if(sequence_type instanceof MStructDef) {
	    MStructDef idl_struct = (MStructDef)sequence_type;
	    String item_access = "."; 
	    if(p.getDirection() == MParameterMode.PARAM_OUT) {	
		ret.add("  " + p.getIdentifier() + " = new " + alias.getIdentifier() + ";"); 
		item_access = "->";
	    }
	    ret.add("  " + p.getIdentifier() + item_access + "length(parameter_" 
		    + p.getIdentifier() + ".size());");
	    
	    ret.add("  for(unsigned long i=0; i< parameter_" + p.getIdentifier() + ".size(); i++) {");
	    ret.add(convertStructParameterFromCppToCorba2(p,idl_struct)); 
	    ret.add("  }");
	}
	else {
	    // all other idl types
	    // TODO
	    return "// unhandled idl type in convertSequenceParameterFromCppToCorba()";
	}
	return join("\n", ret) + "\n";
    }
    



    /**
     * Create the code that converts the result value as well as the inout 
     * and out parameters from local C++ to CORBA types.
     *
     * The  %(MParameterDefConvertResult)s tag forces a call to this method
     * via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertResultToCorba(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.getParameterConvertResult()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType();

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = convertPrimitiveResultFromCppToCorba(op);
	}
	else if(idl_type instanceof MStructDef) { 
	    MTypedefDef idl_typedef = (MTypedefDef)idl_type;
	    MStructDef idl_struct = (MStructDef)idl_typedef;
	    ret_string = convertStructResultFromCppToCorba(idl_struct);
	}
	else if(idl_type instanceof MAliasDef) {
	    MAliasDef alias = (MAliasDef)idl_type;
	    if(alias.getIdlType() instanceof MSequenceDef) {
		ret_string = convertSequenceResultFromCppToCorba(alias);
	    }
	    else {
		// all other alias types
		// TODO
		return "// unhandled idl alias type in convertResultToCorba()";
	    }
	}
	else { 
	    // all other idl_types
	    // TODO
	    ret_string = "// unhandled idl type in convertResultToCorba()";
	}
	return ret_string;
    }

    protected String convertPrimitiveResultFromCppToCorba(MOperationDef op) 
    {
	String base_type = getBaseIdlType(op);
	String ret_string = "";

	// Convert the result iff the result type is not void
	if(!base_type.equals("void")) {
	    ret_string = "  return CCM::" + base_type 
		+ "_to_CORBA" + base_type + "(result);";
	}
	return ret_string;
    }

    protected String convertStructResultFromCppToCorba(MStructDef idl_struct) 
    {
	String ret_string = "";
	List ret = new ArrayList();
	ret.add("  " 
		+ idl_struct.getIdentifier() + "_var return_value = new " 
		+ idl_struct.getIdentifier() + ";"); 
	
	for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
	    MFieldDef member = (MFieldDef)members.next();
	    MIDLType member_idl = ((MTyped)member).getIdlType();
	    String base_type = getBaseIdlType((MTyped)member);
	    
	    if(member_idl instanceof MPrimitiveDef 
	       || member_idl instanceof MStringDef) {
		ret.add("  " 
			+ "return_value->" + member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(result." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructResultFromCorbaToCpp()");
	    } 
	}    
	ret.add("  return return_value._retn();");
	return join("\n", ret);	
    }

    protected String convertStructResultFromCppToCorba2(MStructDef idl_struct) 
    {
	String ret_string = "";
	List ret = new ArrayList();
	for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
	    MFieldDef member = (MFieldDef)members.next();
	    MIDLType member_idl = ((MTyped)member).getIdlType();
	    String base_type = getBaseIdlType((MTyped)member);
	    
	    if(member_idl instanceof MPrimitiveDef 
	       || member_idl instanceof MStringDef) {
		ret.add("    " 
			+ "(*return_value)[i]." + member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(result[i]." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructResultFromCppToCorba2()");
	    } 
	}    
	return join("\n", ret);	
    }

    protected String convertSequenceResultFromCppToCorba(MAliasDef alias) 
    {
	MSequenceDef idl_sequence = (MSequenceDef)alias.getIdlType();
	MIDLType sequence_type = ((MTyped)idl_sequence).getIdlType();
	String base_type = getBaseIdlType((MTyped)idl_sequence);
	List ret = new ArrayList();

	if(sequence_type instanceof MPrimitiveDef
	   || sequence_type instanceof MStringDef) {
    	    ret.add("  " + alias.getIdentifier() + "_var return_value = new " 
		    + alias.getIdentifier() + ";"); 
	    ret.add("  return_value->length(result.size());");
	    
	    ret.add("  for(unsigned long i=0; i< result.size(); i++) {");

	    ret.add("    (*return_value)[i] = CCM::" + base_type 
		    + "_to_CORBA" + base_type + "(result[i]);"); 
	    ret.add("  }");
	    ret.add("  return return_value._retn();");	    
	}
	else if(sequence_type instanceof MStructDef) {
	    MStructDef idl_struct = (MStructDef)sequence_type;
	    
	    ret.add("  " + alias.getIdentifier() + "_var return_value = new " 
		    + alias.getIdentifier() + ";"); 
	    ret.add("  return_value->length(result.size());");
	    
	    ret.add("  for(unsigned long i=0; i< result.size(); i++) {");
	    ret.add(convertStructResultFromCppToCorba2(idl_struct)); 
	    ret.add("  }");
	    ret.add("  return return_value._retn();");
	}
	else {
	    // all other idl types
	    // TODO
	    return "// unhandled idl type in convertSequenceResultFromCppToCorba()";
	}
	return join("\n", ret);
    }




    /**
     * Creates the code that converts the exception list of an operation into 
     * catch statements for local exceptions that throw corresponding remote 
     * exceptions.  
     * 
     * The template contains the following structure:
     *    try {
     *    //...
     *    }
     *    %(MParameterDefConvertExceptions)s
     *    catch(...) {
     *      throw CORBA::SystemException();	
     *    } 
     * The %(MParameterDefConvertExceptions)s tag forces a call to this method
     * via getTwoStepVariables(). 
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */ 
    protected String convertExceptionsToCorba(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.getParameterConvertExceptions()");

	List ret = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext(); ) {
	    String exception_name = ((MExceptionDef) es.next()).getIdentifier(); 
            ret.add("  catch(const CCM_Local::" 
		    + exception_name 
		    + "&) { \n    throw " 
		    + exception_name 
		    + "();\n  }");
	}
	return join("\n", ret);
    }







    /**
     * Creates code that converts the local C++ parameters to CORBA types.
     * Note that only the in and inout parameters are converted.
     *
     * The %(MParameterDefConvertReceptacleParameterToCorba)s tag forces a 
     * call to this method via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertReceptacleParameterToCorba(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.convertParameterToCorba()");

        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    MParameterMode direction = p.getDirection();
	    MIDLType idl_type = ((MTyped)p).getIdlType();
	    String base_type = getBaseIdlType(p);
	    String corba_type = (String)CORBA_mappings.get(base_type);

	    // Note that the out parameters must not be converted to C++
	    if(direction == MParameterMode.PARAM_OUT) {
		ret.add("  " 
			+ corba_type + " parameter_" 
			+ p.getIdentifier() + ";"); 
	    }
	    else { 
		ret.add("  " 
			+ corba_type + " parameter_" 
			+ p.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA"
			+ base_type + "(" +p.getIdentifier() + ");");  
	    }
	}
	return join("\n", ret) + "\n";
    }


    /**
     * Create the code that declases the variable (CORBA type and name) in 
     * which the result value will be stored.
     *
     * The %(MParameterDefDeclareReceptacleCorbaResult)s tag forces a call to 
     * this method via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String declareReceptacleCorbaResult(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.declareCorbaResult()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType(); 

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = "  " + (String)CORBA_mappings.get((String)getBaseIdlType(op)) +
		         " result;";
	}
	return ret_string;
    }


    /**
     * Create the code that makes to remote method call, with all of the 
     * CORBA parameters.
     *
     * The %(MParameterDefConvertReceptacleMethodToCorba)s tag forces a call 
     * to this method via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertReceptacleMethodToCorba(MOperationDef op, String receptacleName)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.convertMethodToCorba()");

	String ret_string = "";
	MIDLType idl_type = op.getIdlType(); 

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = "  " 
		+ "result = component_adapter->get_connection_" 
		+ receptacleName + "()->" 
		+ op.getIdentifier() + "(";
	}
 
	List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    String base_type = (String)CORBA_mappings.get((String)getBaseIdlType(p));
	    ret.add(" parameter_" + p.getIdentifier()); 
        }
        return ret_string + join(", ", ret) + ");";
    }


    /**
     * Create the code that converts the result value as well as the inout
     * and out parameters from CORBA to local C++ types.
     *
     * The  %(MParameterDefConvertReceptacleResultToCpp)s tag forces a call 
     * to this method via getTwoStepVariables().
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertReceptacleResultToCpp(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.convertResultToCpp()");
	
	String ret_string = "";
	MIDLType idl_type = op.getIdlType();

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    String base_type = getBaseIdlType(op);
	    List ret = new ArrayList();
	    // Convert the inout and out parameters 
	    for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
		MParameterDef p = (MParameterDef) params.next();
		MParameterMode direction = p.getDirection();
		if(direction != MParameterMode.PARAM_IN) {
		    String parameter_base_type = getBaseIdlType(p);
		    ret.add("  " 
			    + p.getIdentifier() 
			    + "= CCM::CORBA" + parameter_base_type 
			    + "_to_" + parameter_base_type 
			    + "(parameter_" + p.getIdentifier() + ");"); 
		}
	    }
	    ret_string = join("\n", ret) + "\n";
	    
	    // Convert the result iff the result type is not void
	    if(!base_type.equals("void")) {
		ret_string += "  return CCM::CORBA" + base_type 
		    + "_to_" + base_type + "(result);";
	    }
	}
	return ret_string;
    }


    /**
     * Creates the code that converts the exception list of an operation
     * into catch statements for CORBA exceptions that throw corresponding
     * remote exceptions.
     *
     * The template contains the following structure:
     *    try {
     *    //...
     *    }
     *    %(MParameterDefConvertReceptacleExceptionsToCpp)s
     *    catch(...) {
     *      throw;	
     *    } 
     * The %(MParameterDefConvertExceptionsToCpp)s tag forces a call to this 
     * method via getTwoStepVariables(). 
     *
     *  @param op Reference to an OperationDef element in the CCM model.
     *  @return Generated code as a string.
     */
    protected String convertReceptacleExceptionsToCpp(MOperationDef op)
    {
	Debug.println(Debug.METHODS,
		      "CppRemoteGeneratorImpl.convertExceptionsToCpp()");

	List ret = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext(); ) {
	    String exception_name = ((MExceptionDef) es.next()).getIdentifier(); 
            ret.add("  catch(const " 
		    + exception_name 
		    + "&) { \n    throw CCM_Local::" 
		    + exception_name 
		    + "();\n  }");
	}
	return join("\n", ret);
    }
}





