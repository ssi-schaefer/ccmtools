/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
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
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class CppLocalGeneratorImpl
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    {
        "MComponentDef", "MInterfaceDef", "MHomeDef"
    };

    // output locations and templates for "environment files", the files that we
    // need to output once per project. the length of this list needs to be the
    // same as the length of the following list ; this list provides the file
    // names, and the next one provides the templates to use for each file.

    private final static File[] local_environment_files =
    {
        new File("localComponents", "CCM.h"),
        new File("localComponents", "dummy.cc"),
        new File("localComponents", "Makefile.py"),

        new File("localTransaction", "UserTransaction.h"),
        new File("localTransaction", "dummy.cc"),
        new File("localTransaction", "Makefile.py"),

        new File("CCM_HomeFinder", "HomeFinder.h"),
        new File("CCM_HomeFinder", "HomeFinder.cc"),
        new File("CCM_HomeFinder", "Makefile.py"),

        new File("CCM_Utils", "SmartPointer.h"),
        new File("CCM_Utils", "SmartPointer.cc"),
        new File("CCM_Utils", "LinkAssert.h"),
        new File("CCM_Utils", "Debug.h"),
        new File("CCM_Utils", "Debug.cc"),
        new File("CCM_Utils", "DebugWriterManager.h"),
        new File("CCM_Utils", "DebugWriterManager.cc"),
        new File("CCM_Utils", "CerrDebugWriter.h"),
        new File("CCM_Utils", "CerrDebugWriter.cc"),
        new File("CCM_Utils", "DebugWriter.h"),
        new File("CCM_Utils", "Makefile.py"),
    };

    private final static String[] local_environment_templates =
    {
        "LocalComponentsHeader", "Blank", "Blank",
        "LocalTransactionHeader", "Blank", "Blank",
        "HomeFinderHeader", "HomeFinderImpl", "Blank",

        "SmartPointerHeader", "SmartPointerImpl",
        "LinkAssertHeader", "DebugHeader", "DebugImpl",
        "DebugWriterManagerHeader", "DebugWriterManagerImpl",
        "CerrDebugWriterHeader", "CerrDebugWriterImpl",
        "DebugWriterHeader", "Blank",
    };

    /**************************************************************************/

    public CppLocalGeneratorImpl(Driver d, File out_dir)
        throws IOException
    {
        super("CppLocal", d, out_dir, local_output_types,
              local_environment_files, local_environment_templates);
    }

    /**
     * Finalize the output files. This function just writes a minimal Confix
     * configuration file with the -D flags for the compiler.
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files)
    {
        Template template = template_manager.getRawTemplate("Confix");
        writeFinalizedFile("", "confix.conf",
                           template.substituteVariables(defines));
    }

    /**
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    protected void writeOutput(Template template)
        throws IOException
    {
        List out_paths = getOutputFiles();
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");

        Iterator path_iterator = out_paths.iterator();
        for (int i = 0; i < out_strings.length; i++) {
            String generated_code = out_strings[i];
            List out_path = (ArrayList) path_iterator.next();

            // from the getOutputFiles function we know each entry in the output
            // file list has exactly two parts ... the dirname and the filename.

            String file_dir = (String) out_path.get(0);
            String file_name = (String) out_path.get(1);

            // don't add blank output files. this lets us discard parts of the
            // templates that we don't want to output (see the component section
            // of the getOutputFiles function)

            if (file_name.equals("")) continue;

            writeFinalizedFile(file_dir, file_name, generated_code);

            // output a confix Makefile.py file if it's not in this directory.

            File confix_file = new File(output_dir, file_dir);
            confix_file = new File(confix_file, "Makefile.py");
            if (! confix_file.isFile())
                writeFinalizedFile(file_dir, "Makefile.py", "");
        }
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
        String lang_type = getLanguageType(operation);
        Map vars = new Hashtable();

        vars.put("Object",            container.getIdentifier());
        vars.put("Identifier",        operation.getIdentifier());
        vars.put("ProvidesType",      iface.getIdentifier());
        vars.put("SupportsType",      iface.getIdentifier());
        vars.put("LanguageType",      lang_type);
        vars.put("MExceptionDef",     getOperationExcepts(operation));
        vars.put("MParameterDefAll",  getOperationParams(operation, "all"));
        vars.put("MParameterDefName", getOperationParams(operation, "name"));

        if (! lang_type.equals("void")) vars.put("Return", "return ");
        else                            vars.put("Return", "");

        return vars;
    }

    /**************************************************************************/

    /**
     * Create a list of lists of pathname components for output files needed by
     * the current node type.
     *
     * @return a list of List objects containing file names for all output files
     *         to be generated for the current node.
     */
    private List getOutputFiles()
    {
        String node_name = ((MContained) current_node).getIdentifier();

        List files = new ArrayList();
        List f = null;

        if ((current_node instanceof MComponentDef) ||
            (current_node instanceof MHomeDef)) {
            String base_name = node_name;

            // we put home files in the dir with the component files to convince
            // confix to work with us. beware the evil voodoo that results when
            // home and component files are in separate directories !

            if (current_node instanceof MHomeDef)
                base_name =
                    ((MHomeDef) current_node).getComponent().getIdentifier();

            String base = handleNamespace("FileNamespace", base_name);

            f = new ArrayList();
            f.add(base); f.add(node_name + "_gen.h"); files.add(f);
            f = new ArrayList();
            f.add(base); f.add(node_name + "_gen.cc"); files.add(f);

            if ((flags & FLAG_APPLICATION_FILES) != 0) {
                f = new ArrayList();
                f.add(base); f.add(node_name + "_app.h"); files.add(f);
                f = new ArrayList();
                f.add(base); f.add(node_name + "_app.cc"); files.add(f);
            } else {
                f = new ArrayList(); f.add(base); f.add(""); files.add(f);
                f = new ArrayList(); f.add(base); f.add(""); files.add(f);
            }
        } else if (current_node instanceof MInterfaceDef) {
            f = new ArrayList();
            f.add("CCM_Local"); f.add(node_name + ".h"); files.add(f);
            f = new ArrayList();
            f.add("CCM_Local"); f.add(node_name + "_types.h"); files.add(f);
        } else if (current_node instanceof MContainer) {
            f = new ArrayList(); f.add("CCM_Local");
            if ((flags & FLAG_USER_TYPES_FILES) == 0) f.add("");
            else f.add(node_name + "_user_types.h");
            files.add(f);
        } else {
            throw new RuntimeException("Invalid output node "+node_name);
        }
        return files;
    }
}

