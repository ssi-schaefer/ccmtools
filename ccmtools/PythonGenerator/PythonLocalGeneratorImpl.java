/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
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

package ccmtools.PythonGenerator;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MAliasDef;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MEnumDef;
import ccmtools.Metamodel.BaseIDL.MExceptionDef;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MStructDef;
import ccmtools.Metamodel.BaseIDL.MUnionDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PythonLocalGeneratorImpl
    extends PythonGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types =
    {
        "MComponentDef", "MInterfaceDef", "MHomeDef",
        "MStructDef", "MUnionDef", "MAliasDef", "MEnumDef", "MExceptionDef"
    };

    /**************************************************************************/

    public PythonLocalGeneratorImpl(Driver d, File out_dir)
        throws IOException
    { 
	super("PythonLocal", d, out_dir, local_output_types); 
    }

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

        if ((node instanceof MContainer) &&
            (((MContainer) node).getDefinedIn() == null))
            namespace.push("CCM_Local");
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
            List out_path = (List) path_iterator.next();

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

        vars.put("Object",              container.getIdentifier());
        vars.put("Identifier",          operation.getIdentifier());
        vars.put("ProvidesType",        iface.getIdentifier());
        vars.put("SupportsType",        iface.getIdentifier());
        vars.put("LanguageType",        lang_type);
        vars.put("MExceptionDefThrows", getOperationExcepts(operation));
        vars.put("MParameterDefAll",    getOperationParams(operation));
        vars.put("MParameterDefName",   getOperationParamNames(operation));

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

            if (current_node instanceof MHomeDef)
                base_name =
                    ((MHomeDef) current_node).getComponent().getIdentifier();

            String base = handleNamespace("FileNamespace", base_name);

            f = new ArrayList();
            f.add(base); f.add(node_name + "_gen.py"); files.add(f);

            if ((flags & FLAG_APPLICATION_FILES) != 0) {
                f = new ArrayList();
                f.add(base); f.add(node_name + "_app.py"); files.add(f);
            } else {
                f = new ArrayList(); f.add(base); f.add(""); files.add(f);
            }

        } else if ((current_node instanceof MInterfaceDef)
                   || (current_node instanceof MStructDef)
                   || (current_node instanceof MUnionDef)
                   || (current_node instanceof MAliasDef)
                   || (current_node instanceof MEnumDef)
                   || (current_node instanceof MExceptionDef)) {
            f = new ArrayList(); f.add(handleNamespace("AllFileNamespace", ""));
            f.add(node_name + ".py"); files.add(f);

        } else {
            f = new ArrayList(); f.add(""); f.add(""); files.add(f);
        }

        return files;
    }


    protected String getScopedInclude(MContained node)
    {
	// FIXME: Implement this method
        return "#include <...h>";
    }
}
