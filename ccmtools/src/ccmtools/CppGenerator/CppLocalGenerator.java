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
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
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
import ccmtools.Metamodel.BaseIDL.MSequenceDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MTyped;
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
    private final static String[] LOCAL_OUTPUT_TEMPLATE_TYPES = {
            "MComponentDef", "MInterfaceDef", "MHomeDef", "MStructDef",
            "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef",
            "MProvidesDef"
    };

    
    /** 
     * 
     * @param d
     * @param out_dir
     * @throws IOException
     */
    public CppLocalGenerator(Driver d, File out_dir) throws IOException
    {
        super("CppLocal", d, out_dir, LOCAL_OUTPUT_TEMPLATE_TYPES);
        
        logger = Logger.getLogger("ccm.generator.cpp.local");
        logger.fine("enter CppLocalGenerator()");
        
        baseNamespace.add("CCM_Local");
        
        logger.fine("leave CppLocalGenerator()");
    }

    
    // FIXME ---------------------------------
    // This hack is only temporarily to compile generated structures via PMM
    protected String getScopedInclude(MContained contained)
    {
        List scope = getScope(contained);
        Collections.reverse(baseNamespace);
        for(Iterator i = baseNamespace.iterator(); i.hasNext();) {
            scope.add(0, i.next());
        }
        Collections.reverse(baseNamespace);
        scope.add(contained.getIdentifier());
        String code = getPmmHack(scope, contained);
        System.out.println("-->" + code + "<--");	
        return code;
    }
    // FIXME ---------------------------------

    //====================================================================
    // Code generator core methods
    //====================================================================
    
    
    public String getScopedNamespace(MContained contained,
                                     String separator, String local)
    {
        StringBuffer buffer = new StringBuffer();
        List scope = getScope(contained);

        if(local.length() > 0) {
            scope.add("CCM_Session_" + local);
        }
        buffer.append(Text.join(separator, baseNamespace));
        buffer.append(separator);
        if(scope.size() > 0) {
            buffer.append(Text.join(separator, scope));
            buffer.append(separator);
        }
        return buffer.toString();
    }

    public String getScopedName(MContained contained, 
                                String separator, String local)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getScopedNamespace(contained, separator, local));
        buffer.append(contained.getIdentifier());
        return buffer.toString();
    }    
    
    
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
        StringBuffer code = new StringBuffer();
        if(idlType instanceof MAliasDef) {
            MTyped type = (MTyped) idlType;
            MIDLType innerIdlType = type.getIdlType();
            if(innerIdlType instanceof MPrimitiveDef 
                    || innerIdlType instanceof MStringDef
                    || innerIdlType instanceof MWstringDef) {
                code.append(Text.join(Text.SCOPE_SEPARATOR, baseNamespace)); 
                code.append(Text.SCOPE_SEPARATOR);
            }
            else {
                code.append(getScopedNamespace((MContained)idlType, 
                                               Text.SCOPE_SEPARATOR,""));
            }
        }
        else if(idlType instanceof MContained) {
            code.append(getScopedNamespace((MContained)idlType, 
                                           Text.SCOPE_SEPARATOR,""));
    	}
        else {
            code.append(Text.join(Text.SCOPE_SEPARATOR, baseNamespace)); 
            code.append(Text.SCOPE_SEPARATOR);
        }
        return code.toString();
    }
    
    // TODO: Handle scope in terms of getScopedNamespace() 
