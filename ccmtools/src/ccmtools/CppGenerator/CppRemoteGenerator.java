/* CCM Tools : C++ Code Generator Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003, 2004 Salomon Automation
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
import java.util.logging.Logger;

import ccmtools.CodeGenerator.Template;
import ccmtools.CppGenerator.utils.LocalHelper;
import ccmtools.CppGenerator.utils.Scope;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MAttributeDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
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
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;
import ccmtools.UI.Driver;
import ccmtools.utils.Code;
import ccmtools.utils.Text;

/*******************************************************************************
 * Remote C++ component generator
 * 
 * This generator creates CORBA objects that build the remote skin of a
 * component as well as a set of adapters that convert CORBA types into C++
 * native types and vice versa.
 ******************************************************************************/
public class CppRemoteGenerator extends CppGenerator 
{

    //====================================================================
    // Definition of arrays that determine the generator's behavior
    //====================================================================

    private Map corbaMappings;
    protected List corbaStubsNamespace = null;
    protected List localNamespace = null;

    protected LocalHelper  localHelper = new LocalHelper();
    //protected RemoteHelper remoteHelper = new RemoteHelper();
    
    /**
     * Top level node types: Types for which we have a global template; that is,
     * a template that is not contained inside another template.
     */
    private final static String[] LOCAL_OUTPUT_TYPES = {
            "MHomeDef", "MComponentDef", "MInterfaceDef", "MStructDef", "MAliasDef", "MEnumDef",
            "MExceptionDef"
    };

    /**
     * Language type mapping: Defines the IDL to C++ Mappings for primitive
     * types.
     * 
     * Note: order and length of the array must be the same as used by the
     * MPrimitiveKind enumeration of the CCM metamodel.
     */
    private final static String[] REMOTE_LANGUAGE_MAP = { 
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
            "", 				 	// PK_PRINCIPAL
            "CORBA::Short", 	 	// PK_SHORT
            "char*", 	         	// PK_STRING
            "", 				 	// PK_TYPECODE
            "CORBA::ULong", 	 	// PK_ULONG
            "CORBA::ULongLong",  	// PK_ULONGLONG
            "CORBA::UShort", 	 	// PK_USHORT
            "", 				 	// PK_VALUEBASE
            "void", 				// PK_VOID
            "CORBA::WChar", 		// PK_WCHAR
            "CORBA::WChar*" 		// PK_WSTRING
    };

    /** Define constants for parameter directions */
    protected static final int IN    = 0;
    protected static final int INOUT = 1;
    protected static final int OUT   = 2;
    
    
    /**
     * The generator constructor calls the constructor of the base class and
     * sets up the map for the CORBA to C++ mappings.
     * 
     * @param d
     * @param out_dir
     * 
     * @exception IOException
     */
    public CppRemoteGenerator(Driver d, File out_dir) throws IOException
    {
        super("CppRemote", d, out_dir, LOCAL_OUTPUT_TYPES);
        
        logger = Logger.getLogger("ccm.generator.cpp.remote");
        logger.fine("enter CppRemoteGenerator()");
        
        baseNamespace.add("CCM_Remote");
        corbaStubsNamespace = new ArrayList();
        localNamespace = new ArrayList();
        localNamespace.add("CCM_Local");

        // Fill the CORBA_mappings with IDL to C++ Mapping types
        String[] labels = MPrimitiveKind.getLabels();
        corbaMappings = new Hashtable();
        for(int i = 0; i < labels.length; i++) {
            corbaMappings.put(labels[i], REMOTE_LANGUAGE_MAP[i]);
        }
        logger.fine("leave CppRemoteGenerator()");
    }
    

    //====================================================================
    // Code generator core methods
    //====================================================================

    /**
     * Overwrites the CppGenerator's method to handle namespaces in different
     * ways. There are local (CCM_Local) namespaces, remote (CCM_Remote)
     * namespaces and the namespaces of the generated stubs and skeletons.
     * 
     * "FileNamespace": is used to create the directory in which the remote
     * component logic will be generated "LocalNamespace": "RemoteNamespace":
     * "LocalIncludeNamespace": "StubsNamespace": "StubsIncludeNamespace":
     */
    protected String handleNamespace(String dataType, String local)
    {
        logger.fine("handleNamespace()");
        
        List names = new ArrayList(namespaceStack);
        MContained node = (MContained)currentNode;

        if (dataType.equals("FileNamespace")) {
            return Text.join("_", Text.slice(names, 0));
        }
        else if (dataType.equals("LocalNamespace")) {
            return Scope.getLocalNamespace(localNamespace,node,Text.SCOPE_SEPARATOR,local);
        }
        else if (dataType.equals("RemoteNamespace")) {
            return Scope.getRemoteNamespace(namespaceStack,Text.SCOPE_SEPARATOR,local);
        }
        else if (dataType.equals("LocalIncludeNamespace")) {
            return Scope.getLocalNamespace(localNamespace,node,Text.FILE_SEPARATOR, local);
        }
        else if (dataType.equals("StubsNamespace")) {
            return Scope.getCorbaStubsNamespace(corbaStubsNamespace, node,Text.SCOPE_SEPARATOR);
        }
        else if (dataType.equals("StubsIncludeNamespace")) {
            return Scope.getCorbaStubsNamespace(corbaStubsNamespace,node,Text.MANGLING_SEPARATOR);
        }
        return super.handleNamespace(dataType, local);
    }

    /**
     * Overrides method from CppGenerator to handle the following tags within an
     * interface attribute template: %(CORBAType)s
     * %(MAttributeDefConvertResultType)s %(MAttributeDefConvertParameter)s
     */
    protected Map getTwoStepAttributeVariables(MAttributeDef attr,
            MContained container)
    {
        logger.fine("getTwoStepAttributeVariables()");
        
        String lang_type = getLanguageType(attr);
        Map vars = super.getTwoStepAttributeVariables(attr, container);

        MTyped object = (MTyped) attr;
        String base_type = getBaseIdlType(object);
        return vars;
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
        logger.fine("getTwoStepOperationVariables()");
        
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object", container.getIdentifier());
        vars.put("Identifier", operation.getIdentifier());
        vars.put("LanguageType", lang_type);
        vars.put("CORBAType", getCORBALanguageType(operation));
        
        vars.put("LocalExceptions", getOperationExcepts(operation));
        vars.put("MExceptionDefCORBA", getCORBAExcepts(operation));

        vars.put("ParameterDefLocal", getLocalOperationParams(operation));
        vars.put("MParameterDefCORBA", getCORBAOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));

        // Used for supports adapter generation
        vars.put("ConvertFacetParameterToCpp", convertParameterToCpp(operation));
        vars.put("DeclareFacetCppResult", declareCppResult(operation));
        vars.put("ConvertFacetMethodToCpp", convertMethodToCpp(operation));
        vars.put("ConvertFacetExceptionsToCorba", convertExceptionsToCorba(operation));
        vars.put("ConvertFacetParameterToCorba", convertParameterToCorba(operation));
        vars.put("ConvertFacetResultToCorba", convertResultToCorba(operation));

        vars.put("Return", (lang_type.equals("void")) ? "" : "return ");

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
        logger.fine("getLocalValue(" + variable + ")");
        
        // Get local value of CppGenerator 
        String value = super.getLocalValue(variable);

        // Handle simple tags from templates which are related to 
        // the remote generator 
        if (variable.equals("CcmToolsVersion")) {
            return localHelper.getCcmToolsVersion();
        }
        
        if(currentNode instanceof MContained) {
            MContained contained = (MContained)currentNode;
            if(variable.equals("LocalName")) {
                return Scope.getLocalName(localNamespace,contained,
                                          Text.SCOPE_SEPARATOR);
            }
            else if(variable.equals("CorbaStubName")) {
                return Scope.getCorbaStubName(corbaStubsNamespace,
                                              contained,Text.SCOPE_SEPARATOR);
            }
            else if(variable.equals("CorbaRemoteName")) {
                return Scope.getRemoteName(baseNamespace,
                                           contained,Text.SCOPE_SEPARATOR,"");
            }
        }
        
