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
import ccmtools.Metamodel.BaseIDL.MTyped;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;

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
     * Finalize the output files. This function's implementation writes a global
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
        // output a confix config file.

        String[][] global_files = { { "confix.conf", "Confix" } };

        for (int g = 0; g < global_files.length; g++) {
            Template tmpl = template_manager.getRawTemplate(global_files[g][1]);
            writeFinalizedFile("", global_files[g][0],
                               tmpl.substituteVariables(defines));
        }

        // write a global user_types.h file, but only if we've been told to do
        // so.

        if ((flags & FLAG_USER_TYPES_FILES) == 0) return;

        StringBuffer output = new StringBuffer("");
        output.append("\n#ifndef ___GLOBAL__USER__TYPES__H___\n");
        output.append("#define ___GLOBAL__USER__TYPES__H___\n\n");
        for (Iterator i = files.iterator(); i.hasNext(); ) {
            File file = new File((String) i.next());
            String name = file.getName().toString().split("\\.")[0];
            if (file.isFile())
                output.append("#include \""+name+"_user_types.h\"\n");
        }
        output.append("\nnamespace CCM_Local {\n\n");
        output.append("} // /namespace CCM_Local\n\n");
        output.append("#endif // ___GLOBAL__USER__TYPES__H___\n\n");

        writeFinalizedFile("CCM_Local", "user_types.h", output.toString());
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
        List out_paths = setupOutputFiles();
        String out_string = template.substituteVariables(output_variables);
        String[] out_strings = out_string.split("<<<<<<<SPLIT>>>>>>>");

        Iterator path_iterator = out_paths.iterator();
        for (int i = 0; (i < out_strings.length) && path_iterator.hasNext(); i++) {
            String generated_code = out_strings[i];
            List out_path = (ArrayList) path_iterator.next();

            String file_name = (String) out_path.get(out_path.size() - 1);
            List file_path = slice(out_path, -1);

            // don't add blank output files. this lets us discard parts of the
            // templates that we don't want to output (see the component section
            // of the setupOutputFiles function)

            if (file_name.equals("")) continue;

            buildOutputFilePath(file_path);
            File out_dir = new File("");
            for (Iterator p = file_path.iterator(); p.hasNext(); )
                out_dir = new File(out_dir, (String) p.next());
            writeFinalizedFile(out_dir.toString(), file_name, generated_code);
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

    // helper functions for setting up output files. this is crazy complicated.

    /**
     * Returns a list of lists of strings, where each sublist contains all
     * components from "base" and one component from "files". For example, the
     * result from parameters like { "A", "B" } and { "C", "D", "E" } would be a
     * list like :
     *
     *   { { "A", "B", "C" }, { "A", "B", "D" }, { "A", "B", "E" } }
     *
     * This resulting list is normally passed to the buildOutputFilePath
     * function.
     *
     * @param base an array of base (path) name components to add to each list
     *        in the return list.
     * @param files an array of filenames. One member of this array goes at the
     *        end of each list in the return list.
     * @return a list of List objects containing strings as described above.
     */
    private List addBaseFiles(String[] base, String[] files)
    {
        List ret = new ArrayList();
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                List element = new ArrayList();
                if (base != null)
                    for (int j = 0; j < base.length; j++)
                        element.add(base[j]);
                element.add(files[i]);
                ret.add(element);
            }
        return ret;
    }

    /**
     * Create a list of lists of pathname components for output files needed by
     * the current node type.
     *
     * @return a list of List objects containing file names for all output files
     *         to be generated for the current node.
     */
    private List setupOutputFiles()
    {
        String node_name = ((MContained) current_node).getIdentifier();

        if ((current_node instanceof MComponentDef) ||
            (current_node instanceof MHomeDef)) {
            String base_name = node_name;

            // we put home files in the dir with the component files to convince
            // confix to work with us. beware the evil voodoo that results when
            // home and component files are in separate directories !

            if (current_node instanceof MHomeDef)
                base_name = ((MHomeDef) current_node).getComponent().getIdentifier();

            String[] base  = { handleNamespace("FileNamespace", base_name) };
            String[] files = null;

            if ((flags & FLAG_APPLICATION_FILES) != 0) {
                String[] silly = { node_name + "_gen.h", node_name + "_gen.cc",
                                   node_name + "_app.h", node_name + "_app.cc" };
                files = silly;
            } else {
                String[] silly = { node_name + "_gen.h", node_name + "_gen.cc",
                                   "", "" /* intentionally blank */ };
                files = silly;
            }

            return addBaseFiles(base, files);
        } else if (current_node instanceof MInterfaceDef) {
            String[] base  = { "CCM_Local" };
            String[] files = { node_name + ".h", node_name + "_types.h" };

            return addBaseFiles(base, files);
        } else if (current_node instanceof MContainer) {
            String[] base  = { "CCM_Local" };
            String[] files = null;

            if ((flags & FLAG_USER_TYPES_FILES) != 0) {
                String[] silly = { node_name + "_user_types.h" };
                files = silly;
            } else {
                String[] silly = { "" /* intentionally blank */ };
                files = silly;
            }

            return addBaseFiles(base, files);
        } else {
            throw new RuntimeException("Invalid output node "+node_name);
        }
    }

    /**
     * Build up a File object based on the pathname components given in
     * prev_path. If any of the given pathname directories do not exist, create
     * them on the filesystem. Also output confix files (Makefile.py, dummy.cc)
     * in each directory that doesn't have them.
     *
     * @param prev_path a list of directory names leading to the current output
     *        directory.
     */
    private void buildOutputFilePath(List prev_path)
        throws IOException
    {
        File read_path = new File(output_dir, "");
        File write_path = new File("");
        for (Iterator p = prev_path.iterator(); p.hasNext(); ) {
            String component = (String) p.next();
            read_path = new File(read_path, component);
            write_path = new File(write_path, component);

            // this is a hack for confix, which needs a Makefile.py file in each
            // directory that it looks at. so we simply put a blank one in each
            // directory, if it's not there already.

            File confix_file = new File(read_path, "Makefile.py");
            if (! confix_file.isFile())
                writeFinalizedFile(write_path.toString(), "Makefile.py", "");
        }
    }
}

