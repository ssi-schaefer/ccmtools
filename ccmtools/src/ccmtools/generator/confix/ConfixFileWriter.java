package ccmtools.generator.confix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ccmtools.ui.UserInterfaceDriver;

public abstract class ConfixFileWriter
{
    public abstract void write(UserInterfaceDriver uiDriver, File path) 
        throws IOException;
    
    
    protected void writeFile(UserInterfaceDriver uiDriver, File file, String content) 
        throws IOException
    {
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
}
