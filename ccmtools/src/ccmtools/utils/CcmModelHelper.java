package ccmtools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;

import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.ui.Driver;

public class CcmModelHelper
{

    public static  MContainer loadCcmModel(Driver uiDriver, String fileName, List includes) 
		throws CcmtoolsException
	{
		MContainer ccmModel = null;
		File source = new File(fileName);
		try
		{
			// create the name of the temporary idl file generated from the
			// preprocessor cpp
			String tmpFile = "_CCM_" + source.getName();
			File idlfile = new File(System.getProperty("user.dir"), tmpFile.substring(0, tmpFile.lastIndexOf(".idl")));

			// step (0). run the C preprocessor on the input file.
			// Run the GNU preprocessor cpp in a separate process.
			StringBuffer cmd = new StringBuffer();
            if(CcmtoolsProperties.Instance().get("ccmtools.cpp").length() != 0)
            {
                cmd.append(CcmtoolsProperties.Instance().get("ccmtools.cpp"));                
            }
            else
            {
                cmd.append(Constants.CPP_PATH);
            }
            cmd.append(" ");
			for (Iterator i = includes.iterator(); i.hasNext();)
			{
				cmd.append("-I").append((String) i.next()).append(" ");
			}
			cmd.append(source);
                        
			uiDriver.printMessage(cmd.toString()); // show cpp command line
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
				uiDriver.printMessage(s);

			// Wait for the process to complete and evaluate the return
			// value of the attempted command
			preproc.waitFor();
			if (preproc.exitValue() != 0)
            {
				throw new CcmtoolsException("Preprocessor: Please verify your include paths or file names ("
						+ source + ").");
            }
            else
            {
                FileWriter writer = new FileWriter(idlfile);
                writer.write(code.toString(), 0, code.toString().length());
                writer.close();
            }
            
			// step (1). parse the resulting preprocessed file.
//			uiDriver.printMessage("parse " + idlfile.toString());

			ccmtools.parser.idl3.ParserManager manager = new ccmtools.parser.idl3.ParserManager(source.toString());			
			
			ccmModel = manager.parseFile(idlfile.toString());			
			if (ccmModel == null)
			{
				throw new CcmtoolsException("Parser error " + source + ":\n" + "parser returned an empty CCM model");
			}
			String kopf_name = source.getName();// .split("\\.")[0];
			kopf_name = kopf_name.replaceAll("[^\\w]", "_");
			ccmModel.setIdentifier(kopf_name);
			idlfile.deleteOnExit();
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("loadCcmModel():" + e.getMessage());
		}
		return ccmModel;
	}
}
