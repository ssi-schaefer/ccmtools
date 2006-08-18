/* CCM Tools : Utilities
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003, 2004 Salomon Automation
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


package ccmtools.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.ui.UserInterfaceDriver;

/*******************************************************************************
 * This class collects some helper methods to handle source code as strings and
 * files.
 * 
 *  
 ******************************************************************************/
public class Code
{	
    /**
     * Helper function for writing finalized files. (see also
     * CodeGenerator.java)
     * 
     * @param driver
     * @param directory
     *            the directory, relative to the package root, where the file
     *            should be written.
     * @param file
     *            the name of the file to write.
     * @param output
     *            a string holding the destination file's contents.
     */
    public static void writeFile(UserInterfaceDriver driver, File outDir, String directory, String file,
            String output) throws IOException
    {
        File local_dir = new File(outDir, directory);
        if(!local_dir.isDirectory())
            local_dir.mkdirs();

        File out_file = new File(local_dir, file);
        FileWriter writer = new FileWriter(out_file);
        writer.write(output, 0, output.length());
        writer.close();
        if(driver != null)
            driver.println("writing " + out_file.toString());
    }

    /**
     * This method writes a Makefile with a given extension (e.g. .py or .pl)
     * and a given content to the file system. If a Makefile already exists, it
     * will not be overwritten.
     * 
     * @param outDir
     * @param fileDir
     * @param extension
     *            A strint that will be used as an extension to Makefile.
     * @param content
     *            A string that will be pasted into the Makefile.
     * @return true if the Makefile has been written, false in all other cases.
     */
    public static boolean writeMakefile(UserInterfaceDriver driver, File outDir, String fileDir,
            String extension, String content) throws IOException
    {
        boolean result;
        File makeFile = new File(outDir, fileDir);
        makeFile = new File(makeFile, "Makefile." + extension);
        if(!makeFile.isFile()) {
            writeFile(driver, outDir, fileDir, "Makefile." + extension, content);
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }


    /**
     * This method removes empty lines (if more than one) and similar #include
     * statements from the generated code.
     * 
     * @param code
     *            A string containing generated code that should be prettified.
     * @return A string containing a prittified version of a given source code.
     */
    public static String prettifySourceCode(String code)
    {
        StringBuffer pretty_code = new StringBuffer();
        Set<String> include_set = new HashSet<String>();
        int from_index = 0;
        int newline_index = 0;
        boolean isEmptyLineSuccessor = false;
        do {
            newline_index = code.indexOf('\n', from_index);
            String code_line = code.substring(from_index, newline_index);
            from_index = newline_index + 1;
            if(code_line.length() != 0) 
            {
                isEmptyLineSuccessor = false;

                if(code_line.startsWith("#include")) 
                {
                    if(include_set.contains(code_line)) 
                    {
                        // Ignore similar #include statements
                    }
                    else 
                    {
                        include_set.add(code_line);
                        pretty_code.append(code_line);
                        pretty_code.append('\n');
                    }
                }
                else 
                {
                    pretty_code.append(code_line);
                    pretty_code.append('\n');
                }
            }
            else 
            {
                if(isEmptyLineSuccessor) 
                {
                    // Ignore second empty line
                }
                else 
                {
                    isEmptyLineSuccessor = true;
                    pretty_code.append('\n');
                }
            }
        } while(from_index < code.length());
        return pretty_code.toString();
    }

    
    // Methods used to handle CORBA repository IDs ----------------------------
    
    public static String getRepositoryId(MContained node)
    {
    		return "IDL:" + getAbsoluteName(node, "/") + ":1.0";
    }
    
    public static String getRepositoryId(String name)
    {
        return "IDL:" + name + ":1.0";
    }
    
    public static String getRepositoryId(String[] name)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("IDL:");
        if(name != null && name.length > 0) {
            buffer.append(name[0]);
            if(name.length > 1) {
                for(int i = 1; i < name.length; i++) {
                    buffer.append("/");
                    buffer.append(name[i]);
                }
            }
        }
        buffer.append(":1.0");
        return buffer.toString();
    }
    
    public static String getRepositoryId(List ns, String name)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("IDL:");
		buffer.append(Text.joinList("/", ns));
		buffer.append("/").append(name);
		buffer.append(":1.0");
		return buffer.toString();
	}
    
    public static List getListFromAbsoluteName(String name)
    {
        List<String> list = new ArrayList<String>();
        if(name != null) {
            String[] names = name.split("/");
            for(int i = 0; i < names.length; i++) {
                list.add(names[i]);
            }
        }
        return list;
    }
    
    public static String[] getArrayFromAbsoluteName(String name)
    {
        return name.split("/");
    }
    
    public static String getNameFromRepositoryId(String repoId)
    {
        return repoId.substring(repoId.indexOf(':')+1, repoId.lastIndexOf(':'));
    }
    
    
    // Methods used to handle CCM model namespaces ----------------------------
    
    public static String getAbsoluteName(MContained node, String sep)
    {
   		if(getNamespaceList(node).size() == 0)
		{
   			return node.getIdentifier();
		}
   		else
   		{
   			return getNamespace(node,sep) + sep + node.getIdentifier();
   		}
    }
    
    public static String getNamespace(MContained node, String sep)
    {
        List nsList = getNamespaceList(node);
        return Text.join(sep, nsList);
    }
    
    /**
     * Calculates the model element namespace by going back from the
     * model element to the top container element and collecting the 
     * names of all module definitions in-between.
     *
     * @param node
     * @return
     */
    public static List<String> getNamespaceList(MContained node)
    {        
        List<String> scope = new ArrayList<String>();
        MContainer c = node.getDefinedIn();
        while(c.getDefinedIn() != null) 
        {
            if(c instanceof MModuleDef)
            {
                scope.add(0, c.getIdentifier());
            }
            c = c.getDefinedIn();
        }
        return scope;
    }
}