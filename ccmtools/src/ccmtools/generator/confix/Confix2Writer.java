package ccmtools.generator.confix;

import java.io.File;
import java.io.IOException;

import ccmtools.ui.UserInterfaceDriver;

public class Confix2Writer extends ConfixFileWriter
{
    private CommandLineParameters parameters;
    
    public Confix2Writer(CommandLineParameters parameters)
    {
        this.parameters = parameters;
    }
    
    public void write(UserInterfaceDriver uiDriver, File path) throws IOException
    {
        // In every directory generate an empty "Confix2.dir" file
        writeFile(uiDriver, new File(path, "Confix2.dir"), ""); 
        // Only in the top-level directory generate a "Confix2.pkg" file
        StringBuilder content = new StringBuilder();
        if(path.getPath().equals(parameters.getOutDir()))
        {           
            if(parameters.getPackageName().length() != 0)
            {
                content.append("PACKAGE_NAME('");
                content.append(parameters.getPackageName());
                content.append("')\n");
                
                if(parameters.getPackageVersion().length() != 0)
                {
                    content.append("PACKAGE_VERSION('");
                    content.append(parameters.getPackageVersion());
                    content.append("')\n");
                }
                else
                {
                    content.append("PACKAGE_VERSION('0.0.0')\n");
                }
                writeFile(uiDriver, new File(path, "Confix2.pkg"), content.toString());
            }
        }
    }
}
