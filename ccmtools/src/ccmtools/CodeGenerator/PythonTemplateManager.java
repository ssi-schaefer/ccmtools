/* CCM Tools : Code Generator Library
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

package ccmtools.CodeGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PythonTemplateManager
    implements TemplateManager
{
    private File source;
    
    /**
     * Initialize the class instance by locating a likely directory for
     * templates.
     * All templates are organized in directories that reflect
     * the name of generated languages:
     *   IDL3Templates/
     *   CppLocalTemplates/
     *   CppRemoteTemplates/
     *   etc.
     * 
     * @param language the language to use for templates. This is used to
     *                 locate a likely template source.
     *                 ("CppLocalTemplates", "CppRemoteTemplates", etc).
     */
    public PythonTemplateManager(String language)
        throws IOException
    {
        String templatesDir = language + "Templates";
        source = new File(System.getProperty("ccmtools.templates"), templatesDir);
        System.out.println("> load templates from: " + source);
        if (source.exists() && source.isDirectory()) {
            // 
        }
        else {
            // Stop code generation because there are no valid templates found.
            throw new IOException("Error: No template source found for " + language);
        }
    }


    /**
     * Load all templates for the given node type and locate the variables in
     * each template. Return a list of unique variable names from the templates.
     *
     * @param node_type the type of node to find a template for.
     */
    public Set getVariables(String node_type)
    {
        Set ret = new HashSet();
        Set templates = loadTemplates(node_type);
        for (Iterator i = templates.iterator(); i.hasNext(); ) {
            Set variables = ((Template) i.next()).findVariables();
            for (Iterator j = variables.iterator(); j.hasNext(); ) {
                ret.add(j.next());
            }
        }
        return ret;
    }

    /**
     * Load the template for the given node type, find and replace short
     * variables with full scope identifiers, and return the fully scoped
     * template.
     *
     * @param node_type a string providing the name of the desired template.
     *                  This is usually the name of an interface from the CCM
     *                  MOF library, i.e. a string starting with 'M' and ending
     *                  with 'Def'. Sometimes there are modifiers on the end of
     *                  the name, however, such as 'MInterfaceDefLocal'.
     * @param scope_id The full scope identifier to use as a base name for
     *                 variable replacement. See the documentation for
     *                 CodeGeneratorHandlerImpl for information on the full
     *                 scope identifier.
     * @return a Template object representing the template found for the given
     *         node type. Can be null, if no template was found.
     */
    public Template getTemplate(String node_type, String scope_id)
    {
        Template template = getRawTemplate(node_type);
        if (template == null) {
            return null;
        } 
        else {
            template.scopeVariables(scope_id);
            return template;
        }
    }

    /**
     * Load and the template for the given node type.
     *
     * @param node_type a string indicating the type of the node to find a
     *                  template for. This is usually the name of an interface
     *                  from the CCM MOF library, i.e. a string starting with
     *                  'M' and ending with 'Def'.
     * @return a Template object with unscoped variables.
     */
    public Template getRawTemplate(String node_type)
    {
        Set templates = loadTemplates(node_type);

        for (Iterator i = templates.iterator(); i.hasNext(); ) {
            Template template = (PythonTemplate) i.next();
            if (template.getName().equals(node_type)) {
                return template;
            }
        }

        return null;
    }

    /**************************************************************************/

    /**
     * Load all templates matching the node type.
     *
     * @param node_type The type of the node to find a template for. This is
     *                  usually the name of an interface from the CCM MOF
     *                  library, i.e. a string starting with 'M' and ending with
     *                  'Def'.
     * @return  a (possibly empty) set of Template objects.
     */
    private Set loadTemplates(String node_type)
    {
        Set ret = new HashSet();
        //String[] candidates = source.list();
        String[] candidates = TemplateLoader.getInstance().loadTemplateList(source);
        
        for (int i = 0; i < candidates.length; i++) {
            File file = new File(source, candidates[i]);
            if (file.getName().startsWith(node_type)) {
//                try {
                    ret.add(new PythonTemplate(file));
//                } 
//                catch (IOException e) {
                    // TODO: logging
//                }
            }
        }
        return ret;
    }
}

