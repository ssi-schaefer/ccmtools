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
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MProvidesDef;
import ccmtools.Metamodel.ComponentIDL.MSupportsDef;
import ccmtools.Metamodel.ComponentIDL.MUsesDef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IDL3MirrorGeneratorImpl
    extends IDLGenerator
{
    public IDL3MirrorGeneratorImpl(Driver d, File out_dir)
        throws IOException { super("IDL3Mirror", d, out_dir); }

    public void writeOutput(Template template)
        throws IOException { writeOutput(template, "3mirror"); }

    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);
        if (current_node instanceof MComponentDef)
            return data_MComponentDef(variable, value);
        return value;
    }

    protected String data_MComponentDef(String data_type, String data_value)
    {
        List ifaces = new ArrayList();
        if (data_type.equals("MProvidesDefInclude")) {
            Set contents = ((MComponentDef) current_node).getFacets();
            for (Iterator i = contents.iterator(); i.hasNext(); )
                ifaces.add(((MProvidesDef) i.next()).getProvides());
            return join("\n", filterIncludes(data_value.split("\n"), ifaces));
        } else if (data_type.equals("MSupportsDefInclude")) {
            Set contents = ((MComponentDef) current_node).getSupportss();
            for (Iterator i = contents.iterator(); i.hasNext(); )
                ifaces.add(((MSupportsDef) i.next()).getSupports());
            return join("\n", filterIncludes(data_value.split("\n"), ifaces));
        } else if (data_type.equals("MUsesDefInclude")) {
            Set contents = ((MComponentDef) current_node).getReceptacles();
            for (Iterator i = contents.iterator(); i.hasNext(); )
                ifaces.add(((MUsesDef) i.next()).getUses());
            return join("\n", filterIncludes(data_value.split("\n"), ifaces));
        }
        return super.data_MComponentDef(data_type, data_value);
    }

    private List filterIncludes(String[] includes, List contents)
    {
        List ext_lines = new ArrayList();
        MComponentDef component = (MComponentDef) current_node;
        for (int i = 0; i < includes.length; i++) {
            String name = includes[i];

            if (name.trim().equals("")) {
                ext_lines.add("");
                continue;
            }

            name = name.substring(10, name.length() - 12);
            for (Iterator it = contents.iterator(); it.hasNext(); ) {
                MInterfaceDef iface = (MInterfaceDef) it.next();
                if (iface.getIdentifier().equals(name)) {
                    if (! iface.isDefinedInOriginalFile())
                        ext_lines.add(includes[i]);
                    break;
                }
            }
        }
        return ext_lines;
    }
}

