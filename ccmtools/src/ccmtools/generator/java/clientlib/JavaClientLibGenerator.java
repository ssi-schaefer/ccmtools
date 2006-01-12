package ccmtools.generator.java.clientlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.CodeGenerator.CcmGraphTraverser;
import ccmtools.CodeGenerator.GraphTraverser;
import ccmtools.IDL3Parser.ParserManager;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.UI.Driver;
import ccmtools.generator.java.clientlib.metamodel.SourceFile;
import ccmtools.generator.java.clientlib.ui.CommandLineParameters;

public class JavaClientLibGenerator
{
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public JavaClientLibGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (CommandLineParameters)parameters;
		
        logger = Logger.getLogger("ccm.generator.java.clientlib");
        logger.fine("JavaClientLibGenerator()");
	}
	
	
	public void generate()
		throws CcmtoolsException
	{		
		System.out.println("JavaClientLibGenerator.generate()"); //!!!!!!!!!
	
		for(Iterator i = parameters.getIdlFiles().iterator(); i.hasNext(); )
		{
			String idlFile = (String)i.next();
			MContainer ccmModel = loadCcmModel(idlFile, parameters.getIncludePaths());

			// Transform CCM Model to Java Implementation Model
			GraphTraverser traverser = new CcmGraphTraverser();
	        CcmModelNodeHandler nodeHandler = new CcmModelNodeHandler();
	        traverser.addHandler(nodeHandler);
	        traverser.traverseGraph(ccmModel);    
	        
	        // Save source code to files
	        for(Iterator j = nodeHandler.getSourceFileList().iterator(); j.hasNext(); )
	        {
	        	SourceFile source = (SourceFile)j.next();
	        	writeCode(parameters.getOutDir(), source);
	        }
		}
	}
	
	public void writeCode(String outDir, SourceFile source)
		throws CcmtoolsException
	{
		File location = new File(outDir, source.getPackageName());
		File file = new File(location, source.getClassName());		
		uiDriver.println("> write " + file);
		try
		{
			if(!location.isDirectory()) 
			{
				location.mkdirs();
			}			
			FileWriter writer = new FileWriter(file);
			writer.write(source.getCode(), 0, source.getCode().length());
			writer.close();
		}
		catch (IOException e)
		{
			throw new CcmtoolsException("writeCode(): " + e.getMessage());
		}
	}
	
	
    private MContainer loadCcmModel(String fileName, List includes) 
    	throws CcmtoolsException
	{
		logger.fine("enter loadCcmModel()");
		MContainer ccmModel = null;
		File source = new File(fileName);
		try
		{
			ParserManager manager = new ParserManager(Driver.M_NONE);

			// create the name of the temporary idl file generated from the
			// preprocessor cpp
			String tmpFile = "_CCM_" + source.getName();
			File idlfile = new File(System.getProperty("user.dir"), tmpFile.substring(0, tmpFile.lastIndexOf(".idl")));

			// step (0). run the C preprocessor on the input file.
			// Run the GNU preprocessor cpp in a separate process.
			StringBuffer cmd = new StringBuffer();
			cmd.append(Constants.CPP_PATH);
			cmd.append(" -o ").append(idlfile).append(" ");
			for (Iterator i = includes.iterator(); i.hasNext();)
			{
				cmd.append("-I").append((String) i.next()).append(" ");
			}
			cmd.append(source);

			uiDriver.printMessage(cmd.toString());
			Process preproc = Runtime.getRuntime().exec(cmd.toString());
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(preproc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(preproc.getErrorStream()));

			// Read the output and any errors from the command
			String s;
			while ((s = stdInput.readLine()) != null)
				uiDriver.printMessage(s);
			while ((s = stdError.readLine()) != null)
				uiDriver.printMessage(s);

			// Wait for the process to complete and evaluate the return
			// value of the attempted command
			preproc.waitFor();
			if (preproc.exitValue() != 0)
				throw new CcmtoolsException("Preprocessor: " + "Please verify your include paths or file names ("
						+ source + ").");

			// step (1). parse the resulting preprocessed file.
			uiDriver.printMessage("parse " + idlfile.toString());
			manager.reset();
			manager.setOriginalFile(source.toString());
			ccmModel = manager.parseFile(idlfile.toString());
			if (ccmModel == null)
			{
				throw new CcmtoolsException("Parser error " + source + ":\n" + "parser returned an empty CCM model");
			}
			String kopf_name = source.getName();//.split("\\.")[0];
			kopf_name = kopf_name.replaceAll("[^\\w]", "_");
			ccmModel.setIdentifier(kopf_name);

			idlfile.deleteOnExit();
		}
		catch (Exception e)
		{
			throw new CcmtoolsException("loadCcmModel():" + e.getMessage());
		}
		logger.fine("leave loadCcmModel()");
		return ccmModel;
	}
}
