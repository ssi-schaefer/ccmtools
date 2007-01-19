/* CCM Tools : C++ Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Egon Teiniker <egon.teiniker@salomon.at>
 * Copyright (C) 2002 - 2005 Salomon Automation
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
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import ccmtools.CodeGenerator.Template;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDef;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;
import ccmtools.utils.Confix;
import ccmtools.utils.SourceFileHelper;
import ccmtools.utils.Text;

public class CppLocalTestGenerator
    extends CppGenerator
{
    // types for which we have a global template ; that is, a template that is
    // not contained inside another template.

    private final static String[] local_output_types = { 
            "MComponentDef" 
    };

    
    public CppLocalTestGenerator(UserInterfaceDriver d, File out_dir)
        throws IOException
    {
        super("CppLocalTest", d, out_dir, local_output_types);
        logger = Logger.getLogger("ccm.generator.cpp.local.test");
        logger.fine("begin");        
        cxxGenNamespace = ConfigurationLocator.getInstance().getCppLocalNamespaceExtension();
        logger.fine("end");
    }

    /**
     * Write generated code to an output file.
     *
     * @param template the template object to get the generated code structure
     *        from ; variable values should come from the node handler object.
     */
    public void writeOutput(Template template)
        throws IOException
    {
        logger.fine("enter writeOutput()");
        // try to prittify generated code (eliminate empty lines etc.
        String generated_code = SourceFileHelper.prettifySourceCode(template.substituteVariables(output_variables));

        if(generated_code.trim().equals("")) 
            return;

        MContained contained = (MContained)currentNode;        
        String file_dir = "test";
        String file_name = "_check" + getLocalCxxNamespace(contained, Text.MANGLING_SEPARATOR)
                                + contained.getIdentifier() + ".cc";        
        File outFile = new File(output_dir + File.separator + file_dir, file_name);
        if(outFile.isFile()) 
        {
            if(!isCodeEqualWithFile(generated_code, outFile)) 
            {
                uiDriver.printMessage("WARNING: " + outFile + " already exists!");
                file_name += ".new";
                outFile = new File(output_dir + File.separator + file_dir, file_name);
            }
        }
	
        if(isCodeEqualWithFile(generated_code, outFile)) 
        {
            System.out.println("skipping " + outFile);
        }
        else 
        {
            writeFinalizedFile(file_dir, file_name, generated_code);
            Confix.writeConfix2File(uiDriver, output_dir + File.separator + file_dir);
        }
        logger.fine("leave writeOutput()");
    }
    

    protected Map getTwoStepOperationVariables(MOperationDef operation, MContained container)
    { 
        logger.fine("getTwoStepOperationVariables()");
        return new Hashtable(); 
    }
}
