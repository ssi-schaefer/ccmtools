package ccmtools.generator.confix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.UI.Driver;
import ccmtools.generator.confix.ui.CommandLineParameters;

public class ConfixGenerator
{
    /** String constants used for generator selection */
    public static final String MAKEFILE_PY_GENERATOR_ID = "makefiles";
    public static final String PACKAGE_VERSION = "pversion";
    public static final String PACKAGE_NAME = "pname";
    
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	private Set ignoredDirs;
	
	public ConfixGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;		
		this.parameters = (CommandLineParameters)parameters;
		ignoredDirs = new HashSet();
		ignoredDirs.add("CVS"); // ignore CVS directories for Makefile.py generation
		
        logger = Logger.getLogger("ccm.generator.confix");
        logger.fine("");
        printVersion();
	}
	
	
	public void generate() 
		throws  CcmtoolsException
	{
		logger.fine("begin");
		try
		{
			for(Iterator i = parameters.getGeneratorIds().iterator(); i.hasNext(); )
	    	{
				String generatorId = (String)i.next();
	    		if(generatorId.equals(MAKEFILE_PY_GENERATOR_ID))
	    		{
	    			generateMakefilePy();               			
	    		}				
	    		// TODO: implement other Confix generators
	    	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in ConfixGenerator: " + e.getMessage());
		}		
		logger.fine("end");
	}
	
	
	private void generateMakefilePy()
		throws  CcmtoolsException
	{
		logger.fine("begin");
		try
		{
			File file = new File(parameters.getOutDir());
			traverseDirectoryTree(file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException("Error in ConfixGenerator.generateMakefilePy: " + e.getMessage());
		}		
		logger.fine("end");
	}
	
	private void traverseDirectoryTree(File currentFile) 
		throws IOException
	{
		if(currentFile.isDirectory())
		{
			writeMakefilePy(currentFile);
			String[] fileList =  currentFile.list();
			for(int i = 0; i< fileList.length; i++)
			{
				if(ignoredDirs.contains(fileList[i]))
				{					
					continue; // Don't generate a Makefile.py file in this directory
				}
				File f = new File(currentFile, fileList[i]);
				traverseDirectoryTree(f);
			}
		}
	}
	
	private void writeMakefilePy(File f) 
		throws IOException
	{
		File file = new File(f, "Makefile.py");
		String content;
		if(f.getPath().equals(parameters.getOutDir()))
		{
			content = "PACKAGE_NAME('" + parameters.getPackageName() + "')\n" 
				+	"PACKAGE_VERSION('" + parameters.getPackageVersion() + "')\n";			
		}
		else
		{
			content = "";
		}
		write(file, content);
	}

	private void write(File file, String content) 
		throws IOException
	{
		if(file.exists())
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
	
    private void printVersion()
    {
        uiDriver.println("Confix Generator, " + Constants.VERSION_TEXT);
    }
}
