/* CCM Tools : IDL Code Generator Library
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

package ccmtools.IDLGenerator;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class IDL3GeneratorImpl
    extends IDLGenerator
{
    public IDL3GeneratorImpl(Driver d, File out_dir)
        throws IOException { super("IDL3", d, out_dir); }

    /**
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException { writeOutput(template, "3"); }

    /**
     * Get a local value for the given variable name.
     *
     * This function performs some common value parsing in the CCM MOF library.
     * More specific value parsing needs to be provided in the subclass for a
     * given language, in the subclass' getLocalValue function. Subclasses
     * should call this function first and then perform any subclass specific
     * value manipulation with the returned value.
     *
     * @param variable The variable name to get a value for.
     * @return the value of the variable available from the current
     *         output_variables hash table. Could be an empty string.
     */
    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);

        if (current_node instanceof MHomeDef)
            return data_MHomeDef(variable, value);

        return value;
    }

    /**************************************************************************/

    protected String data_MHomeDef(String data_type, String data_value)
    {
        if (data_type.equals("MHomeDefPublicKeyExceptions")) {
            return ", Components::DuplicateKeyValue, Components::InvalidKey";
        } else if (data_type.equals("MHomeDefPublicKeyParameters")) {
            return "in "+((MHomeDef) current_node).getPrimary_Key()+" key";
        } else if (data_type.equals("MHomeDefPublicKeyFunctions")) {
            MHomeDef home = (MHomeDef) current_node;

            String[] keys = { "KeyType", "ComponentType" };
            String[] values = { home.getPrimary_Key().getIdentifier(),
                                home.getComponent().getIdentifier() };

            Map local_map = new Hashtable();
            for (int i = 0; i < keys.length ; i++)
                local_map.put(keys[i], values[i]);

            Template template = template_manager.getRawTemplate(data_type);
            return template.substituteVariables(local_map);
        }
        return data_MComponentDef(data_type, data_value);
    }
}

