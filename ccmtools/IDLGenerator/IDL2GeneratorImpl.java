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


    protected String getLocalValue(String variable)
    {
        if (current_node instanceof MComponentDef ||
            current_node instanceof MHomeDef) {
            if (variable.equals("BaseType")) {
                String base = joinBaseNames(", ");
                if (base.length() > 0) return ", " + base;
            }
        }

        return super.getLocalValue(variable);
    }


    /***
     * The IDL2 files are used by the ORB's IDL compiler to create stub and skeleton code
     * (*.h and *.cc files). To run the IDL compiler, we need a Makefile.py and a Makefile.
     * The Makefile calls the ORB's IDL compiler (via ccmtools-idl Python script).
     * The Mapefile.py forces a call to make and to compile the *.h and *.cc files. 
     */
    protected void writeOutput(Template template)
        throws IOException
    {
	super.writeOutput(template);

        template = template_manager.getRawTemplate("MakefilePy");
        if (template != null)
            writeFinalizedFile("", "Makefile.py", template.getTemplate());

        template = template_manager.getRawTemplate("MakefileIdl");
        if(template != null)
            writeFinalizedFile("", "Makefile", template.getTemplate());
    }

}

