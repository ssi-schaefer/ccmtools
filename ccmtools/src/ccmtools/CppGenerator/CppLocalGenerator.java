/*
 * CCM Tools : C++ Code Generator Library 
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@salomon.at> Copyright (C) 2002, 2003 Salomon
 * Automation
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

import ccmtools.CodeGenerator.Template;
import ccmtools.CppGenerator.utils.Scope;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MArrayDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MFieldDef;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
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

public class CppLocalGenerator extends CppGenerator
{

    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types = {
            "MComponentDef", "MInterfaceDef", "MHomeDef", "MStructDef",
            "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef",
            "MProvidesDef"
    };

    /** *********************************************************************** */

    public CppLocalGenerator(Driver d, File out_dir) throws IOException
    {
        super("CppLocal", d, out_dir, local_output_types);
        baseNamespace.add("CCM_Local");
    }

    // ------------------------------------------------------------------------
    // Code generator core functionality extensions (compared to CppGenerator)
    // ------------------------------------------------------------------------
    
    // FIXME ---------------------------------
    // This hack is only temporarily to compile generated structures via PMM
    protected String getScopedInclude(MContained node)
    {
        List scope = getScope(node);
        StringBuffer buffer = new StringBuffer();
        Collections.reverse(baseNamespace);
        for(Iterator i = baseNamespace.iterator(); i.hasNext();) {
            scope.add(0, i.next());
        }
        Collections.reverse(baseNamespace);
        scope.add(node.getIdentifier());
        buffer.append("#ifdef HAVE_CONFIG_H\n");
        buffer.append("#  include <config.h>\n");
        buffer.append("#endif\n\n");
        buffer.append("#ifdef USING_CONFIX \n");
        buffer.append("#include <");
        buffer.append(join(file_separator, scope));
        buffer.append(".h> \n");
        buffer.append("#else \n");
        buffer.append("#include <");
        buffer.append(node.getIdentifier());
        buffer.append(".h> \n");
        buffer.append("#endif \n");
        return buffer.toString();
    }
    // FIXME ---------------------------------

    
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
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object", container.getIdentifier());
        vars.put("Identifier", operation.getIdentifier());
        vars.put("LanguageType", lang_type);
        vars.put("MExceptionDefThrows", getOperationExcepts(operation));
        vars.put("MParameterDefAll", getOperationParams(operation));
        vars.put("MParameterDefName", getOperationParamNames(operation));

        if(!lang_type.equals("void"))
            vars.put("Return", "return ");
        else
            vars.put("Return", "");

        return vars;
    }

    /**
     * Overrides the CppGenerator method to handle MAliasDef subtypes
     * TODO: Refactor - move this code up to the superclass
     */
    protected String getLocalValue(String variable)
    {
        // Get local value of CppGenerator 
        String value = super.getLocalValue(variable);

        // Handle common utility tags from CppLocalTemplates
        if (variable.equals("CcmToolsVersion")) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("CCM Tools version ").append(ccmtools.Constants.VERSION);
            return buffer.toString();
        }
        else if(variable.equals("DebugInclude")) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("#ifdef WXDEBUG").append(Text.NL);
            buffer.append("#include <CCM_Local/Debug.h>").append(Text.NL);
            buffer.append("#endif // WXDEBUG").append(Text.NL);
            return buffer.toString();
        }
        
        // Handle simple tags from templates which are related to
        // the c++local generator
        if (currentNode instanceof MAliasDef) {
            // determine the contained type of MaliasDef
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            if (idlType instanceof MPrimitiveDef || idlType instanceof MStringDef
                    || idlType instanceof MWstringDef) {
                return data_MPrimitiveDef(variable,value);
            }
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
        
    protected String data_MPrimitiveDef(String dataType, String dataValue)
    {
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        StringBuffer buffer = new StringBuffer();
        if(dataType.equals("MAliasDefDebug")) {
            buffer.append("// MAliasDefDebug - MPrimitiveDef");
            return buffer.toString();
        }
        // if no CppLocalTemplates specific tag is processed
        return dataValue;
    }
    
    protected String data_MFieldDef(String dataType, String dataValue) 
    {
        if(dataType.equals("DebugNamespace")) {
            MTyped type = (MTyped) currentNode;
            MIDLType idlType = type.getIdlType();
            return Scope.getDebugNamespace(baseNamespace,idlType);
        }
        return dataValue;
    }
    
    protected String data_MSequenceDef(String dataType, String dataValue)
    {
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        MContained contained = (MContained) type;
        MTyped singleType = (MTyped) idlType;
        MIDLType singleIdlType = singleType.getIdlType();
        StringBuffer buffer = new StringBuffer(); 
        if(dataType.equals("MAliasDefDebug")) {
            String sequenceName = getLocalName(contained,"::");
            buffer.append("#ifdef WXDEBUG").append(Text.NL);
            buffer.append("inline").append(Text.NL);
            buffer.append("std::string").append(Text.NL); 
            buffer.append("ccmDebug(const ").append(sequenceName).append("& in, int indent = 0)").append(Text.NL);
            buffer.append("{").append(Text.NL);
            buffer.append(Text.TAB).append("std::ostringstream os;").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) << \"sequence ")
            	.append(sequenceName).append("\" << endl;").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) << \"[\" << std::endl;").append(Text.NL);
            buffer.append(Text.TAB).append("for(unsigned int i=0; i<in.size(); i++) {")
            	.append(Text.NL);
            buffer.append(Text.tab(2)).append("os << ");
            buffer.append(Scope.getDebugNamespace(baseNamespace,singleIdlType));
            buffer.append("ccmDebug(in[i], indent+1) << std::endl;").append(Text.NL);
            buffer.append(Text.TAB).append("}").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) << \"]\";").append(Text.NL);
            buffer.append(Text.TAB).append("return os.str();").append(Text.NL);
            buffer.append("}").append(Text.NL);
            buffer.append("#endif").append(Text.NL);
            return buffer.toString();
        }
        // if no CppLocalTemplates specific tag is processed
        return dataValue;
    }
    
    protected String data_MArrayDef(String dataType, String dataValue)
    {
        MTyped type = (MTyped) currentNode;
        MIDLType idlType = type.getIdlType();
        MContained contained = (MContained) type;
        StringBuffer buffer = new StringBuffer();
        if(dataType.equals("MAliasDefDebug")) {
            String sequenceName = getLocalName(contained,"::");
            buffer.append("#ifdef WXDEBUG").append(Text.NL);
            buffer.append("inline").append(Text.NL);
            buffer.append("std::string").append(Text.NL); 
            buffer.append("ccmDebug(const ").append(sequenceName).append("& in, int indent = 0)").append(Text.NL);
            buffer.append("{").append(Text.NL);
            buffer.append(Text.TAB).append("std::ostringstream os;").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) << \"array ")
            	.append(sequenceName).append("\" << endl;").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) <<  \"[\" << std::endl;").append(Text.NL);
            buffer.append(Text.TAB).append("for(unsigned int i=0; i<in.size(); i++) {")
            	.append(Text.NL);
            buffer.append(Text.tab(2)).append("os << ccmDebug(in[i], indent+1) << std::endl;").append(Text.NL);
            buffer.append(Text.TAB).append("}").append(Text.NL);
            buffer.append(Text.TAB).append("os << doIndent(indent) << \"]\";").append(Text.NL);
            buffer.append(Text.TAB).append("return os.str();").append(Text.NL);
            buffer.append("}").append(Text.NL);
            buffer.append("#endif").append(Text.NL);
            return buffer.toString();
        }
        // if no CppLocalTemplates specific tag is processed
        return dataValue;
    }
    
    protected String data_MEnumDef(String dataType, String dataValue)
    {
        if(dataType.equals("MembersDebug")) {
            StringBuffer buffer = new StringBuffer();
            MEnumDef enum = (MEnumDef) currentNode;
            for(Iterator i = enum.getMembers().iterator(); i.hasNext();) {
                String member = (String)i.next(); 
                buffer.append(Text.TAB).append("case ").append(member).append(": ")
                	.append(Text.NL);
                buffer.append(Text.tab(2)).append("os << \"").append(member)
                	.append("\";").append(Text.NL);
                buffer.append(Text.tab(2)).append("break;").append(Text.NL);
            }
            return buffer.toString();
        }
        else {
            return super.data_MEnumDef(dataType, dataValue);
        }
    }
    
    
    
    // ------------------------------------------------------------------------
    // How to write generated code to files
    // ------------------------------------------------------------------------
   
    /**
     * Write generated code to an output file.
     * 
     * @param template
     *            the template object to get the generated code structure from ;
     *            variable values should come from the node handler object.
     */
    protected void writeOutput(Template template)
    {
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
}