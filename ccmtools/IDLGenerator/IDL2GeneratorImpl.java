/* CCM Tools : IDL Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

package ccmtools.IDLGenerator;

import ccmtools.CodeGenerator.Driver;
import ccmtools.CodeGenerator.Template;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.ComponentIDL.MComponentDef;
import ccmtools.Metamodel.ComponentIDL.MHomeDef;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class IDL2GeneratorImpl
    extends IDLGenerator
{
    public IDL2GeneratorImpl(Driver d, File out_dir) throws IOException
    { super("2", d, out_dir); }

    /**
     * Finalize the output files. This method is used to create the needed
     * Makefiles : the confix Makefile.py and a Makefile that calls the idl2
     * compiler (*.idl> *.h *.cc).
     *
     * @param defines a map of environment variables and their associated
     *        values. This usually contains things like the package name,
     *        version, and other generation info.
     * @param files a list of the filenames (usually those that were provided to
     *        the generator front end).
     */
    public void finalize(Map defines, List files)
    {
	Template template;

	template = template_manager.getRawTemplate("MakefilePy");
	if (template != null)
	    writeFinalizedFile("", "Makefile.py", template.getTemplate());

	template = template_manager.getRawTemplate("MakefileIdl");
	if(template != null)
	    writeFinalizedFile("", "Makefile", template.getTemplate());
    }

    protected String getLocalValue(String variable)
    {
        String value = super.getLocalValue(variable);

        if (current_node instanceof MComponentDef ||
            current_node instanceof MHomeDef) {
            if (variable.equals("BaseType")) {
                String base = joinBaseNames(", ");
                if (base.length() > 0) return ", " + base;
            }
        }

        return value;
    }
}

