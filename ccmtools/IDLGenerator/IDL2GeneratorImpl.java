/* CCM Tools : IDL Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

import java.io.File;
import java.io.IOException;

public class IDL2GeneratorImpl
    extends IDLGenerator
{
    public IDL2GeneratorImpl(Driver d, File out_dir)
        throws IOException 
    { 
	super("IDL2", d, out_dir, null, null); 
    }


    /**
     * This method is used to create the needed Makefiles
     *  - the confix' Makefile.py
     *  - a Makefile that calls the idl2 compiler (*.idl2 => *.h *.cc)
     **/
    public void writeMakefiles()
    {
	Template template;

	// Confix Makefile.py
	template = template_manager.getRawTemplate("MakefilePy");
	if(template != null)
	    writeFinalizedFile("","Makefile.py",template.getTemplate());

	// Makefile that calls the idl compiler
	template = template_manager.getRawTemplate("MakefileIdl");
	if(template != null)
	    writeFinalizedFile("","Makefile",template.getTemplate());
    }


    public void writeOutput(Template template)
        throws IOException 
    { 
	super.writeOutput(template, "2");
	writeMakefiles();
    }
}

