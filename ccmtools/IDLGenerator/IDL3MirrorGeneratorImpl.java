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

    /**
     * Filter the include statements by seeing if each include statement refers
     * to an interface that's defined in another source IDL file. All interfaces
     * that are defined locally (i.e. in the same source file as the current
     * node) will not be #include'd in the generated .idl3mirror file.
     *
     * @param includes an array of the include statements that will go in the
     *        generated .idl3mirror file.
     * @param contents a list of the interface objects that are referenced by
     *        the current node.
     * @return a list of the filtered include statements that are supposed to
     *         actually be written to the output file. The number of include
     *         statements in this list will always be less than or equal to the
     *         length of the original includes array.
     */
    private List filterIncludes(String[] includes, List contents)
    {
        List filtered_lines = new ArrayList();
        for (int i = 0; i < includes.length; i++) {
            String name = includes[i];

            if (name.trim().equals("")) { filtered_lines.add(""); continue; }

            // the include statement is of the form :
            // #include "<filename>.idl3mirror"
            // so this retrieves <filename> from the include statement.

            name = name.substring(10, name.length() - 12);

            // see if this interface is in the list of referenced interfaces ...
            // if so, and if it's not defined in the local file, go ahead and
            // add it to the filtered lines to output in generated code.

            for (Iterator it = contents.iterator(); it.hasNext(); ) {
                MInterfaceDef iface = (MInterfaceDef) it.next();
                if (iface.getIdentifier().equals(name)) {
                    if (! iface.getSourceFile().equals(""))
                        filtered_lines.add(includes[i]);
                    break;
                }
            }
        }
        return filtered_lines;
    }
}

