package ccmtools.parser.cpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.ConfigurationLocator;

public class ExternalCpp
    implements PreProcessor
{
    
    public void process(UserInterfaceDriver uiDriver, String idlFileName, List<String> includes) 
        throws CcmtoolsException
    {
        File idlFile = new File(idlFileName);
        File tmpIdlFile = new File(idlFileName + ".tmp");
        try
        {
            // Run a C preprocessor on the input file, in a separate process.
            StringBuffer cmd = new StringBuffer();
            cmd.append(ConfigurationLocator.getInstance().get("ccmtools.cpp"));
            cmd.append(" ");
            for (String includePath : includes)
            {
                cmd.append("-I").append(includePath).append(" ");
            }
            cmd.append(idlFile.getAbsolutePath());
            uiDriver.printMessage("use external preprocessor: " + cmd.toString()); // print cpp command line

            Process preproc = Runtime.getRuntime().exec(cmd.toString());
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(preproc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

            // Read the output and any errors from the command
            String s;
            StringBuffer code = new StringBuffer();
            while ((s = stdInput.readLine()) != null)
            {
                code.append(s).append("\n");
            }
            while ((s = stdError.readLine()) != null)
            {
                uiDriver.printMessage(s);
            }

            // Wait for the process to complete and evaluate the return
            // value of the attempted command
            preproc.waitFor();
            if (preproc.exitValue() != 0)
            {
                throw new CcmtoolsException("Preprocessor Error: Please verify your include paths or file names (" 
                        + idlFileName + ")!!");
            }
            else
            {
                uiDriver.println("writing " + tmpIdlFile);
                FileWriter writer = new FileWriter(tmpIdlFile);
                writer.write(code.toString(), 0, code.toString().length());
                writer.close();
            }            
        }
        catch (Exception e)
        {
            throw new CcmtoolsException(e.getMessage());
        }
    }
}
