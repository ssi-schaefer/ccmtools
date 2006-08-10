package ccmtools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.ui.Driver;

public class Utility
{
    
    /*************************************************************************
     * Source Code Utility Methods
     *************************************************************************/
    
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
    
    
    
    /*************************************************************************
     * File IO Utility Methods
     *************************************************************************/
          
    public static void writeSourceFiles(Driver uiDriver, String outDir, List<SourceFile> sourceFileList)
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
    
    
    public static void writeApplicationFiles(Driver uiDriver, String outDir, List<SourceFile> sourceFileList)
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
    
    
    public static void writeSourceCodeToFile(Driver uiDriver, String outDir, SourceFile source, String suffix)
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
    
}
