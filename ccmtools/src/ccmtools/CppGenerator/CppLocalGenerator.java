/*
 * CCM Tools : C++ Code Generator Library 
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@salomon.at> 
 * Copyright (C) 2002 - 2005 Salomon Automation
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

import ccmtools.CcmtoolsProperties;
import ccmtools.Constants;
import ccmtools.CodeGenerator.Template;
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
import ccmtools.Metamodel.BaseIDL.MUnionDef;
import ccmtools.Metamodel.BaseIDL.MWstringDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.UI.Driver;
import ccmtools.utils.Text;

/***
 * Local C++ component generator
 * 
 * This generator creates local C++ interfaces and component implementations
 * that conforms to the CORBA Component Model but does not use CORBA types
 * and libraries.
 ***/
public class CppLocalGenerator 
	extends CppGenerator
{
    //====================================================================
    // Definition of arrays that determine the generator's behavior
    //====================================================================
    
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.
    protected final static String[] LOCAL_OUTPUT_TEMPLATE_TYPES = {
            "MComponentDef", "MInterfaceDef", "MHomeDef", "MStructDef",
            "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef",
            "MProvidesDef"
    };

    
    public CppLocalGenerator(Driver uiDriver, File outDir) 
    	throws IOException
    {
        super("CppLocal", uiDriver, outDir, LOCAL_OUTPUT_TEMPLATE_TYPES);
        logger = Logger.getLogger("ccm.generator.cpp.local");
        logger.fine("enter CppLocalGenerator()");
        baseNamespace.add("ccm");
        baseNamespace.add("local");
        logger.fine("leave CppLocalGenerator()");
    }

    
    //====================================================================
    // Code generator core methods
    //====================================================================
        
    /**
     * Generate the namespace for ccmDebug() methods.
     * For model elements not derived from MContained the predefined
     * CCM_Local::ccmDebug() methods will be used (defined in the 
     * cpp_environment).
     * 
     * @param baseNamespace List of predefined namespaces (e.g. CCM_Local)
     * @param idlType IDL type of the current model element.
     * @return A string containing the ccmDebug() method's namespace of 
     * the current model element.
     */
    public String getDebugNamespace(MIDLType idlType)
    {
        logger.fine("enter getDebugNamespace()");
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MAliasDef) {
            MTyped type = (MTyped) idlType;
            MIDLType innerIdlType = type.getIdlType();
            if(innerIdlType instanceof MPrimitiveDef 
                    || innerIdlType instanceof MStringDef
                    || innerIdlType instanceof MWstringDef) {
                code.append(Text.join(Text.SCOPE_SEPARATOR, baseNamespace)); 
            }
            else {
                code.append(getLocalCppNamespace((MContained)idlType,
                                                 Text.SCOPE_SEPARATOR));
            }
        }
        else if(idlType instanceof MContained) {
            code.append(getLocalCppNamespace((MContained)idlType, 
                                             Text.SCOPE_SEPARATOR));
    	}
        else {
            code.append(Text.join(Text.SCOPE_SEPARATOR, baseNamespace)); 
        }
        logger.fine("leave getDebugNamespace()");
        return code.toString();
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
    protected Map getTwoStepOperationVariables(MOperationDef operation,
                                               MContained container)
    {
        logger.fine("enter getTwoStepOperationVariables()");
        String langType = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object", container.getIdentifier());
        vars.put("Identifier", operation.getIdentifier());
        vars.put("LanguageType", langType);
        
        vars.put("ExceptionThrows", getOperationExcepts(operation));
        vars.put("OperationParameterList", getOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));
        
        // these tags are used to generate local adapter implementations
        vars.put("OperationToFacetDelegation", 
                 getOperationDelegation(operation,"facet"));
        vars.put("OperationToLocalComponentDelegation",
                 getOperationDelegation(operation,"local_component"));
        vars.put("OperationResult", 
                 getOperationResult(operation));         
        vars.put("Return", getOperationReturn(operation));
        
        // these tags are used for debug output generation
        vars.put("DebugOperationInParameter", 
                 getDebugOperationInParameter(operation));
        vars.put("DebugOperationOutParameter", 
                 getDebugOperationOutParameter(operation));
        vars.put("DebugOperationResult", 
                 getDebugOperationResult(operation));
        
        logger.fine("leave getTwoStepOperationVariables()");
        return vars;
    }

    /**
     * Overrides the CppGenerator method to handle MAliasDef subtypes
     * TODO: Refactor - move this code up to the superclass
     */
    protected String getLocalValue(String variable)
    {
        logger.fine("getLocalValue()");
        // Get local value of CppGenerator 
        String value = super.getLocalValue(variable);
        
        if(variable.equals("DebugInclude")) {
            return getDebugInclude();
        }
        else if(variable.equals("DebugNamespace")) {
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            return getDebugNamespace(idlType);
        }
        else if(variable.equals("LanguageTypeInclude")) {
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            return getLanguageTypeInclude(idlType);
        }
        
        // Handle simple tags from templates which are related to
        // the c++local generator
        if (currentNode instanceof MAttributeDef) {
            return data_MAttributeDef(variable, value);
        }
        else if (currentNode instanceof MFieldDef) {
            return data_MFieldDef(variable, value);
        }
        return value;
    }
            
    /***
     * Generate the #include<> statement for a MIDLType model element
     * with full scope.
     * e.g: #include <world/europe/austria/ccm/local/Person.h>
     */
    protected String getLanguageTypeInclude(MIDLType idlType) 
    {
        logger.fine("enter getLanguageTypeInclude()");
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MStringDef) {
            code.append("#include <string>\n");
        }
        else if(idlType instanceof MPrimitiveDef) {
            MPrimitiveDef primitive = (MPrimitiveDef)idlType;
            if(primitive.getKind() == MPrimitiveKind.PK_ANY) {
                code.append("#include <WX/Utils/value.h>\n");
            }
        }
        else if(idlType instanceof MSequenceDef
                || idlType instanceof MArrayDef) {
            MTyped singleType = (MTyped)idlType;
            MIDLType singleIdlType = singleType.getIdlType();
            code.append("#include <vector>\n");
            code.append(getLanguageTypeInclude(singleIdlType));
        }
        else if(idlType instanceof MContained) {
            code.append(getScopedInclude((MContained) idlType));
        }
        logger.fine("leave getLanguageTypeInclude()");
        return code.toString();
    }
    
    
    protected String data_MAliasDef(String dataType, String dataValue) 
    {
        logger.fine("enter data_MAliasDef()");
        MAliasDef alias = (MAliasDef)currentNode;
        MTyped type = (MTyped) alias;
        MIDLType idlType = type.getIdlType();
        
        if(dataType.equals("TypedefDefinition")) {
            if(isTypedefAny(alias)) {
                // handle typedef -> any
                dataValue = getTypedefAny(alias);
            }
            else {
                dataValue = getTypedef(alias);
            }
        }
        else if (idlType instanceof MSequenceDef) {
            dataValue = data_MSequenceDef(dataType, dataValue);
        }
        else if (idlType instanceof MArrayDef) {
            dataValue = data_MArrayDef(dataType, dataValue);
        } 
        else { // fallback to super class
            dataValue = super.data_MAliasDef(dataType,dataValue);
        }
        logger.fine("leave data_MAliasDef()");
        return dataValue;
    }
        
    protected boolean isTypedefAny(MAliasDef alias)
    {
        MTyped type = (MTyped) alias;
        MIDLType idlType = type.getIdlType();
        if(idlType instanceof MPrimitiveDef) {
            MPrimitiveDef primitive = (MPrimitiveDef)idlType;
            if(primitive.getKind() == MPrimitiveKind.PK_ANY) {
                return true;
            }
        }  
        return false;
    }
    
    protected String getTypedefAny(MAliasDef alias) 
    {
        StringBuffer code = new StringBuffer();
        MTypedefDef typedef = (MTypedefDef) alias;
        code.append("// typedef ").append(typedef.getIdentifier());
        code.append(" -> any\n");
        // TODO: replace known typedef
        code.append(getTypedef(alias));
        return code.toString();
    }
    
    protected String getTypedef(MAliasDef alias) 
    {
        StringBuffer code = new StringBuffer();
        MTyped type = (MTyped) alias;
        MTypedefDef typedef = (MTypedefDef) alias;
        code.append("typedef ");
        code.append(getLanguageType(type));
        code.append(" ");
        code.append(typedef.getIdentifier());
        code.append(";\n");
        return code.toString();
    }
    
    protected String data_MFieldDef(String dataType, String dataValue) 
    {
        logger.fine("enter data_MFieldDef()");
        if(dataType.equals("DebugNamespace")) {
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            return getDebugNamespace(idlType);
        }
        logger.fine("leave data_MFieldDef()");
        return dataValue;
    }
    
    protected String data_MSequenceDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MSequenceDef()");
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        if(dataType.equals("MAliasDefDebug")) {
            dataValue =  getDebugSequence(type);
        }
        logger.fine("leave data_MSequenceDef()");
        return dataValue;
    }
    
    protected String data_MArrayDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MArrayDef()");
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        
        if(dataType.equals("MAliasDefDebug")) {
            dataValue = getDebugArray(type);	 
        }
        logger.fine("leave data_MArrayDef()");
        return dataValue;
    }
    
    protected String data_MEnumDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MEnumDef()");
        if(dataType.equals("MembersDebug")) {
            MEnumDef enum = (MEnumDef) currentNode;
            dataValue = getDebugEnum(enum);
        }
        else {
            dataValue = super.data_MEnumDef(dataType, dataValue);
        }
        logger.fine("leave data_MEnumDef()");
        return dataValue;
    }
    
    protected String data_MAttributeDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MAttributeDef()");
        MAttributeDef attr = (MAttributeDef)currentNode;
        if(dataType.equals("InterfaceIdentifier")) {
            dataValue = attr.getDefinedIn().getIdentifier();
        }
        else {
            dataValue = super.data_MAttributeDef(dataType, dataValue);
        }
        logger.fine("leave data_MAttributeDef()");
        return dataValue;
    }
    
    protected String data_MOperationDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MOperationDef()");
        MOperationDef op = (MOperationDef)currentNode;
        if(dataType.equals("InterfaceIdentifier")) {
            dataValue = op.getDefinedIn().getIdentifier();
        }
        else if(dataType.equals("ExceptionThrows")) {
            dataValue = getOperationExcepts(op);
        }
        else if(dataType.equals("OperationToFacetDelegation")) {
            dataValue = getOperationDelegation(op,"facet_");
        }
        else if(dataType.equals("OperationResult")) {
            dataValue = getOperationResult(op);
        }
        else if(dataType.equals("DebugOperationInParameter")) {
            dataValue = getDebugOperationInParameter(op);
        }
        else if(dataType.equals("DebugOperationOutParameter")) {
            dataValue = getDebugOperationOutParameter(op);
        }
        else if(dataType.equals("DebugOperationResult")) {
            dataValue = getDebugOperationResult(op);
        }
        else {
            dataValue = super.data_MOperationDef(dataType, dataValue);
        }
        logger.fine("leave data_MOperationDef()");
        return dataValue;
    }
        
    
    protected String data_MInterfaceDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MInterfaceDef()");
        MInterfaceDef iface = (MInterfaceDef) currentNode;
        if(dataType.equals("InterfaceInclude")) {
            MContained contained = (MContained)currentNode;
            dataValue = getLocalCppName(contained, Text.FILE_SEPARATOR);
        }
        else if(dataType.equals("BaseInterfaceAdapterAttributeHeader")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = false;
            dataValue = getBaseInterfaceAttributes(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterAttributeImpl")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = true;
            dataValue = getBaseInterfaceAttributes(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterOperationHeader")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = false;
            dataValue = getBaseInterfaceOperations(isImpl,iface,baseInterfaceList);
        }
        else if(dataType.equals("BaseInterfaceAdapterOperationImpl")) {
            List baseInterfaceList = iface.getBases();
            boolean isImpl = true;
            dataValue = getBaseInterfaceOperations(isImpl,iface,baseInterfaceList);
        }
        else {
            dataValue = super.data_MInterfaceDef(dataType, dataValue);
        }
        logger.fine("leave data_MInterfaceDef()");
        return dataValue;
    }

    
    protected String getBaseInterfaceOperations(boolean isImpl, 
                                                MInterfaceDef iface,
                                                List baseInterfaceList)
    {
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) {
                MContained contained = (MContained)j.next();	
                if(contained instanceof MOperationDef) {
                    MOperationDef op = (MOperationDef)contained;	
                    if(isImpl) {
                        // generate code for C++ impl file
                        code.append(getAdapterOperationImpl(iface,op));
                    }
                    else { 
                        // generate code for C++ header file 
                        code.append(getAdapterOperationHeader(op));
                    }
                }
            }
            code.append("\n");
        }
        return code.toString();
    }
    
    protected String getAdapterOperationHeader(MOperationDef op) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("virtual").append("\n");
        code.append(Text.TAB).append(getLanguageType(op)).append("\n");
        code.append(Text.TAB).append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op));
        code.append(")\n");
        code.append(Text.tab(2)).append(getOperationExcepts(op)).append(";\n");
        return code.toString();
    }
    
    protected String getAdapterOperationImpl(MInterfaceDef iface, MOperationDef op)
    {
        StringBuffer code = new StringBuffer();
        code.append(getLanguageType(op)).append("\n");
        code.append(iface.getIdentifier());
        code.append("Adapter::").append(op.getIdentifier()).append("(");
        code.append(getOperationParams(op)).append(")\n");
        code.append(Text.TAB).append(getOperationExcepts(op)).append("\n");
        code.append("{\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL,\" ");
        code.append(iface.getIdentifier());
        code.append("Adapter::").append(op.getIdentifier()).append("()\");\n");
        code.append(getDebugOperationInParameter(op)).append("\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::ccm::local::Components::InvalidConnection();\n\n");
        code.append(getOperationDelegation(op,"facet_")).append("\n\n");
        code.append(getDebugOperationOutParameter(op)).append("\n");
        code.append(getDebugOperationResult(op)).append("\n");
        code.append(getOperationResult(op)).append("\n");
        code.append("}\n");
        return code.toString();
    }
    
    

    

    protected String getBaseInterfaceAttributes(boolean isImpl, 
                                                MInterfaceDef iface,
                                                List baseInterfaceList)
    {
        StringBuffer code = new StringBuffer();
        for(Iterator i = baseInterfaceList.iterator(); i.hasNext();) {
            MInterfaceDef baseIface = (MInterfaceDef) i.next();
            List contentList = baseIface.getContentss();
            for(Iterator j = contentList.iterator(); j.hasNext();) {
                MContained contained = (MContained)j.next();	
                if(contained instanceof MAttributeDef) {
                    MAttributeDef attr = (MAttributeDef)contained;
                    if(isImpl) {
                        // generate code for C++ impl file
                        code.append(getAdapterAttributeImpl(iface,attr));
                    }
                    else {
                        // generate code for C++ header file 
	                    code.append(getAdapterAttributeHeader(attr));
                    }
                }
            }
            code.append("\n");
        }
        return code.toString();
    }
    
    protected String getAdapterAttributeHeader(MAttributeDef attr) 
    {
        StringBuffer code = new StringBuffer();
        code.append(Text.TAB).append("virtual\n");
        code.append(Text.TAB).append("const ").append(getLanguageType(attr));
        code.append(" ").append(attr.getIdentifier()).append("() const\n");
        code.append(Text.tab(2)).append("throw(::ccm::local::Components::CCMException);\n\n");

        code.append(Text.TAB).append("virtual\n");
        code.append(Text.TAB).append("void ").append(attr.getIdentifier()).append("(const ");
        code.append(getLanguageType(attr)).append(" value)\n");
        code.append(Text.tab(2)).append("throw(::ccm::local::Components::CCMException);\n\n");
        return code.toString();
    }
    
    protected String getAdapterAttributeImpl(MInterfaceDef iface, MAttributeDef attr) 
    {
        StringBuffer code = new StringBuffer();
        code.append("const ").append(getLanguageType(attr)).append("\n");
        code.append(iface.getIdentifier()).append("Adapter::").append(attr.getIdentifier());
        code.append("() const\n");
        code.append(Text.TAB).append("throw(::ccm::local::Components::CCMException)\n");
        code.append("{\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \" ").append(iface.getIdentifier());
        code.append("Adapter::").append(attr.getIdentifier()).append("() = \"\n");
        code.append(Text.tab(2)).append("<< ::").append(getDebugNamespace(attr.getIdlType()));
        code.append("::ccmDebug(facet_->").append(attr.getIdentifier()).append("()));\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::ccm::local::Components::InvalidConnection();\n");
        code.append(Text.TAB).append("return facet_->").append(attr.getIdentifier());
        code.append("();\n");       
        code.append("}\n\n");
        
        code.append("void\n");
        code.append(iface.getIdentifier()).append("Adapter::").append(attr.getIdentifier());
        code.append("(const ").append(getLanguageType(attr)).append(" value)\n");
        code.append(Text.TAB).append("throw(::ccm::local::Components::CCMException)\n");
        code.append("{\n");
        code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \" ").append(iface.getIdentifier());
        code.append("Adapter::").append(attr.getIdentifier()).append("(\"\n");
        code.append(Text.tab(2)).append("<< ::").append(getDebugNamespace(attr.getIdlType()));
        code.append("::ccmDebug(value) << \")\");\n");
        code.append(Text.TAB).append("if(!validConnection())\n");
        code.append(Text.tab(2)).append("throw ::ccm::local::Components::InvalidConnection();\n");
        code.append(Text.TAB).append("facet_->").append(attr.getIdentifier());
        code.append("(value);\n");       
        code.append("}\n\n");
        return code.toString();
    }
    
    /**
     * Write generated code to an output file.
     * 
     * @param template
     *            the template object to get the generated code structure from ;
     *            variable values should come from the node handler object.
     */
    protected void writeOutput(Template template)
    {
        logger.fine("enter writeOutput()");
        List out_paths = getOutputFiles();
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");
        String implDirectory = CcmtoolsProperties.Instance().get("ccmtools.dir.impl");

        try {
            Iterator path_iterator = out_paths.iterator();
            for(int i = 0; i < out_strings.length; i++) {
                String generated_code = prettifyCode(out_strings[i]);

                // out_path = [directory, filename]
                List out_path = (List) path_iterator.next();

                // from the getOutputFiles function we know each entry in the
                // output file list has exactly two parts ... the dirname and
                // the filename.
                String file_dir = (String) out_path.get(0);
                String file_name = (String) out_path.get(1);

                // don't add blank output files. this lets us discard parts of
                // the templates that we don't want to output (see the component
                // section of the getOutputFiles function)
                if(file_name.equals(""))
                    continue;

                File outFile = new File(output_dir + File.separator + file_dir,
                                        file_name);
                if((file_dir == implDirectory) && outFile.isFile()) {
                    if(outFile.getName().endsWith("_entry.h")) {
                        // *_entry.h files must be overwritten by every generator
                        // call because they are part of the component logic
                        writeFinalizedFile(file_dir, file_name, generated_code);
                    }
                    else if(!isCodeEqualWithFile(generated_code, outFile)) {
                        uiDriver.printMessage("WARNING: " + outFile
                                + " already exists!");
                        file_name += ".new";
                        outFile = new File(output_dir + File.separator
                                + file_dir, file_name);
                    }
                }
                if(isCodeEqualWithFile(generated_code, outFile)) {
                    uiDriver.printMessage("Skipping " + outFile);
                }
                else {
                    writeFinalizedFile(file_dir, file_name, generated_code);
                }
                writeMakefile(output_dir, file_dir, "py", "");
            }
        }
        catch(Exception e) {
            uiDriver.printError("!!!Error " + e.getMessage());
        }
        logger.fine("leave writeOutput()");
    }

    protected boolean writeMakefile(File outDir, String fileDir,
                                    String extension, String content)
        throws IOException
    {
        logger.fine("enter writeMakefile()");
        boolean result;
        File makeFile = new File(outDir, fileDir);
        makeFile = new File(makeFile, "Makefile." + extension);

        if(!makeFile.isFile()) {
            writeFinalizedFile(fileDir, "Makefile." + extension, content);
            result = true;
        }
        else {
            result = false; // no Makefile.py written
        }
        logger.fine("leave writeMakefile()");
        return result;
    }

    
    /**
     * Create a list of lists of pathname components for output files needed by
     * the current node type.
     * 
     * @return a list of List objects containing file names for all output files
     *         to be generated for the current node.
     */
    protected List getOutputFiles()
    {
        logger.fine("enter getOutputFiles()");
        String node_name = ((MContained) currentNode).getIdentifier();
        List files = new ArrayList();
        List f = null;
        String implDirectory =
            CcmtoolsProperties.Instance().get("ccmtools.dir.impl");
        
        if((currentNode instanceof MComponentDef)
                || (currentNode instanceof MHomeDef)) {
            String base_name = node_name;

            // we put home files in the dir with the component files to convince
            // confix to work with us. beware the evil voodoo that results when
            // home and component files are in separate directories !

            if(currentNode instanceof MHomeDef) {
                base_name = ((MHomeDef) currentNode).getComponent()
                        .getIdentifier();
            }
            String base = getOutputDirectory(base_name);

            f = new ArrayList();
            f.add(base);
            f.add(node_name + "_gen.h");
            files.add(f);
            f = new ArrayList();
            f.add(base);
            f.add(node_name + "_gen.cc");
            files.add(f);

            f = new ArrayList();
            f.add(base + "_share");
            f.add(node_name + "_share.h");
            files.add(f);

            if(currentNode instanceof MHomeDef) {
                f = new ArrayList();
                f.add(implDirectory);
                f.add(getLocalCppName((MContained)currentNode, 
                                      Text.MANGLING_SEPARATOR)
                	+ "_entry.h");
                files.add(f);
            }

            if((flags & FLAG_APPLICATION_FILES) != 0) {
                f = new ArrayList();
                f.add(implDirectory);
                f.add(node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add(node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
            }
        }
        else if((currentNode instanceof MStructDef)
                || (currentNode instanceof MUnionDef)
                || (currentNode instanceof MAliasDef)
                || (currentNode instanceof MEnumDef)
                || (currentNode instanceof MExceptionDef)) {
            f = new ArrayList();
            f.add(getOutputDirectory(""));
            f.add(node_name + ".h");
            files.add(f);
        }
        else if ((currentNode instanceof MInterfaceDef)) {
            // Interface part
            f = new ArrayList();
            f.add(getOutputDirectory(""));
            f.add(node_name + ".h");
            files.add(f);
            // Adapter part (header file)
            f = new ArrayList();
            f.add(getOutputDirectory("") + "_adapter");
            f.add(node_name + "Adapter.h");
            files.add(f);
            // Adapter part (impl file)
            f = new ArrayList();
            f.add(getOutputDirectory("") + "_adapter");
            f.add(node_name + "Adapter.cc");
            files.add(f);
        }
        else if((currentNode instanceof MProvidesDef)) {
            if((flags & FLAG_APPLICATION_FILES) != 0) {
                MComponentDef component = ((MProvidesDef) currentNode)
                        .getComponent();
                f = new ArrayList();
                f.add(implDirectory);
                f.add(component.getIdentifier() + "_" + node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add(component.getIdentifier() + "_" + node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add(implDirectory);
                f.add("");
                files.add(f);
            }
        }
        else {
            f = new ArrayList();
            f.add("");
            f.add("");
            files.add(f);
        }
        logger.fine("leave getOutputFiles()");
        return files;
    }

    /***
     * Calculate the directory name for output files.
     * 
     * @param component name of a component which will be added to the 
     * 		  directory name.
     * @return A mangled name containing the namespace of a component
     *         logic artifact.
     */
    protected String getOutputDirectory(String component)
    {
        logger.fine("enter getOutputDirectory()");
        List modules = new ArrayList(namespaceStack);
        modules.addAll(baseNamespace);
        if(!component.equals("")) {
            modules.add(Constants.COMPONENT_NAMESPACE 
                        + Text.MANGLING_SEPARATOR + component);
        }
        String generatorPrefix = 
            CcmtoolsProperties.Instance().get("ccmtools.dir.gen");
        logger.fine("leave getOutputDirectory()");
        return generatorPrefix + join("_", modules);
    }

    
    
    
    //====================================================================
    // Simple %(tag)s helper methods
    //====================================================================


    
    
    //====================================================================
    // MOperationDef %(tag)s helper methods
    //====================================================================
       
    public String getOperationDelegation(MOperationDef op, String target)
    {
        logger.finer("enter getOperationDelegation()");
        StringBuffer code = new StringBuffer();
        String langType = getLanguageType(op);
        code.append(Text.TAB);
        if(!langType.equals("void")) {
            code.append(langType);
            code.append(" result = ");
        }
        code.append(target).append("->");
        code.append(op.getIdentifier()).append("(");
        List parameterList = new ArrayList();
        for(Iterator params = op.getParameters().iterator();params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            parameterList.add(p.getIdentifier());
        }
        code.append(Text.join(",", parameterList)).append(");");
        logger.finer("leave getOperationDelegation()");
        return code.toString();
    }    
    
    public String getOperationResult(MOperationDef op)
    {
        logger.finer("enter getOperationResult()");
        StringBuffer code = new StringBuffer();
        String langType = getLanguageType(op);
        if(!langType.equals("void")) {
            code.append(Text.TAB).append("return result;");
        }
        logger.finer("leave getOperationResult()");
        return code.toString();
    }
    
    public String getOperationReturn(MOperationDef op)
    {
        logger.finer("enter getOperationReturn()");
        String code;
        String langType = getLanguageType(op);
        if(!langType.equals("void")) {
            code = "return ";
        }
        else {	
            code = "";
        }
        logger.finer("leave getOperationReturn()");
        return code;
    }
    
    
    
    
    //====================================================================
    // Debug %(tag)s helper methods
    //====================================================================
    
    public String getDebugInclude()
    {
        logger.finer("enter getDebugInclude()");
        StringBuffer code = new StringBuffer();
        code.append("#ifdef WXDEBUG\n");
        code.append("#  include <ccm").append(Text.FILE_SEPARATOR);
        code.append("local").append(Text.FILE_SEPARATOR);
        code.append("Debug.h>\n");
        code.append("#endif // WXDEBUG\n");
        logger.finer("leave getDebugInclude()");
        return code.toString();
    }
    
    public String getDebugSequence(MTyped type)
    {
        logger.finer("enter getDebugSequence()");
        StringBuffer code = new StringBuffer(); 	
        MContained contained = (MContained) type;
        MIDLType idlType = type.getIdlType();
        MTyped singleType = (MTyped) idlType;
        MIDLType singleIdlType = singleType.getIdlType();
        String sequenceName = getLocalCppName(contained,Text.SCOPE_SEPARATOR);
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n"); 
        code.append("ccmDebug(const ").append(sequenceName);
        code.append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append(Text.TAB).append("std::ostringstream os;\n");
        code.append(Text.TAB).append("os << ::ccm::local::doIndent(indent) << \"sequence ");
        code.append(sequenceName).append("\" << endl;\n");
        code.append(Text.TAB);
        code.append("os << ::ccm::local::doIndent(indent) << \"[\" << std::endl;\n");
        code.append(Text.TAB);
        code.append("for(unsigned int i=0; i<in.size(); i++) {\n");
        code.append(Text.tab(2)).append("os << ");
        code.append("::").append(getDebugNamespace(singleIdlType));
        code.append("::ccmDebug(in[i], indent+1) << std::endl;\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("os << ::ccm::local::doIndent(indent) << \"]\";\n");
        code.append(Text.TAB).append("return os.str();\n");
        code.append("}\n");
        code.append("#endif\n");
        logger.finer("leave getDebugSequence()");
        return code.toString();
    }
       
    public String getDebugArray(MTyped type)
    {
        logger.finer("enter getDebugArray()");
        StringBuffer code = new StringBuffer();
        MContained contained = (MContained) type;
        MIDLType idlType = type.getIdlType();
        MTyped singleType = (MTyped) idlType;
        MIDLType singleIdlType = singleType.getIdlType();
        String sequenceName = getLocalCppName(contained,Text.SCOPE_SEPARATOR);
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n"); 
        code.append("ccmDebug(const ").append(sequenceName);
        code.append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append(Text.TAB).append("std::ostringstream os;\n");
        code.append(Text.TAB).append("os << ::ccm::local::doIndent(indent) << \"array ");
        code.append(sequenceName).append("\" << endl;\n");
        code.append(Text.TAB);
        code.append("os << ::ccm::local::doIndent(indent) <<  \"[\" << std::endl;\n");
        code.append(Text.TAB);
        code.append("for(unsigned int i=0; i<in.size(); i++) {\n");
        code.append(Text.tab(2)).append("os << ");
        code.append("::").append(getDebugNamespace(singleIdlType));            
        code.append("::ccmDebug(in[i], indent+1) << std::endl;\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("os << ::ccm::local::doIndent(indent) << \"]\";\n");
        code.append(Text.TAB).append("return os.str();\n");
        code.append("}\n");
        code.append("#endif\n");
        logger.finer("leave getDebugArray()");
        return code.toString();
    }
           
    public String getDebugEnum(MEnumDef enum)
    {
        logger.finer("enter getDebugEnum()");
        StringBuffer code = new StringBuffer();
        for(Iterator i = enum.getMembers().iterator(); i.hasNext();) {
            String member = (String)i.next(); 
            code.append(Text.TAB).append("case ").append(member);
            code.append(":\n");
            code.append(Text.tab(2)).append("os << \"").append(member);
            code.append("\";\n");
            code.append(Text.tab(2)).append("break;\n");
        }
        logger.finer("leave getDebugEnum()");
        return code.toString();        
    }
           
    public String getDebugOperationInParameter(MOperationDef op)
    {
        logger.finer("enter getDebugOperationInParameter()");
        StringBuffer code = new StringBuffer();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();
            MParameterMode direction = p.getDirection();
            if(direction == MParameterMode.PARAM_IN) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"IN ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append("::").append(getDebugNamespace(idlType));
                code.append("::ccmDebug(").append(p.getIdentifier()).append(")");
                code.append(");");
                code.append(Text.NL);
            }
            else if(direction == MParameterMode.PARAM_INOUT) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"INOUT ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append("::").append(getDebugNamespace(idlType));
                code.append("::ccmDebug(").append(p.getIdentifier()).append(")");
                code.append(");");
                code.append(Text.NL);
            }
        }
        logger.finer("leave getDebugOperationInParameter()");
        return code.toString();
    }
   
    public String getDebugOperationOutParameter(MOperationDef op)
    {
        logger.finer("enter getDebugOperationOutParameter()");
        StringBuffer code = new StringBuffer();
        for(Iterator params = op.getParameters().iterator();params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();
            MParameterMode direction = p.getDirection();
            if(direction == MParameterMode.PARAM_OUT) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"OUT ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append("::").append(getDebugNamespace(idlType));
                code.append("::ccmDebug(").append(p.getIdentifier());
                code.append("));");
            }
            else if(direction == MParameterMode.PARAM_INOUT) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"INOUT' ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append("::").append(getDebugNamespace(idlType));
                code.append("::ccmDebug(").append(p.getIdentifier()).append(")");
                code.append(");");
                code.append(Text.NL);
            }
        }
        logger.finer("leave getDebugOperationOutParameter()");
        return code.toString();
    }
        
    public String getDebugOperationResult(MOperationDef op)
    {
        logger.finer("enter getDebugOperationResult()");
        StringBuffer code = new StringBuffer();
        MIDLType idlType = op.getIdlType();
        String langType = getLanguageType(op);
        if(!langType.equals("void")) {
            code.append(Text.TAB);
            code.append("LDEBUGNL(CCM_LOCAL, \"result = \" << ");
            code.append("::").append(getDebugNamespace(idlType));
            code.append("::ccmDebug(").append("result");
            code.append(")").append(");");
        }
        logger.finer("leave getDebugOperationResult()");
        return code.toString();
    }
}