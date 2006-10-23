package ccmtools.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import ccmtools.ui.UserInterfaceDriver;

public class Confix
{
    
    /*************************************************************************
     * Confix Utility Methods
     *************************************************************************/
    
    
    public static void writeConfix2File(UserInterfaceDriver uiDriver, String directory)
        throws IOException
    {
        writeConfix2File(uiDriver, directory, "");
    }
    
    public static void writeConfix2File(UserInterfaceDriver uiDriver, String directory, String content)
        throws IOException
    {
        File file = new File(directory, "Confix2.dir");
        if (file.exists())
        {
            uiDriver.println("skipping " + file);
        }
        else
        {
            uiDriver.println("writing " + file);
            FileWriter writer = new FileWriter(file);
            writer.write(content, 0, content.length());
            writer.close();
        }
    }
    
    public static void writeConfix2Files(UserInterfaceDriver uiDriver, Set<String> outputDirectories) 
        throws IOException
    {
        for(String dir : outputDirectories)
        {
            writeConfix2File(uiDriver, dir, "");
        }        
    }
}
