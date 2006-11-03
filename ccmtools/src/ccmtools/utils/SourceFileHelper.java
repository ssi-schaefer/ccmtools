package ccmtools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ccmtools.CcmtoolsException;
import ccmtools.ui.UserInterfaceDriver;

public class SourceFileHelper
{
    /*
     * Source Code Utility Methods
     */
    
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

    
    public static void writeSourceFiles(UserInterfaceDriver uiDriver, String outDir, List<SourceFile> sourceFileList)
        throws CcmtoolsException
    {
        try
        {
            for(SourceFile source : sourceFileList)
            {
                writeSourceCodeToFile(uiDriver, outDir, source, "");
            }
        }
        catch (IOException e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    
    public static void writeApplicationFiles(UserInterfaceDriver uiDriver, String outDir, List<SourceFile> sourceFileList)
        throws CcmtoolsException
    {
        try
        {
            for(SourceFile source : sourceFileList)
            {
                File location = new File(outDir, source.getPackageName());
                File file = new File(location, source.getClassName());
                if (file.exists())
                {
                    uiDriver.println("WARNING " + file + " already exists!");
                    writeSourceCodeToFile(uiDriver, outDir, source, ".new");
                }
                else
                {
                    writeSourceCodeToFile(uiDriver, outDir, source, "");
                }
            }
        }
        catch (IOException e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    
    public static void writeSourceCodeToFile(UserInterfaceDriver uiDriver, String outDir, SourceFile source, String suffix)
        throws IOException
    {
        File location = new File(outDir, source.getPackageName());
        File file = new File(location, source.getClassName() + suffix);
        String sourceCode = source.getCode();

        if (compareSorceWithExistingFile(sourceCode, file))
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
    
    
    public static boolean compareSorceWithExistingFile(String code, File file) 
        throws IOException
    {
        if(file.isFile()) 
        {
            StringBuffer buffer = new StringBuffer();
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(input);
            String line = null;
            while((line = reader.readLine()) != null) 
            {
                buffer.append(line + "\n");
            }
//            System.out.println(">>>" + buffer + "<<<");
//            System.out.println(">>>" + code + "<<<");
            return code.equals(buffer.toString());
        }
        return false;
    }


    
    
    /*
     * Deprecated write methods (used by the old C++ generators)
     */
    
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
    public static void writeFile(UserInterfaceDriver driver, File outDir, String directory, String file, String output)
        throws IOException
    {
        File local_dir = new File(outDir, directory);
        if (!local_dir.isDirectory())
            local_dir.mkdirs();

        File out_file = new File(local_dir, file);
        FileWriter writer = new FileWriter(out_file);
        writer.write(output, 0, output.length());
        writer.close();
        if (driver != null)
            driver.println("writing " + out_file.toString());
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
}
