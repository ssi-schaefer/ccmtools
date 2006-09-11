/*
 * CCM Tools : C++ Code Generator Library Egon Teiniker
 * <egon.teiniker@tugraz.at> copyright (c) 2002, 2003 Salomon Automation
 * 
<<<<<<< CppRemoteTestGenerator.java
=======
 * $Id$
 * 
>>>>>>> 1.3.6.1
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

import java.io.File;
import java.io.IOException;

import ccmtools.CodeGenerator.Template;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Text;

public class CppRemoteTestGenerator 
    extends CppRemoteGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.
    private final static String[] REMOTE_TEST_OUTPUT_TEMPLATE_TYPES = {
        "MComponentDef"
    };

    
    public CppRemoteTestGenerator(UserInterfaceDriver uiDriver, File outDir)
        throws IOException
    {
        super("CppRemoteTest", uiDriver, outDir, REMOTE_TEST_OUTPUT_TEMPLATE_TYPES);
    }


    /**
     * Write generated code to an output file.
     * 
     * @param template
     *            the template object to get the generated code structure from ;
     *            variable values should come from the node handler object.
     */
    public void writeOutput(Template template) throws IOException
    {
        String generated_code = template.substituteVariables(output_variables);

        if(generated_code.trim().equals(""))
            return;

      //  String node_name = ((MContained) currentNode).getIdentifier();
        String namespace = getRemoteNamespace(((MContained) currentNode),Text.MANGLING_SEPARATOR);
        String file_dir = "test";
        String file_name = "_check_"
                            + namespace.substring(0, namespace.length()-1)
                            + ".cc";

        writeFinalizedFile(file_dir, file_name, generated_code);

        // generate an empty Makefile.py in the CCM_Test
        // directory - needed by Confix
        writeFinalizedFile(file_dir, "Makefile.py", "");
    }
}