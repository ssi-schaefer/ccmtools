/*
 * CCM Tools : C++ Code Generator Library Egon Teiniker
 * <egon.teiniker@tugraz.at> copyright (c) 2002, 2003 Salomon Automation
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

package ccmtools.CppGenerator;

import java.io.File;
import java.io.IOException;

import ccmtools.CodeGenerator.Template;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.Confix;
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

        MContained contained = (MContained)currentNode;
        String remoteName = getRemoteName(contained,Text.MANGLING_SEPARATOR);
        String file_dir = "test";
        String file_name = "_check" + remoteName + ".cc";

        writeFinalizedFile(file_dir, file_name, generated_code);
        
        Confix.writeConfix2File(uiDriver, output_dir + File.separator + file_dir);
    }
}