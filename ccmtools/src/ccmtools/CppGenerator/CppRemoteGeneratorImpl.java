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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.Metamodel.BaseIDL.MParameterMode;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.BaseIDL.MTypedefDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.utils.Debug;

/**
 * Remote C++ component generator
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
	"MHomeDef", "MComponentDef", "MStructDef" 
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
        super("CppRemote", d, out_dir, local_output_types);

	base_namespace.add("CCM_Remote");

	Debug.setDebugLevel(Debug.NONE);
	Debug.println(Debug.METHODS,"CppRemoteGeneratorImpl.CppRemoteGeneratorImpl()");

	// fill the CORBA_mappings with IDL to C++ Mapping types
	String[] labels = MPrimitiveKind.getLabels();
	CORBA_mappings = new Hashtable();
	for (int i = 0; i < labels.length; i++)
	    CORBA_mappings.put(labels[i], remote_language_map[i]);
    }

    //====================================================================
    // Code generator core functions
    //====================================================================

    /**
     * Overwrites the CppGenerator's method to handle namespaces in different
     * ways. There are local (CCM_Local) namespaces, remote (CCM_Remote)
     * namespaces and the namespaces of the generated stubs and skeletons.
     *
     * "FileNamespace": is used to create the directory in which the
     *                  remote component logic will be generated
     *
     * "IncludeNamespace": is used to create the include path depending
     *                     on the component's namespace.
     *                     The CCM_Remote namespace is cut off because 
     *                     there can be local and remote include paths.
     *
     * "Namespace": is used to create the scope for classes.
     *              The CCM_Remote namespace is cut off because 
     *              there can be local and remote include paths.
     *              If a namespace is defined, it has to start with "::"
     *
     * "ShortNamespace": corresponds with the module hierarchy in the IDL file,
     *                   there is no CCM_Local, CCM_Remote or CCM_Session_ included.
     *
     * "IdlFileNamespace":
     *
     * "IdlNamespace":
     **/
    protected String handleNamespace(String data_type, String local)
    {
        List names = new ArrayList(namespace);
		
	if (!local.equals("")) names.add("CCM_Session_" + local);

	if(data_type.equals("FileNamespace")) {
            return join("_", slice(names, 0));
        } 
	else if(data_type.equals("IncludeNamespace")) {
	    return join("/", slice(names, 1));
	}
	else if (data_type.equals("Namespace")) {
	    List NamespaceList = slice(names, 1);
	    if(NamespaceList.size() > 0) {
		return "::" + join("::", NamespaceList);
	    }
	    return "";
        } 
	else if (data_type.equals("ShortNamespace")) {
	    if(names.size() > 1) {
		List shortList = new ArrayList(names.subList(1, names.size()-1));
		if(shortList.size() > 0)
		    return join("::", shortList) + "::";
	    }
	    return "";
        }
	else if (data_type.equals("IdlFileNamespace")) {
	    if(names.size() > 1) {
		List IdlFileList = new ArrayList(names.subList(1, names.size()-1));
		if(IdlFileList.size() > 0)
		    return join("_", IdlFileList) + "_";
	    }
	    return "";
        } 
	else if (data_type.equals("IdlNamespace")) {
	    if(names.size() > 2) {
		List IdlFileList = new ArrayList(names.subList(2, names.size()-1));
		return join("::", IdlFileList) + "::";
	    }
	    return "";
        }
        return super.handleNamespace(data_type, local);
    }


    /***
     * Overrides method from CppGenerator to handle the following tags within an
     * interface attribute template: 
     *   %(CORBAType)s 
     *   %(MAttributeDefConvertResultType)s
     *   %(MAttributeDefConvertParameter)s
     **/
    protected Map getTwoStepAttributeVariables(MAttributeDef attr,
                                               MContained container)
    {
        String lang_type = getLanguageType(attr);
        Map vars = super. getTwoStepAttributeVariables(attr,container);

	MTyped object = (MTyped)attr;
	String base_type = getBaseIdlType(object);

	vars.put("CORBAType", getCORBALanguageType((MTyped)attr)); 
	vars.put("MAttributeDefConvertResultType", base_type + "_to_" + "CORBA" + base_type);
	vars.put("MAttributeDefConvertParameter", "CORBA" + base_type + "_to_" + base_type);

        return vars;
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
	vars.put("MExceptionDefCORBA",  getCORBAExcepts(operation));
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

        vars.put("Return", (lang_type.equals("void")) ? "" : "return ");

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
	//	else if(data_type.equals("MAttributeDefConvertResultType")) {
	//	    data_value = base_type + "_to_" + "CORBA" + base_type;
	//	}
	// Handle %(MAttributeDefConvertParameter)s tag in %(MAttributeDef*)s
	// templates
	//	else if(data_type.equals("MAttributeDefConvertParameter")) {
	//	    data_value = "CORBA" + base_type + "_to_" + base_type;
	//	}
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



    protected String data_MHomeDef(String data_type, String data_value)
    {
        MHomeDef home = (MHomeDef) current_node;
        MComponentDef component = home.getComponent();

        String home_id = home.getIdentifier();
        String component_id = component.getIdentifier();

        if(data_type.endsWith("ComponentType")) {
            return component_id;
        } 
	else {
	    return super.data_MHomeDef(data_type,data_value);
	}
    }


    protected String data_MSupportsDef(String data_type, String data_value)
    {
	MSupportsDef supports = (MSupportsDef)current_node;

	if(data_type.equals("SupportsInclude")) {
	    // Generates the include path of supported interfaces.
	    // Note that there is no CCM_Remote prefix in the namespace.
	    List scope = getScope((MContained)supports);
	    if(scope.size() > 0) {
		return "CCM_Local/" + join("/", scope) + "/" 
		    + supports.getSupports().getIdentifier(); 
	    }
	    else {
		return "CCM_Local/" + supports.getSupports().getIdentifier();
	    }
	}

	return super.data_MSupportsDef(data_type,data_value);
    }


    protected String data_MProvidesDef(String data_type, String data_value)
    {
	MProvidesDef provides = (MProvidesDef)current_node;
        MComponentDef component = provides.getComponent();

	if(data_type.equals("ProvidesInclude")) {
	    // Generates the include path of provides interfaces.
	    // Note that there is no CCM_Remote prefix in the namespace.
	    List scope = getScope((MContained)provides);
	    if(scope.size() > 0) {
		return "CCM_Local/" + join("/", scope) + "/" 
		    + provides.getProvides().getIdentifier(); 
	    }
	    else {
		return "CCM_Local/" + provides.getProvides().getIdentifier();
	    }
	}
	else if(data_type.equals("ProvidesType")) {
	    List scope = getScope((MContained)provides);
	    if(scope.size() > 0) {
		return join("::", scope) + "::" 
		    + provides.getProvides().getIdentifier();
	    }
	    else {
		return provides.getProvides().getIdentifier();
	    }
	}
        else if(data_type.equals("ComponentType")) {
	    return component.getIdentifier();
        }
	return super.data_MProvidesDef(data_type,data_value);
    }


    protected String data_MUsesDef(String data_type, String data_value)
    {
	MUsesDef usesDef = (MUsesDef)current_node;

	if(data_type.equals("UsesInclude")) {
	    // Generates the include path of used interfaces.
	    // Note that there is no CCM_Remote prefix in the namespace.
	    List scope = getScope((MContained)usesDef);
	    if(scope.size() > 0) {
		return "CCM_Local/" + join("/", scope) + "/" 
		    + usesDef.getUses().getIdentifier(); 
	    }
	    else {
		return "CCM_Local/" + usesDef.getUses().getIdentifier();
	    }
	}
	else if(data_type.equals("CCM_UsesType")) {
	    List scope = getScope((MContained)usesDef);
	    if(scope.size() > 0) {
		return join("::", scope) + "::CCM_" 
		    + usesDef.getUses().getIdentifier();
	    }
	    else {
		return "CCM_" + usesDef.getUses().getIdentifier();
	    }
	}
	else if(data_type.equals("UsesType")) {
	    List scope = getScope((MContained)usesDef);
	    if(scope.size() > 0) {
		return join("::", scope) + "::" 
		    + usesDef.getUses().getIdentifier();
	    }
	    else {
		return usesDef.getUses().getIdentifier();
	    }
	}
        return super.data_MUsesDef(data_type,data_value);
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
	    if (out_strings[i].trim().equals("")) continue;

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
	    else {
		String node_name = ((MContained) current_node).getIdentifier();
		List names = new ArrayList(namespace);
		String file_dir = join("_",names);
		writeFinalizedFile(file_dir,
				   node_name + out_file_types[i],
				   out_strings[i]);

		// TODO: Write Makefile.py only once...
	    }

	}
    }



    //====================================================================
    // Handle the C++ data types
    //====================================================================

    /**
     * Converts the CCM model type information (MTyped) to the corresponding
     * local C++ types.
     *
     * @param object Reference to an element of the CCM model.
     * @return Generated code for the local C++ type as string.
     */
    protected String getLanguageType(MTyped object)
    {
        String base_type = getBaseIdlType(object);

	// override IDL array mapping from parent function.
	if (object instanceof MArrayDef) {
            Iterator i = ((MArrayDef) object).getBounds().iterator();
            Long bound = (Long) i.next();
            String result = base_type + "[" + bound;
            while (i.hasNext()) result += "][" + (Long) i.next();
            return result + "]";
        }

        return super.getLanguageType(object);
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
	String corba_type = "";

        if (CORBA_mappings.containsKey(base_type)) {
	    // Primitive data types are mapped via map.
	    corba_type = (String) CORBA_mappings.get(base_type);
	}
	else if(object instanceof MContained) {
	    // Contained types are mapped with CORBA namespace
	    List scope = getScope((MContained)object);
	    if(scope.size() > 0)
		corba_type = "::" + join("::", scope) + "::" + base_type; 
	    else
		corba_type = "::" + base_type;
	}
	else if (idl_type instanceof MTypedefDef) {
	    List scope = getScope((MContained)idl_type);
	    if(scope.size() > 0)
		corba_type = "::" + join("::", scope) + "::" + base_type;  
	    else
		corba_type = "::" + base_type;
	}

	// Reduce MAliasDef to the original type
	if(idl_type instanceof MAliasDef) {
	    idl_type = ((MTyped)idl_type).getIdlType();
	}

	// Handle operation parameter types and passing rules 
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();
            String prefix = "const ";
	    String suffix = "&";

	    // IN Parameter
	    if (direction == MParameterMode.PARAM_IN) {
		if(idl_type instanceof MPrimitiveDef
		   || idl_type instanceof MEnumDef
		   || idl_type instanceof MArrayDef) {
		    prefix = "";
		    suffix = "";
		}
		else if(idl_type instanceof MStringDef) {
		    suffix = "";
		}
		    
	    }
	    // OUT Parameter
	    else if(direction == MParameterMode.PARAM_OUT) {
		if(idl_type instanceof MStringDef) {
		     // OUT string is a special case 
		    return "CORBA::String_out"; 
		}
		else {
		    return corba_type + "_out";
		}
	    }
	    // INOUT Parameter
	    else if(direction == MParameterMode.PARAM_INOUT) {
		prefix = "";
		if(idl_type instanceof MArrayDef) {
		    suffix = "";
		}
	    }
	    return prefix + corba_type + suffix;
        }

	// Handle operation return types
	else if(object instanceof MOperationDef) { 
	    // ToDo separate fixed and variable struct
	    if(idl_type instanceof MPrimitiveDef
	       || idl_type instanceof MEnumDef
	       || idl_type instanceof MStringDef) {

		return corba_type;
	    }
	    else if(idl_type instanceof MArrayDef) {
		return corba_type + "_slice*";
	    }
	    else
		return corba_type + "*";
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
    protected String getCORBAExcepts(MOperationDef op)
    {
	Debug.println(Debug.METHODS,"CppRemoteGenerator.getOperationExcepts()"); 
	
        List ret = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext(); ) {
	    MExceptionDef IdlException = (MExceptionDef)es.next();
	    List scope = getScope((MContained)IdlException);
	    if(scope.size() > 0)
		ret.add("::" + join("::", scope) + "::" + IdlException.getIdentifier());
	    else
		ret.add("::" + IdlException.getIdentifier());
	}
	
        if (ret.size() > 0) {
	    return "throw(CORBA::SystemException, " + join(", ", ret) + " )";
	}
        else                
	    return "throw(CORBA::SystemException)";
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
	    else if(idl_type instanceof MEnumDef) {
		MEnumDef idl_enum = (MEnumDef)idl_type;
		ret.add(convertEnumParameterFromCorbaToCpp(p, idl_enum));
	    }
	    else if(idl_type instanceof MStructDef) { 
		MTypedefDef idl_typedef = (MTypedefDef)idl_type;
		MStructDef idl_struct = (MStructDef)idl_typedef;
		ret.add(convertStructParameterFromCorbaToCpp(p, idl_struct, "", "parameter_"));
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

    // !!!!
    protected String convertPrimitiveParameterFromCorbaToCpp(MParameterDef p)
    {
	MParameterMode direction = p.getDirection();
	String base_type = getBaseIdlType(p);
	String cpp_type = (String)language_mappings.get(base_type);
        List ret = new ArrayList();
	ret.add("    " + cpp_type + " parameter_" + p.getIdentifier() + ";"); 
	if(direction != MParameterMode.PARAM_OUT) {
	    ret.add("    CCM::convertFromCorba(" + p.getIdentifier() +
		    ", parameter_" + p.getIdentifier() + ");");
	}
	return join("\n", ret) + "\n";
    }

    protected String convertEnumParameterFromCorbaToCpp(MParameterDef p, MEnumDef idl_enum)
    {
	MParameterMode direction = p.getDirection();
	String EnumScope = "";
	List scope = getScope((MContained)idl_enum);
	String local_scope;

	if(scope.size() > 0) 
	    local_scope =  join("::", scope) + "::";
	else
	    local_scope = "";

        List ret = new ArrayList();
	ret.add("  CCM_Local::" + local_scope + idl_enum.getIdentifier() + " parameter_"
		+ p.getIdentifier() + ";");
	
	if(direction != MParameterMode.PARAM_OUT) {
	    ret.add("  switch(" + p.getIdentifier() + ") {");
	    for (Iterator members = idl_enum.getMembers().iterator(); members.hasNext(); ) {
		String member = (String)members.next();
		ret.add("    case ::" + local_scope + member + ":");
		ret.add("    parameter_" + p.getIdentifier() + " = CCM_Local" + EnumScope 
			+ member + ";");
		ret.add("    break;");
	    }
	    ret.add("  }");
	}
	return join("\n", ret) + "\n";
    }

    protected String convertStructParameterFromCorbaToCpp(MParameterDef p, 
							  MStructDef idl_struct,
							  String CorbaPrefix,
							  String CppPrefix) 
    {
	MParameterMode direction = p.getDirection();
	List scope = getScope((MContained)idl_struct);
	String local_scope;

	if(scope.size() > 0) 
	    local_scope =  join("::", scope) + "::";
	else
	    local_scope = "";

	List ret = new ArrayList();
	ret.add("  CCM_Local::" + local_scope + idl_struct.getIdentifier() + " " + CppPrefix 
		+ p.getIdentifier() + ";"); 
	
	if(direction != MParameterMode.PARAM_OUT) {
	    for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
		MFieldDef member = (MFieldDef)members.next();
		MIDLType member_idl = ((MTyped)member).getIdlType();
		String base_type = getBaseIdlType((MTyped)member);

		if(member_idl instanceof MPrimitiveDef 
		   || member_idl instanceof MStringDef) {
		    ret.add("  " 
			    + CppPrefix + p.getIdentifier() + "." 
			    + member.getIdentifier() + " = CCM::CORBA" 
			    + base_type + "_to_" + base_type + "(" 
			    + CorbaPrefix + p.getIdentifier() + "." 
			    + member.getIdentifier() + ");");
		}
		else { 
		    // all other idl_types
		    // TODO
		    ret.add("  // unhandled idl type in convertStructFromCorbaToCpp()");
		} 
	    }    
	}
	return join("\n", ret);
    }

    protected String convertSequenceParameterFromCorbaToCpp(MParameterDef p, 
							    MAliasDef alias) 
    {
	MSequenceDef idl_sequence = (MSequenceDef)alias.getIdlType();
	MIDLType sequence_type = ((MTyped)idl_sequence).getIdlType();
	String base_type = getBaseIdlType((MTyped)idl_sequence);
	List ret = new ArrayList();
	List scope = getScope((MContained)alias);
	String local_scope;

	if(scope.size() > 0) 
	    local_scope =  join("::", scope) + "::";
	else
	    local_scope = "";

	if(sequence_type instanceof MPrimitiveDef
	   || sequence_type instanceof MStringDef) {
	    ret.add("  CCM_Local::" + local_scope + alias.getIdentifier() + " parameter_"
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
	    String CorbaPrefix = "CORBA_item_";
	    String CppPrefix = "Cpp_item_";
	    ret.add("  CCM_Local::" + local_scope + alias.getIdentifier() + " parameter_"
		    + p.getIdentifier() + ";");

	    if(p.getDirection() != MParameterMode.PARAM_OUT) {	
		ret.add("  for(unsigned long i=0; i< " + p.getIdentifier() + ".length(); i++) {");
		ret.add("  ::" + idl_struct.getIdentifier() + " " + CorbaPrefix +  p.getIdentifier()
			+ " = " + p.getIdentifier() + "[i];");
		ret.add(convertStructParameterFromCorbaToCpp(p,idl_struct, 
							     CorbaPrefix, CppPrefix)); 
		ret.add("  parameter_" + p.getIdentifier() 
			+ ".push_back(" + CppPrefix + p.getIdentifier() + ");");
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
	List scope = getScope((MContained)op);
	String local_scope;

	if(scope.size() > 0) 
	    local_scope =  join("::", scope) + "::";
	else
	    local_scope = "";

	if(idl_type instanceof MPrimitiveDef
	   && ((MPrimitiveDef)idl_type).getKind() == MPrimitiveKind.PK_VOID) {
	    return ""; // void foo() does not need a result declaration
	}

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = "  " + (String)language_mappings.get((String)getBaseIdlType(op)) +
		         " result;";
	}
	else if(idl_type instanceof MEnumDef) {
	    MEnumDef idl_enum = (MEnumDef)idl_type;
	    ret_string = "  CCM_Local::" + local_scope + idl_enum.getIdentifier() + " result;";
	}
	else if(idl_type instanceof MStructDef) { 
	    MTypedefDef idl_typedef = (MTypedefDef)idl_type;
	    MStructDef idl_struct = (MStructDef)idl_typedef;
		ret_string = "  CCM_Local::" + local_scope + idl_struct.getIdentifier() + " result;"; 
	}
	else if(idl_type instanceof MAliasDef) {
	    MAliasDef alias = (MAliasDef)idl_type;
	    if(alias.getIdlType() instanceof MSequenceDef) {
		ret_string = "  CCM_Local::" + local_scope + alias.getIdentifier() + " result;";
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
	String ResultString;
	MIDLType idl_type = op.getIdlType(); 
	
	if(idl_type instanceof MPrimitiveDef 
	   && ((MPrimitiveDef)idl_type).getKind() == MPrimitiveKind.PK_VOID) {
	    ResultString = "  "; // void foo() does not have a result value
	}
	else {
	    ResultString = "  result = ";
	}
	
	if(idl_type instanceof MPrimitiveDef 
	   || idl_type instanceof MStringDef
	   || idl_type instanceof MEnumDef
	   || idl_type instanceof MStructDef
	   || idl_type instanceof MAliasDef) {
	    ret_string = "  " + ResultString + "local_adapter->" 
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
	    else if(idl_type instanceof MEnumDef) {
		MEnumDef idl_enum = (MEnumDef)idl_type;
		ret.add(convertEnumParameterFromCppToCorba(p, idl_enum));
	    }
	    else if(idl_type instanceof MStructDef) { 
		MTypedefDef idl_typedef = (MTypedefDef)idl_type;
		MStructDef idl_struct = (MStructDef)idl_typedef;
		ret.add(convertStructParameterFromCppToCorba(p, idl_struct, 
							     "", "parameter_"));
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

    //!!!!!!!
    protected String convertPrimitiveParameterFromCppToCorba(MParameterDef p)
    {
	List ret = new ArrayList();
	MParameterMode direction = p.getDirection();
	if(direction != MParameterMode.PARAM_IN) {
	    ret.add("    CCM::convertToCorba(parameter_" +  p.getIdentifier() + 
		    ", " + p.getIdentifier() + ");" );
	}
	return join("\n", ret);
    }

    protected String convertEnumParameterFromCppToCorba(MParameterDef p, MEnumDef idl_enum)
    {
	List ret = new ArrayList();
	MParameterMode direction = p.getDirection();
	String EnumScope = "";

	if(direction != MParameterMode.PARAM_IN) {
	    ret.add("  switch(parameter_" + p.getIdentifier() + ") {");
	    for (Iterator members = idl_enum.getMembers().iterator(); members.hasNext(); ) {
		String member = (String)members.next();

		List scope = getScope((MContained)idl_enum);
		if(scope.size() > 0)
		    EnumScope = "::" + join("::", scope) + "::"; 
		else
		    EnumScope = "::";

		ret.add("    case " + "CCM_Local" + EnumScope + member + ": ");
		ret.add("    " + p.getIdentifier() + " = " + EnumScope + member + ";");
		ret.add("    break;");
	    }	    
	    ret.add("  }");
	}
	return join("\n", ret);
    }

    protected String convertStructParameterFromCppToCorba(MParameterDef p, 
							  MStructDef idl_struct,
							  String CorbaPrefix,
							  String CppPrefix)
    {
	MParameterMode direction = p.getDirection();
	String ItemAccess="";
	
	List ret = new ArrayList();
	if(direction == MParameterMode.PARAM_IN) {
	    return "";
	}
	else if(direction == MParameterMode.PARAM_OUT) {
	    ret.add("  " + CorbaPrefix + p.getIdentifier() + " = new ::" 
		    + getFullScopeIdentifier(idl_struct) + ";");
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
			+ CorbaPrefix + p.getIdentifier() + ItemAccess + member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(" + CppPrefix + p.getIdentifier() + "." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructParameterFromCppToCorba()");
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
		ret.add("  " + p.getIdentifier() + " = new ::" + getFullScopeIdentifier(alias) + ";"); 
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
	    String CorbaPrefix = "CORBA_item_";
	    String CppPrefix  = "Cpp_item_";

	    if(p.getDirection() == MParameterMode.PARAM_OUT) {	
		ret.add("  " + p.getIdentifier() + " = new ::" + getFullScopeIdentifier(alias) + ";"); 
		ret.add("  " + p.getIdentifier() + "->length(parameter_" 
			+ p.getIdentifier() + ".size());");
		ret.add("  for(unsigned long i=0; i< parameter_" + p.getIdentifier() 
			+ ".size(); i++) {");
		ret.add("  CCM_Local::" + getFullScopeIdentifier(idl_struct) + " " + CppPrefix 
			+ p.getIdentifier() + "= parameter_" + p.getIdentifier() + "[i];");
		ret.add("  ::" +  getFullScopeIdentifier(idl_struct) + "_var " + CorbaPrefix 
			+ p.getIdentifier() + ";");
		ret.add(convertStructParameterFromCppToCorba(p,idl_struct,CorbaPrefix, CppPrefix)); 
		ret.add("  (*" + p.getIdentifier() + ")[i] = " + CorbaPrefix 
			+ p.getIdentifier() + ";");
		ret.add("  }");
	    }
	    else {
		ret.add("  " + p.getIdentifier() + ".length(parameter_" 
			+ p.getIdentifier() + ".size());");
		ret.add("  for(unsigned long i=0; i< parameter_" + p.getIdentifier() 
			+ ".size(); i++) {");
		ret.add("  CCM_Local::" + getFullScopeIdentifier(idl_struct) + " " + CppPrefix 
			+ p.getIdentifier() + "= parameter_" + p.getIdentifier() + "[i];");
		ret.add("  ::" +  getFullScopeIdentifier(idl_struct) + " " + CorbaPrefix 
			+ p.getIdentifier() + ";");
		ret.add(convertStructParameterFromCppToCorba(p,idl_struct,CorbaPrefix, CppPrefix)); 
		ret.add("  " + p.getIdentifier() + "[i] = " + CorbaPrefix 
			+ p.getIdentifier() + ";");
		ret.add("  }");
	    }
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

	List ret = new ArrayList();
	MIDLType idl_type = op.getIdlType();


	if(idl_type instanceof MPrimitiveDef
	   && ((MPrimitiveDef)idl_type).getKind() == MPrimitiveKind.PK_VOID) {
	    return ""; // void foo() does not need a result convertion
	}

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret.add(convertPrimitiveResultFromCppToCorba(op));
	}
	else if(idl_type instanceof MEnumDef) {
	    MEnumDef idl_enum = (MEnumDef)idl_type;
	    ret.add(convertEnumResultFromCppToCorba(idl_enum));
	    ret.add("  return return_value;");
	}
	else if(idl_type instanceof MStructDef) { 
	    MTypedefDef idl_typedef = (MTypedefDef)idl_type;
	    MStructDef idl_struct = (MStructDef)idl_typedef;
	    ret.add(convertStructResultFromCppToCorba(idl_struct,
							   "return_value","result"));
	    ret.add("  return return_value._retn();");
	}
	else if(idl_type instanceof MAliasDef) {
	    MAliasDef alias = (MAliasDef)idl_type;
	    if(alias.getIdlType() instanceof MSequenceDef) {
		ret.add(convertSequenceResultFromCppToCorba(alias));
		ret.add("  return return_value._retn();");
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
	    ret.add("// unhandled idl type in convertResultToCorba()");
	}
	return join("\n", ret);
    }

    // !!!!
    protected String convertPrimitiveResultFromCppToCorba(MOperationDef op) 
    {
	String base_type = getBaseIdlType(op);
	List ret = new ArrayList();
	// Convert the result iff the result type is not void
	if(!base_type.equals("void")) {
	    ret.add("    " + getCORBALanguageType(op) + " return_value;");
	    ret.add("    CCM::convertToCorba(result, return_value);"); 
	    ret.add("    return return_value;");
	}
	return join("\n", ret);
    }

    protected String convertEnumResultFromCppToCorba(MEnumDef idl_enum)
    {
	String EnumScope = "";
	List ret = new ArrayList();
	ret.add("  ::" + getFullScopeIdentifier(idl_enum) + " return_value;"); 
	ret.add("  switch(result) {");	
	for (Iterator members = idl_enum.getMembers().iterator(); members.hasNext(); ) {
	    String member = (String)members.next();
	    List scope = getScope((MContained)idl_enum);
	    
	    if(scope.size() > 0)
		EnumScope = "::" + join("::", scope) + "::"; 
	    else
		EnumScope = "::";

	    ret.add("    case CCM_Local" + EnumScope + member + ":");
	    ret.add("    return_value = " + EnumScope + member + ";");
	    ret.add("    break;");
	}
	ret.add("  }");
	return join("\n", ret);	
    }

    protected String convertStructResultFromCppToCorba(MStructDef idl_struct,
						       String CorbaPrefix,
						       String CppPrefix) 
    {
	List ret = new ArrayList();
	List scope = getScope((MContained)idl_struct);
	String remote_scope;
    
	if(scope.size() > 0)
	    remote_scope = join("::", scope) + "::"; 
	else
	    remote_scope = "::";
	
	ret.add("  ::" 
		+ remote_scope + idl_struct.getIdentifier() + "_var " + CorbaPrefix + " = new ::" 
		+ remote_scope + idl_struct.getIdentifier() + ";"); 
	
	for (Iterator members = idl_struct.getMembers().iterator(); members.hasNext(); ) {
	    MFieldDef member = (MFieldDef)members.next();
	    MIDLType member_idl = ((MTyped)member).getIdlType();
	    String base_type = getBaseIdlType((MTyped)member);
	    
	    if(member_idl instanceof MPrimitiveDef 
	       || member_idl instanceof MStringDef) {
		ret.add("  " 
			+ CorbaPrefix + "->" + member.getIdentifier() 
			+ " = CCM::" + base_type + "_to_CORBA" + base_type 
			+ "(" + CppPrefix + "." + member.getIdentifier() + ");");
	    }
	    else { 
		// all other idl_types
		// TODO
		ret.add("// unhandled idl type in convertStructResultFromCorbaToCpp()");
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
	List scope = getScope((MContained)alias);
	String remote_scope;
    
	if(scope.size() > 0)
	    remote_scope = join("::", scope) + "::"; 
	else
	    remote_scope = "::";
	
	if(sequence_type instanceof MPrimitiveDef
	   || sequence_type instanceof MStringDef) {
    	    ret.add("  ::" + remote_scope+ alias.getIdentifier() + "_var return_value = new ::" 
		    + remote_scope + alias.getIdentifier() + ";"); 
	    ret.add("  return_value->length(result.size());");
	    ret.add("  for(unsigned long i=0; i< result.size(); i++) {");
	    ret.add("    (*return_value)[i] = CCM::" + base_type 
		    + "_to_CORBA" + base_type + "(result[i]);"); 
	    ret.add("  }");
	}
	else if(sequence_type instanceof MStructDef) {
	    MStructDef idl_struct = (MStructDef)sequence_type;
	    String CorbaPrefix = "CORBA_item_result";
	    String CppPrefix = "Cpp_item_result";
	    List lscope = getScope((MContained)idl_struct);
	    String local_scope;
	
	    if(lscope.size() > 0) 
		local_scope =  join("::", lscope) + "::";
	    else
		local_scope = "";
	    
	    ret.add("  ::" + remote_scope + alias.getIdentifier() + "_var return_value = new ::" 
		    + remote_scope + alias.getIdentifier() + ";"); 
	    ret.add("  return_value->length(result.size());");
	    ret.add("  for(unsigned long i=0; i< result.size(); i++) {");
	    ret.add("  CCM_Local::" + local_scope + idl_struct.getIdentifier() + " " 
		    + CppPrefix + " = result[i];");
	    ret.add(convertStructResultFromCppToCorba(idl_struct,CorbaPrefix, CppPrefix)); 
	    ret.add("  (*return_value)[i] = " + CorbaPrefix + ";");
	    ret.add("  }");
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
	    MExceptionDef IdlException = (MExceptionDef)es.next();
	    List scope = getScope(IdlException);
	    String IdlExceptionScope = "";
            
	    if(scope.size() >0) {
		IdlExceptionScope = join("::", scope) + "::";
	    }
	    ret.add("  catch(const CCM_Local::" + IdlExceptionScope
		    + IdlException.getIdentifier() + "&) { ");
	    ret.add("    throw " + "::" + IdlExceptionScope
		    + IdlException.getIdentifier() + "();");
	    ret.add("  }");
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
        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
            MParameterDef p = (MParameterDef) params.next();
	    MParameterMode direction = p.getDirection();
	    //	    MIDLType idl_type = ((MTyped)p).getIdlType();
	    String base_type = getBaseIdlType(p);
	    String corba_type = (String)CORBA_mappings.get(base_type);

	    // Note that the out parameters must not be converted to C++
	    if(direction == MParameterMode.PARAM_OUT) {
		ret.add("    " + corba_type + " parameter_" + p.getIdentifier() + ";"); 
	    }
	    else { 
		ret.add("    " + corba_type + " parameter_" + p.getIdentifier() +";");
		ret.add("    CCM::convertToCorba(" + p.getIdentifier() + 
			", parameter_" + p.getIdentifier() + ");");
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
	String ret_string = "";
	MIDLType idl_type = op.getIdlType(); 

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    ret_string = "    " 
		+ (String)CORBA_mappings.get((String)getBaseIdlType(op)) 
		+ " result;";
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
	    ret_string = "    " 
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


    //!!!!!!
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
	
	List ret = new ArrayList();
	//	String ret_string = "";
	MIDLType idl_type = op.getIdlType();

	if(idl_type instanceof MPrimitiveDef || 
	   idl_type instanceof MStringDef) {
	    String base_type = getBaseIdlType(op);
	    //	    List ret = new ArrayList();
	    // Convert the inout and out parameters 
	    for (Iterator params = op.getParameters().iterator(); params.hasNext(); ) {
		MParameterDef p = (MParameterDef) params.next();
		MParameterMode direction = p.getDirection();
		if(direction != MParameterMode.PARAM_IN) {
		    String parameter_base_type = getBaseIdlType(p);
		    // TODO: change converter line
		    ret.add("  " 
			    + p.getIdentifier() 
			    + "= CCM::CORBA" + parameter_base_type 
			    + "_to_" + parameter_base_type 
			    + "(parameter_" + p.getIdentifier() + ");"); 
		}
	    }
	    //	    ret_string = join("\n", ret) + "\n";
	    // Convert the result iff the result type is not void
	    if(!base_type.equals("void")) {
		ret.add("    " + getLanguageType(op) + " return_value;");
		ret.add("    CCM::convertFromCorba(result, return_value);");
		ret.add("    return return_value;");
	    }
	}
	return join("\n", ret);
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





