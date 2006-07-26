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

import ccmtools.CcmtoolsException;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MModuleDef;
import ccmtools.UI.Driver;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/*******************************************************************************
 * This class collects some helper methods to handle source code as strings and
 * files.
 * 
 *  
 ******************************************************************************/
public class Code
{
	
	/**
	 * Helper method to write out a list of SourceFile objects.
	 * 
	 * @param uiDriver User interface driver to write some "> write ..." messages 
	 * @param outDir Output directory
	 * @param sourceFileList List of SourceFile objects
	 *
	 * @throws CcmtoolsException
	 */
	public static void writeSourceCodeFiles(Driver uiDriver, String outDir, List sourceFileList)
			throws CcmtoolsException
	{
		try
		{
			for (Iterator i = sourceFileList.iterator(); i.hasNext();)
			{
				SourceFile source = (SourceFile) i.next();
				writeJavaSourceFile(uiDriver, outDir, source, "");
			}
		}
		catch (IOException e)
		{
			throw new CcmtoolsException("writeCode(): " + e.getMessage());
		}
	}

	
	public static void writeJavaApplicationFiles(Driver uiDriver, String outDir, List sourceFileList)
			throws CcmtoolsException
	{
		try
		{
			for (Iterator i = sourceFileList.iterator(); i.hasNext();)
			{
				SourceFile source = (SourceFile) i.next();
				
				File location = new File(outDir, source.getPackageName());			
				File file = new File(location, source.getClassName());
				if(file.exists())
				{
					uiDriver.println("WARNING " + file + " already exists!");
					writeJavaSourceFile(uiDriver, outDir, source, ".new");
				}
				else
				{
					writeJavaSourceFile(uiDriver, outDir, source, "");
				}
			}
		}
		catch (IOException e)
		{
			throw new CcmtoolsException("writeCode(): " + e.getMessage());
		}
	}
	
	
	public static void writeJavaSourceFile(Driver uiDriver, String outDir, SourceFile source, 
											String suffix)
		throws IOException
	{
		File location = new File(outDir, source.getPackageName());
		File file = new File(location, source.getClassName() + suffix);
		String sourceCode = source.getCode()+"\n";

		if(compareWithFile(sourceCode, file))
		{
			uiDriver.println("skipping " + file);
		}
		else
		{
			uiDriver.println("writing " + file);
			if (!location.isDirectory())
			{
				location.mkdirs();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(source.getCode(), 0, source.getCode().length());
			writer.close();
		}		
	}
	
	
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
    public static void writeFile(Driver driver, File outDir, String directory, String file,
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
    public static boolean writeMakefile(Driver driver, File outDir, String fileDir,
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

    public static String removeEmptyLines(String code)
    {
        StringBuilder out = new StringBuilder();
        int indexFrom = 0;
        int indexNewline = 0;
        boolean isEmptyLineSuccessor = false;
        do 
        {
            indexNewline = code.indexOf('\n', indexFrom);
            String codeLine = code.substring(indexFrom, indexNewline);
            indexFrom = indexNewline + 1;
            if(codeLine.length() > 0) 
            {
                isEmptyLineSuccessor = false;
                out.append(codeLine).append('\n');
            }
            else 
            {
                if(isEmptyLineSuccessor) 
                {
                    // Ignore next empty line
                }
                else 
                {
                    out.append('\n');
                    isEmptyLineSuccessor = true;
                }
            }                         
        }while(indexFrom < code.length());
        return out.toString();
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
        Set include_set = new HashSet();
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

    /**
     * This method reads a file, specified by a File object, and compares the
     * file's content with a given code string.
     * 
     * @param code
     *            A string containing source code.
     * @param file
     *            A File object that points to a file which should be compare.
     * @return true if the file's content is equal with the given code string
     *         false in all other cases
     */
    public static boolean compareWithFile(String code, File file) throws IOException
    {
        if(file.isFile()) {
            StringBuffer buffer = new StringBuffer();
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(input);
            String line = null;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return code.equals(buffer.toString());
        }
        return false;
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
        List list = new ArrayList();
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