//    protected String handleNamespace(String dataType, String local)
//    {
//        List tmp = new ArrayList();
//        List names = new ArrayList(namespaceStack);
//
//        if(!local.equals(""))
//            names.add("CCM_Session_" + local);
//
//        if(dataType.equals("Namespace")) {
//            return Text.join(Text.SCOPE_SEPARATOR, names);
//        }
//        else if(dataType.equals("IncludeNamespace")) {
//            return Text.join(Text.FILE_SEPARATOR, names);
//        }
//        else if(dataType.equals("UsingNamespace")) {
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("using namespace " + i.next() + ";\n");
//            return Text.join("", tmp);
//        }
//        else if(dataType.equals("OpenNamespace")) {
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("namespace " + i.next() + " {\n");
//            return Text.join("", tmp);
//        }
//        else if(dataType.equals("CloseNamespace")) {
//            Collections.reverse(names);
//            for(Iterator i = names.iterator(); i.hasNext();)
//                tmp.add("} // /namespace " + i.next() + "\n");
//            return Text.join("", tmp);
//        }
//        
//        else {
//            return "";
//        }
//    }    
    
    
    
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
        vars.put("MExceptionDefThrows", getOperationExcepts(operation));
        vars.put("MParameterDefAll", getOperationParams(operation));
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

        // Handle common utility tags from CppLocalTemplates
//        StringBuffer buffer = new StringBuffer();
//        if (variable.equals("CcmToolsVersion")) {
//            return localHelper.getCcmToolsVersion();
//            buffer.append("CCM Tools version ");
//            buffer.append(ccmtools.Constants.VERSION);
//            return buffer.toString();
//        }
//        if(variable.equals("CcmGeneratorTimeStamp")) {
//            return localHelper.getCcmGeneratorTimeStamp();
//            Calendar now = Calendar.getInstance();
//            buffer.append(now.getTime());
//            return buffer.toString();
//        }
//        else if(variable.equals("CcmGeneratorUser")) {
//            return localHelper.getCcmGeneratorUser();
//            buffer.append(System.getProperty("user.name"));
//            return buffer.toString();
//        }
        if(variable.equals("DebugInclude")) {
            return getDebugInclude();
        }
        
        // Handle simple tags from templates which are related to
        // the c++local generator
        if (currentNode instanceof MAliasDef) {
            // determine the contained type of MaliasDef
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            if (idlType instanceof MSequenceDef) {
                return data_MSequenceDef(variable,value);
            }
            else if (idlType instanceof MArrayDef) {
                return data_MArrayDef(variable, value);
            }
        }    
        else if (currentNode instanceof MFieldDef) {
            return data_MFieldDef(variable, value);
        }
        return value;
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
        //MSequenceDef sequence = (MSequenceDef) currentNode;
        MTyped type = (MTyped) currentNode;
