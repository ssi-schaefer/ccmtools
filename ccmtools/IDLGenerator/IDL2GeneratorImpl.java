/* CCM Tools : IDL Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
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

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IDL2GeneratorImpl
    extends IDLGenerator
{
    public IDL2GeneratorImpl(Driver d, File out_dir)
        throws IOException { super("IDL2", d, out_dir); }

    public void writeOutput(Template template)
        throws IOException { writeOutput(template, "2"); }

    /**
     * Create an #include statement sufficient for including the given node's
     * header file.
     *
     * @param node the node to use for gathering include statement information.
     * @return a string containing an #include statement.
     */
    protected String getScopedInclude(MContained node)
    {
        List scope = getScope(node);
        scope.add(node.getIdentifier());
        return "#include <"+join("_", scope)+".idl2>";
    }
}

