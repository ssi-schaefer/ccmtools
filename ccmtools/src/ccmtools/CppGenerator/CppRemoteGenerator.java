/* CCM Tools : C++ Code Generator Library
 * Egon Teiniker <egon.teiniker@salomon.at>
 * copyright (c) 2002 - 2006 Salomon Automation
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.Template;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MTypedefDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.metamodel.ComponentIDL.MSupportsDef;
import ccmtools.metamodel.ComponentIDL.MUsesDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;
import ccmtools.utils.Confix;
import ccmtools.utils.SourceFileHelper;
import ccmtools.utils.Text;

/***
 * Remote C++ component generator
 * 
 * This generator creates CORBA objects that build the remote skin of a
 * component as well as a set of adapters that convert CORBA types into C++
 * native types and vice versa.
 ***/
public class CppRemoteGenerator 
	extends CppGenerator 
{
    //====================================================================
    // Definition of arrays that determine the generator's behavior
    //====================================================================

    // Shortcuts for code artifacts 
    private static final String TAB = Text.TAB;
    private static final String TAB2 = Text.tab(2);
    private static final String TAB3 = Text.tab(3);
    private static final String NL = Text.NL;
    private static final String NL2 = Text.nl(2);
    private static final String NL3 = Text.nl(3);
    
    protected Map  corbaMappings;
   
    protected final String CORBA_CONVERTER_DIR = "corba_converter"; 
   
    protected Set<String> outputDirectories;
    
    
    /**
     * Top level node types: Types for which we have a global template; that is,
     * a template that is not contained inside another template.
     */
    protected final static String[] REMOTE_OUTPUT_TEMPLATE_TYPES = 
    {
    		"MHomeDef", 
    		"MComponentDef", 
    		"MInterfaceDef", 
    		"MStructDef", 
    		"MAliasDef", 
    		"MEnumDef",
        "MExceptionDef"
    };

    /**
     * Language type mapping: Defines the IDL to C++ Mappings for primitive
     * types.
     * Note: order and length of the array must be the same as used by the
     * MPrimitiveKind enumeration of the CCM metamodel.
     */
    protected final static String[] REMOTE_LANGUAGE_MAP = 
    { 
    		"", 
        "CORBA::Any", 			// PK_ANY
        "CORBA::Boolean", 		// PK_BOOLEAN
        "CORBA::Char",    		// PK_CHAR
        "CORBA::Double",  		// PK_DOUBLE
        "",               		// PK_FIXED
        "CORBA::Float",   		// PK_FLOAT
        "CORBA::Long",    		// PK_LONG
        "CORBA::LongDouble", 	// PK_LONGDOUBLE
        "CORBA::LongLong",   	// PK_LONGLONG
        "",                  	// PK_NULL
        "",                  	// PK_OBJREF
        "CORBA::Octet",      	// PK_OCTET
        "", 				 		// PK_PRINCIPAL
        "CORBA::Short", 	 		// PK_SHORT
        "char*", 	         	// PK_STRING
        "", 				 		// PK_TYPECODE
        "CORBA::ULong", 	 		// PK_ULONG
        "CORBA::ULongLong",  	// PK_ULONGLONG
        "CORBA::UShort", 	 	// PK_USHORT
        "", 				 		// PK_VALUEBASE
        "void", 					// PK_VOID
        "CORBA::WChar", 			// PK_WCHAR
        "CORBA::WChar*" 			// PK_WSTRING
    };

    /** Define constants for parameter directions */
    protected static final int IN    = 0;
    protected static final int INOUT = 1;
    protected static final int OUT   = 2;
    
    
    /**
     * The generator constructor calls the constructor of the base class and
     * sets up the map for the CORBA to C++ mappings.
     * 
     * @param uiDriver
     * @param outDir
     * 
     * @exception IOException
     */
    public CppRemoteGenerator(UserInterfaceDriver uiDriver, File outDir) 
    		throws IOException
    {
        this("CppRemote",uiDriver, outDir, REMOTE_OUTPUT_TEMPLATE_TYPES);
    }
    
    public CppRemoteGenerator(String language, UserInterfaceDriver uiDriver, File outDir, String[] output_types) 
        throws IOException
    {
        super(language, uiDriver, outDir, output_types);
        
        logger = Logger.getLogger("ccm.generator.cpp.remote");
        logger.fine("begin");

        cxxGenNamespace = ConfigurationLocator.getInstance().getCppLocalNamespaceExtension();
        remoteNamespace = ConfigurationLocator.getInstance().getCppRemoteNamespaceExtension();
        corbaStubsNamespace = ConfigurationLocator.getInstance().getIdl2NamespaceExtension();
        
        // Fill the CORBA_mappings with IDL to C++ Mapping types
        String[] labels = MPrimitiveKind.getLabels();
        corbaMappings = new Hashtable();
        for(int i = 0; i < labels.length; i++) 
        {
            corbaMappings.put(labels[i], REMOTE_LANGUAGE_MAP[i]);
        }
        logger.fine("end");
    }
    
    //====================================================================
    // Code generator core methods
    //====================================================================
    

    protected String getRemoteNamespace(MContained node, String separator)
    {
	    	logger.fine("begin");
		StringBuffer code = new StringBuffer();
		try
		{
			if (node == null)
			{
				return "";
			}
			List scope = getScope(node);
			if (scope.size() > 0)
			{
				code.append(join(separator, scope));
				code.append(separator);
			}

			if (remoteNamespace.size() > 0)
			{
				code.append(join(separator, remoteNamespace));
				code.append(separator);
			}
			logger.fine("end");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return code.toString();
	}
   
    protected String getRemoteNamespace(MIDLType idlType, String separator)
    {
    		logger.fine("begin");
    		String namespace = "";
		if(idlType instanceof MContained || idlType instanceof MAliasDef)
		{
			MContained contained = (MContained)idlType;
			namespace = getRemoteNamespace(contained, separator);
		}
		else
		{
			// PrimitiveDef, StringDef, WStringDef, FixedDef
		}
		logger.fine("end");
		return namespace;	
    }
    
    protected String getRemoteName(MContained node, String separator)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(getRemoteNamespace(node, separator));
        code.append(node.getIdentifier());
        logger.fine("end");
        return code.toString();
    }

    protected String getOpenRemoteNamespace(MContained node)
    {
    		logger.fine("begin");
        List modules = new ArrayList(namespaceStack);
        modules.addAll(remoteNamespace);
        StringBuffer code = new StringBuffer();
        for(Iterator i = modules.iterator(); i.hasNext();) 
        {
            code.append("namespace " + i.next() + " {\n");
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    protected String getCloseRemoteNamespace(MContained node)
    {
    		logger.fine("begin");
        List modules = new ArrayList(namespaceStack);
        modules.addAll(remoteNamespace);
        Collections.reverse(modules);

        StringBuffer code = new StringBuffer();
        for(Iterator i = modules.iterator(); i.hasNext();) 
        {
            code.append("} // /namespace " + i.next() + "\n");
        }        
        logger.fine("end");
        return code.toString();
    }

    
    
    protected String getCorbaStubNamespace(MContained contained, String separator)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        List scope = getScope(contained);
        if(corbaStubsNamespace.size() > 0) 
        {
            code.append(Text.join(separator, corbaStubsNamespace));
            code.append(separator);
        }
        if (scope.size() > 0) 
        {
            code.append(Text.join(separator, scope));
            code.append(separator);
        }
        logger.fine("end");
		return code.toString();	
    }
    
    protected String getCorbaStubNamespace(MIDLType idlType, String separator)
    {
    		logger.fine("begin");
    		String namespace = "";
		if(idlType instanceof MContained || idlType instanceof MAliasDef)
		{
			MContained contained = (MContained)idlType;
			namespace = getCorbaStubNamespace(contained, separator);
		}
		else
		{
			// PrimitiveDef, StringDef, WStringDef, FixedDef
		}    	
		logger.fine("end");
		return namespace;
    }
        
    protected String getAbsoluteCorbaType(MContained contained, String separator)
    {
    		return getCorbaStubNamespace(contained, "::") + contained.getIdentifier(); 
    }
    
    protected String getAbsoluteCorbaType(MIDLType idlType, String separator)
    {
    		logger.fine("begin");
    		String name = "";
    		if(idlType instanceof MContained)
    		{
    			MContained contained = (MContained)idlType;
    			name = getAbsoluteCorbaType(contained, "::"); 
    		}
    		else if(idlType instanceof MAliasDef)
    		{
    			MAliasDef alias = (MAliasDef)idlType;
    			name = getCorbaStubNamespace(idlType, "::") + alias.getIdentifier();     			
    		}
    		else
    		{
    	        if(idlType instanceof MPrimitiveDef) 
    	        {
    	            String key = ((MPrimitiveDef) idlType).getKind().toString();
    	            name = (String)corbaMappings.get(key);
    	        }
    	        else if(idlType instanceof MStringDef) 
    	        {
    	        		String key = ((MStringDef) idlType).getKind().toString();
    	        		name = (String)corbaMappings.get(key);
    	        }
    	        else if(idlType instanceof MWstringDef) 
    	        {
    	        		String key = ((MWstringDef) idlType).getKind().toString();
    	        		name = (String)corbaMappings.get(key);
    	        }
    	        else if(idlType instanceof MFixedDef) 
    	        {
    	        		String key = ((MFixedDef) idlType).getKind().toString();
    	        		name = (String)corbaMappings.get(key);
    	        }
    		}
    		logger.fine("end");
    		return name;
    }
    
        
//    protected String getCorbaConverterNamespace(MContained contained, String separator)
//	{
//		return getRemoteNamespace(contained, separator);
//	}

    /**
     * Generate a CORBA converter's namespace.
     * The converter namespaces is the same as the remote namespace except for
     * basic types - here the converter does have a namespace (::ccm::remote)
     * because the converters for basic types are defined in the C++ runtime
     * environment in the namespace ::ccm::remote.  
     */
    protected String getCorbaConverterNamespace(MIDLType idlType, String separator)
	{
    		logger.fine("begin");
    		String namespace = "";
		if(idlType instanceof MAliasDef)
		{
			// unwind MAliasDef
			MIDLType innerIdlType = ((MTyped)idlType).getIdlType();
			if(innerIdlType instanceof MPrimitiveDef
					|| idlType instanceof MStringDef
					|| idlType instanceof MWstringDef
					|| idlType instanceof MFixedDef)
			{
				return getCorbaConverterNamespace(innerIdlType, separator);
			}
		}
		
    		if(idlType instanceof MContained)
		{
			namespace = getRemoteNamespace((MContained)idlType, separator);
		}
		else
		{
			// PrimitiveDef, StringDef, WStringDef, FixedDef
			namespace = separator + "ccm" + separator + "remote" + separator;
		}
    		logger.fine("end");
    		return namespace;
	}

    
    protected String generateCorbaConverterInclude(MContained base, MContained element, String baseType)
	{
    		logger.fine("begin");
		StringBuffer code = new StringBuffer();
		code.append("#include <");
		code.append(getRemoteNamespace(element, Text.INCLUDE_SEPARATOR));
		code.append(baseType);
		code.append("_remote.h>\n");
		logger.fine("end");
		return code.toString();
	}
    

    protected String generateCorbaConverterConfixInclude(MContained base, MContained element, String baseType)
    {
        logger.fine("begin");
        // Here we use the remote namespace to compare because we have to
        // support these ugly *_component_ComponentName namespace artefact.
        String baseNamespace = getRemoteNamespace(base, Text.INCLUDE_SEPARATOR);
        String elementNamespace =  getRemoteNamespace(element, Text.INCLUDE_SEPARATOR);
        
        StringBuffer code = new StringBuffer();
        if(baseNamespace.equals(elementNamespace))
        {
            code.append("#include \"");
            code.append(baseType);
            code.append("_remote.h\"\n");
        }
        else
        {
            code.append("#include <");
            code.append(getRemoteNamespace(element, Text.INCLUDE_SEPARATOR));
            code.append(baseType);
            code.append("_remote.h>\n");
        }
        logger.fine("end");
        return code.toString();
    }

    
    /**
     * Overide the CodeGenerator method to use the
     * remote C++ namespace.
     */
    protected String getFullScopeInclude(MContained node)
    {
        logger.fine("");
        return getRemoteName(node, Text.FILE_SEPARATOR);
    }
    
    
    /**
     * Overwrites the CppGenerator's method to handle namespaces in different
     * ways. There are local (CCM_Local) namespaces, remote (CCM_Remote)
     * namespaces and the namespaces of the generated CORBA stubs.
     */
    protected String handleNamespace(String dataType, String local)
    {
    		logger.fine("begin");
        logger.finer("dataType = " + dataType + ", local = " + local);
        String code;
        MContained contained = null;    
        if(currentNode instanceof MContained) 
        {
            contained = (MContained)currentNode;
        }
        
        if (dataType.equals("FileNamespace")) 
        {
            code = getRemoteNamespace(contained,Text.MANGLING_SEPARATOR);
        }
        else if(dataType.equals("LocalNamespace")) 
        {
            code = getLocalCxxNamespace(contained, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("RemoteNamespace")) 
        {
            code = getRemoteNamespace(contained,Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("RemoteIncludeNamespace")) 
        {
            code = getRemoteNamespace(contained,Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("LocalIncludeNamespace")) 
        {           
            code = getLocalCxxNamespace(contained, Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("LocalGenIncludeNamespace")) 
        {           
            code = getLocalCxxGenNamespace(contained, Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("StubsNamespace")) 
        {
            code = getCorbaStubNamespace(contained, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("StubsIncludeNamespace")) 
        {
            code = getCorbaStubNamespace(contained, Text.MANGLING_SEPARATOR);
        }            
        else if(dataType.equals("CorbaConverterNamespace")) 
        {
            code = getRemoteNamespace(contained, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("OpenNamespace")) 
        {
            code = getOpenRemoteNamespace(contained);
        }
        else if(dataType.equals("CloseNamespace")) 
        {            
            code = getCloseRemoteNamespace(contained);
        }
        else 
        {
            code = super.handleNamespace(dataType);
        }
        logger.fine("end");
        return code;
    }


    /**
     * Get a variable hash table sutable for filling in the template from the
     * fillTwoStepTemplates function. This version of the function fills in
     * operation information from the given interface.
     * 
     * @param operation
     *            the particular interface operation that we're filling in a
     *            template for.
     * @param container
     *            the container in which the given interface is defined.
     * @return a map containing the keys and values needed to fill in the
     *         template for this interface.
     */
    protected Map getTwoStepOperationVariables(MOperationDef operation, MContained container)
    {
        logger.fine("begin");
        
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object", container.getIdentifier());
        vars.put("Identifier", operation.getIdentifier());
        vars.put("LanguageType", lang_type);
        vars.put("CORBAType", getAbsoluteCorbaType(operation.getIdlType(), "::"));
        vars.put("CORBAResultType", generateCorbaOperationResultType(operation.getIdlType()));	
        
        vars.put("LocalExceptions", getOperationExcepts(operation));
        vars.put("MExceptionDefCORBA", getCORBAExcepts(operation));

        vars.put("ParameterDefLocal", getLocalOperationParams(operation));
        vars.put("MParameterDefCORBA", getCorbaOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));

        // Used for supports adapter generation
        vars.put("ConvertFacetParameterToCpp", convertParameterToCpp(operation));
        vars.put("DeclareFacetCppResult", declareCppResult(operation));
        vars.put("ConvertFacetMethodToCpp", convertMethodToCpp(operation));
        vars.put("ConvertFacetExceptionsToCorba", convertExceptionsToCorba(operation));
        vars.put("ConvertFacetParameterToCorba", convertParameterToCorba(operation));
        vars.put("ConvertFacetResultToCorba", convertResultToCorba(operation));

        vars.put("Return", (lang_type.equals("void")) ? "" : "return ");
        logger.fine("end");
        return vars;
    }
    
    
    /**
     * Handles the different template names found for a particular model node
     * and returns the generated code. The current model node is represented in
     * the current_node variable. The string parameter contains the name of the
     * found template
     * 
     * Note that the method calls the super class method.
     * 
     * @param variable
     *            The variable name (tag name) to get a value (generated code)
     *            for.
     * 
     * @return The value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
    		logger.fine("");
        logger.finer("variable = " + variable);
        
        // Get local value of CppGenerator 
        String value = super.getLocalValue(variable);
        
        if(currentNode instanceof MContained) 
        {
            MContained contained = (MContained)currentNode;
            if(variable.equals("LocalName")) 
            {
                return getLocalCxxName(contained,Text.SCOPE_SEPARATOR);
            }
            else if(variable.equals("CorbaStubName")) 
            {
                return getAbsoluteCorbaType(contained,Text.SCOPE_SEPARATOR);
            }
            else if(variable.equals("CorbaRemoteName")) 
            {
                return getRemoteName(contained,Text.SCOPE_SEPARATOR);
            }
        }
       
        if (currentNode instanceof MStructDef) 
        {
            return data_MStructDef(variable, value);
        }
        else if (currentNode instanceof MAttributeDef) 
        {
            return data_MAttributeDef(variable, value);
        }
        else if (currentNode instanceof MFieldDef) 
        {
            return data_MFieldDef(variable, value);
        }
        else if (currentNode instanceof MAliasDef) 
        {

            // determine the contained type of MaliasDef
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            
            if(idlType instanceof MSequenceDef) 
            {
                return data_MSequenceDef(variable, value);
            }
            else if (idlType instanceof MArrayDef) 
            {
                return data_MArrayDef(variable, value);
            }
            else 
            {
                return data_MTypedefDef(variable, value);
            }
        }
        return value;
    }

    protected String data_MTypedefDef(String dataType, String dataValue)
    {
        logger.fine("begin");       
        logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        MContained contained = (MContained) type;

        if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace(contained, Text.INCLUDE_SEPARATOR);
        }
        if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace(contained, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(contained);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(contained);
        }        
        logger.fine("end");
        return dataValue;
    }
    

    protected String data_MExceptionDef(String dataType, String dataValue)
    {
        logger.fine("begin");       
        logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MExceptionDef ex = (MExceptionDef) currentNode;

        if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace((MContained)ex, Text.INCLUDE_SEPARATOR);
        }
        if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)ex, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace((MContained)ex);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace((MContained)ex);
        }        
        logger.fine("end");
        return dataValue;
    }

    
    protected String data_MSequenceDef(String dataType, String dataValue)
    {
        logger.fine("begin");       
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        MContained contained = (MContained) type;
        MTyped singleType = (MTyped) idlType;
        MIDLType singleIdlType = singleType.getIdlType();
        MContained node = (MContained)currentNode;
        
        if (dataType.equals("ConvertFromCorbaDeclaration")) 
        {
            dataValue = getConvertFromCorbaDeclaration(node);
        }
        else if (dataType.equals("ConvertToCorbaDeclaration")) 
        {
            dataValue = getConvertToCorbaDeclaration(node);           
        }
        else if (dataType.equals("ConvertFromCorbaImplementation")) 
        {
            dataValue = getConvertFromCorbaImplementation(node, singleType);
        }
        else if (dataType.equals("ConvertToCorbaImplementation")) 
        {
            dataValue = getConvertToCorbaImplementation(node, singleType);
        }
        else if (dataType.equals("SingleValue")) 
        {
            dataValue = getSingleValue(singleType);
        }
        else if (dataType.equals("InOutValue")) 
        {
            dataValue = getInOutValue(singleType);
        }
        else if (dataType.equals("CORBASequenceConverterInclude")) 
        {
            dataValue = getCORBASequenceConverterInclude(idlType,singleIdlType);
        }
        else if (dataType.equals("ConvertAliasFromCORBA")) 
        {
            dataValue = getConvertAliasFromCORBA(singleType);
        }
        else if (dataType.equals("ConvertAliasToCORBA")) 
        {
            dataValue = getConvertAliasToCORBA(singleType);
        }
        else if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace(contained, Text.INCLUDE_SEPARATOR);
        }
        if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace(contained, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(contained);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(contained);
        }        
        logger.fine("end");
        return dataValue;
    }


    protected String data_MArrayDef(String dataType, String dataValue)
    {
        logger.fine("");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        // TODO: Implement array converter
        throw new RuntimeException("Not implemented!");
    }
    
    
    protected String data_MFieldDef(String dataType, String dataValue)
    {
    		logger.fine("begin");
    		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue); 
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String fieldName = ((MFieldDef) currentNode).getIdentifier();

        if (dataType.equals("CORBAType")) 
        {
            dataValue = fieldName;
        }
        else if (dataType.equals("CORBATypeIn")) 
        {
            dataValue = getCORBAFieldDirection(idlType, fieldName, IN);
        }
        else if (dataType.equals("CORBATypeInOut")) 
        {
            dataValue = getCORBAFieldDirection(idlType, fieldName, INOUT);
        }
        else if (dataType.equals("CORBATypeOut")) 
        {
            dataValue = getCORBAFieldDirection(idlType, fieldName, OUT);
        }
        else if (dataType.equals("CORBAFieldConverterInclude")) 
        {
            dataValue = getCORBAFieldConverterInclude(idlType,fieldName);
        }
        else if(dataType.equals("CorbaConverterNamespace"))
        {
        		dataValue = getCorbaConverterNamespace(idlType, Text.SCOPE_SEPARATOR);	
        }
        logger.fine("end");
        return dataValue;
    }

    
    protected String data_MAttributeDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String baseType = getBaseIdlType(type);
        MAttributeDef attribute = (MAttributeDef)currentNode;
       
        if(dataType.equals("InterfaceType")) 
        {        
            dataValue = attribute.getDefinedIn().getIdentifier();
        }
        else if(dataType.equals("ComponentType")) 
        {
            MContained contained = attribute.getDefinedIn();
            // used from component attributes
            if(contained instanceof MComponentDef) 
            {
                MComponentDef component = (MComponentDef) contained;
                dataValue = component.getIdentifier();
            }
        }
        else if(dataType.equals("CORBAType")) 
        {
        		dataValue = getAbsoluteCorbaType(idlType, "::");
        }
        else if(dataType.equals("CORBAAttributeResult")) 
        {
            dataValue = getCorbaAttributeResult(type);
        }
        else if(dataType.equals("CORBAAttributeParameter")) 
        {
            dataValue = getCorbaAttributeParameter(type);
        }
        else if(dataType.equals("LocalAttributeType")) 
        {
            dataValue = getLocalAttributeType(type);
        }
        else if(dataType.equals("ConvertComponentGetAttributeFromCorba")) 
        {
            dataValue = convertGetAttributeFromCorba(attribute,"local_adapter"); 
        }
        else if(dataType.equals("ConvertComponentSetAttributeFromCorba")) 
        {
            dataValue = convertSetAttributeFromCorba(attribute,"local_adapter");
        }
        else if(dataType.equals("ConvertInterfaceGetAttributeFromCorba")) 
        {
            dataValue = convertGetAttributeFromCorba(attribute,"localInterface"); 
        }
        else if(dataType.equals("ConvertInterfaceSetAttributeFromCorba")) 
        {
            dataValue = convertSetAttributeFromCorba(attribute,"localInterface");
        }
        else if(dataType.equals("ConvertInterfaceGetAttributeToCorba")) 
        {
            dataValue = convertGetAttributeToCorba(attribute);
        }
        else if(dataType.equals("ConvertInterfaceSetAttributeToCorba")) 
        {
            dataValue = convertSetAttributeToCorba(attribute);
        }
        else if(dataType.equals("AttributeConvertInclude")) 
        {
            dataValue = getAttributeConvertInclude(idlType, baseType);
        }
        logger.fine("end");
        return dataValue;
    }

    
    protected String data_MOperationDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MOperationDef op = (MOperationDef)currentNode;
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String baseType = getBaseIdlType(type);
        MOperationDef operation = (MOperationDef) type;

        if(dataType.equals("InterfaceType")) 
        {
            dataValue = operation.getDefinedIn().getIdentifier();
        }       
        else if(dataType.equals("CORBAResultType")) 
        {
        		dataValue = generateCorbaOperationResultType(op.getIdlType());	
        }
        else if(dataType.equals("CORBAType")) 
        {
        		dataValue = getAbsoluteCorbaType(idlType, "::");
        }
        else if(dataType.equals("CORBAParameters")) 
        {
            dataValue = getCorbaOperationParams(operation);
        }
        else if(dataType.equals("CORBAExceptions")) 
        { 
            dataValue = getCORBAExcepts(operation);
        }
        if(dataType.equals("LocalParameters")) 
        {
            dataValue = getLocalOperationParams(operation);
        }
        else if(dataType.equals("LocalExceptions")) 
        { 
            dataValue = getOperationExcepts(operation);
        }
        else if (dataType.equals("OperationConvertInclude")) 
        {
            dataValue = getOperationConvertInclude(idlType, baseType);
        }
        else if (dataType.equals("ParameterConvertInclude")) 
        {
            dataValue = getParameterConvertInclude(operation);
        }
        else if (dataType.equals("ExceptionConvertInclude")) 
        {
            dataValue = getExceptionConvertInclude(operation);
        }
        else if (dataType.equals("ConvertFacetParameterToCpp")) 
        {
            dataValue = convertParameterToCpp(operation);
        }
        else if (dataType.equals("DeclareFacetCppResult")) 
        {
            dataValue = declareCppResult(operation);
        }
        else if (dataType.equals("ConvertInterfaceMethodToCpp")) 
        {
            dataValue = convertInterfaceMethodToCpp(operation);
        }
        else if (dataType.equals("ConvertFacetExceptionsToCorba")) 
        {
            dataValue = convertExceptionsToCorba(operation);
        }
        else if (dataType.equals("ConvertFacetParameterToCorba")) 
        {
            dataValue = convertParameterToCorba(operation);
        }
        else if (dataType.equals("ConvertFacetResultToCorba")) 
        {
            dataValue = convertResultToCorba(operation);
        }
        else if (dataType.equals("ConvertReceptacleParameterToCorba")) 
        {
            dataValue = convertReceptacleParameterToCorba(operation);
        }
        else if (dataType.equals("DeclareReceptacleCorbaResult")) 
        {
            dataValue = declareReceptacleCorbaResult(operation);
        }
        else if (dataType.equals("ConvertReceptacleMethodToCorba")) 
        {
            dataValue = convertInterfaceMethodToCorba(operation);
        }
        else if (dataType.equals("ConvertReceptacleExceptionsToCpp")) 
        {
            dataValue = convertReceptacleExceptionsToCpp(operation);
        }
        else if (dataType.equals("ConvertReceptacleParameterToCpp")) 
        {
            dataValue = convertReceptacleParameterToCpp(operation);
        }
        else if (dataType.equals("ConvertReceptacleResultToCpp")) 
        {
            dataValue = convertReceptacleResultToCpp(operation);
        }
        else 
        {
            dataValue = super.data_MOperationDef(dataType, dataValue);
        }
        logger.fine("end");
        return dataValue;
    }

    
    protected String data_MEnumDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		MEnumDef enumDef = (MEnumDef) currentNode;
		if (dataType.equals("MembersFromCorba"))
		{
			dataValue = getMembersFromCorba(enumDef);
		}
		else if (dataType.equals("MembersToCorba"))
		{
			dataValue = getMembersToCorba(enumDef);
		}
        else if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace(enumDef, Text.INCLUDE_SEPARATOR);
        }
        if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)enumDef, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(enumDef);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(enumDef);
        }        
		else
		{
			dataValue = super.data_MEnumDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue;
    }

    
    protected String data_MFactoryDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		if (dataType.startsWith("MParameterCORBA"))
		{
			dataValue = getCorbaOperationParams((MOperationDef) currentNode);
		}
		else
		{
			dataValue = super.data_MFactoryDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue; 
    }

    
    protected String data_MHomeDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		MHomeDef home = (MHomeDef) currentNode;
		MComponentDef component = home.getComponent();

		if (dataType.endsWith("ComponentType"))
		{
			dataValue = component.getIdentifier();
		}
        else if(dataType.equals("RemoteNamespace")) //!!!
        {
            dataValue = getRemoteNamespace((MContained)home,Text.SCOPE_SEPARATOR);
        }
		else if (dataType.endsWith("AbsoluteRemoteHomeName"))
		{
			dataValue = getRemoteName(home, Text.MANGLING_SEPARATOR);
		}
        else if(dataType.equals("StubsNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)home, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace(home, Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)home, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(home);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(home);
        }        
		else
		{
			dataValue = super.data_MHomeDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue;
    }

    
    protected String data_MComponentDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		MComponentDef component = (MComponentDef) currentNode;
		List homes = component.getHomes();
		MHomeDef home = null;
		if (homes.size() == 1)
		{
			home = (MHomeDef) homes.get(0);
		}
		else
		{
			throw new RuntimeException("Component '" + component.getIdentifier() 
					+ "' does not have exactly one home.");
		}

		if (dataType.endsWith("AbsoluteRemoteHomeName"))
		{
			dataValue = getRemoteName(home, "_");
		}
		else if (dataType.equals("IdlIdentifier"))
		{
			dataValue = getCorbaStubNamespace((MContained) component, "::") + component.getIdentifier();
		}
		else if (dataType.equals("IdlHomeType"))
		{
			dataValue = getCorbaStubNamespace((MContained) home, "::") + home.getIdentifier();
		}
        else if(dataType.equals("LocalIncludeNamespace"))
        {
            dataValue = getLocalCxxNamespace(component, Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("LocalGenIncludeNamespace")) 
        {           
            dataValue = getLocalCxxGenNamespace(component, Text.INCLUDE_SEPARATOR);
        }        
        else if(dataType.equals("RemoteIncludeNamespace"))
        {
            dataValue = getRemoteNamespace((MContained)component,Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)component, Text.MANGLING_SEPARATOR);
        }           
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(component);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(component);
        }                
		else
		{
			dataValue = super.data_MComponentDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue;
    }
    
    
    protected String data_MInterfaceDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		MInterfaceDef iface = (MInterfaceDef) currentNode;

		if (dataType.equals("StubIdentifier"))
		{
			dataValue = getStubIdentifier(iface);
		}
		else if (dataType.equals("CCM_LocalType"))
		{
			dataValue = getCCM_LocalType(iface);
		}
		else if(dataType.equals("StubsIncludeNamespace")) 
		{
		    dataValue = getCorbaStubNamespace((MContained)iface, Text.MANGLING_SEPARATOR);
		}  
		else if(dataType.equals("LocalIncludeNamespace")) //!!!!
		{
		    dataValue = getLocalCxxNamespace(iface, Text.INCLUDE_SEPARATOR);
		}
        else if(dataType.equals("LocalNamespace"))  //!!!!!
        {
            dataValue = getLocalCxxNamespace(iface, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("StubsNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)iface, Text.SCOPE_SEPARATOR);
        }
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(iface);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(iface);
        }        
		else if (dataType.equals("AttributeBaseInterfaceAdapterFromCorbaHeader"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = false;
			dataValue = getBaseInterfaceAttributesFromCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("AttributeBaseInterfaceAdapterToCorbaHeader"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = false;
			dataValue = getBaseInterfaceAttributesToCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("AttributeBaseInterfaceAdapterFromCorbaImpl"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = true;
			dataValue = getBaseInterfaceAttributesFromCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("AttributeBaseInterfaceAdapterToCorbaImpl"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = true;
			dataValue = getBaseInterfaceAttributesToCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("OperationBaseInterfaceAdapterFromCorbaHeader"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = false;
			dataValue = getBaseInterfaceOperationsFromCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("OperationBaseInterfaceAdapterToCorbaHeader"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = false;
			dataValue = getBaseInterfaceOperationsToCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("OperationBaseInterfaceAdapterFromCorbaImpl"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = true;
			dataValue = getBaseInterfaceOperationsFromCorba(isImpl, iface, baseInterfaceList);
		}
		else if (dataType.equals("OperationBaseInterfaceAdapterToCorbaImpl"))
		{
			List baseInterfaceList = iface.getBases();
			boolean isImpl = true;
			dataValue = getBaseInterfaceOperationsToCorba(isImpl, iface, baseInterfaceList);
		}
		else
		{
			dataValue = super.data_MInterfaceDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue;
    }
    
    protected String data_MStructDef(String dataType, String dataValue)
    {
        logger.fine("begin");
        logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MStructDef struct = (MStructDef) currentNode;

        if(dataType.equals("StubsIncludeNamespace")) 
        {
            dataValue = getCorbaStubNamespace((MContained)struct, Text.MANGLING_SEPARATOR);
        }  
        else if(dataType.equals("LocalIncludeNamespace")) //!!!!
        {
            dataValue = getLocalCxxNamespace(struct, Text.INCLUDE_SEPARATOR);
        }
        else if(dataType.equals("OpenNamespace"))  //!!!!!
        {
            dataValue = getOpenRemoteNamespace(struct);
        }
        else if(dataType.equals("CloseNamespace")) //!!!!!!
        {
            dataValue = getCloseRemoteNamespace(struct);
        }        
        logger.fine("end");
        return dataValue;
    }
    
    
    protected String data_MSupportsDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
		MSupportsDef supports = (MSupportsDef) currentNode;

		if (dataType.equals("SupportsInclude"))
		{
			dataValue = getSupportsInclude(supports);
		}
		else if (dataType.equals("SupportsConvertInclude"))
		{
			dataValue = getSupportsConvertInclude(supports);
		}
		else
		{
			dataValue = super.data_MSupportsDef(dataType, dataValue);
		}
		logger.fine("end");
		return dataValue;
    }

    
    protected String data_MProvidesDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MProvidesDef provides = (MProvidesDef) currentNode;
        MComponentDef component = provides.getComponent();
        MInterfaceDef iface = provides.getProvides();
        
        if(dataType.equals("ProvidesInclude")) 
        {
            dataValue = getProvidesInclude(provides);
        }
        else if(dataType.equals("ProvidesConvertInclude")) 
        {
            dataValue = getProvidesConvertInclude(provides); 
        }
        else if(dataType.equals("IdlProvidesType")) 
        {
            dataValue = getIdlProvidesType(provides);
        }
        else if(dataType.equals("ProvidesType")) 
        {
            dataValue = getProvidesType(provides);
        }
        else if(dataType.equals("InterfaceType")) 
        {
            dataValue = provides.getProvides().getIdentifier();
        }
        else if(dataType.equals("ComponentType")) 
        {
            dataValue = component.getIdentifier();
        }
        else if(dataType.equals("ProvidesConvertNamespace"))
        {        		
        		dataValue = getRemoteNamespace((MContained)iface, "::");
        }
        else 
        {
            dataValue = super.data_MProvidesDef(dataType, dataValue);
        }
        logger.fine("end");
        return dataValue;
    }

    
    protected String data_MUsesDef(String dataType, String dataValue)
    {
        logger.fine("begin");
		logger.finer("dataType = " + dataType + ", dataValue = "+ dataValue);
        MUsesDef usesDef = (MUsesDef) currentNode;
        MComponentDef component = usesDef.getComponent();
        MInterfaceDef iface = (MInterfaceDef)usesDef.getUses();
        
        if (dataType.equals("UsesInclude")) 
        {
            dataValue = getUsesInclude(usesDef);
        }
        else if(dataType.equals("UsesConvertInclude")) 
        {
            dataValue = getUsesConvertInclude(usesDef);
        }
        else if(dataType.equals("CCM_UsesType")) 
        {
            dataValue = getCCM_UsesType(usesDef);
        }
        else if(dataType.equals("IdlUsesType")) 
        {
            dataValue = getIdlUsesType(usesDef);
        }
        else if(dataType.equals("UsesType")) 
        {
            dataValue = getUsesType(usesDef);
        }
        else if(dataType.equals("InterfaceType")) 
        {
            dataValue = usesDef.getUses().getIdentifier();
        }
        else if (dataType.equals("ComponentType")) 
        {
            dataValue = component.getIdentifier();
        }
        else if(dataType.equals("UsesConvertNamespace"))
        {        		
        		dataValue = getRemoteNamespace((MContained)iface, "::");
        }
        else 
        {
            super.data_MUsesDef(dataType, dataValue);
        }
        logger.fine("end");                
        return dataValue;
    }



    
    //====================================================================
    // Write source files methods
    //====================================================================

    /**
     * Write generated code to an output file. Each global template consists of
     * two sections separated by " < < < < < < <SPLIT>>>>>>>" that are written
     * in two different files node_name + "_remote.h" and node_name +
     * "_remote.cc"
     * 
     * @param template
     *            the template object to get the generated code structure from;
     *            variable values should come from the node handler object.
     */
    public void writeOutput(Template template) 
    		throws IOException
    {
        logger.fine("begin");
        String sourceCode = template.substituteVariables(output_variables);
        String[] sourceFiles = sourceCode.split("<<<<<<<SPLIT>>>>>>>");
        String[] remoteSuffix = {"_remote.h", "_remote.cc"};
        String generatorPrefix = ConfigurationLocator.getInstance().get("ccmtools.dir.gen");
        
        for(int i = 0; i < sourceFiles.length; i++)
        {
            if(sourceFiles[i].trim().equals("")) 
            {
                // skip the file creation
                continue;
            }

            try 
            {
                outputDirectories = new HashSet<String>();
                
                if(currentNode instanceof MComponentDef) 
                {
                    // write the component files
                		MComponentDef component = (MComponentDef)currentNode;
                    String componentName = component.getIdentifier();
                    String namespace = getRemoteNamespace((MContained)component,Text.MANGLING_SEPARATOR);
                    String fileDir = generatorPrefix + namespace.substring(0, namespace.length()-1);
                    writeFile(uiDriver, output_dir, fileDir, componentName + remoteSuffix[i],
                                   SourceFileHelper.prettifySourceCode(sourceFiles[i]));
                }
                else if(currentNode instanceof MHomeDef) 
                {
                    // write the home files
                    MHomeDef home = (MHomeDef) currentNode;
                    MComponentDef component =  home.getComponent();
                    String homeName = home.getIdentifier();
                    String namespace = getRemoteNamespace((MContained)component,Text.MANGLING_SEPARATOR);
                    String fileDir = generatorPrefix + namespace.substring(0, namespace.length()-1);

                    writeFile(uiDriver, output_dir, fileDir, homeName + remoteSuffix[i],
                                   SourceFileHelper.prettifySourceCode(sourceFiles[i]));
                }
                else if(currentNode instanceof MInterfaceDef || currentNode instanceof MAliasDef
                        || currentNode instanceof MStructDef || currentNode instanceof MExceptionDef
                        || currentNode instanceof MEnumDef) 
                {
                    // write converter files
                		MContained contained = (MContained) currentNode;
                    String nodeName = contained.getIdentifier();
                    String fileDir = generatorPrefix + getRemoteNamespace(contained, "_") 
                    		+ CORBA_CONVERTER_DIR;

                    writeFile(uiDriver, output_dir, fileDir, nodeName + remoteSuffix[i],
                                   SourceFileHelper.prettifySourceCode(sourceFiles[i]));
                }
                else 
                {
                    throw new RuntimeException("Unhandled model element type!");
                }
                
                Confix.writeConfix2Files(uiDriver, outputDirectories);
            }
            catch(Exception e) 
            {
            		e.printStackTrace();
                uiDriver.printError("!!!Error " + e.getMessage());
            }
        }
        logger.fine("end");
    }

    
    protected void writeFile(UserInterfaceDriver driver, File outDir, String directory, String file, String output) 
        throws IOException
    {
        outputDirectories.add(output_dir + File.separator + directory);
        SourceFileHelper.writeFile(driver, outDir, directory, file, output);
    }
    
    
    
    //====================================================================
    // Handle the C++ data types
    //====================================================================

    /**
     * Override a super class method to add a local namespace to operation
     * parameters (if they are not primitive types). Refactoring: move it to the
     * super class...
     */
    protected String getBaseLanguageType(MTyped object)
    {
        logger.fine("getBaseLanguageType()");
        
        StringBuffer buffer = new StringBuffer();
        if (object instanceof MParameterDef || object instanceof MOperationDef) 
        {
            MIDLType idlType = object.getIdlType();
            if(idlType instanceof MPrimitiveDef 
            		|| idlType instanceof MStringDef
                || idlType instanceof MWstringDef) 
            {
                buffer.append(super.getBaseLanguageType(object));
            }
            else 
            {
                buffer.append(super.getBaseLanguageType(object));
            }
        }
        else 
        {
            // Only add namespace to operation parameters
            buffer.append(super.getBaseLanguageType(object));
        }
        return buffer.toString();
    }

    /**
     * Converts the CCM model type information (MTyped) to the corresponding
     * local C++ types.
     * 
     * @param object
     *            Reference to an element of the CCM model.
     * @return Generated code for the local C++ type as string.
     */
    public String getLanguageType(MTyped object)
    {
		logger.fine("getLanguageType(\"" + object + "\")");

		String base_type = getBaseIdlType(object);
		// override IDL array mapping from parent function.
		if (object instanceof MArrayDef)
		{
			Iterator i = ((MArrayDef) object).getBounds().iterator();
			Long bound = (Long) i.next();
			String result = base_type + "[" + bound;
			while (i.hasNext())
				result += "][" + (Long) i.next();
			return result + "]";
		}
		return super.getLanguageType(object);
	}

    
    
    
    // ====================================================================
    // Handle the CORBA data types
    //====================================================================

    /**
     * Extract the scoped CORBA type from a MTyped entity. TODO: Refactoring,
     * this method is a subset of getCORBALanguageType()
     */
    protected String getCorbaType(MTyped type)
    {
        logger.fine("begin");
        
        MIDLType idlType = type.getIdlType();
		String baseType = getBaseIdlType(type);
		String corbaType = "";

		if (corbaMappings.containsKey(baseType))
		{
			// Primitive data types are mapped via map.
			corbaType = (String) corbaMappings.get(baseType);
		}
		else if (idlType instanceof MTypedefDef)
		{
			corbaType = getCorbaStubNamespace((MContained) currentNode, "::") + baseType;
		}
		else
		{
			throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
		}
		logger.fine("end");
		return corbaType;
    }

    
    protected String generateCorbaOperationResultType(MIDLType idlType)
    {
    		logger.fine("begin");
	    	logger.finer("idlType = " + idlType);
		String absoluteCorbaName = getAbsoluteCorbaType(idlType, "::");
		String suffix="";

		if(idlType instanceof MAliasDef)
		{
			// unwind AliasDef
			idlType = ((MTyped) idlType).getIdlType();
		}
		
		if (idlType instanceof MPrimitiveDef 
        			|| idlType instanceof MEnumDef
                || idlType instanceof MStringDef
                || idlType instanceof MWstringDef) 
        {
            // no suffix
        }
        else if(idlType instanceof MArrayDef) 
        {
            suffix = "_slice*";
        }
        else
        {
            suffix = "*";
        }	
		logger.fine("end");
        return absoluteCorbaName + suffix;
    }

    
    /**
     * Handle the CORBA specific mapping rules for operation parameters.
     * 
     * see Henning/Vinoski 
     */
    protected String generateCorbaParameterType(MParameterDef parameter)
    {    		
    		logger.fine("begin");
    		MIDLType idlType = parameter.getIdlType();
		String absoluteCorbaType = getAbsoluteCorbaType(idlType, "::");
    		String prefix = "const ";
		String suffix = "&";
		MParameterMode direction = parameter.getDirection();

		if(idlType instanceof MAliasDef)
		{
			// unwind AliasDef
			idlType = ((MTyped) idlType).getIdlType();
		}
		
		// IN Parameter
		if (direction == MParameterMode.PARAM_IN)
		{
			if (idlType instanceof MPrimitiveDef 
					|| idlType instanceof MEnumDef 
					|| idlType instanceof MArrayDef)
			{
				prefix = "";
				suffix = "";
			}
			else if (idlType instanceof MStringDef)
			{
				suffix = "";
			}
			else if (idlType instanceof MInterfaceDef)
			{
				prefix = "";
				suffix = "_ptr";
			}
		}
		// OUT Parameter
		else if (direction == MParameterMode.PARAM_OUT)
		{
			if (idlType instanceof MStringDef)
			{
				// OUT string is a special case
				return "CORBA::String_out";
			}
			else
			{
				return absoluteCorbaType + "_out";
			}
		}
		// INOUT Parameter
		else if (direction == MParameterMode.PARAM_INOUT)
		{
			prefix = "";
			if (idlType instanceof MArrayDef)
			{
				suffix = "";
			}
		}
		logger.fine("end");
		return prefix + absoluteCorbaType + suffix;
	}
    
    
    // ====================================================================
    // MSequenceDef %(tag)s helper methods
    // ====================================================================
    
    protected String getConvertFromCorbaDeclaration(MContained contained) 
    {
    		logger.fine("begin");
        String stubName = getAbsoluteCorbaType(contained,Text.SCOPE_SEPARATOR);
        String localName = getLocalCxxName(contained,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void convertFromCorba(const ");
        code.append(Text.SCOPE_SEPARATOR).append(stubName);
        code.append("& in, ");
        code.append(localName);
        code.append("& out);");
        logger.fine("end");
        return code.toString();
    }
        
    protected String getConvertToCorbaDeclaration(MContained contained) 
    {
    		logger.fine("begin");
        String localName = getLocalCxxName(contained,Text.SCOPE_SEPARATOR);
        String stubName = getAbsoluteCorbaType(contained,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void convertToCorba(const ");
        code.append(localName);
        code.append("& in, ");
        code.append(Text.SCOPE_SEPARATOR).append(stubName);
        code.append("& out);");
        logger.fine("end");
        return code.toString();
    }

    protected String getConvertFromCorbaImplementation(MContained contained, MTyped singleType)
    {
    		logger.fine("begin");
        String stubName = getAbsoluteCorbaType(contained,Text.SCOPE_SEPARATOR);
        String localName = getLocalCxxName(contained,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void\n");
        code.append("convertFromCorba(const "); 
        code.append(Text.SCOPE_SEPARATOR).append(stubName);
        code.append("& in, ");
        code.append(localName);
        code.append("& out)\n");
        code.append("{\n");
        code.append(TAB);
        code.append(getConvertAliasFromCORBA(singleType));
        code.append("}\n");
        logger.fine("end");
        return code.toString();
    }

    protected String getConvertToCorbaImplementation(MContained contained, MTyped singleType) 
    {
    		logger.fine("begin");
        String localName = getLocalCxxName(contained, Text.SCOPE_SEPARATOR);
        String stubName = getAbsoluteCorbaType(contained,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void\n");
        code.append("convertToCorba(const "); 
        code.append(localName); 
        code.append("& in, "); 
        code.append(Text.SCOPE_SEPARATOR).append(stubName);
        code.append("& out)\n");
        code.append("{\n");
        code.append(TAB);
        code.append(getConvertAliasToCORBA(singleType));
        code.append("}\n");
        logger.fine("end");
        return code.toString();
    }

    
    protected String getSingleValue(MTyped singleType) 
    {
    		logger.fine("begin");
		StringBuffer code = new StringBuffer();
		MIDLType singleIdlType = singleType.getIdlType();
		if (singleIdlType instanceof MPrimitiveDef 
				|| singleIdlType instanceof MStringDef
				|| singleIdlType instanceof MWstringDef)
		{
			code.append(getBaseLanguageType(singleType));
		}
		else
		{
			// TODO: Handle local Namespace
			code.append(getBaseLanguageType(singleType));
		}
		logger.fine("end");
		return code.toString();
    }
    
    protected String getInOutValue(MTyped singleType) 
    {
    		logger.fine("begin");
        MIDLType singleIdlType = singleType.getIdlType();
		StringBuffer code = new StringBuffer();
		if (singleIdlType instanceof MStringDef)
		{
			code.append("out[i].inout()");
		}
		else
		{
			code.append("out[i]");
		}
		logger.fine("end");
        return code.toString();
    }

    
    protected String getCORBASequenceConverterInclude(MIDLType idlType, MIDLType singleIdlType) 
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        if (singleIdlType instanceof MPrimitiveDef 
                || singleIdlType instanceof MStringDef
                || idlType instanceof MWstringDef) 
        {
        		// no include statement needed for primitive types
        }
        else
        {
        		MContained singleContained = (MContained) singleIdlType;
        		code.append(generateCorbaConverterConfixInclude((MContained)currentNode, singleContained, 
        				singleContained.getIdentifier()));
        }
        logger.fine("end");
        return code.toString();
    }
    
    protected String getConvertAliasFromCORBA(MTyped singleType) 
    {
    		logger.fine("begin");
	    	MIDLType idlType = singleType.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("out.clear();\n");
        code.append(TAB).append("out.reserve(in.length());\n");
        code.append(TAB);
        code.append("for(unsigned long i=0; i < in.length();i++) {\n");
        code.append(TAB2).append(getSingleValue(singleType));
        code.append(" singleValue;\n");
        code.append(TAB2).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(in[i], singleValue);\n");
        code.append(TAB2).append("out.push_back(singleValue);\n");
        code.append(TAB).append("}\n");
        logger.fine("end");
        return code.toString();
    }

    protected String getConvertAliasToCORBA(MTyped singleType) 
    {
    		logger.fine("begin");
    		MIDLType idlType = singleType.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("out.length(in.size());\n");
        code.append(TAB);
        code.append("for(unsigned long i=0; i < in.size(); i++) {\n");
        code.append(TAB2).append(getSingleValue(singleType));
        code.append(" singleValue = in[i];\n");
        code.append(TAB2).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(singleValue, ");
        code.append(getInOutValue(singleType)).append(");\n");
        code.append(TAB).append("}\n");
        logger.fine("end");
        return code.toString();
    }
    
    
    
    //====================================================================
    // MFieldDef tags helper methods
    //====================================================================
    
    protected String getCORBAFieldDirection(MIDLType idlType, String fieldName, int direction) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
		code.append(fieldName);
		if (idlType instanceof MStringDef)
		{
			switch (direction)
			{
			case IN:
				code.append(".in()");
				break;
			case OUT:
				code.append(".out()");
				break;
			case INOUT:
				code.append(".inout()");
				break;
			}
		}
		logger.fine("end");
        return code.toString();
    }
    

    protected String getCORBAFieldConverterInclude(MIDLType idlType, String fieldName) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        if (idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef
                || idlType instanceof MWstringDef) 
        {
            // no include statement needed for primitive types
        }
        else if(idlType instanceof MContained)
        {
        		MFieldDef field = (MFieldDef)currentNode;
        		MContained contained = (MContained)idlType;   
        		MContained base;
        		if(field.getStructure() != null)
        		{
        			base = field.getStructure();
        		}
        		else if(field.getException() != null)
        		{
        			base = field.getException();
        		}
        		else
        		{
        			throw new RuntimeException("MFieldDef without MStructDef or MExceptionDef!");
        		}
        		code.append(generateCorbaConverterConfixInclude(base, contained, contained.getIdentifier()));
        }
        else 
        {
        		throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // MAttributeDef tags helper methods
    //====================================================================

    protected String getCorbaAttributeResult(MTyped type)
    {
        logger.fine("begin");
		MIDLType idlType = type.getIdlType();
		String corbaType = getAbsoluteCorbaType(idlType, "::");
        String dataValue = "";// = getAbsoluteCorbaType(idlType, "::");
		if (idlType instanceof MPrimitiveDef 
				|| idlType instanceof MStringDef 
				|| idlType instanceof MWstringDef)
		{
			dataValue = corbaType;
		}
        else if (idlType instanceof MEnumDef)
        {
            dataValue = "::" + corbaType;
        }
		else if (idlType instanceof MStructDef)
		{
			dataValue = "::" + corbaType + "*";
		}
		else if (idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();

			if (containedIdlType instanceof MPrimitiveDef)
			{
				dataValue = "::" + corbaType;
			}
			else if (containedIdlType instanceof MSequenceDef)
			{
				dataValue = "::" + corbaType + "*";
			}
			// TODO: MArrayDef
		}
		logger.fine("end");
		return dataValue;
    }
    

    protected String getCorbaAttributeParameter(MTyped type)
    {
        logger.fine("begin");
        MIDLType idlType = type.getIdlType();
        String corbaType = getAbsoluteCorbaType(idlType, "::");
        String dataValue = "";//"::" + getAbsoluteCorbaType(idlType, "::");
       
        if (idlType instanceof MPrimitiveDef 
        			|| idlType instanceof MStringDef 
        			|| idlType instanceof MWstringDef)
		{
			dataValue = corbaType;
		}
        else if (idlType instanceof MEnumDef)
        {
            dataValue = "::" + corbaType;
        }
		else if (idlType instanceof MStructDef)
		{
			dataValue = "::" + corbaType + "&";
		}
		else if (idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();
			if (containedIdlType instanceof MPrimitiveDef)
			{
				dataValue = "::" + corbaType;
			}
			else if (containedIdlType instanceof MSequenceDef)
			{
				dataValue = "::" + corbaType + "&";
			}
			// TODO: MArrayDef
		}
        logger.fine("end");
		return dataValue;
	}
    

    /**
	 * Generates the scoped (with namespace) local type of an attribute. -
	 * primitive types must not have namespaces. - attributes are always passed
	 * by value.
	 * 
	 * @param object
	 * @return
	 */
    protected String getLocalAttributeType(MTyped object)
    {
        logger.fine("begin");
        MIDLType idlType = object.getIdlType();
        String dataValue;
        
        if (idlType instanceof MPrimitiveDef 
        		|| idlType instanceof MStringDef 
        		|| idlType instanceof MWstringDef)
		{
			dataValue = getLanguageType(object);
		}
		else if (idlType instanceof MStructDef || idlType instanceof MEnumDef)
		{
			dataValue = getLocalCxxName((MContained) idlType, "::");
		}
		else if (idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();
			if (containedIdlType instanceof MPrimitiveDef)
			{
				dataValue = getLanguageType(object);
			}
			else if (containedIdlType instanceof MSequenceDef)
			{
				dataValue = getLocalCxxName((MContained) idlType, "::");
			}
			else
			{
				String message = "Unhandled alias type in getLocalAttributeResult(): ";
				throw new RuntimeException(message + containedIdlType);
			}
		}
		else
		{
			throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
		}
		logger.fine("end");
        return dataValue;
    }
    
    
    /**
	 * Creates the code that converts CORBA attribute getter methods to local
	 * C++ calls.
	 * 
	 * @param attr
	 *            An AttributeDef item of a CCM model.
	 * @return A string containing the generated code.
	 */
    protected String convertGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("begin");
		MIDLType idlType = attr.getIdlType();
		String code = "";

		if (idlType instanceof MPrimitiveDef 
				|| idlType instanceof MStringDef 
				|| idlType instanceof MWstringDef
				|| idlType instanceof MEnumDef)
		{
			code = convertPrimitiveGetAttributeFromCorba(attr, delegate);
		}
		else if (idlType instanceof MStructDef)
		{
			code = convertUserGetAttributeFromCorba(attr, delegate);
		}
		else if (idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();
			if (containedIdlType instanceof MPrimitiveDef)
			{
				code = convertPrimitiveGetAttributeFromCorba(attr, delegate);
			}
			else if (containedIdlType instanceof MSequenceDef)
			{
				code = convertUserGetAttributeFromCorba(attr, delegate);
			}
			// TODO: MArrayDef
		}
		logger.fine("end");
		return code;
	}

    protected String convertPrimitiveGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();	
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MEnumDef) 
        {
            code.append(TAB);
            code.append(getBaseLanguageType((MTyped) attr));
            code.append(" result;\n");
        }
        else 
        {
            code.append(TAB).append(getBaseLanguageType((MTyped)attr));
            code.append(" result;\n");
        }
        code.append(TAB).append("result = ").append(delegate);
        code.append("->").append(attr.getIdentifier()).append("();\n");
        code.append(TAB);
        if(idlType instanceof MEnumDef || idlType instanceof MAliasDef) 
        {
            code.append("::");
        }
        code.append(getAbsoluteCorbaType(idlType, "::"));
        code.append(" return_value;\n");
        code.append(TAB);
        code.append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(result, return_value);\n");
        code.append(TAB).append("return return_value;\n");
        logger.fine("end");
        return code.toString();
    }
        
    protected String convertUserGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB);
        code.append(getBaseLanguageType((MTyped) attr)).append(" result;\n");
        code.append(TAB).append("result = ").append(delegate);
        code.append("->").append(attr.getIdentifier()).append("();\n");
        code.append(TAB).append("::").append(getAbsoluteCorbaType(idlType, "::"));
        code.append("_var return_value = new ");
        code.append("::").append(getAbsoluteCorbaType(idlType, "::")).append(";\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(result, return_value);\n");
        code.append(TAB).append("return return_value._retn();\n");
        logger.fine("end");
        return code.toString();
    }    
    
    
    /**
     * Creates the code that converts CORBA attribute setter methods to local C++ calls.
     * @param attr An AttributeDef item of a CCM model.
     * @return A string containing the generated code.
     */
    protected String convertSetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
       
        if(idlType instanceof MPrimitiveDef 
        		|| idlType instanceof MStringDef 
            || idlType instanceof MWstringDef) 
        {
            code.append(TAB);
            code.append(getBaseLanguageType((MTyped) attr));
            code.append(" local_value;\n");   
        }
        else 
        {
            code.append(TAB);
    			code.append(getBaseLanguageType((MTyped) attr));
            code.append(" local_value;\n");  
        }
        code.append(TAB);
        code.append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(value, local_value);\n");
        code.append(TAB);
        code.append(delegate).append("->").append(attr.getIdentifier()).append("(local_value);\n");
        logger.fine("end");
        return code.toString();    
    }    
    
    
    /**
     * Generate attribute converter logic for getter methods from C++ to CORBA.
     *  
     * @param attr    
     * @return Generated source code in a String.
     *     
     * TODO: Try to handle this in a template
     */
    protected String convertGetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        String code;
        
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef
                || idlType instanceof MEnumDef) 
        {	
            code = convertPrimitiveGetAttributeToCorba(attr);
        }
		else if (idlType instanceof MStructDef)
		{
			code = convertUserGetAttributeToCorba(attr);
		}
		else if (idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();
			if (containedIdlType instanceof MPrimitiveDef)
			{
				code = convertPrimitiveGetAttributeToCorba(attr);
			}
			else if (containedIdlType instanceof MSequenceDef)
			{
				code = convertUserGetAttributeToCorba(attr);
			}
			else
			{
				throw new RuntimeException("Unhandled idlType type (" + containedIdlType + ")!");
			}
		}
		else
		{
			throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
		}
		return code;
	}
    
    protected String convertPrimitiveGetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB);
        if(idlType instanceof MEnumDef || idlType instanceof MAliasDef)
        {
            code.append("::");
        }
        code.append(getAbsoluteCorbaType(idlType, "::")).append(" result;\n");
        code.append(TAB).append("result = remoteInterface->").append(attr.getIdentifier()).append("();\n");
        code.append(TAB).append(getLocalAttributeType(attr)).append(" return_value;\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(result, return_value);\n");
        code.append(TAB).append("return return_value;\n");
        logger.fine("end");	
        return code.toString();
    }
        
    protected String convertUserGetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("::").append(getAbsoluteCorbaType(idlType, "::")).append("_var result;\n");
        code.append(TAB).append("result = remoteInterface->").append(attr.getIdentifier()).append("();\n");
        code.append(TAB).append(getLocalAttributeType(attr)).append(" return_value;\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(result, return_value);\n");
        code.append(TAB).append("return return_value;\n");
        logger.fine("end");
        return code.toString();
    }
    
    
    /**
     * Generate attribute converter logic for setter methods from C++ to CORBA.
     * 
     * @param attr
     * @return Generated source code in a String.
     * 
     * TODO: Try to handle this in a template
     */
    protected String convertSetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        String code;
       
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef
                || idlType instanceof MEnumDef) 
        {
            code = convertPrimitiveSetAttributeToCorba(attr);
        }
		else if(idlType instanceof MStructDef)
		{
			code = convertUserSetAttributeToCorba(attr);
		}
		else if(idlType instanceof MAliasDef)
		{
			MTyped containedType = (MTyped) idlType;
			MIDLType containedIdlType = containedType.getIdlType();
			if (containedIdlType instanceof MPrimitiveDef)
			{
				code = convertPrimitiveSetAttributeToCorba(attr);
			}
			else if(containedIdlType instanceof MSequenceDef)
			{
				code = convertUserSetAttributeToCorba(attr);
			}
			else
			{
				throw new RuntimeException("Unhandled idlType (" + containedIdlType + ")!");
			}
		}
		else
		{
			throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
		}
        logger.fine("end");
        return code;
	}
    
    protected String convertPrimitiveSetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();    
        code.append(TAB);
        if(idlType instanceof MEnumDef || idlType instanceof MAliasDef)
        {
            code.append("::");
        }
        code.append(getAbsoluteCorbaType(idlType, "::")).append(" remote_value;\n");   
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(value, remote_value);\n");
        code.append(TAB).append("remoteInterface->").append(attr.getIdentifier());
        code.append("(remote_value);\n");
        logger.fine("end");
        return code.toString();
    }
 
    protected String convertUserSetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("begin");
        MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("::").append(getAbsoluteCorbaType(idlType, ""));
        code.append("_var remote_value = new ");
        code.append("::").append(getAbsoluteCorbaType(idlType, "::")).append(";\n");   
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(value, remote_value);\n");
        code.append(TAB).append("remoteInterface->").append(attr.getIdentifier());
        code.append("(remote_value);\n");
        logger.fine("end");
        return code.toString();
    }    
    
    
    protected String getAttributeConvertInclude(MIDLType idlType, String baseType)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef) 
        {
            // no include statement needed for primitive types
        }
        else if(idlType instanceof MContained)
        {   
        		code.append(generateCorbaConverterConfixInclude((MContained)currentNode, (MContained)idlType, baseType));
        }
        logger.fine("end");
        return code.toString(); 
    }
    
    
    
    
    //====================================================================
    // MOperationDef tags helper methods
    //====================================================================
    
    /**
     * Creates code that describes the parameter list of the operation using
     * CORBA data types.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String getCorbaOperationParams(MOperationDef op)
    {
        logger.fine("begin");
        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef parameter = (MParameterDef) params.next();
            ret.add(generateCorbaParameterType(parameter) + " " + parameter.getIdentifier());            
        }
        logger.fine("end");
        return Text.join(", ", ret);
    }

    
    /**
     * Creates the code that declares the exceptions to the given operation in
     * CORBA style. Note that every CORBA operation can throw the
     * CORBA::SystemException.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String getCORBAExcepts(MOperationDef op)
    {
        logger.fine("");
        
        List code = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) 
        {
            MExceptionDef IdlException = (MExceptionDef) es.next();
            code.add(getAbsoluteCorbaType(IdlException, "::"));
        }

        if (code.size() > 0) 
        {
            return "throw(CORBA::SystemException, " + Text.join(", ", code) + ")";
        }
        else {
            return "throw(CORBA::SystemException)";
        }
    }

    
    protected String getLocalOperationParams(MOperationDef op)
    {
        logger.fine("begin");

        List list = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            list.add(getLanguageType(p) + " " + p.getIdentifier());
        }
        logger.fine("end");
        return Text.join(", ", list);
    }


    /**
     * overwrite CppGenerator.getOperationExcepts() 
     * Refactoring: move this method up to CppGenerator
     */
    protected String getOperationExcepts(MOperationDef op)
    {
        logger.fine("");
        
        List code = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) 
        {
            MExceptionDef idlException = (MExceptionDef) es.next();
            code.add(getLocalCxxName(idlException, "::"));
        }
        if (code.size() > 0) 
        {
            return ", " + Text.join(", ", code) + ")";
        }
        else 
        {
            return ")";
        }
    }

    
	protected String getOperationConvertInclude(MIDLType idlType, String baseType) 
	{
		logger.fine("begin");
	    StringBuffer code = new StringBuffer();
	    if (idlType instanceof MPrimitiveDef 
	            || idlType instanceof MStringDef) 
	    {
            // no include statement needed for primitive types
        }
        else if(idlType instanceof MContained)
        {
        		code.append(generateCorbaConverterConfixInclude((MContained)currentNode, (MContained)idlType, baseType));
        }
	    logger.fine("end");
        return code.toString();
    }
    
	protected String getParameterConvertInclude(MOperationDef op) 
	{
		logger.fine("begin");
	    StringBuffer code = new StringBuffer();
        for (Iterator i = op.getParameters().iterator(); i.hasNext();) 
        {
            MParameterDef parameter = (MParameterDef) i.next();
            MTyped parameterType = (MTyped) parameter;
            MIDLType parameterIdlType = parameterType.getIdlType();
            if (parameterIdlType instanceof MPrimitiveDef
                    || parameterIdlType instanceof MStringDef) 
            {
                // no include statement needed for primitive types
            }
            else if(parameterIdlType instanceof MContained)
            {
            		code.append(generateCorbaConverterConfixInclude((MContained)currentNode, 
            				(MContained)parameterIdlType, getBaseIdlType(parameter)));
            }
        }
        logger.fine("end");
        return code.toString();
    }
	
    protected String getExceptionConvertInclude(MOperationDef op) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for (Iterator i = op.getExceptionDefs().iterator(); i.hasNext();) 
        {
        		MExceptionDef exception = (MExceptionDef) i.next();
        		code.append(generateCorbaConverterConfixInclude((MContained)currentNode, exception, 
        				exception.getIdentifier()));
        }
        logger.fine("end");
        return code.toString();
    }

    
    /**
     * Creates the code that converts the CORBA parameters to local C++ types.
     * Note that only the in and inout parameters are converted.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertParameterToCpp(MOperationDef op)
    {
        logger.fine("begin");
        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();
            if(isPrimitiveType(idlType)) 
            {
                ret.add(convertPrimitiveParameterFromCorbaToCpp(p));
            }
            else if(isComplexType(idlType)) 
            {
                ret.add(convertUserParameterFromCorbaToCpp(p));
            }
        }
        logger.fine("end");
        return Text.join("\n", ret) + "\n";
    }

    protected String convertPrimitiveParameterFromCorbaToCpp(MParameterDef p)
    {
        logger.fine("begin");
        MIDLType idlType = ((MTyped) p).getIdlType();
        MParameterMode direction = p.getDirection();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append(getBaseLanguageType(p));
        code.append(" parameter_").append(p.getIdentifier()).append(";\n");
        if(direction != MParameterMode.PARAM_OUT) 
        {
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertFromCorba(");
            code.append(p.getIdentifier()).append(", parameter_");
            code.append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }

    protected String convertUserParameterFromCorbaToCpp(MParameterDef p)
    {
        logger.fine("begin");        
        MParameterMode direction = p.getDirection();
        MIDLType idlType = ((MTyped) p).getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        StringBuffer code = new StringBuffer();
        code.append(TAB);
        code.append(getLocalCxxName(contained,Text.SCOPE_SEPARATOR));
        code.append(" parameter_").append(p.getIdentifier()).append(";\n");
        if(direction != MParameterMode.PARAM_OUT) 
        {
            code.append(TAB);
            code.append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertFromCorba(");
            code.append(p.getIdentifier()).append(", parameter_");
            code.append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }

    
    /**
     * Create the code that declares the variable (C++ type and name) in which
     * the result value will be stored.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String declareCppResult(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            // no code
        }
        else if(isPrimitiveType(idlType)) 
        {
            code.append(TAB).append(getBaseLanguageType(op)).append(" result;\n");
        }
        else if(isComplexType(idlType)) 
        {
            MTypedefDef typedef = (MTypedefDef) idlType;
            MContained contained = (MContained) typedef;
            code.append(TAB).append(getLocalCxxName(contained,Text.SCOPE_SEPARATOR));
            code.append(" result;\n");
        }
        logger.fine("end");
        return code.toString();
    }

    
    protected String convertInterfaceMethodToCpp(MOperationDef op)
    {
        logger.fine("");        
        List ret = new ArrayList();
        String resultPrefix = Text.tab(2);
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            // void foo()
        }
        else 
        {
            resultPrefix += "result = ";
        }

        if(isPrimitiveType(idlType) 
                || isComplexType(idlType)) 
        {
            resultPrefix += "localInterface->" + op.getIdentifier() + "(";
            for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
            {
                MParameterDef p = (MParameterDef) params.next();
                ret.add(" parameter_" + p.getIdentifier());
            }
            return resultPrefix + Text.join(", ", ret) + ");";
        }
        else if(idlType instanceof MInterfaceDef) 
        {
            return "// fixme: convertInterfaceMethodToCpp()";
        }
        else 
        {
            throw new RuntimeException("Unhandled idlType " + idlType + ")!");
        }
    }
        
    
    /**
     * Creates the code that converts the exception list of an operation into
     * catch statements for local exceptions that throw corresponding remote
     * exceptions.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertExceptionsToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        StringBuffer code = new StringBuffer();
        for(Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) 
        {
            MExceptionDef exception = (MExceptionDef) es.next();
            code.append(TAB).append("catch(const ").append(getLocalCxxName(exception, "::")).append("& ce) {\n");            
            code.append(TAB2).append(getAbsoluteCorbaType(exception, "::")).append(" te;\n");
            code.append(TAB2).append(getRemoteNamespace(exception, "::"));
            code.append("convertToCorba(ce, te);\n");
            code.append(TAB2).append("throw te;\n");
            code.append(TAB).append("}\n");
        }
        logger.fine("end");
        return code.toString();
    }

    
    /**
     * Create the code for the remote facet and supports adapter.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertParameterToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idl_type = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idl_type)) 
            {
                ret.add(convertPrimitiveParameterFromCppToCorba(p));
            }
            else if(idl_type instanceof MStructDef || idl_type instanceof MEnumDef) 
            {
                ret.add(convertUserParameterFromCppToCorba(p));
            }
            else if(idl_type instanceof MAliasDef) 
            {
                MTyped containedType = (MTyped) idl_type;
                MIDLType containedIdlType = containedType.getIdlType();
                if(isPrimitiveType(containedIdlType)) 
                {
                    ret.add(convertPrimitiveParameterFromCppToCorba(p));
                }
                else 
                {
                    ret.add(convertUserParameterFromCppToCorba(p));
                }
            }
        }
        logger.fine("end");
        return Text.join("\n", ret) + "\n";
    }

    protected String convertPrimitiveParameterFromCppToCorba(MParameterDef p)
    {
        logger.fine("begin");       
        StringBuffer code = new StringBuffer();        
        MIDLType idlType = p.getIdlType();
        MParameterMode direction = p.getDirection();
        if(direction != MParameterMode.PARAM_IN) 
        {
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertToCorba(parameter_");
            code.append(p.getIdentifier()).append(", ").append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }

    protected String convertUserParameterFromCppToCorba(MParameterDef p)
    {
        logger.fine("begin");
        MIDLType idlType = p.getIdlType();
        MParameterMode direction = p.getDirection();
        StringBuffer code = new StringBuffer();
        if(direction == MParameterMode.PARAM_IN) 
        {
            // do nothing
        }
        else if(direction == MParameterMode.PARAM_INOUT) 
        {
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertToCorba(parameter_").append(p.getIdentifier()).append(", ");
            code.append(p.getIdentifier()).append(");\n");
        }
        else 
        {
        		code.append(TAB).append(p.getIdentifier()).append(" = new ");
        		code.append(getAbsoluteCorbaType((MContained)idlType, "::")).append(";\n");
        		code.append(TAB).append(getCorbaConverterNamespace(idlType, "::")).append("convertToCorba(parameter_");
        		code.append( p.getIdentifier()).append(", ").append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    /**
     * Create the code that converts the result value as well as the inout and
     * out parameters from local C++ to CORBA types.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertResultToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        List ret = new ArrayList();
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            return "";
        }

        if(isPrimitiveType(idlType)) 
        {
            ret.add(convertPrimitiveResultFromCppToCorba(op));
        }
        else if(idlType instanceof MStructDef 
                || idlType instanceof MSequenceDef
                || idlType instanceof MEnumDef) 
        {
            ret.add(convertUserResultFromCppToCorba(op));
        }
        else if(idlType instanceof MAliasDef) 
        {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
   
            if(isPrimitiveType(containedIdlType)) 
            {
                ret.add(convertPrimitiveResultFromCppToCorba(op));
            }
            else if(isComplexType(containedIdlType)) 
            {
                ret.add(convertUserResultFromCppToCorba(op));
            }
            else 
            {
                throw new RuntimeException("Unhadled idlType (" + containedIdlType + ")!");
            }
        }
        else if(idlType instanceof MInterfaceDef) 
        {
            ret.add("// fixme: convertResultToCorba()");
        }
        else 
        {
            throw new RuntimeException("Unhandled idlType" + idlType + ")!");
        }
        logger.fine("end");
        return Text.join("\n", ret);
    }

    protected String convertPrimitiveResultFromCppToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        String base_type = getBaseIdlType(op);
        StringBuffer code = new StringBuffer();
        // Convert the result iff the result type is not void
        if(!base_type.equals("void")) 
        {
            code.append(TAB).append(getAbsoluteCorbaType(idlType, "::")).append(" return_value;\n");
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertToCorba(result, return_value);\n");
            code.append(TAB).append("return return_value;\n");
        }
        logger.fine("end");
        return code.toString();
    }

    protected String convertUserResultFromCppToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        StringBuffer code = new StringBuffer();
        MIDLType idlType = op.getIdlType();
        code.append(TAB).append(getAbsoluteCorbaType((MContained)idlType, "::")).append("_var ");
        code.append("return_value = new ").append(getAbsoluteCorbaType((MContained)idlType, "::")).append(";\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(result, return_value);\n");
        code.append(TAB).append("return return_value._retn();\n");        
        logger.fine("end");
        return code.toString();
    }

    
    /**
     * Creates code that converts the local C++ parameters to CORBA types. Note
     * that only the in and inout parameters are converted.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertReceptacleParameterToCorba(MOperationDef op)
    {
        logger.fine("begin");        
        List list = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idlType)) 
            {
                list.add(convertPrimitiveParameterToCorba(p));
            }
            else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) 
            {
                list.add(convertUserParameterToCorba(p));
            }
            else if(idlType instanceof MAliasDef) 
            {
                MTyped containedType = (MTyped) idlType;
                MIDLType containedIdlType = containedType.getIdlType();

                if(isPrimitiveType(containedIdlType)) 
                {
                    list.add(convertPrimitiveParameterToCorba(p));
                }
                else if(isComplexType(containedIdlType)) 
                {
                    list.add(convertUserParameterToCorba(p));
                }
                else 
                {
                    throw new RuntimeException("Unhandled idlType " + containedIdlType + ")!");
                }
            }
        }
        logger.fine("end");
        return Text.join("\n", list) + "\n";
    }

    protected String convertPrimitiveParameterToCorba(MParameterDef p)
    {
        logger.fine("begin");
        MIDLType idlType = p.getIdlType();
        MParameterMode direction = p.getDirection();
        String corbaType = getAbsoluteCorbaType(idlType, "::");
        StringBuffer code = new StringBuffer();
        code.append(TAB).append(corbaType).append(" parameter_").append(p.getIdentifier()).append(";\n");
        if(direction != MParameterMode.PARAM_OUT) 
        {
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::")); 
            code.append("convertToCorba(").append(p.getIdentifier());
            code.append(", parameter_").append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }

    protected String convertUserParameterToCorba(MParameterDef p)
    {
        logger.fine("begin");     
        MParameterMode direction = p.getDirection();
        MIDLType idlType = ((MTyped) p).getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        StringBuffer code = new StringBuffer();
        if(direction == MParameterMode.PARAM_IN 
                || direction == MParameterMode.PARAM_INOUT) 
        {
        		code.append(TAB).append(getAbsoluteCorbaType(contained, "::")).append("_var parameter_");
        		code.append(p.getIdentifier()).append("= new ");
        		code.append(getAbsoluteCorbaType(contained, "::")).append(";\n");
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertToCorba(").append(p.getIdentifier());
            code.append(", parameter_").append(p.getIdentifier()).append(");\n");
        }
        else 
        { // MParameterMode.PARAM_OUT
            code.append(TAB).append(getAbsoluteCorbaType(contained, "::")).append("_var parameter_");
            code.append(p.getIdentifier()).append(";\n");
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    /**
     * Create the code that declases the variable (CORBA type and name) in which
     * the result value will be stored.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String declareReceptacleCorbaResult(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        String result = "";

        // void foo() does not need a result declaration
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            return "";
        }

        if(isPrimitiveType(idlType)) 
        {
            result = declareReceptacleCorbaPrimitiveResult(op);
        }
        else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) 
        {
            result = declareReceptacleCorbaUserResult(op);
        }
        else if(idlType instanceof MAliasDef) 
        {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(isPrimitiveType(containedIdlType)) 
            {
                result = declareReceptacleCorbaPrimitiveResult(op);
            }
            else if(isComplexType(containedIdlType)) 
            {
                result = declareReceptacleCorbaUserResult(op);
            }
            else 
            {
                throw new RuntimeException("Unhandled idlType" + containedIdlType + ")!");
            }
        }
        else if(idlType instanceof MInterfaceDef) 
        {
            result = declareReceptacleCorbaInterfaceResult(op);
        }
        else 
        {
            throw new RuntimeException("Unhandled idlType (" + idlType + ")!");
        }
        logger.fine("end");
        return result;
    }

    protected String declareReceptacleCorbaPrimitiveResult(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB);
        code.append(getAbsoluteCorbaType(idlType, "::"));
        code.append(" result;");
        logger.fine("end");
        return code.toString();
    }

    protected String declareReceptacleCorbaUserResult(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB);
        code.append(getAbsoluteCorbaType(idlType, "::"));
        code.append("_var result;");
        logger.fine("end");
        return code.toString();
    }

    protected String declareReceptacleCorbaInterfaceResult(MOperationDef op)
    {
        logger.fine("begin");        
        MIDLType idlType = op.getIdlType();
        MInterfaceDef iface = (MInterfaceDef)idlType;
        StringBuffer code = new StringBuffer();
        code.append(Text.tab(1));
        code.append("::").append(getCorbaStubNamespace((MContained)currentNode,"::"));
        code.append(iface.getIdentifier());
        code.append("_var result;");
        logger.fine("end");
        return code.toString();
    }
    
    protected String convertInterfaceMethodToCorba(MOperationDef op)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer(Text.tab(2));
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();

        code.append(TAB);
        // void method, no result declaration
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
        }
        else 
        {
            code.append("result = ");
        }
        code.append("remoteInterface->");
        code.append(op.getIdentifier());
        code.append("(");

        for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            list.add("parameter_" + p.getIdentifier());
        }
        code.append(Text.join(", ", list));
        code.append(");");
        logger.fine("end");
        return code.toString();
    }
    
    
    /**
     * Creates the code that converts the exception list of an operation into
     * catch statements for CORBA exceptions that throw corresponding remote
     * exceptions.
     * 
     * The template contains the following structure: try { //... }
     * %(MParameterDefConvertReceptacleExceptionsToCpp)s catch(...) { throw; }
     * The %(MParameterDefConvertExceptionsToCpp)s tag forces a call to this
     * method via getTwoStepVariables().
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertReceptacleExceptionsToCpp(MOperationDef op)
    {
        logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for(Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) 
        {
            MExceptionDef exception = (MExceptionDef) es.next();
            code.append(Text.TAB).append("catch(const ").append(getAbsoluteCorbaType(exception, "::"));
            code.append("& ce) {\n");
            code.append(TAB2).append(getLocalCxxName(exception, "::")).append(" te;\n");
            code.append(TAB2).append(getRemoteNamespace(exception, "::"));
            code.append("convertFromCorba(ce, te);\n");
            code.append(TAB2).append("throw te;\n");
            code.append(TAB).append("}\n");
        }
        logger.fine("end");
        return code.toString();
    }

    
    protected String convertReceptacleParameterToCpp(MOperationDef op)
    {
        logger.fine("begin");        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
        {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idlType)) 
            {
                ret.add(convertReceptaclePrimitiveParameterToCpp(p));
            }
            else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) 
            {
                ret.add(convertReceptacleUserParameterToCpp(p));
            }
            else if(idlType instanceof MAliasDef) 
            {
                MTyped containedType = (MTyped) idlType;
                MIDLType containedIdlType = containedType.getIdlType();
                if(isPrimitiveType(containedIdlType)) 
                {
                    ret.add(convertReceptaclePrimitiveParameterToCpp(p));
                }
                else 
                {
                    ret.add(convertReceptacleUserParameterToCpp(p));
                }
            }
        }
        logger.fine("end");
        return Text.join("\n", ret) + "\n";
    }

    protected String convertReceptaclePrimitiveParameterToCpp(MParameterDef p)
    {
        logger.fine("begin");
        MIDLType idlType = p.getIdlType();
        StringBuffer code = new StringBuffer();
        MParameterMode direction = p.getDirection();
        if(direction != MParameterMode.PARAM_IN) 
        {
            code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
            code.append("convertFromCorba(parameter_");
            code.append(p.getIdentifier()).append(", ").append(p.getIdentifier()).append(");\n");
        }
        logger.fine("end");
        return code.toString();
    }

    protected String convertReceptacleUserParameterToCpp(MParameterDef p)
    {
        logger.fine("");        
        return convertReceptaclePrimitiveParameterToCpp(p);
    }

    
    /**
     * Create the code that converts the result value as well as the inout and
     * out parameters from CORBA to local C++ types.
     * 
     * The %(MParameterDefConvertReceptacleResultToCpp)s tag forces a call to
     * this method via getTwoStepVariables().
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertReceptacleResultToCpp(MOperationDef op)
    {
        logger.fine("begin");        
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            return ""; // convertion
        }

        if(isPrimitiveType(idlType)) 
        {
            list.add(convertReceptaclePrimitiveResultToCpp(op));
        }
        else if(idlType instanceof MStructDef 
        			|| idlType instanceof MSequenceDef
                || idlType instanceof MEnumDef) 
        {
            list.add(convertReceptacleUserResultToCpp(op));
        }
        else if(idlType instanceof MAliasDef) 
        {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(isPrimitiveType(containedIdlType)) 
            {
                list.add(convertReceptaclePrimitiveResultToCpp(op));
            }
            else if(isComplexType(containedIdlType)) 
            {
                list.add(convertReceptacleUserResultToCpp(op));
            }
            else 
            {
                throw new RuntimeException("Unhandled idlType (" + containedIdlType + ")!");
            }
        }
        else if(idlType instanceof MInterfaceDef) 
        {
            list.add("// fixme: convertReceptacleResultToCpp()");
        }
        else 
        {
            throw new RuntimeException("Unhandled idlType " + idlType + ")!");
        }
        logger.fine("end");
        return Text.join("\n", list);
    }

    protected String convertReceptaclePrimitiveResultToCpp(MOperationDef op)
    {
        logger.fine("begin");
        MIDLType idlType = op.getIdlType(); 
        StringBuffer code = new StringBuffer();
        code.append(TAB).append(getLanguageType(op)).append(" return_value;\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(result, return_value);\n");
        code.append(TAB).append("return return_value;\n");
        logger.fine("end");
        return code.toString();
    }

    protected String convertReceptacleUserResultToCpp(MOperationDef op)
    {
        logger.fine("begin");        
        StringBuffer code = new StringBuffer();
        MIDLType idlType = op.getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        code.append(TAB).append(getLocalCxxName(contained, "::")).append(" return_value;\n");
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(result, return_value);\n");
        code.append(TAB).append("return return_value;\n");
        logger.fine("end");
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // MEnumDef %(tag)s helper methods
    //====================================================================
    
    protected String getMembersFromCorba(MEnumDef enumDef) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for (Iterator i = enumDef.getMembers().iterator(); i.hasNext();) 
        {
            String member = (String) i.next();
            String stubNs = getCorbaStubNamespace((MIDLType)enumDef,Text.SCOPE_SEPARATOR);
            String localNs = getLocalCxxNamespace(enumDef,Text.SCOPE_SEPARATOR); 
            code.append(TAB).append("case "); 
            code.append(Text.SCOPE_SEPARATOR).append(stubNs).append(member).append(":\n");
            code.append(TAB2).append("out = ");
            code.append(localNs).append(member).append(";\n");
            code.append(TAB2).append("break;\n");
        }
        logger.fine("end");
        return code.toString();
    }
    
    protected String getMembersToCorba(MEnumDef enumDef) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for (Iterator i = enumDef.getMembers().iterator(); i.hasNext();) 
        {
            String member = (String) i.next();
            String lns = getLocalCxxNamespace(enumDef,Text.SCOPE_SEPARATOR);
            String sns = getCorbaStubNamespace((MIDLType)enumDef,Text.SCOPE_SEPARATOR); 
            code.append(TAB).append("case "); 
            code.append(lns).append(member).append(":\n");
            code.append(TAB2).append("out = "); 
            code.append(Text.SCOPE_SEPARATOR).append(sns).append(member).append(";\n");
            code.append(TAB2).append("break;\n");
        }
        logger.fine("end");
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // MInterfaceDef %(tag)s helper methods
    //====================================================================
    
    protected String getStubIdentifier(MInterfaceDef iface) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(getCorbaStubNamespace((MContained)iface,Text.SCOPE_SEPARATOR));
        code.append(iface.getIdentifier());
        logger.fine("end");
        return code.toString();
    }
    
    protected String getCCM_LocalType(MInterfaceDef iface) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(getLocalCxxNamespace(iface,Text.SCOPE_SEPARATOR));
        code.append("CCM_");  // internally we implement CCM_IFace types
        code.append(iface.getIdentifier());
        logger.fine("end");
        return code.toString();
    }
    
    
    protected String getBaseInterfaceOperationsFromCorba(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) 
        {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) 
            {
                MContained contained = (MContained)j.next();    
                if(contained instanceof MOperationDef) 
                {
                    MOperationDef op = (MOperationDef)contained;    
                    if(isImpl) 
                    {
                        // generate code for C++ impl file
                        code.append(getAdapterOperationImplFromCorba(iface,op));
                    }
                    else 
                    { 
                        // generate code for C++ header file 
                        code.append(getAdapterOperationHeaderFromCorba(op));
                    }
                }
            }
            code.append(NL);
        }
        logger.fine("end");
        return code.toString();
    }
    
    protected String getAdapterOperationHeaderFromCorba(MOperationDef op) 
    {
    		logger.fine("begin");
    		MIDLType idlType = op.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB2).append("virtual").append(NL);
        code.append(TAB2).append(getAbsoluteCorbaType(idlType, "::")).append(NL);
        code.append(TAB2).append(op.getIdentifier()).append("(");
        code.append(getCorbaOperationParams(op));
        code.append(")").append(NL);
        code.append(TAB3).append(getCORBAExcepts(op)).append(";").append(NL);
        logger.fine("end");
        return code.toString();
    }
      
    protected String getAdapterOperationImplFromCorba(MInterfaceDef iface, MOperationDef op)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        MIDLType idlType = op.getIdlType();
        code.append(getAbsoluteCorbaType(idlType, "::")).append(NL);
        code.append(iface.getIdentifier());
        code.append("AdapterFromCorba::").append(op.getIdentifier()).append("(");
        code.append(getCorbaOperationParams(op)).append(")").append(NL);
        code.append(TAB).append(getCORBAExcepts(op)).append(NL);
        code.append("{").append(NL);
        code.append(convertParameterToCpp(op)).append(NL);
        code.append(declareCppResult(op)).append(NL);
        code.append(TAB).append("try {").append(NL);
        code.append(convertInterfaceMethodToCpp(op)).append(NL);
        code.append(TAB).append("}").append(NL);
        code.append(convertExceptionsToCorba(op)).append(NL);
        code.append(TAB).append("catch(...) {").append(NL);
        code.append(TAB2).append("throw CORBA::SystemException();").append(NL);
        code.append(TAB).append("}").append(NL);
        code.append(convertParameterToCorba(op)).append(NL);
        code.append(convertResultToCorba(op)).append(NL);
        code.append("}").append(NL);
        logger.fine("end");
        return code.toString();
    }    
    
        
    protected String getBaseInterfaceOperationsToCorba(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) 
        {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) 
            {
                MContained contained = (MContained) j.next();
                if(contained instanceof MOperationDef) 
                {
                    MOperationDef op = (MOperationDef) contained;
                    if(isImpl) 
                    {
                        // generate code for C++ impl file
                         code.append(getAdapterOperationImplToCorba(iface,op));
                    }
                    else 
                    {
                        // generate code for C++ header file
                        code.append(getAdapterOperationHeaderToCorba(op));
                    }
                }
            }
            code.append(NL);
        }
        logger.fine("end");
        return code.toString();
    }
    
    protected String getAdapterOperationHeaderToCorba(MOperationDef op) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("virtual").append(NL);
        code.append(TAB).append(getLanguageType(op)).append(NL);
        code.append(TAB).append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op));
        code.append(")").append(NL);
        code.append(TAB2).append("throw(::Components::ccm::local::CCMException ");
        code.append(getOperationExcepts(op)).append(";").append(NL);
        logger.fine("end");
        return code.toString();
    }
    
    protected String getAdapterOperationImplToCorba(MInterfaceDef iface, MOperationDef op)
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        code.append(getLanguageType(op)).append(NL);
        code.append(iface.getIdentifier());
        code.append("AdapterToCorba::").append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op)).append(")").append(NL);
        code.append(TAB).append("throw(::Components::ccm::local::CCMException ");
        code.append(getOperationExcepts(op)).append(NL);
        code.append("{").append(NL);
        code.append(convertReceptacleParameterToCorba(op)).append(NL);
        code.append(declareReceptacleCorbaResult(op)).append(NL);
        code.append(TAB).append("try {").append(NL);
        code.append(convertInterfaceMethodToCorba(op)).append(NL);
        code.append(TAB).append("}").append(NL);
        code.append(convertReceptacleExceptionsToCpp(op)).append(NL);
        code.append(TAB).append("catch(const Components::NoConnection&) {").append(NL);
        code.append(TAB2).append("throw ::Components::ccm::local::NoConnection();").append(NL);
        code.append(TAB).append("}").append(NL);
        code.append(TAB).append("catch(...) {").append(NL);
        code.append(TAB2).append("throw ::Components::ccm::local::CCMException();").append(NL);
        code.append(TAB).append("}").append(NL);
        code.append(convertReceptacleParameterToCpp(op)).append(NL);
        code.append(convertReceptacleResultToCpp(op)).append(NL);
        code.append("}").append(NL);
        logger.fine("end");
        return code.toString();
    }    
    
   
    protected String getBaseInterfaceAttributesFromCorba(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) 
        {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) 
            {
                MContained contained = (MContained)j.next();    
                if(contained instanceof MAttributeDef) 
                {
                    MAttributeDef attr = (MAttributeDef)contained;
                    if(isImpl) 
                    {
                        // generate code for C++ impl file
                        code.append(getAdapterAttributeFromCorbaImpl(iface,attr));
                    }
                    else 
                    {
                        // generate code for C++ header file 
                        code.append(getAdapterAttributeFromCorbaHeader(attr));
                    }
                }
            }
            code.append(NL);
        }
        logger.fine("end");
        return code.toString();
    }
    
    protected String getAdapterAttributeFromCorbaHeader(MAttributeDef attr) 
    {
    		logger.fine("begin");	
    		MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("virtual ");
        code.append(getAbsoluteCorbaType(idlType, "::"));
        code.append(" ").append(attr.getIdentifier()).append("()").append(NL);
        code.append(TAB2).append("throw(CORBA::SystemException);").append(NL2);

        code.append(TAB).append("virtual ");
        code.append("void ").append(attr.getIdentifier()).append("(const ");
        code.append(getAbsoluteCorbaType(idlType, "::")).append(" value)").append(NL);
        code.append(TAB2).append("throw(CORBA::SystemException);").append(NL2);
        logger.fine("end");
        return code.toString();
    }
        
    protected String getAdapterAttributeFromCorbaImpl(MInterfaceDef iface, MAttributeDef attr) 
    {
    		logger.fine("begin");
    		MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        // Getter method
        code.append(getAbsoluteCorbaType(idlType, "::")).append(NL);
        code.append(iface.getIdentifier()).append("AdapterFromCorba::").append(attr.getIdentifier());
        code.append("()").append(NL);
        code.append(TAB).append("throw(CORBA::SystemException)").append(NL);
        code.append("{").append(NL);
        code.append(TAB).append(getLanguageType(attr)).append(" result;").append(NL);
        code.append(TAB).append("result = localInterface->").append(attr.getIdentifier());
        code.append("();").append(NL);
        code.append(TAB).append(getAbsoluteCorbaType(idlType, "::")).append(" return_value;").append(NL);
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(result, return_value);").append(NL);
        code.append(TAB).append("return return_value;").append(NL);
        code.append("}").append(NL2);

        // Setter Method
        code.append("void").append(NL);
        code.append(iface.getIdentifier()).append("AdapterFromCorba::").append(attr.getIdentifier());
        code.append("(const ").append(getAbsoluteCorbaType(idlType, "::")).append(" value)").append(NL);
        code.append(TAB).append("throw(CORBA::SystemException)").append(NL);
        code.append("{").append(NL);
        code.append(TAB).append(getLanguageType(attr)).append(" local_value;").append(NL);
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(value, local_value);").append(NL);
        code.append(TAB).append("localInterface->");
        code.append(attr.getIdentifier()).append("(local_value);").append(NL);
        code.append("}").append(NL2);
        logger.fine("end");
        return code.toString();
    }    
    
        
    protected String getBaseInterfaceAttributesToCorba(boolean isImpl, MInterfaceDef iface, List baseInterfaceList)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) 
        {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) 
            {
                MContained contained = (MContained) j.next();
                if(contained instanceof MAttributeDef) 
                {
                    MAttributeDef attr = (MAttributeDef) contained;
                    if(isImpl) 
                    {
                        // generate code for C++ impl file
                        code.append(getAdapterAttributeToCorbaImpl(iface,attr));
                    }
                    else 
                    {
                        // generate code for C++ header file
                        code.append(getAdapterAttributeToCorbaHeader(attr));
                    }
                }
            }
            code.append(NL);
        }
        logger.fine("end");
        return code.toString();
    }
        
    protected String getAdapterAttributeToCorbaHeader(MAttributeDef attr) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(TAB).append("virtual ");
        code.append("const ").append(getLanguageType(attr));
        code.append(" ").append(attr.getIdentifier()).append("() const").append(NL);
        code.append(TAB2).append("throw(::Components::ccm::local::CCMException);");
        code.append(NL2);
        
        code.append(TAB).append("virtual ");
        code.append("void ").append(attr.getIdentifier()).append("(const ");
        code.append(getLanguageType(attr)).append(" value)").append(NL);
        code.append(TAB2).append("throw(::Components::ccm::local::CCMException);");
        code.append(NL2);
        logger.fine("end");
        return code.toString();
    }
    
    protected String getAdapterAttributeToCorbaImpl(MInterfaceDef iface, MAttributeDef attr) 
    {
    		logger.fine("begin");
    		MIDLType idlType = attr.getIdlType();
        StringBuffer code = new StringBuffer();
        code.append("const ").append(getLanguageType(attr)).append(NL);
        code.append(iface.getIdentifier()).append("AdapterToCorba::").append(attr.getIdentifier());
        code.append("() const").append(NL);
        code.append(TAB).append("throw(::Components::ccm::local::CCMException)").append(NL);
        code.append("{").append(NL);
        code.append(TAB).append(getAbsoluteCorbaType(idlType, "::")).append(" result;").append(NL);
        code.append(TAB).append("result = remoteInterface->");
        code.append(attr.getIdentifier()).append("();").append(NL);
        code.append(TAB).append(getLanguageType(attr)).append(" return_value;").append(NL);
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertFromCorba(result, return_value);").append(NL);
        code.append(TAB).append("return return_value;").append(NL);
        code.append("}").append(NL2);
        
        code.append("void").append(NL);
        code.append(iface.getIdentifier()).append("AdapterToCorba::").append(attr.getIdentifier());
        code.append("(const ").append(getLanguageType(attr)).append(" value)").append(NL);
        code.append(TAB).append("throw(::Components::ccm::local::CCMException)").append(NL);
        code.append("{").append(NL);
        code.append(TAB).append(getAbsoluteCorbaType(idlType, "::"));
        code.append(" remote_value;").append(NL);
        code.append(TAB).append(getCorbaConverterNamespace(idlType, "::"));
        code.append("convertToCorba(value, remote_value);").append(NL);
        code.append(TAB).append("remoteInterface->").append(attr.getIdentifier());
        code.append("(remote_value);").append(NL);
        code.append("}").append(NL2);
        logger.fine("end");
        return code.toString();
    }    
    
   
       
    
    //====================================================================
    // MSupportsDef %(tag)s helper methods
    //====================================================================
    
    protected String getSupportsInclude(MSupportsDef supports) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append("#include <");
        code.append(getLocalCxxNamespace(supports,Text.INCLUDE_SEPARATOR));
        code.append(supports.getSupports().getIdentifier());
        code.append(".h>\n");
        logger.fine("end");
        return code.toString();
    }
        
    protected String getSupportsConvertInclude(MSupportsDef supports)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(generateCorbaConverterInclude((MContained)currentNode, 
        		supports.getSupports(), supports.getSupports().getIdentifier()));
        logger.fine("end");
        return code.toString();
    }
    
    /**
     * Create the code that makes the local method call, with all of the local
     * parameters. Note that the local method must be part of the object
     * local_adapter points to.
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertMethodToCpp(MOperationDef op)
    {
        logger.fine("");        
        List ret = new ArrayList();
        String resultPrefix;
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) 
        {
            resultPrefix = ""; // void foo()
        }
        else 
        {
            resultPrefix = TAB2 + "result = ";
        }
        if(isPrimitiveType(idlType) 
                || isComplexType(idlType)) 
        {
            resultPrefix += "local_adapter->" + op.getIdentifier() + "(";
            for(Iterator params = op.getParameters().iterator(); params.hasNext();) 
            {
                MParameterDef p = (MParameterDef) params.next();
                ret.add(" parameter_" + p.getIdentifier());
            }
            return resultPrefix + Text.join(", ", ret) + ");";
        }
        else if(idlType instanceof MInterfaceDef) 
        {
            return "// fixme: convertMethodToCpp()";
        }
        else 
        {
            throw new RuntimeException("Unhandled idlType " + idlType + ")!");
        }        
    }
    
    
       
    //====================================================================
    // MProvidesDef %(tag)s helper methods
    //====================================================================
    
    protected String getProvidesInclude(MProvidesDef provides) 
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MProvidesDef)currentNode).getProvides();
        code.append("#include <");
        code.append(getLocalCxxNamespace(iface,Text.INCLUDE_SEPARATOR));
        code.append(provides.getProvides().getIdentifier());
        code.append(".h>\n");
        logger.fine("end");
        return code.toString();
    }
    
    protected String getProvidesConvertInclude(MProvidesDef provides)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(generateCorbaConverterInclude((MContained)currentNode, 
        		provides.getProvides(), provides.getProvides().getIdentifier()));
        logger.fine("end");
        return code.toString();
    }

    protected String getIdlProvidesType(MProvidesDef provides) 
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MProvidesDef) currentNode).getProvides();
        code.append(getCorbaStubNamespace((MContained)iface,Text.SCOPE_SEPARATOR));
        code.append(iface.getIdentifier());
        logger.fine("end");
        return code.toString();
    }
    
    protected String getProvidesType(MProvidesDef provides) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MProvidesDef) currentNode).getProvides();
        code.append(getLocalCxxNamespace(iface,Text.SCOPE_SEPARATOR));
        code.append(provides.getProvides().getIdentifier());
        logger.fine("end");
        	return code.toString();
    }
    
    
    
       
    //====================================================================
    // MUsesDef %(tag)s helper methods
    //====================================================================    
    
    protected String getUsesInclude(MUsesDef usesDef)
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();
        code.append("#include <");
        code.append(getLocalCxxNamespace(iface, Text.INCLUDE_SEPARATOR));
        code.append(usesDef.getUses().getIdentifier());
        code.append(".h>");
        logger.fine("end");
        return code.toString();
    }
    
    protected String getUsesConvertInclude(MUsesDef usesDef) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        code.append(generateCorbaConverterInclude((MContained)currentNode, 
        		usesDef.getUses(), usesDef.getUses().getIdentifier()));
        logger.fine("end");
        return code.toString();
    }
    
    protected String getCCM_UsesType(MUsesDef usesDef) 
    {
    		logger.fine("begin");
        StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();
        List scope = getScope((MContained) iface);
        // TODO: Refactoring namespace method !
        if (scope.size() > 0) 
        {
            code.append(Text.join("::", scope));
            code.append("::CCM_");
        }
        else 
        {
            code.append("CCM_");
        }
        code.append(usesDef.getUses().getIdentifier());
        logger.fine("end");
        return code.toString();
    }
    
    protected String getIdlUsesType(MUsesDef usesDef) 
    {
    		logger.fine("begin");
    		StringBuffer code = new StringBuffer();
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();
        code.append(getCorbaStubNamespace((MContained)iface,Text.SCOPE_SEPARATOR));
        code.append(usesDef.getUses().getIdentifier());
        logger.fine("end");
        return code.toString();
    }
    
    protected String getUsesType(MUsesDef usesDef) 
    {
    		logger.fine("begin");
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();
        StringBuffer code = new StringBuffer();
        code.append(getLocalCxxName(iface,Text.SCOPE_SEPARATOR));
        logger.fine("end");
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // Utility methods
    //====================================================================

    protected boolean isPrimitiveType(MIDLType type)
    {
        if(type instanceof MPrimitiveDef || type instanceof MStringDef
                || type instanceof MWstringDef || type instanceof MEnumDef) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    protected boolean isComplexType(MIDLType type)
    {
        if(type instanceof MStructDef || type instanceof MSequenceDef || type instanceof MAliasDef) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
}