//        MTyped type = (MTyped) currentNode;
//        MContained contained = (MContained) type;
//        MIDLType idlType = type.getIdlType();
//        MTyped singleType = (MTyped) idlType;
//        MIDLType singleIdlType = singleType.getIdlType();
        if(dataType.equals("MAliasDefDebug")) {
//            String sequenceName = getLocalName(contained,"::");
            //return getDebugSequence(contained, singleIdlType);
            dataValue =  getDebugSequence(type);
        }
        // if no CppLocalTemplates specific tag is processed
        logger.fine("leave data_MSequenceDef()");
        return dataValue;
    }
    
    protected String data_MArrayDef(String dataType, String dataValue)
    {
        logger.fine("enter data_MArrayDef()");
        MTyped type = (MTyped) currentNode;
//        MTyped type = (MTyped) currentNode;
//        MContained contained = (MContained) type;
//        MIDLType idlType = type.getIdlType();
//        MTyped singleType = (MTyped) idlType;
//        MIDLType singleIdlType = singleType.getIdlType();
        
        if(dataType.equals("MAliasDefDebug")) {
            //String sequenceName = getLocalName(contained,"::");
//            return getDebugSequence(contained, singleIdlType);
            dataValue = getDebugArray(type);	 
        }
        // if no CppLocalTemplates specific tag is processed
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

        try {
            Iterator path_iterator = out_paths.iterator();
            for(int i = 0; i < out_strings.length; i++) {

                String generated_code = prettifyCode(out_strings[i]);

                // out_path = [directory, filename]
                List out_path = (List) path_iterator.next();

                // from the getOutputFiles function we know each entry in the
                // output file list has exactly two parts ... the dirname and
                // the
                // filename.
                String file_dir = (String) out_path.get(0);
                String file_name = (String) out_path.get(1);

                // don't add blank output files. this lets us discard parts of
                // the templates that we don't want to output (see the component
                // section of the getOutputFiles function)
                if(file_name.equals(""))
                    continue;

                File outFile = new File(output_dir + File.separator + file_dir,
                                        file_name);
                if((file_dir == "impl") && outFile.isFile()) {
                    if(outFile.getName().endsWith("_entry.h")) {
                        // *_entry.h files must be overwritten by every generator
                        // call because they are part of the component logic
                        writeFinalizedFile(file_dir, file_name, generated_code);
                    }
                    else if(!isCodeEqualWithFile(generated_code, outFile)) {
                        System.out.println("WARNING: " + outFile
                                + " already exists!");
                        file_name += ".new";
                        outFile = new File(output_dir + File.separator
                                + file_dir, file_name);
                    }
                }
                if(isCodeEqualWithFile(generated_code, outFile)) {
                    System.out.println("Skipping " + outFile);
                }
                else {
                    writeFinalizedFile(file_dir, file_name, generated_code);
                }

                writeMakefile(output_dir, file_dir, "py", "");

                // FIXME ---------------------------------
                // This hack is only temporarily to compile generated structures
                // via PMM
                if(currentNode instanceof MComponentDef
                        || currentNode instanceof MHomeDef
                        || currentNode instanceof MProvidesDef) {
                    // Makefile.pl is not needed by components, homes and facets
                }
                else {
                    writeMakefile(output_dir, file_dir, "pl", "1;");
                }
                // FIXME --------------------------------
            }
        }
        catch(Exception e) {
            System.out.println("!!!Error " + e.getMessage());
        }
        logger.fine("leave writeOutput()");
    }

    protected boolean writeMakefile(File outDir, String fileDir,
                                    String extension, String content)
        throws IOException
    {
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
        return result;
    }

    
    private String getOutputDirectory(String local)
    {
        List names = new ArrayList(namespaceStack);
        if(!local.equals("")) {
            names.add("CCM_Session_" + local);
        }
        return join("_", names);
    }

    /**
     * Create a list of lists of pathname components for output files needed by
     * the current node type.
     * 
     * @return a list of List objects containing file names for all output files
     *         to be generated for the current node.
     */
    private List getOutputFiles()
    {
        String node_name = ((MContained) currentNode).getIdentifier();

        List files = new ArrayList();
        List f = null;

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
                f.add("impl");
                f.add(node_name + "_entry.h");
                files.add(f);
            }

            if((flags & FLAG_APPLICATION_FILES) != 0) {
                f = new ArrayList();
                f.add("impl");
                f.add(node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add("impl");
                f.add(node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add("impl");
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add("impl");
                f.add("");
                files.add(f);
            }
        }
        else if((currentNode instanceof MInterfaceDef)
                || (currentNode instanceof MStructDef)
                || (currentNode instanceof MUnionDef)
                || (currentNode instanceof MAliasDef)
                || (currentNode instanceof MEnumDef)
                || (currentNode instanceof MExceptionDef)) {
            f = new ArrayList();
            f.add(getOutputDirectory(""));
            f.add(node_name + ".h");
            files.add(f);
        }
        else if((currentNode instanceof MProvidesDef)) {
            if((flags & FLAG_APPLICATION_FILES) != 0) {
                MComponentDef component = ((MProvidesDef) currentNode)
                        .getComponent();
                f = new ArrayList();
                f.add("impl");
                f.add(component.getIdentifier() + "_" + node_name + "_impl.h");
                files.add(f);
                f = new ArrayList();
                f.add("impl");
                f.add(component.getIdentifier() + "_" + node_name + "_impl.cc");
                files.add(f);
            }
            else {
                f = new ArrayList();
                f.add("impl");
                f.add("");
                files.add(f);
                f = new ArrayList();
                f.add("impl");
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
        return files;
    }

    

    
    //====================================================================
    // Simple %(tag)s helper methods
    //====================================================================

    public String getPmmHack(List scope, MContained contained)
    {
        logger.finer("enter getPmmHack()");
        StringBuffer code = new StringBuffer();
        System.out.println(">>>>>>>>>>><<" + scope);
        
        code.append("#ifdef HAVE_CONFIG_H\n");
        code.append("#  include <config.h>\n");
        code.append("#endif\n");
 
        code.append("#ifdef USING_CONFIX \n");
        code.append("#include <");
        code.append(Text.join(SourceConstants.fileSeparator, scope));
        code.append(".h> \n");
        code.append("#else \n");
        code.append("#include <");
        code.append(contained.getIdentifier());
        code.append(".h> \n");
        code.append("#endif\n\n");
        logger.finer("leave getPmmHack()");
        return code.toString();
    }
    
    
    
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
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
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
        code.append("#include <CCM_Local").append(Text.FILE_SEPARATOR);
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
        String sequenceName = getLocalName(contained,Text.SCOPE_SEPARATOR);
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n"); 
        code.append("ccmDebug(const ").append(sequenceName);
        code.append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append(Text.TAB).append("std::ostringstream os;\n");
        code.append(Text.TAB).append("os << doIndent(indent) << \"sequence ");
        code.append(sequenceName).append("\" << endl;\n");
        code.append(Text.TAB);
        code.append("os << doIndent(indent) << \"[\" << std::endl;\n");
        code.append(Text.TAB);
        code.append("for(unsigned int i=0; i<in.size(); i++) {\n");
        code.append(Text.tab(2)).append("os << ");
        code.append(getDebugNamespace(singleIdlType));
        code.append("ccmDebug(in[i], indent+1) << std::endl;\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("os << doIndent(indent) << \"]\";\n");
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
        String sequenceName = getLocalName(contained,Text.SCOPE_SEPARATOR);
        code.append("#ifdef WXDEBUG\n");
        code.append("inline\n");
        code.append("std::string\n"); 
        code.append("ccmDebug(const ").append(sequenceName);
        code.append("& in, int indent = 0)\n");
        code.append("{\n");
        code.append(Text.TAB).append("std::ostringstream os;\n");
        code.append(Text.TAB).append("os << doIndent(indent) << \"array ");
        code.append(sequenceName).append("\" << endl;\n");
        code.append(Text.TAB);
        code.append("os << doIndent(indent) <<  \"[\" << std::endl;\n");
        code.append(Text.TAB);
        code.append("for(unsigned int i=0; i<in.size(); i++) {\n");
        code.append(Text.tab(2)).append("os << ");
        code.append(getDebugNamespace(singleIdlType));            
        code.append("ccmDebug(in[i], indent+1) << std::endl;\n");
        code.append(Text.TAB).append("}\n");
        code.append(Text.TAB).append("os << doIndent(indent) << \"]\";\n");
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
                code.append(getDebugNamespace(idlType));
                code.append("ccmDebug(").append(p.getIdentifier()).append(")");
                code.append(");");
                code.append(Text.NL);
            }
            else if(direction == MParameterMode.PARAM_INOUT) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"INOUT ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append(getDebugNamespace(idlType));
                code.append("ccmDebug(").append(p.getIdentifier()).append(")");
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
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            MIDLType idlType = ((MTyped) p).getIdlType();
            MParameterMode direction = p.getDirection();
            if(direction == MParameterMode.PARAM_OUT) {
                code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"OUT ");
                code.append(p.getIdentifier()).append(" = \" << ");
                code.append(getDebugNamespace(idlType));
                code.append("ccmDebug(").append(p.getIdentifier()).append("));");
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
            code.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"result = \" << ");
            code.append(getDebugNamespace(idlType));
            code.append("ccmDebug(").append("result").append(")").append(");");
        }
        logger.finer("leave getDebugOperationResult()");
        return code.toString();
    }
}