        if (currentNode instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        }
        else if (currentNode instanceof MFieldDef) {
            return data_MFieldDef(variable, value);
        }
        else if (currentNode instanceof MAliasDef) {
            // determine the contained type of MaliasDef
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            if (idlType instanceof MPrimitiveDef || idlType instanceof MStringDef
                    || idlType instanceof MWstringDef) {
                return value;
            }
            else if (idlType instanceof MSequenceDef) {
                return data_MSequenceDef(variable, value);
            }
            else if (idlType instanceof MArrayDef) {
                return data_MArrayDef(variable, value);
            }
            else {
                // Signal an implementation bug
                throw new RuntimeException("Unhandled alias type:" + "CppRemoteGenerator."
                        + "getLocalValue(" + variable + ")");
            }
        }
        return value;
    }

    
 
    protected String data_MSequenceDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MSequenceDef()");       
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        MContained contained = (MContained) type;
        MTyped singleType = (MTyped) idlType;
        MIDLType singleIdlType = singleType.getIdlType();
        MContained node = (MContained)currentNode;
        
        if (dataType.equals("ConvertFromCorbaDeclaration")) {
            dataValue = getConvertFromCorbaDeclaration(node);
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("void convertFromCorba(const ");
//            buffer.append(Scope.getCorbaStubName(corbaStubsNamespace,node, "::"));
//            buffer.append("& in, ");
//            buffer.append(Scope.getLocalName(localNamespace,node, "::"));
//            buffer.append("& out);");
//            dataValue = buffer.toString();
//      
        }
        else if (dataType.equals("ConvertToCorbaDeclaration")) {
            dataValue = getConvertToCorbaDeclaration(node);           
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("void convertToCorba(const ");
//            buffer.append(Scope.getLocalName(localNamespace,node, "::"));
//            buffer.append("& in, ");
//            buffer.append(Scope.getCorbaStubName(corbaStubsNamespace,node, "::"));
//            buffer.append("& out);");
//            dataValue = buffer.toString();
        }
        else if (dataType.equals("ConvertFromCorbaImplementation")) {
            dataValue = getConvertFromCorbaImplementation(node, singleType);
//            List code = new ArrayList();
//            code.add("void");
//            
//            code.add("convertFromCorba(const " 
//                    + Scope.getCorbaStubName(corbaStubsNamespace,node, "::")
//                    + "& in, " 
//                    + Scope.getLocalName(localNamespace,node, "::")
//                    + "& out)");
//            code.add("{");
//            code.add("    LDEBUGNL(CCM_REMOTE,\" convertFromCorba("
//                    + Scope.getCorbaStubName(corbaStubsNamespace,node, "::")
//                    + ")\");");
//            code.add("    LDEBUGNL(CCM_REMOTE, in);");
//            code.add(data_MSequenceDef("ConvertAliasFromCORBA", ""));
//            code.add("}");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("ConvertToCorbaImplementation")) {
            dataValue = getConvertToCorbaImplementation(node, singleType);
//            List code = new ArrayList();
//            code.add("void");
//            code.add("convertToCorba(const " 
//                    + Scope.getLocalName(localNamespace,node, "::") 
//                    + "& in, " 
//                    + Scope.getCorbaStubName(corbaStubsNamespace,node, "::")
//                    + "& out)");
//            code.add("{");
//            code.add("    LDEBUGNL(CCM_REMOTE,\" convertToCorba("
//                    + Scope.getCorbaStubName(corbaStubsNamespace,node, "::")
//                    + ")\");");
//            code.add(data_MSequenceDef("ConvertAliasToCORBA", ""));
//            code.add("    LDEBUGNL(CCM_REMOTE, out);");
//            code.add("}");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("OutputCorbaTypeDeclaration")) {
            dataValue = getOutputCorbaTypeDeclaration(node);
//            dataValue = "std::ostream& operator<<(std::ostream& o, const "
//                	+ Scope.getCorbaStubName(corbaStubsNamespace,node, "::")
//                    + "& value);";
        }
        else if (dataType.equals("OutputCorbaTypeImplementation")) {
            dataValue = getOutputCorbaTypeImplementation(node, contained);
//            List code = new ArrayList();
//            code.add("std::ostream&");
//            code.add("operator<<(std::ostream& o, const " 
//                     + Scope.getCorbaStubName(corbaStubsNamespace,node, "::")	
//                     + "& value)");
//            code.add("{");
//            code.add(data_MSequenceDef("OutputCORBAType", ""));
//            code.add("    return o;");
//            code.add("}");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("SingleValue")) {
            dataValue = getSingleValue(singleType);
//            if (singleIdlType instanceof MPrimitiveDef 
//                    || singleIdlType instanceof MStringDef) {
//                dataValue = getBaseLanguageType(singleType);
//            }
//            else {
//                dataValue = "CCM_Local::"
//                // TODO: Handle local Namespace
//                   				+ getBaseLanguageType(singleType);
//            }
        }
        else if (dataType.equals("InOutValue")) {
            dataValue = getInOutValue(singleType);
//            if (singleIdlType instanceof MStringDef) {
//                dataValue = "out[i].inout()";
//            }
//            else {
//                dataValue = "out[i]";
//            }
        }
        else if (dataType.equals("CORBASequenceConverterInclude")) {
            dataValue = getCORBASequenceConverterInclude(idlType,singleIdlType);
//            
//            if (singleIdlType instanceof MPrimitiveDef || singleIdlType instanceof MStringDef) {
//                dataValue = "";
//            }
//            else if (idlType instanceof MStructDef || idlType instanceof MAliasDef
//                    || idlType instanceof MSequenceDef) {
//                MContained singleContained = (MContained) singleIdlType;
//                StringBuffer buffer = new StringBuffer();
//                buffer.append("#include \"");
//                buffer.append(singleContained.getIdentifier());
//                buffer.append("_remote.h\"");
//                dataValue = buffer.toString();
//            }
//            else {
//                throw new RuntimeException("data_MSequenceDef(" + dataType + dataValue
//                        + ") Unhandled idlType: " + idlType);
//            }
        }
        else if (dataType.equals("ConvertAliasFromCORBA")) {
            dataValue = getConvertAliasFromCORBA(singleType);
//            List code = new ArrayList();
//            code.add("    out.clear();");
//            code.add("    out.reserve(in.length());");
//            code.add("    for(unsigned long i=0; i < in.length();i++) {");
//            code.add("        " + singleValue + " singleValue;");
//            code.add("        convertFromCorba(in[i], singleValue);");
//            code.add("        out.push_back(singleValue);");
//            code.add("    }");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("ConvertAliasToCORBA")) {
            dataValue = getConvertAliasToCORBA(singleType);
//            List code = new ArrayList();
//            code.add("    out.length(in.size());");
//            code.add("    for(unsigned long i=0; i < in.size(); i++) {");
//            code.add("        " + singleValue + " singleValue = in[i];");
//            code.add("        convertToCorba(singleValue, " + inOutValue + ");");
//            code.add("    }");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("OutputCORBAType")) {
            dataValue = getOutputCORBAType(node, contained); 
//            List code = new ArrayList();
//            code.add("    o << endl;");
//            code.add("    o << \"sequence " + handleNamespace("StubsNamespace", "")
//                    + contained.getIdentifier() + " [ \" << endl;");
//            code.add("    for(unsigned long i=0; i < value.length();i++) {");
//            code.add("        o << value[i] << endl;");
//            code.add("    }");
//            code.add("    o << \"]\";");
//            dataValue = Text.join("\n", code);
        }
        else if (dataType.equals("OutputCppType")) {
            dataValue = getOutputCppType();
//            List code = new ArrayList();
//            code.add("    o << \"[ \";");
//            code.add("    for(unsigned long i=0; i < value.size(); i++) {");
//            code.add("        if(i) o << \",\";");
//            code.add("        o << value[i];");
//            code.add("    }");
//            code.add("    o << \" ]\";");
//            dataValue = Text.join("\n", code);
        }
        logger.fine("leave data_MSequenceDef()");
        return dataValue;
    }


    protected String data_MArrayDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MArrayDef()");
        
        // TODO: Implement array converter
        throw new RuntimeException("CppRemoteGenerator.data_MArrayDef(" 
                                   + dataType + ", "
                                   + dataValue + " - Not implemented!");
    }
    
    
    protected String data_MFieldDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MFieldDef()");       
        // current_node is MFieldDef
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String fieldName = ((MFieldDef) currentNode).getIdentifier();

        // Handle %(CORBATypeIn)s tag in %(MFieldDef*)s templates
        if (dataType.equals("CORBAType")) {
            dataValue = fieldName;
        }
        // Handle %(CORBATypeIn)s tag in %(MFieldDef*)s templates
        else if (dataType.equals("CORBATypeIn")) {
            dataValue = getCORBAFieldDirection(idlType, fieldName, IN);
//            if (idlType instanceof MStringDef) {
//                dataValue = fieldName + ".in()";
//            }
//            else {
//                dataValue = fieldName;
//            }
        }
        // Handle %(CORBATypeInOut)s tag in %(MFieldDef*)s templates
        else if (dataType.equals("CORBATypeInOut")) {
            dataValue = getCORBAFieldDirection(idlType, fieldName, INOUT);
//            if (idlType instanceof MStringDef) {
//                dataValue = fieldName + ".inout()";
//            }
//            else {
//                dataValue = fieldName;
//            }
        }
        // Handle %(CORBATypeOut)s tag in %(MFieldDef*)s templates
        else if (dataType.equals("CORBATypeOut")) {
            dataValue = getCORBAFieldDirection(idlType, fieldName, OUT);
//            if (idlType instanceof MStringDef) {
//                dataValue = fieldName + ".out()";
//            }
//            else {
//                dataValue = fieldName;
//            }
        }
        else if (dataType.equals("CORBAFieldConverterInclude")) {
            dataValue = getCORBAFieldConverterInclude(idlType,fieldName);
//            if (idlType instanceof MPrimitiveDef || idlType instanceof MStringDef) {
//                dataValue = "";
//            }
//            else if (idlType instanceof MStructDef || idlType instanceof MAliasDef
//                    || idlType instanceof MEnumDef) {
//                MContained contained = (MContained) idlType;
//                StringBuffer ret = new StringBuffer();
//                ret.append("#include \"");
//                ret.append(contained.getIdentifier());
//                ret.append("_remote.h\"");
//                dataValue = ret.toString();
//            }
//            else {
//                throw new RuntimeException("data_MFieldDef(" + dataType + dataValue
//                        + ") Unhandled idlType: " + idlType);
//            }
        }
        logger.fine("leave data_MFieldDef()");
        return dataValue;
    }

    
    protected String data_MAttributeDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MAttributeDef()");
        // current_node is MAttributeDef
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String baseType = getBaseIdlType(type);
        MAttributeDef attribute = (MAttributeDef)currentNode;
        
        // Handle %(CORBAType)s tag in %(MAttributeDef*)s templates
       
        if(dataType.equals("InterfaceType")) {
            dataValue = attribute.getDefinedIn().getIdentifier();
        }
        else if(dataType.equals("CORBAType")) {
          //dataValue = getCORBALanguageType((MTyped) currentNode);
            dataValue = getCORBALanguageType(type);
        }

        else if(dataType.equals("CORBAAttributeResult")) {
          //dataValue = getCorbaAttributeResult((MTyped) currentNode);
            dataValue = getCorbaAttributeResult(type);
        }
        else if(dataType.equals("CORBAAttributeParameter")) {
         // dataValue = getCorbaAttributeParameter((MTyped) currentNode);
            dataValue = getCorbaAttributeParameter(type);
        }
        else if(dataType.equals("LocalAttributeType")) {
          //dataValue = getLocalAttributeType((MTyped) currentNode);
            dataValue = getLocalAttributeType(type);
        }
        
        else if(dataType.equals("ConvertComponentGetAttributeFromCorba")) {
            dataValue = 
              //convertGetAttributeFromCorba((MAttributeDef)currentNode,
                convertGetAttributeFromCorba(attribute,
                                             "local_adapter"); 
        }
        else if(dataType.equals("ConvertComponentSetAttributeFromCorba")) {
            dataValue = 
              //convertSetAttributeFromCorba((MAttributeDef)currentNode,
                convertSetAttributeFromCorba(attribute,
                                             "local_adapter");
        }
        else if(dataType.equals("ConvertInterfaceGetAttributeFromCorba")) {
            dataValue = 
              //convertGetAttributeFromCorba((MAttributeDef)currentNode,
                convertGetAttributeFromCorba(attribute,
                                             "localInterface"); 
        }
        else if(dataType.equals("ConvertInterfaceSetAttributeFromCorba")) {
            dataValue = 
              //convertSetAttributeFromCorba((MAttributeDef)currentNode,
                convertSetAttributeFromCorba(attribute,
                                             "localInterface");
        }
        
        else if(dataType.equals("ConvertInterfaceGetAttributeToCorba")) {
            dataValue = 
              //convertGetAttributeToCorba((MAttributeDef)currentNode);
                convertGetAttributeToCorba(attribute);
        }
        else if(dataType.equals("ConvertInterfaceSetAttributeToCorba")) {
            dataValue = 
              //convertSetAttributeToCorba((MAttributeDef)currentNode);
                convertSetAttributeToCorba(attribute);
        }
        
        else if(dataType.equals("AttributeConvertInclude")) {
            dataValue = getAttributeConvertInclude(idlType, baseType);
//            Set code = new HashSet();
//            StringBuffer buffer = new StringBuffer();
//            if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
//                    || idlType instanceof MWstringDef) {
//                // no include statement needed for these primitive types
//            }
//             else {   
//                buffer.append("#include \"").append(baseType).append("_remote.h\"\n");
//            }
//            dataValue = buffer.toString(); 
        }
        logger.fine("leave data_MAttributeDef()");
        return dataValue;
    }

    
    protected String data_MOperationDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MOperationDef()");
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        String baseType = getBaseIdlType(type);
        MOperationDef operation = (MOperationDef) type;

        if(dataType.equals("InterfaceType")) {
            dataValue = operation.getDefinedIn().getIdentifier();
        }        
        else if(dataType.equals("CORBAType")) {
            dataValue = getCORBALanguageType((MTyped) currentNode);
        }
        else if(dataType.equals("CORBAParameters")) {
            dataValue = getCORBAOperationParams(operation);
        }
        else if(dataType.equals("CORBAExceptions")) { 
            dataValue = getCORBAExcepts(operation);
        }
        if(dataType.equals("LocalParameters")) {
            dataValue = getLocalOperationParams(operation);
        }
        else if(dataType.equals("LocalExceptions")) { 
            dataValue = getOperationExcepts(operation);
        }
        else if (dataType.equals("OperationConvertInclude")) {
            dataValue = getOperationConvertInclude(idlType, baseType);
//            if (idlType instanceof MPrimitiveDef || idlType instanceof MStringDef) {
//                dataValue = "";
//            }
//            else {
//                StringBuffer ret = new StringBuffer();
//                ret.append("#include\"");
//                ret.append(baseType);
//                ret.append("_remote.h\"");
//                ret.append("\n");
//                dataValue = ret.toString();
//            }
        }
        else if (dataType.equals("ParameterConvertInclude")) {
            dataValue = getParameterConvertInclude(operation);
//            for (Iterator i = operation.getParameters().iterator(); i.hasNext();) {
//                MParameterDef parameter = (MParameterDef) i.next();
//                MTyped parameterType = (MTyped) parameter;
//                MIDLType parameterIdlType = parameterType.getIdlType();
//                if (parameterIdlType instanceof MPrimitiveDef
//                        || parameterIdlType instanceof MStringDef) {
//                    dataValue += "";
//                }
//                else {
//                    StringBuffer ret = new StringBuffer();
//                    ret.append("#include\"");
//                    ret.append(getBaseIdlType(parameter));
//                    ret.append("_remote.h\"");
//                    ret.append("\n");
//                    dataValue += ret.toString();
//                }
//            }
        }
        else if (dataType.equals("ExceptionConvertInclude")) {
            dataValue = getExceptionConvertInclude(operation);
//            StringBuffer ret = new StringBuffer();
//            for (Iterator i = operation.getExceptionDefs().iterator(); i.hasNext();) {
//                MExceptionDef exception = (MExceptionDef) i.next();
//                ret.append("#include\"");
//                ret.append(exception.getIdentifier());
//                ret.append("_remote.h\"");
//                ret.append("\n");
//                dataValue = ret.toString();
//            }
        }

        // Tags for Adapters from CORBA
        else if (dataType.equals("ConvertFacetParameterToCpp")) {
            dataValue = convertParameterToCpp(operation);
        }
        else if (dataType.equals("DeclareFacetCppResult")) {
            dataValue = declareCppResult(operation);
        }
        else if (dataType.equals("ConvertInterfaceMethodToCpp")) {
            dataValue = convertInterfaceMethodToCpp(operation);
        }
        else if (dataType.equals("ConvertFacetExceptionsToCorba")) {
            dataValue = convertExceptionsToCorba(operation);
        }
        else if (dataType.equals("ConvertFacetParameterToCorba")) {
            dataValue = convertParameterToCorba(operation);
        }
        else if (dataType.equals("ConvertFacetResultToCorba")) {
            dataValue = convertResultToCorba(operation);
        }
        
        // Tags for Adapters to CORBA
        else if (dataType.equals("ConvertReceptacleParameterToCorba")) {
            dataValue = convertReceptacleParameterToCorba(operation);
        }
        else if (dataType.equals("DeclareReceptacleCorbaResult")) {
            dataValue = declareReceptacleCorbaResult(operation);
        }
        else if (dataType.equals("ConvertReceptacleMethodToCorba")) {
            dataValue = convertInterfaceMethodToCorba(operation);
        }
        else if (dataType.equals("ConvertReceptacleExceptionsToCpp")) {
            dataValue = convertReceptacleExceptionsToCpp(operation);
        }
        else if (dataType.equals("ConvertReceptacleParameterToCpp")) {
            dataValue = convertReceptacleParameterToCpp(operation);
        }
        else if (dataType.equals("ConvertReceptacleResultToCpp")) {
            dataValue = convertReceptacleResultToCpp(operation);
        }
        else {
            dataValue = super.data_MOperationDef(dataType, dataValue);
        }
        logger.fine("leave data_MOperationDef()");
        return dataValue;
    }

    
    protected String data_MEnumDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MEnumDef()");
        List ret = new ArrayList();
        MEnumDef enum = (MEnumDef) currentNode;
        if (dataType.equals("MembersFromCorba")) {
            dataValue = getMembersFromCorba(enum);
//            for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
//                String member = (String) i.next();
//                ret.add(Text.tab(1) + "case " 
//                        + Scope.getCorbaStubsNamespace(corbaStubsNamespace,
//                                                       (MContained)currentNode, "::") 
//                        + member + ":");
//                ret.add(Text.tab(2) + "out = " 
//                        + Scope.getLocalNamespace(localNamespace,
//                                                  (MContained)currentNode, "::", "") 
//                        + member + ";");
//                ret.add(Text.tab(2) + "break;");
//            }
//            return Text.join("\n", ret);
        }
        else if (dataType.equals("MembersToCorba")) {
            dataValue = getMembersToCorba(enum);
//            for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
//                String member = (String) i.next();
//                ret.add(Text.tab(1) + "case " 
//                        + Scope.getLocalNamespace(localNamespace,
//                                                  (MContained)currentNode, "::", "") 
//                        + member + ":");
//                ret.add(Text.tab(2) + "out = " 
//                        + Scope.getCorbaStubsNamespace(corbaStubsNamespace,
//                                                       (MContained)currentNode, "::") 
//                        + member + ";");
//                ret.add(Text.tab(2) + "break;");
//            }
//            return Text.join("\n", ret);
        }
        else if (dataType.equals("EnumCorbaOutput")) {
            dataValue = getEnumCorbaOutput(enum);
//            for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
//                String member = (String) i.next();
//                ret.add(Text.tab(1) + "case " 
//                        + Scope.getLocalNamespace(localNamespace,
//                                                  (MContained)currentNode, "::", "") 
//                        + member + ":");
//                ret.add(Text.tab(2) + "o << \"" 
//                        + Scope.getCorbaStubsNamespace(corbaStubsNamespace,
//                                                       (MContained)currentNode,"::") 
//                        + member 
//                        + "\" << endl;");
//                ret.add(Text.tab(2) + "break;");
//            }
//            return Text.join("\n", ret);
        }
        else {
            dataValue = super.data_MEnumDef(dataType, dataValue);
        }
        logger.fine("leave data_MEnumDef()");
        return dataValue;
    }

    
    protected String data_MFactoryDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MFactoryDef()");
        if (dataType.startsWith("MParameterCORBA")) {
            dataValue = getCORBAOperationParams((MOperationDef)currentNode);
        }
        else {
            dataValue = super.data_MFactoryDef(dataType, dataValue);
        }
        logger.fine("leave data_MFactoryDef()");
        return dataValue; 
    }

    
    protected String data_MHomeDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MHomeDef()");
        MHomeDef home = (MHomeDef) currentNode;
        MComponentDef component = home.getComponent();

        if (dataType.endsWith("ComponentType")) {
            dataValue = component.getIdentifier();
        }
        else if(dataType.endsWith("AbsoluteRemoteHomeName")) {
            dataValue = Scope.getRemoteName(baseNamespace, home,
                                            Text.MANGLING_SEPARATOR,"");
        }
        else {
            dataValue = super.data_MHomeDef(dataType, dataValue);
        }
        logger.fine("leave data_MHomeDef()");
        return dataValue;
    }

    
    protected String data_MInterfaceDef(String dataType, String dataValue)
    {
        logger.fine("data_MInterfaceDef()");
        
        MInterfaceDef iface = (MInterfaceDef) currentNode;
        StringBuffer buffer = new StringBuffer();
        
        if(dataType.equals("StubIdentifier")) {
            buffer.append(Scope.getCorbaStubsNamespace(corbaStubsNamespace,
                                                       iface,"::"));
            buffer.append(iface.getIdentifier());
            return buffer.toString();
        }
        else if(dataType.equals("CCM_LocalType")) {
            buffer.append(Scope.getLocalNamespace(localNamespace,iface,"::",""));
            buffer.append("CCM_");
            buffer.append(iface.getIdentifier());
            return buffer.toString();
        }

        else {
            return super.data_MInterfaceDef(dataType,dataValue);
        }
    }
    
    
    protected String data_MSupportsDef(String data_type, String data_value)
    {
        logger.fine("data_MSupportsDef()");
        
        MSupportsDef supports = (MSupportsDef) currentNode;

        if (data_type.equals("SupportsInclude")) {
            List scope = Scope.getScope((MContained) supports);
            if (scope.size() > 0) {
                return "CCM_Local/" + Text.join("/", scope) + "/"
                        + supports.getSupports().getIdentifier();
            }
            else {
                return "CCM_Local/" + supports.getSupports().getIdentifier();
            }
        }
        return super.data_MSupportsDef(data_type, data_value);
    }

    
    /**
     * Implements the following tags found in the MProvidesDef* templates:
     * 'ProvidesInclude' 'ProvidesConvertInclude' includes facet converters
     * 'IdlProvidesType' 'ProvidesType' 'ComponentType'
     */
    protected String data_MProvidesDef(String dataType, String dataValue)
    {
        logger.fine("data_MProvidesDef()");
        
        MProvidesDef provides = (MProvidesDef) currentNode;
        MInterfaceDef iface = ((MProvidesDef) currentNode).getProvides();
        MComponentDef component = provides.getComponent();
        List scope = Scope.getScope((MContained) iface);
        StringBuffer ret = new StringBuffer();

        if (dataType.equals("ProvidesInclude")) {
            // TODO: Refactoring namespace method
            ret.append("#include <CCM_Local/");
            if (scope.size() > 0) {
                ret.append(Text.join("/", scope));
                ret.append("/");
            }
            ret.append(provides.getProvides().getIdentifier());
            ret.append(".h>");
            dataValue = ret.toString();
        }
        else if (dataType.equals("ProvidesConvertInclude")) {
            ret.append("#include <CCM_Remote/");
            ret.append(provides.getProvides().getIdentifier());
            ret.append("_remote.h>");
            ret.append("\n");
            dataValue = ret.toString();
        }
        else if (dataType.equals("IdlProvidesType")) {
            ret.append(Scope.getCorbaStubsNamespace(corbaStubsNamespace,
                                                    iface,"::"));
            ret.append(iface.getIdentifier());
            dataValue = ret.toString();
        }
        else if (dataType.equals("ProvidesType")) {
            // TODO: Refactoring namespace method
            if (scope.size() > 0) {
                ret.append(Text.join("::", scope));
                ret.append("::");
            }
            ret.append(provides.getProvides().getIdentifier());
            dataValue = ret.toString();
        }
        else if (dataType.equals("InterfaceType")) {
            dataValue = provides.getProvides().getIdentifier();
        }
        else if (dataType.equals("ComponentType")) {
            dataValue = component.getIdentifier();
        }
        else {
            dataValue = super.data_MProvidesDef(dataType, dataValue);
        }
        return dataValue;
    }

    
    protected String data_MUsesDef(String dataType, String dataValue)
    {
        logger.fine("data_MUsesDef()");
        
        MUsesDef usesDef = (MUsesDef) currentNode;
        MInterfaceDef iface = ((MUsesDef) currentNode).getUses();
        List scope = Scope.getScope((MContained) iface);
        StringBuffer buffer = new StringBuffer();
        
        if (dataType.equals("UsesInclude")) {
            buffer.append("#include <");
            buffer.append(Scope.getLocalNamespace(localNamespace, 
                                                  iface, "/", ""));
            buffer.append(usesDef.getUses().getIdentifier());
            buffer.append(".h>");
            dataValue = buffer.toString();
        }
        else if(dataType.equals("UsesConvertInclude")) {
            buffer.append("#include <CCM_Remote/");
            buffer.append(usesDef.getUses().getIdentifier());
            buffer.append("_remote.h>");
            buffer.append("\n");
            dataValue = buffer.toString();
        }
        else if(dataType.equals("CCM_UsesType")) {
            // TODO: Refactoring namespace method
            if (scope.size() > 0) {
                buffer.append(Text.join("::", scope));
                buffer.append("::CCM_");
            }
            else {
                buffer.append("CCM_");
            }
            buffer.append(usesDef.getUses().getIdentifier());
            dataValue = buffer.toString();
        }
        else if(dataType.equals("IdlUsesType")) {
            buffer.append(Scope.getCorbaStubsNamespace(corbaStubsNamespace,
                                                       iface,"::"));
            buffer.append(usesDef.getUses().getIdentifier());
            dataValue = buffer.toString();
        }
        else if(dataType.equals("UsesType")) {
            // TODO: Refactoring namespace method
            if (scope.size() > 0) {
                buffer.append(Text.join("::", scope));
                buffer.append("::");
            }
            buffer.append(usesDef.getUses().getIdentifier());
            dataValue = buffer.toString();
        }
        else if(dataType.equals("InterfaceType")) {
            dataValue = usesDef.getUses().getIdentifier();
        }
        return super.data_MUsesDef(dataType, dataValue);
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
    public void writeOutput(Template template) throws IOException
    {
        logger.fine("writeOutput()");
        
        String sourceCode = template.substituteVariables(output_variables);
        String[] sourceFiles = sourceCode.split("<<<<<<<SPLIT>>>>>>>");
        String[] remoteSuffix = {
                "_remote.h", "_remote.cc"
        };

        for(int i = 0; i < sourceFiles.length; i++) {
            if(sourceFiles[i].trim().equals("")) {
                // skip the file creation
                continue;
            }

            try {
                if(currentNode instanceof MComponentDef) {
                    // write the component files
                    String componentName = ((MContained) currentNode).getIdentifier();
                    String fileDir = handleNamespace("FileNamespace", componentName)
                            + "_CCM_Session_" + componentName;

                    Code.writeFile(uiDriver, output_dir, fileDir, componentName + remoteSuffix[i],
                                   sourceFiles[i]);
                }
                else if(currentNode instanceof MHomeDef) {
                    // write the home files
                    MHomeDef home = (MHomeDef) currentNode;
                    String componentName = ((MContained) home.getComponent()).getIdentifier();
                    String homeName = home.getIdentifier();
                    String fileDir = handleNamespace("FileNamespace", componentName)
                            + "_CCM_Session_" + componentName;

                    Code.writeFile(uiDriver, output_dir, fileDir, homeName + remoteSuffix[i],
                                   sourceFiles[i]);
                    Code.writeMakefile(uiDriver, output_dir, fileDir, "py", "");
                }
                else if(currentNode instanceof MInterfaceDef || currentNode instanceof MAliasDef
                        || currentNode instanceof MStructDef
                        || currentNode instanceof MExceptionDef
                        || currentNode instanceof MEnumDef) {
                    // write converter files
                    String nodeName = ((MContained) currentNode).getIdentifier();
                    String fileDir = "CORBA_Converter";

                    Code.writeFile(uiDriver, output_dir, fileDir, nodeName + remoteSuffix[i],
                                   sourceFiles[i]);
                    Code.writeMakefile(uiDriver, output_dir, fileDir, "py", "");
                }
                else {
                    throw new RuntimeException("CppRemoteGeneratorImpl.writeOutput()"
                            + ": unhandled node!");
                }
            }
            catch(Exception e) {
                System.out.println("!!!Error " + e.getMessage());
            }
        }
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
        if (object instanceof MParameterDef || object instanceof MOperationDef) {
            MIDLType idlType = object.getIdlType();
            if (idlType instanceof MPrimitiveDef || idlType instanceof MStringDef
                    || idlType instanceof MWstringDef) {
                buffer.append(super.getBaseLanguageType(object));
            }
            else {
                buffer.append("CCM_Local::");
                buffer.append(super.getBaseLanguageType(object));
            }
        }
        else {
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
    protected String getLanguageType(MTyped object)
    {
        logger.fine("getLanguageType()");
        
        String base_type = getBaseIdlType(object);

        // override IDL array mapping from parent function.
        if (object instanceof MArrayDef) {
            Iterator i = ((MArrayDef) object).getBounds().iterator();
            Long bound = (Long) i.next();
            String result = base_type + "[" + bound;
            while (i.hasNext())
                result += "][" + (Long) i.next();
            return result + "]";
        }
        return super.getLanguageType(object);
    }

    
    
    
    //====================================================================
    // Handle the CORBA data types
    //====================================================================

    /**
     * Extract the scoped CORBA type from a MTyped entity. TODO: Refactoring,
     * this method is a subset of getCORBALanguageType()
     */
    protected String getCorbaType(MTyped type)
    {
        logger.fine("getCorbaType()");
        
        MIDLType idlType = type.getIdlType();
        String baseType = getBaseIdlType(type);
        String corbaType = "";

        if (corbaMappings.containsKey(baseType)) {
            // Primitive data types are mapped via map.
            corbaType = (String) corbaMappings.get(baseType);
        }
        else if (idlType instanceof MTypedefDef) {
            corbaType = Scope.getCorbaStubsNamespace(corbaStubsNamespace,
                                                     (MContained)currentNode,"::") 
            + baseType;
        }
        else {
            throw new RuntimeException("CppRemoteGeneratorImpl.getCorbaType(" + type
                    + "): unhandled MTyped!");
        }
        return corbaType;
    }

    /**
     * Converts the CCM model type information (MTyped) to the corresponding
     * CORBA types. If the MTyped type is used as an operation parameter, the
     * CORBA to C++ mapping parameter passing rules take place.
     * 
     * @param object
     *            Reference to a MTyped element of the CCM model.
     * @return Generated code for the CORBA type as string.
     * 
     * TODO: - Refactoring, move parameter handling out of this method, thus,
     * getCorbaType() can be eliminated! - Refactoring, use
     * getCorbaStubsNamespace("::") instead of scope...
     */
    protected String getCORBALanguageType(MTyped object)
    {
        logger.fine("getCORBALanguageType()");
        
        MIDLType idl_type = object.getIdlType();
        String base_type = getBaseIdlType(object);
        String corba_type = "";

        if (corbaMappings.containsKey(base_type)) {
            // Primitive data types are mapped via map.
            corba_type = (String) corbaMappings.get(base_type);
        }
        else if (object instanceof MContained) {
            // Contained types are mapped with CORBA namespace
            List scope = Scope.getScope((MContained) object);
            if (scope.size() > 0) {
                corba_type = Text.join("::", scope) + "::" + base_type;
            }
            else {
                corba_type = base_type;
            }
        }
        else if (idl_type instanceof MTypedefDef) {
            List scope = Scope.getScope((MContained) idl_type);
            if (scope.size() > 0) {
                corba_type = Text.join("::", scope) + "::" + base_type;
            }
            else {
                corba_type = base_type;
            }
        }

        // Reduce MAliasDef to the original type
        if (idl_type instanceof MAliasDef) {
            idl_type = ((MTyped) idl_type).getIdlType();
        }

        // Handle operation parameter types and passing rules
        if (object instanceof MParameterDef) {
            MParameterDef param = (MParameterDef) object;
            MParameterMode direction = param.getDirection();
            String prefix = "const ";
            String suffix = "&";

            // IN Parameter
            if (direction == MParameterMode.PARAM_IN) {
                if (idl_type instanceof MPrimitiveDef || idl_type instanceof MEnumDef
                        || idl_type instanceof MArrayDef) {
                    prefix = "";
                    suffix = "";
                }
                else if (idl_type instanceof MStringDef) {
                    suffix = "";
                }

            }
            // OUT Parameter
            else if (direction == MParameterMode.PARAM_OUT) {
                if (idl_type instanceof MStringDef) {
                    // OUT string is a special case
                    return "CORBA::String_out";
                }
                else {
                    return corba_type + "_out";
                }
            }
            // INOUT Parameter
            else if (direction == MParameterMode.PARAM_INOUT) {
                prefix = "";
                if (idl_type instanceof MArrayDef) {
                    suffix = "";
                }
            }
            return prefix + corba_type + suffix;
        }

        // Handle operation return types
        else if (object instanceof MOperationDef) {
            // ToDo separate fixed and variable struct
            if (idl_type instanceof MPrimitiveDef || idl_type instanceof MEnumDef
                    || idl_type instanceof MStringDef) {

                return corba_type;
            }
            else if (idl_type instanceof MArrayDef) {
                return corba_type + "_slice*";
            }
            else
                return corba_type + "*";
        }
        return corba_type;
    }
    
    

    

    
 

    
    //====================================================================
    // MSequenceDef tags helper methods
    //====================================================================
    
    protected String getConvertFromCorbaDeclaration(MContained node) 
    {
        String stubName =
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);
        String localName =
            Scope.getLocalName(localNamespace,node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void convertFromCorba(const ");
        code.append(stubName);
        code.append("& in, ");
        code.append(localName);
        code.append("& out);");
        return code.toString();
    }
        
    protected String getConvertToCorbaDeclaration(MContained node) 
    {
        String localName =
            Scope.getLocalName(localNamespace,node,Text.SCOPE_SEPARATOR);
        String stubName =
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void convertToCorba(const ");
        code.append(localName);
        code.append("& in, ");
        code.append(stubName);
        code.append("& out);");
        return code.toString();
    }

    protected String getConvertFromCorbaImplementation(MContained node, MTyped singleType)
    {
        String stubName = 
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);
        String localName =
            Scope.getLocalName(localNamespace,node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void\n");
        code.append("convertFromCorba(const "); 
        code.append(stubName);
        code.append("& in, ");
        code.append(localName);
        code.append("& out)\n");
        code.append("{\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_REMOTE,\" convertFromCorba(");
        code.append(stubName);
        code.append(")\");\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_REMOTE, in);\n");
        code.append(getConvertAliasFromCORBA(singleType));
        code.append("}\n");
        return code.toString();
    }

    protected String getConvertToCorbaImplementation(MContained node, MTyped singleType) 
    {
        String localName =
            Scope.getLocalName(localNamespace,node, Text.SCOPE_SEPARATOR);
        String stubName =
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("void\n");
        code.append("convertToCorba(const "); 
        code.append(localName); 
        code.append("& in, "); 
        code.append(stubName);
        code.append("& out)\n");
        code.append("{\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_REMOTE,\" convertToCorba(");
        code.append(stubName);
        code.append(")\");\n");
        code.append(getConvertAliasToCORBA(singleType));
        code.append(Text.TAB).append("LDEBUGNL(CCM_REMOTE, out);\n");
        code.append("}\n");
        return code.toString();
    }

    protected String getOutputCorbaTypeDeclaration(MContained node) 
    {
        String stubName =
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append("std::ostream& operator<<(std::ostream& o, const ");
        code.append(stubName);
        code.append("& value);");
        return code.toString();
    }

    protected String getOutputCorbaTypeImplementation(MContained node,
                                                      MContained contained) 
    {
        String stubName =
            Scope.getCorbaStubName(corbaStubsNamespace,node,Text.SCOPE_SEPARATOR);	
        StringBuffer code = new StringBuffer();
        code.append("std::ostream&\n");
        code.append("operator<<(std::ostream& o, const "); 
        code.append(stubName);
        code.append("& value)\n");
        code.append("{\n");
        code.append(getOutputCORBAType(node, contained));
        code.append(Text.TAB).append("return o;\n");
        code.append("}\n");
        return code.toString();
    }
    
    protected String getSingleValue(MTyped singleType) 
    {
        StringBuffer code = new StringBuffer();
        MIDLType singleIdlType = singleType.getIdlType();
        if (singleIdlType instanceof MPrimitiveDef 
                || singleIdlType instanceof MStringDef) {
            code.append(getBaseLanguageType(singleType));
        }
        else {
            code.append("CCM_Local::");
            // TODO: Handle local Namespace
            code.append(getBaseLanguageType(singleType));
        }
        return code.toString();
    }
    
    protected String getInOutValue(MTyped singleType) 
    {
        MIDLType singleIdlType = singleType.getIdlType();
        StringBuffer code = new StringBuffer();
        if (singleIdlType instanceof MStringDef) {
            code.append("out[i].inout()");
        }
        else {
            code.append("out[i]");
        }
        return code.toString();
    }

    protected String getCORBASequenceConverterInclude(MIDLType idlType,
                                                      MIDLType singleIdlType) 
    {
        StringBuffer code = new StringBuffer();
        if (singleIdlType instanceof MPrimitiveDef 
                || singleIdlType instanceof MStringDef) {
            
        }
        else if (idlType instanceof MStructDef 
                || idlType instanceof MAliasDef
                || idlType instanceof MSequenceDef) {
            MContained singleContained = (MContained) singleIdlType;
            code.append("#include \"");
            code.append(singleContained.getIdentifier());
            code.append("_remote.h\"");
        }
        else {
            throw new RuntimeException("getCORBASequenceConvertInclude()"
                    + " Unhandled idlType: " + idlType);
        }
        return code.toString();
    }
    
    protected String getConvertAliasFromCORBA(MTyped singleType) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("out.clear();\n");
        code.append(Text.TAB).append("out.reserve(in.length());\n");
        code.append(Text.TAB);
        code.append("for(unsigned long i=0; i < in.length();i++) {\n");
        code.append(Text.tab(2)).append(getSingleValue(singleType));
        code.append(" singleValue;\n");
        code.append(Text.tab(2));
        code.append("convertFromCorba(in[i], singleValue);\n");
        code.append(Text.tab(2)).append("out.push_back(singleValue);\n");
        code.append(Text.TAB).append("}\n");
        return code.toString();
    }

    protected String getConvertAliasToCORBA(MTyped singleType) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("out.length(in.size());\n");
        code.append(Text.TAB);
        code.append("for(unsigned long i=0; i < in.size(); i++) {\n");
        code.append(Text.tab(2)).append(getSingleValue(singleType));
        code.append(" singleValue = in[i];\n");
        code.append(Text.tab(2)).append("convertToCorba(singleValue, ");
        code.append(getInOutValue(singleType)).append(");\n");
        code.append(Text.TAB).append("}\n");
        return code.toString();
    }
    
    protected String getOutputCORBAType(MContained node, MContained contained) 
    {
        String stubNamespace =
            Scope.getCorbaStubsNamespace(corbaStubsNamespace, node,Text.SCOPE_SEPARATOR);
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("o << endl;\n");
        code.append(Text.TAB).append("o << \"sequence ");
        code.append(stubNamespace); 
        code.append(contained.getIdentifier());
        code.append(" [ \" << endl;\n");
        code.append(Text.TAB);
        code.append("for(unsigned long i=0; i < value.length();i++) {\n");
        code.append(Text.tab(2)).append("o << value[i] << endl;\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("o << \"]\";\n");
        return code.toString();
    }
    
    protected String getOutputCppType() 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("o << \"[ \";\n");
        code.append(Text.TAB);
        code.append("for(unsigned long i=0; i < value.size(); i++) {\n");
        code.append(Text.tab(2)).append("if(i) o << \",\";\n");
        code.append(Text.tab(2)).append("o << value[i];\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("o << \" ]\";\n");
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // MFieldDef tags helper methods
    //====================================================================
    
    protected String getCORBAFieldDirection(MIDLType idlType, String fieldName,
                                            int direction) 
    {
        StringBuffer code = new StringBuffer();
        code.append(fieldName);
        if (idlType instanceof MStringDef) {
            switch(direction) {
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
        return code.toString();
    }
    
    protected String getCORBAFieldConverterInclude(MIDLType idlType, 
                                                   String fieldName) 
    {
        StringBuffer code = new StringBuffer();
        if (idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef) {
            // no include statement
        }
        else if (idlType instanceof MStructDef 
                || idlType instanceof MAliasDef
                || idlType instanceof MEnumDef) {
            MContained contained = (MContained) idlType;
            code.append("#include \"");
            code.append(contained.getIdentifier());
            code.append("_remote.h\"");
        }
        else {
            throw new RuntimeException("getCORBAFieldConverterInclude()" 
                                       + ") Unhandled idlType: " + idlType);
        }
        return code.toString();
    }
    
    
    
    
    //====================================================================
    // MAttributeDef tags helper methods
    //====================================================================

    protected String getCorbaAttributeResult(MTyped object)
    {
        logger.fine("getCorbaAttributeResult()");
        MIDLType idlType = object.getIdlType();
        String dataValue = getCORBALanguageType(object);
        if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef || idlType instanceof MEnumDef) {
            dataValue += ""; 
        }
        else if(idlType instanceof MStructDef) {
            dataValue += "*";
        }
        else if(idlType instanceof MAliasDef){
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            
            if(containedIdlType instanceof MPrimitiveDef) {
                dataValue += "";
            }
            else if(containedIdlType instanceof MSequenceDef) {
                dataValue += "*";
            }
            // TODO: MArrayDef
        }
        return dataValue;
    }
    

    protected String getCorbaAttributeParameter(MTyped object)
    {
        logger.fine("getCorbaAttributeParameter()");
        MIDLType idlType = object.getIdlType();
        String dataValue = getCORBALanguageType(object);
       
        if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef || idlType instanceof MEnumDef) {
            dataValue += ""; 
        }
        else if(idlType instanceof MStructDef) {
            dataValue += "&"; 
        }
        else if(idlType instanceof MAliasDef){
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(containedIdlType instanceof MPrimitiveDef) {
                dataValue += "";
            }
            else if(containedIdlType instanceof MSequenceDef) {
                dataValue += "&";
            }
            // TODO: MArrayDef
        }
        return dataValue;			
    }
    

    /**
     * Generates the scoped (with namespace) local type of an attribute. 
     * - primitive types must not have namespaces.
     * - attributes are always passed by value.
     * 
     * @param object
     * @return
     */
    protected String getLocalAttributeType(MTyped object)
    {
        logger.fine("getLocalAttributeType()");
        MIDLType idlType = object.getIdlType();
        String dataValue;
        
        if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef ) {
            dataValue = getLanguageType(object); 
        }
        else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) {
            dataValue = Scope.getLocalName(localNamespace,(MContained)idlType, "::");	
        }
        else if(idlType instanceof MAliasDef){
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(containedIdlType instanceof MPrimitiveDef) {
                dataValue = getLanguageType(object);
            }
            else if(containedIdlType instanceof MSequenceDef) {
                dataValue = Scope.getLocalName(localNamespace,(MContained)idlType, "::");
            }
            else {
                String message = "Unhandled alias type in getLocalAttributeResult(): ";	
                throw new RuntimeException(message + containedIdlType);
            }
        }
        else {
            String message = "Unhandled idl type in getLocalAttributeResult(): ";	
            throw new RuntimeException(message + idlType);
        }
        return dataValue;
    }
    
    
    /**
     * Creates the code that converts CORBA attribute getter methods to local C++ calls.
     * @param attr An AttributeDef item of a CCM model.
     * @return A string containing the generated code.
     */
    protected String convertGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("convertGetAttributeFromCorba()");
        MIDLType idlType = ((MTyped) attr).getIdlType();
        String code = "";
        
        if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef || idlType instanceof MEnumDef) {	
            code = convertPrimitiveGetAttributeFromCorba(attr, delegate);
        }
        else if(idlType instanceof MStructDef) {  
            code = convertUserGetAttributeFromCorba(attr, delegate);
        }
        else if(idlType instanceof MAliasDef){     
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(containedIdlType instanceof MPrimitiveDef) {
                code = convertPrimitiveGetAttributeFromCorba(attr, delegate);
            }
            else if(containedIdlType instanceof MSequenceDef) {
                code = convertUserGetAttributeFromCorba(attr,delegate);
            }
            // TODO: MArrayDef   
        }
        return code;
    }

    protected String convertPrimitiveGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("convertPrimitiveGetAttributeFromCorba()");
        MIDLType idlType = ((MTyped) attr).getIdlType();	
        StringBuffer buffer = new StringBuffer();
        if(idlType instanceof MEnumDef) {
            buffer.append(Text.tab(1)).append("CCM_Local::")
                .append(getBaseLanguageType((MTyped) attr))
                .append(" result;\n");
        }
        else {
            buffer.append(Text.tab(1))
            	.append(getBaseLanguageType((MTyped) attr))
            	.append(" result;\n");
        }
        buffer.append(Text.tab(1)).append("result = ").append(delegate)
        		.append("->")
                .append(attr.getIdentifier()).append("();\n");
        buffer.append(Text.tab(1))
                .append(getCORBALanguageType((MTyped) attr))
                .append(" return_value;\n");
        buffer.append(Text.tab(1))
                .append("CCM_Remote::convertToCorba(result, return_value);\n");
        buffer.append(Text.tab(1)).append("return return_value;\n");
        return buffer.toString();
    }
        
    protected String convertUserGetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("convertUserGetAttributeFromCorba()");
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(1)).append("CCM_Local::")
                .append(getBaseLanguageType((MTyped) attr))
                .append(" result;\n");
        buffer.append(Text.tab(1)).append("result = ").append(delegate)
                .append("->")
                .append(attr.getIdentifier()).append("();\n");
        buffer.append(Text.tab(1))
                .append(getCORBALanguageType((MTyped) attr))
                .append("_var return_value = new ")
                .append(getCORBALanguageType((MTyped) attr)).append(";\n");
        buffer.append(Text.tab(1))
                .append("CCM_Remote::convertToCorba(result, return_value);\n");
        buffer.append(Text.tab(1))
                .append("return return_value._retn();\n");
        return buffer.toString();
    }    
    
    
    /**
     * Creates the code that converts CORBA attribute setter methods to local C++ calls.
     * @param attr An AttributeDef item of a CCM model.
     * @return A string containing the generated code.
     */
    protected String convertSetAttributeFromCorba(MAttributeDef attr, String delegate)
    {
        logger.fine("convertSetAttributeFromCorba()");
        MIDLType idlType = ((MTyped) attr).getIdlType();
        StringBuffer buffer = new StringBuffer();
       
        if(idlType instanceof MPrimitiveDef || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef) {
            buffer.append(Text.tab(1))
        		.append(getBaseLanguageType((MTyped) attr)).append(" local_value;\n");   
        }
        else {
            buffer.append(Text.tab(1)).append("CCM_Local::")
    		.append(getBaseLanguageType((MTyped) attr)).append(" local_value;\n");  
        }
        buffer.append(Text.tab(1))
			.append("CCM_Remote::convertFromCorba(value, local_value);\n");
        buffer.append(Text.tab(1))
            .append(delegate).append("->").append(attr.getIdentifier()).append("(local_value);\n");
        return buffer.toString();    
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
        logger.fine("convertGetAttributeToCorba()");
        MIDLType idlType = ((MTyped) attr).getIdlType();
        String code;
        
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef
                || idlType instanceof MEnumDef) {	
            code = convertPrimitiveGetAttributeToCorba(attr);
        }
        else if(idlType instanceof MStructDef) {  
            code = convertUserGetAttributeToCorba(attr);
        }
        else if(idlType instanceof MAliasDef){     
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(containedIdlType instanceof MPrimitiveDef) {
                code = convertPrimitiveGetAttributeToCorba(attr);
            }
            else if(containedIdlType instanceof MSequenceDef) {
                code = convertUserGetAttributeToCorba(attr);
            }
            else {
                String message = "Unhandled alias type in convertGetAttributeToCorba(): ";
                throw new RuntimeException(message + attr);
            }           
        }
        else {
            String message = "Unhandled idl type in convertGetAttributeToCorba(): ";
            throw new RuntimeException(message + attr);
        }
        return code;
    }
    
    protected String convertPrimitiveGetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("convertPrimitiveGetAttributeToCorba()");
        MIDLType idlType = ((MTyped) attr).getIdlType();	
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(1)).append(getCORBALanguageType((MTyped) attr))
            .append(" result;\n");
        buffer.append(Text.tab(1)).append("result = remoteInterface->")
        	.append(attr.getIdentifier()).append("();\n");
        buffer.append(Text.tab(1))//.append(getBaseLanguageType((MTyped) attr))
           	.append(getLocalAttributeType(attr))
        	.append(" return_value;\n");
        buffer.append(Text.tab(1))
            .append("CCM_Remote::convertFromCorba(result, return_value);\n");
        buffer.append(Text.tab(1)).append("return return_value;\n");
        return buffer.toString();
    }
        
    protected String convertUserGetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("convertUserGetAttributeToCorba()");
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(1)).append(getCORBALanguageType((MTyped) attr))
        	.append("_var result;\n");
        buffer.append(Text.tab(1)).append("result = remoteInterface->")
            .append(attr.getIdentifier()).append("();\n");
        buffer.append(Text.tab(1)).append(getLocalAttributeType(attr))	
            .append(" return_value;\n");
        buffer.append(Text.tab(1))
        	.append("CCM_Remote::convertFromCorba(result, return_value);\n");
        buffer.append(Text.tab(1)).append("return return_value;\n");
        return buffer.toString();
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
        logger.fine("convertSetAttributeToCorba()");
        MIDLType idlType = ((MTyped)attr).getIdlType();
        String code;
       
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef
                || idlType instanceof MEnumDef) {
            code = convertPrimitiveSetAttributeToCorba(attr);
        }
        else if(idlType instanceof MStructDef) {
            code = convertUserSetAttributeToCorba(attr);
        }
        else if(idlType instanceof MAliasDef) {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(containedIdlType instanceof MPrimitiveDef) {
                code = convertPrimitiveSetAttributeToCorba(attr);	
            }
            else if(containedIdlType instanceof MSequenceDef) {
                code = convertUserSetAttributeToCorba(attr);
            }
            else {
                String message = "Unhandled alias type in convertSetAttributeToCorba(): ";
                throw new RuntimeException(message + attr); 
            }
        }
        else {
            String message = "Unhandled idl type in convertSetAttributeToCorba(): ";
            throw new RuntimeException(message + attr);
        }
        return code;    
    }
    
    protected String convertPrimitiveSetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("convertPrimitiveSetAttributeToCorba()");
        MIDLType idlType = ((MTyped)attr).getIdlType();	
        StringBuffer buffer = new StringBuffer();
    
        buffer.append(Text.tab(1))
			.append(getCORBALanguageType((MTyped) attr)).append(" remote_value;\n");   
        buffer.append(Text.tab(1))	
    		.append("CCM_Remote::convertToCorba(value, remote_value);\n");
        buffer.append(Text.tab(1))	
    		.append("remoteInterface->").append(attr.getIdentifier())
    		.append("(remote_value);\n");
        return buffer.toString();
    }
 
    protected String convertUserSetAttributeToCorba(MAttributeDef attr)
    {
        logger.fine("convertUserSetAttributeToCorba()");
        MIDLType idlType = ((MTyped)attr).getIdlType();	
        StringBuffer buffer = new StringBuffer();

        buffer.append(Text.tab(1))
			.append(getCORBALanguageType((MTyped) attr)).append("_var remote_value = new ")
			.append(getCORBALanguageType((MTyped) attr)).append(";\n");   
        buffer.append(Text.tab(1))	
			.append("CCM_Remote::convertToCorba(value, remote_value);\n");
        buffer.append(Text.tab(1))	
			.append("remoteInterface->").append(attr.getIdentifier())
			.append("(remote_value);\n");
        return buffer.toString();
    }    
    
    
    protected String getAttributeConvertInclude(MIDLType idlType, 
                                                String baseType)
    {
        logger.fine("getAttributeConvertInclude()");
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MPrimitiveDef 
                || idlType instanceof MStringDef 
                || idlType instanceof MWstringDef) {
            // no include statement needed for primitive types
        }
         else {   
            code.append("#include \"");
            code.append(baseType);
            code.append("_remote.h\"\n");
        }
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
    protected String getCORBAOperationParams(MOperationDef op)
    {
        logger.fine("getCORBAOperationParams");
        
        List ret = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            ret.add(getCORBALanguageType(p) + " " + p.getIdentifier());
        }
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
        logger.fine("getCORBAExcepts()");
        
        List code = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) {
            MExceptionDef IdlException = (MExceptionDef) es.next();
            code.add(Scope.getCorbaStubName(corbaStubsNamespace,IdlException, "::"));
        }

        if (code.size() > 0) {
            return "throw(CORBA::SystemException, " + Text.join(", ", code) + ")";
        }
        else {
            return "throw(CORBA::SystemException)";
        }
    }

    
    protected String getLocalOperationParams(MOperationDef op)
    {
        logger.fine("getLocalOperationParams()");

        List list = new ArrayList();
        for (Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            list.add(getLanguageType(p) + " " + p.getIdentifier());
        }
        return Text.join(", ", list);
    }


    /**
     * overwrite CppGenerator.getOperationExcepts() 
     * Refactoring: move this method up to CppGenerator
     */
    protected String getOperationExcepts(MOperationDef op)
    {
        logger.fine("getOperationExcepts()");
        
        List code = new ArrayList();
        for (Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) {
            MExceptionDef idlException = (MExceptionDef) es.next();
            code.add(Scope.getLocalName(localNamespace,idlException, "::"));
        }
        if (code.size() > 0) {
            return ", " + Text.join(", ", code) + ")";
        }
        else {
            return ")";
        }
    }

    
	protected String getOperationConvertInclude(MIDLType idlType, String baseType) 
	{
	    StringBuffer code = new StringBuffer();
	    if (idlType instanceof MPrimitiveDef 
	            || idlType instanceof MStringDef) {
            // no include statement needed for primitive types
        }
        else {
            code.append("#include\"");
            code.append(baseType);
            code.append("_remote.h\"");
            code.append("\n");
        }
        return code.toString();
    }
    
	protected String getParameterConvertInclude(MOperationDef op) 
	{
	    StringBuffer code = new StringBuffer();
        for (Iterator i = op.getParameters().iterator(); i.hasNext();) {
            MParameterDef parameter = (MParameterDef) i.next();
            MTyped parameterType = (MTyped) parameter;
            MIDLType parameterIdlType = parameterType.getIdlType();
            if (parameterIdlType instanceof MPrimitiveDef
                    || parameterIdlType instanceof MStringDef) {
                // no include statement needed for primitive types
            }
            else {
                code.append("#include\"");
                code.append(getBaseIdlType(parameter));
                code.append("_remote.h\"");
                code.append("\n");
            }
        }
        return code.toString();
    }
	
    protected String getExceptionConvertInclude(MOperationDef op) 
    {
        StringBuffer code = new StringBuffer();
        for (Iterator i = op.getExceptionDefs().iterator(); i.hasNext();) {
            MExceptionDef exception = (MExceptionDef) i.next();
            code.append("#include\"");
            code.append(exception.getIdentifier());
            code.append("_remote.h\"");
            code.append("\n");
        }
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
        logger.fine("convertParameterToCpp()");
        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idl_type = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idl_type)) {
                ret.add(convertPrimitiveParameterFromCorbaToCpp(p));
            }
            else if(isComplexType(idl_type)) {
                ret.add(convertUserParameterFromCorbaToCpp(p));
            }
        }
        return Text.join("\n", ret) + "\n";
    }

    protected String convertPrimitiveParameterFromCorbaToCpp(MParameterDef p)
    {
        logger.fine("convertPrimitiveParameterFromCorbaToCpp()");
        
        MParameterMode direction = p.getDirection();
        String cppType = getBaseLanguageType(p);
        List ret = new ArrayList();
        ret.add(Text.tab(1) + cppType + " parameter_" + p.getIdentifier() + ";");
        if(direction != MParameterMode.PARAM_OUT) {
            ret.add(Text.tab(1) + "CCM_Remote::convertFromCorba(" + p.getIdentifier()
                    + ", parameter_" + p.getIdentifier() + ");");
        }
        return Text.join("\n", ret);
    }

    protected String convertUserParameterFromCorbaToCpp(MParameterDef p)
    {
        logger.fine("convertUserParameterFromCorbaToCpp()");
        
        List ret = new ArrayList();
        MParameterMode direction = p.getDirection();
        MIDLType idlType = ((MTyped) p).getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        List scope = Scope.getScope(contained);
        String localScope;

        // TODO: use LocalNamespace!!!
        if(scope.size() > 0)
            localScope = Text.join("::", scope) + "::";
        else
            localScope = "";

        ret.add("    CCM_Local::" + localScope + contained.getIdentifier() + " parameter_"
                + p.getIdentifier() + ";");
        if(direction != MParameterMode.PARAM_OUT) {
            ret.add("    CCM_Remote::convertFromCorba(" + p.getIdentifier() + ", parameter_"
                    + p.getIdentifier() + ");");
        }
        return Text.join("\n", ret);
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
        logger.fine("declareCppResult()");
        
        String ret = "";
        MIDLType idlType = op.getIdlType();
        List scope = Scope.getScope((MContained) op);
        String localScope;

        if(scope.size() > 0)
            localScope = Text.join("::", scope) + "::";
        else
            localScope = "";

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
            return "";
        }

        if(isPrimitiveType(idlType)) {
            String cppType = getBaseLanguageType(op);
            ret = Text.tab(1) + cppType + " result;";
        }
        else if(isComplexType(idlType)) {
            MTypedefDef typedef = (MTypedefDef) idlType;
            MContained contained = (MContained) typedef;
            ret = "    CCM_Local::" + localScope + contained.getIdentifier() + " result;";
        }
        return ret;
    }

    
    protected String convertInterfaceMethodToCpp(MOperationDef op)
    {
        logger.fine("convertInterfaceMethodToCpp()");
        
        List ret = new ArrayList();
        String resultPrefix = Text.tab(2);
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
            // void foo()
        }
        else {
            resultPrefix += "result = ";
        }

        if(isPrimitiveType(idlType) || isComplexType(idlType)) {
            resultPrefix += "localInterface->" + op.getIdentifier() + "(";
            for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
                MParameterDef p = (MParameterDef) params.next();
                String base_type = (String) language_mappings.get((String) getBaseIdlType(p));
                ret.add(" parameter_" + p.getIdentifier());
            }
            return resultPrefix + Text.join(", ", ret) + ");";
        }
        else {
            throw new RuntimeException("CppRemoteGeneratorImpl.convertMethodToCpp():"
                    + "unhandled idl type " + idlType);
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
        logger.fine("convertExceptionsToCorba()");
        
        List code = new ArrayList();
        for(Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) {
            MExceptionDef exception = (MExceptionDef) es.next();
            code.add(Text.tab(1) + "catch(const " + 
                     Scope.getLocalName(localNamespace,exception, "::")
                    + "& ce) { ");
            code.add(Text.tab(2) + Scope.getCorbaStubName(corbaStubsNamespace,
                                                          exception, "::") + " te;");
            code.add(Text.tab(2) + "CCM_Remote::convertToCorba(ce, te);");
            code.add(Text.tab(2) + "throw te;");
            code.add(Text.tab(1) + "}");
        }
        return Text.join("\n", code);
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
        logger.fine("convertParameterToCorba()");
        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idl_type = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idl_type)) {
                ret.add(convertPrimitiveParameterFromCppToCorba(p));
            }
            else if(idl_type instanceof MStructDef || idl_type instanceof MEnumDef) {
                ret.add(convertUserParameterFromCppToCorba(p));
            }
            else if(idl_type instanceof MAliasDef) {
                MTyped containedType = (MTyped) idl_type;
                MIDLType containedIdlType = containedType.getIdlType();
                if(isPrimitiveType(containedIdlType)) {
                    ret.add(convertPrimitiveParameterFromCppToCorba(p));
                }
                else {
                    ret.add(convertUserParameterFromCppToCorba(p));
                }
            }
        }
        return Text.join("\n", ret) + "\n";
    }

    protected String convertPrimitiveParameterFromCppToCorba(MParameterDef p)
    {
        logger.fine("convertPrimitiveParameterFromCppToCorba()");
        
        List ret = new ArrayList();
        MParameterMode direction = p.getDirection();
        if(direction != MParameterMode.PARAM_IN) {
            ret.add("    CCM_Remote::convertToCorba(parameter_" + p.getIdentifier() + ", "
                    + p.getIdentifier() + ");");
        }
        return Text.join("\n", ret);
    }

    protected String convertUserParameterFromCppToCorba(MParameterDef p)
    {
        logger.fine("convertUserParameterFromCppToCorba()");
        
        MParameterMode direction = p.getDirection();
        List ret = new ArrayList();

        if(direction == MParameterMode.PARAM_IN) {
            return "";
        }
        else if(direction == MParameterMode.PARAM_INOUT) {
            ret.add("    CCM_Remote::convertToCorba(parameter_" + p.getIdentifier() + ", "
                    + p.getIdentifier() + ");");
        }
        else {
            MIDLType idlType = ((MTyped) p).getIdlType();
            MTypedefDef typedef = (MTypedefDef) idlType;
            MContained contained = (MContained) typedef;
            List scope = Scope.getScope(contained);
            String remoteScope = "::"; //"::CORBA_Stubs::";
            if(scope.size() > 0)
                remoteScope += Text.join("::", scope) + "::";

            ret.add("    " + p.getIdentifier() + " = new " + remoteScope
                    + contained.getIdentifier() + ";");
            ret.add("    CCM_Remote::convertToCorba(parameter_" + p.getIdentifier() + ", "
                    + p.getIdentifier() + ");");
        }
        return Text.join("\n", ret);
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
        logger.fine("convertResultToCorba()");
        
        List ret = new ArrayList();
        MIDLType idl_type = op.getIdlType();

        if(idl_type instanceof MPrimitiveDef
                && ((MPrimitiveDef) idl_type).getKind() == MPrimitiveKind.PK_VOID) {
            return "";
        }

        if(isPrimitiveType(idl_type)) {
            ret.add(convertPrimitiveResultFromCppToCorba(op));
        }
        else if(idl_type instanceof MStructDef || idl_type instanceof MSequenceDef
                || idl_type instanceof MEnumDef) {
            ret.add(convertUserResultFromCppToCorba(op));
        }
        else if(idl_type instanceof MAliasDef) {
            MTyped containedType = (MTyped) idl_type;
            MIDLType containedIdlType = containedType.getIdlType();
   
            if(isPrimitiveType(containedIdlType)) {
                ret.add(convertPrimitiveResultFromCppToCorba(op));
            }
            else if(isComplexType(containedIdlType)) {
                ret.add(convertUserResultFromCppToCorba(op));
            }
            else {
                throw new RuntimeException("CppRemoteGeneratorImpl.convertResultToCorba()"
                        + ": Not supported alias type " + containedIdlType);
            }
        }
        else {
            throw new RuntimeException("CppRemoteGeneratorImpl.convertResultToCorba()"
                    + ": Not supported type" + idl_type);
        }
        return Text.join("\n", ret);
    }

    protected String convertPrimitiveResultFromCppToCorba(MOperationDef op)
    {
        logger.fine("convertPrimitiveResultFromCppToCorba()");
        
        String base_type = getBaseIdlType(op);
        List ret = new ArrayList();
        // Convert the result iff the result type is not void
        if(!base_type.equals("void")) {
            ret.add("    " + getCORBALanguageType(op) + " return_value;");
            ret.add("    CCM_Remote::convertToCorba(result, return_value);");
            ret.add("    return return_value;");
        }
        return Text.join("\n", ret);
    }

    protected String convertUserResultFromCppToCorba(MOperationDef op)
    {
        logger.fine("convertUserResultFromCppToCorba()");
        
        List ret = new ArrayList();
        MIDLType idlType = op.getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        List scope = Scope.getScope(contained);
        String remoteScope = "::"; //"::CORBA_Stubs::";

        if(scope.size() > 0)
            remoteScope += Text.join("::", scope) + "::";

        ret.add("    " + remoteScope + contained.getIdentifier() + "_var " + "return_value = new "
                + remoteScope + contained.getIdentifier() + ";");
        ret.add("    CCM_Remote::convertToCorba(result, return_value);");
        ret.add("    return return_value._retn();");
        return Text.join("\n", ret);
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
        logger.fine("convertReceptacleParameterToCorba()");
        
        List list = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idlType)) {
                list.add(convertPrimitiveParameterToCorba(p));
            }
            else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) {
                list.add(convertUserParameterToCorba(p));
            }
            else if(idlType instanceof MAliasDef) {
                MTyped containedType = (MTyped) idlType;
                MIDLType containedIdlType = containedType.getIdlType();

                if(isPrimitiveType(containedIdlType)) {
                    list.add(convertPrimitiveParameterToCorba(p));
                }
                else if(isComplexType(containedIdlType)) {
                    list.add(convertUserParameterToCorba(p));
                }
                else {
                    throw new RuntimeException(
                            "CppRemoteGenerator.convertReceptacleParameterToCorba()"
                                    + ": Not supported alias type " + containedIdlType);
                }
            }
        }
        return Text.join("\n", list) + "\n";
    }

    protected String convertPrimitiveParameterToCorba(MParameterDef p)
    {
        logger.fine("convertPrimitiveParameterToCorba()");
        
        MParameterMode direction = p.getDirection();
        MIDLType idlType = ((MTyped) p).getIdlType();
        String baseType = getBaseIdlType(p);
        String corbaType = getCorbaType(p);
        List list = new ArrayList();

        list.add(Text.tab(1) + corbaType + " parameter_" + p.getIdentifier() + ";");
        if(direction != MParameterMode.PARAM_OUT) {
            list.add(Text.tab(1) + "CCM_Remote::convertToCorba(" + p.getIdentifier()
                    + ", parameter_" + p.getIdentifier() + ");");
        }
        return Text.join("\n", list);
    }

    protected String convertUserParameterToCorba(MParameterDef p)
    {
        logger.fine("convertUserParameterToCorba()");
        
        MParameterMode direction = p.getDirection();
        MIDLType idlType = ((MTyped) p).getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        List list = new ArrayList();

        if(direction == MParameterMode.PARAM_IN || direction == MParameterMode.PARAM_INOUT) {
            list.add(Text.tab(1) + Scope.getCorbaStubName(corbaStubsNamespace,
                                                          contained, "::") 
                                                          + "_var parameter_"
                    + p.getIdentifier()
                    + "= new " + Scope.getCorbaStubName(corbaStubsNamespace,contained, "::") + ";");
            list.add(Text.tab(1) + "CCM_Remote::convertToCorba(" + p.getIdentifier()
                    + ", parameter_" + p.getIdentifier() + ");");
        }
        else { // MParameterMode.PARAM_OUT
            list.add(Text.tab(1) + Scope.getCorbaStubName(corbaStubsNamespace,
                                                          contained, "::") 
                                                          + "_var parameter_"
                                                          + p.getIdentifier() + ";");
        }
        return Text.join("\n", list);
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
        logger.fine("declareReceptacleCorbaResult()");
        
        MIDLType idlType = op.getIdlType();
        String result = "";

        // void foo() does not need a result declaration
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
            return "";
        }

        if(isPrimitiveType(idlType)) {
            result = declareReceptacleCorbaPrimitiveResult(op);
        }
        else if(idlType instanceof MStructDef || idlType instanceof MEnumDef) {
            result = declareReceptacleCorbaUserResult(op);
        }
        else if(idlType instanceof MAliasDef) {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(isPrimitiveType(containedIdlType)) {
                result = declareReceptacleCorbaPrimitiveResult(op);
            }
            else if(isComplexType(containedIdlType)) {
                result = declareReceptacleCorbaUserResult(op);
            }
            else {
                throw new RuntimeException("CppRemoteGenerator.declareReceptacleCorbaResult()"
                        + ": unhandled MAilasDef " + containedIdlType);
            }
        }
        else {
            throw new RuntimeException("CppRemoteGenerator.declareReceptacleCorbaResult()"
                    + ": unhandled idlType " + idlType);
        }
        return result;
    }

    protected String declareReceptacleCorbaPrimitiveResult(MOperationDef op)
    {
        logger.fine("declareReceptacleCorbaPrimitiveResult()");
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(1));
        buffer.append(getCorbaType(op));
        buffer.append(" result;");
        return buffer.toString();
    }

    protected String declareReceptacleCorbaUserResult(MOperationDef op)
    {
        logger.fine("declareReceptacleCorbaUserResult()");
        
        MIDLType idlType = op.getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(1));
        buffer.append(Scope.getCorbaStubsNamespace(corbaStubsNamespace,
                                                   (MContained)currentNode,"::"));
        buffer.append(contained.getIdentifier());
        buffer.append("_var result;");
        return buffer.toString();
    }

    
    protected String convertInterfaceMethodToCorba(MOperationDef op)
    {
        logger.fine("convertInterfaceMethodToCorba()");
        
        StringBuffer buffer = new StringBuffer(Text.tab(2));
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();

        buffer.append(Text.tab(1));
        // void method, no result declaration
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
        }
        else {
            buffer.append("result = ");
        }
        buffer.append("remoteInterface->");
        buffer.append(op.getIdentifier());
        buffer.append("(");

        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            list.add("parameter_" + p.getIdentifier());
        }
        buffer.append(Text.join(", ", list));
        buffer.append(");");
        return buffer.toString();
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
        logger.fine("convertReceptacleExceptionsToCpp()");
        
        List code = new ArrayList();
        for(Iterator es = op.getExceptionDefs().iterator(); es.hasNext();) {
            MExceptionDef exception = (MExceptionDef) es.next();
            code.add(Text.tab(1) + "catch(const " + 
                     Scope.getCorbaStubName(corbaStubsNamespace,exception, "::")
                    + "& ce) {");
            code.add(Text.tab(2) + Scope.getLocalName(localNamespace,
                                                      exception, "::") + " te;");
            code.add(Text.tab(2) + "CCM_Remote::convertFromCorba(ce, te);");
            code.add(Text.tab(2) + "throw te;");
            code.add(Text.tab(1) + "}");
        }
        return Text.join("\n", code);
    }

    
    protected String convertReceptacleParameterToCpp(MOperationDef op)
    {
        logger.fine("convertReceptacleParameterToCpp()");
        
        List ret = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idl_type = ((MTyped) p).getIdlType();

            if(isPrimitiveType(idl_type)) {
                ret.add(convertReceptaclePrimitiveParameterToCpp(p));
            }
            else if(idl_type instanceof MStructDef || idl_type instanceof MEnumDef) {
                ret.add(convertReceptacleUserParameterToCpp(p));
            }
            else if(idl_type instanceof MAliasDef) {
                MTyped containedType = (MTyped) idl_type;
                MIDLType containedIdlType = containedType.getIdlType();
                if(isPrimitiveType(containedIdlType)) {
                    ret.add(convertReceptaclePrimitiveParameterToCpp(p));
                }
                else {
                    ret.add(convertReceptacleUserParameterToCpp(p));
                }
            }
        }
        return Text.join("\n", ret) + "\n";
    }

    protected String convertReceptaclePrimitiveParameterToCpp(MParameterDef p)
    {
        logger.fine("convertReceptaclePrimitiveParameterToCpp()");
        
        List list = new ArrayList();
        MParameterMode direction = p.getDirection();
        if(direction != MParameterMode.PARAM_IN) {
            list.add("    CCM_Remote::convertFromCorba(parameter_" + p.getIdentifier() + ", "
                    + p.getIdentifier() + ");");
        }
        return Text.join("\n", list);
    }

    // TODO: If there are no additional features, remove this method
    protected String convertReceptacleUserParameterToCpp(MParameterDef p)
    {
        logger.fine("convertReceptacleUserParameterToCpp()");
        
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
        logger.fine("convertReceptacleResultToCpp()");
        
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
            return ""; // convertion
        }

        if(isPrimitiveType(idlType)) {
            list.add(convertReceptaclePrimitiveResultToCpp(op));
        }
        else if(idlType instanceof MStructDef || idlType instanceof MSequenceDef
                || idlType instanceof MEnumDef) {
            list.add(convertReceptacleUserResultToCpp(op));
        }
        else if(idlType instanceof MAliasDef) {
            MTyped containedType = (MTyped) idlType;
            MIDLType containedIdlType = containedType.getIdlType();
            if(isPrimitiveType(containedIdlType)) {
                list.add(convertReceptaclePrimitiveResultToCpp(op));
            }
            else if(isComplexType(containedIdlType)) {
                list.add(convertReceptacleUserResultToCpp(op));
            }
            else {
                throw new RuntimeException("CppRemoteGeneratorImpl.convertReceptacleResultToCpp()"
                        + ": Not supported alias type " + containedIdlType);
            }
        }
        else {
            throw new RuntimeException("CppRemoteGeneratorImpl.convertReceptacleResultToCpp()"
                    + ": unhandled idl type " + idlType);
        }
        return Text.join("\n", list);
    }

    protected String convertReceptaclePrimitiveResultToCpp(MOperationDef op)
    {
        logger.fine("convertReceptaclePrimitiveResultToCpp()");
        
        String baseType = getBaseIdlType(op);
        List list = new ArrayList();

        list.add(Text.tab(1) + getLanguageType(op) + " return_value;");
        list.add(Text.tab(1) + "CCM_Remote::convertFromCorba(result, return_value);");
        list.add(Text.tab(1) + "return return_value;");
        return Text.join("\n", list);
    }

    protected String convertReceptacleUserResultToCpp(MOperationDef op)
    {
        logger.fine("convertReceptacleUserResultToCpp()");
        
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();
        MTypedefDef typedef = (MTypedefDef) idlType;
        MContained contained = (MContained) typedef;

        list.add(Text.tab(1) + 
                 Scope.getLocalNamespace(localNamespace,(MContained)currentNode, "::", "") 
                + contained.getIdentifier()
                + " return_value;");
        list.add(Text.tab(1) + "CCM_Remote::convertFromCorba(result, return_value);");
        list.add(Text.tab(1) + "return return_value;");
        return Text.join("\n", list);
    }
    
    
    
    
    //====================================================================
    // MEnumDef %(tag)s helper methods
    //====================================================================
    
    protected String getMembersFromCorba(MEnumDef enum) 
    {
        StringBuffer code = new StringBuffer();
        for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
            String member = (String) i.next();
            String stubNs = Scope.getCorbaStubsNamespace(corbaStubsNamespace,enum, 
                                             Text.SCOPE_SEPARATOR);
            String localNs = Scope.getLocalNamespace(localNamespace,enum, 
                                        Text.SCOPE_SEPARATOR, ""); 
            code.append(Text.TAB).append("case "); 
            code.append(stubNs).append(member).append(":\n");
            code.append(Text.tab(2)).append("out = ");
            code.append(localNs).append(member).append(";\n");
            code.append(Text.tab(2)).append("break;\n");
        }
        return code.toString();
    }
    
    protected String getMembersToCorba(MEnumDef enum) 
    {
        StringBuffer code = new StringBuffer();
        for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
            String member = (String) i.next();
            String lns = Scope.getLocalNamespace(localNamespace,enum, 
                                          Text.SCOPE_SEPARATOR, "");
            String sns = Scope.getCorbaStubsNamespace(corbaStubsNamespace,enum, 
                                                      Text.SCOPE_SEPARATOR); 
            code.append(Text.TAB).append("case "); 
            code.append(lns).append(member).append(":\n");
            code.append(Text.tab(2)).append("out = "); 
            code.append(sns).append(member).append(";\n");
            code.append(Text.tab(2)).append("break;\n");
        }
        return code.toString();
    }
    
    protected String getEnumCorbaOutput(MEnumDef enum) 
    {
        StringBuffer code = new StringBuffer();
        for (Iterator i = enum.getMembers().iterator(); i.hasNext();) {
            String member = (String) i.next();
            String lns = Scope.getLocalNamespace(localNamespace,enum,
                                                 Text.SCOPE_SEPARATOR, "");
            String sns = Scope.getCorbaStubsNamespace(corbaStubsNamespace,enum,
                                                      Text.SCOPE_SEPARATOR); 
            code.append(Text.TAB).append("case "); 
            code.append(lns).append(member).append(":\n");
            code.append(Text.tab(2)).append("o << \""); 
            code.append(sns).append(member).append("\" << endl;\n");
            code.append(Text.tab(2)).append("break;\n");
        }
        return code.toString();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

 
    

        

        
    

        
    /**
     * Create the code that makes the local method call, with all of the local
     * parameters. Note that the local method must be part of the object
     * local_adapter points to.
     * 
     * The %(MParameterDefConvertMethod)s tag forces a call to this method via
     * getTwoStepVariables().
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertMethodToCpp(MOperationDef op)
    {
        logger.fine("convertMethodToCpp()");
        
        List ret = new ArrayList();
        String resultPrefix;
        MIDLType idlType = op.getIdlType();

        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
            resultPrefix = ""; // void foo()
        }
        else {
            resultPrefix = Text.tab(2) + "result = ";
        }

        if(isPrimitiveType(idlType) || isComplexType(idlType)) {
            resultPrefix += "local_adapter->" + op.getIdentifier() + "(";
            for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
                MParameterDef p = (MParameterDef) params.next();
                String base_type = (String) language_mappings.get((String) getBaseIdlType(p));
                ret.add(" parameter_" + p.getIdentifier());
            }
            return resultPrefix + Text.join(", ", ret) + ");";
        }
        else {
            throw new RuntimeException("CppRemoteGeneratorImpl.convertMethodToCpp():"
                    + "unhandled idl type " + idlType);
        }
    }


    
    
    
    
    //====================================================================
    // Receptacle Adapter Stuff (Converters from local C++ to CORBA) 
    //====================================================================
    
    /**
     * Create the code that makes to remote method call, with all of the CORBA
     * parameters.
     * 
     * The %(MParameterDefConvertReceptacleMethodToCorba)s tag forces a call to
     * this method via getTwoStepVariables().
     * 
     * @param op
     *            Reference to an OperationDef element in the CCM model.
     * @return Generated code as a string.
     */
    protected String convertReceptacleMethodToCorba(MOperationDef op, String receptacleName)
    {
        logger.fine("convertReceptacleMethodToCorba()");
        
        StringBuffer buffer = new StringBuffer(Text.tab(2));
        List list = new ArrayList();
        MIDLType idlType = op.getIdlType();

        // void method, no result declaration
        if(idlType instanceof MPrimitiveDef
                && ((MPrimitiveDef) idlType).getKind() == MPrimitiveKind.PK_VOID) {
        }
        else {
            buffer.append("result = ");
        }
        buffer.append("component_adapter->get_connection_");
        buffer.append(receptacleName);
        buffer.append("()->");
        buffer.append(op.getIdentifier());
        buffer.append("(");

        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            list.add("parameter_" + p.getIdentifier());
        }
        buffer.append(Text.join(", ", list));
        buffer.append(");");
        return buffer.toString();
    }

    
 

    //====================================================================
    // Miscellaneous utility methods
    //====================================================================

    protected boolean isPrimitiveType(MIDLType type)
    {
        if(type instanceof MPrimitiveDef 
                || type instanceof MStringDef
                || type instanceof MWstringDef 
                || type instanceof MEnumDef) {
            return true;
        }
        else {
            return false;
        }
    }

    protected boolean isComplexType(MIDLType type)
    {
        if(type instanceof MStructDef 
                || type instanceof MSequenceDef
                || type instanceof MAliasDef) {
            return true;
        }
        else {
            return false;
        }
    }

}