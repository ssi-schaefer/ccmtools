package ccmtools.generator.confix;

import java.io.File;
import java.io.IOException;

import ccmtools.ui.UserInterfaceDriver;

public class ConfixMakefilePyWriter extends ConfixFileWriter
{
    private CommandLineParameters parameters;
    
    public ConfixMakefilePyWriter(CommandLineParameters parameters)
    {
        this.parameters = parameters;
    }
    
    public void write(UserInterfaceDriver uiDriver, File path) throws IOException
    {
        File file = new File(path, "Makefile.py");
        StringBuilder content = new StringBuilder();
        // Only in a top-level directory generate package name and version
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
            }
        }
        writeFile(uiDriver, file, content.toString());        
    }
}
