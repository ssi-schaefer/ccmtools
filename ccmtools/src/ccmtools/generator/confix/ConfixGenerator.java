package ccmtools.generator.confix;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.Constants;
import ccmtools.ui.UserInterfaceDriver;
import ccmtools.utils.CcmtoolsProperties;

public class ConfixGenerator
{
    /** String constants used for generator selection */
    public static final String MAKEFILE_PY_GENERATOR_ID = "makefiles";
    public static final String PACKAGE_VERSION = "pversion";
    public static final String PACKAGE_NAME = "pname";
    
    public static final String CONFIX2_GENERATOR_ID = "confix2";
    
	/** UI driver for generator messages */
	protected UserInterfaceDriver uiDriver;
	
	/** Command line parameters */
	protected CommandLineParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	private Set<String> ignoredDirs;
	
	public ConfixGenerator(UserInterfaceDriver uiDriver, CommandLineParameters parameters)
	{
		this.uiDriver = uiDriver;		
		this.parameters = (CommandLineParameters)parameters;
        ignoredDirs = getIgnoredDirectories();
        uiDriver.printMessage("Ignored directories: " + ignoredDirs);
        logger = Logger.getLogger("ccm.generator.confix");
        logger.fine("");
        printVersion(uiDriver);
	}
	
    private Set<String> getIgnoredDirectories()
    {
        Set<String> ignoredDirectorySet = new HashSet<String>();
        String ignoredDirString = CcmtoolsProperties.Instance().get("ccmtools.generator.confix.ignore");
        String[] ignoredDirArray = ignoredDirString.split(",");
        for(int i = 0; i<ignoredDirArray.length;i++)
        {
            ignoredDirectorySet.add(ignoredDirArray[i]);             
        }        
        return ignoredDirectorySet;
    }
	
	public void generate() throws CcmtoolsException
    {
        logger.fine("begin");
        try
        {
            for(String generatorId : parameters.getGeneratorIds())
            {
                if(generatorId.equals(MAKEFILE_PY_GENERATOR_ID))
                {
                    generateMakefilePy();
                }
                else if(generatorId.equals(CONFIX2_GENERATOR_ID))
                {
                    generateConfix2();
                }
                // TODO: implement other Confix generators
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException("[Confix Generator] " + e.getMessage());
        }
        logger.fine("end");
    }
	
	
	private void generateMakefilePy() throws  CcmtoolsException
	{
		logger.fine("begin");
		try
		{
		    ConfixFileWriter fileWriter = new ConfixMakefilePyWriter(parameters);
            File file = new File(parameters.getOutDir());
			traverseDirectoryTree(file, fileWriter);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new CcmtoolsException(e.getMessage());
		}		
		logger.fine("end");
	}
	

    private void generateConfix2() throws  CcmtoolsException
    {
        logger.fine("begin");
        try
        {
            ConfixFileWriter fileWriter = new Confix2Writer(parameters);
            File file = new File(parameters.getOutDir());
            traverseDirectoryTree(file, fileWriter);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new CcmtoolsException(e.getMessage());
        }       
        logger.fine("end");
    }

        
	private void traverseDirectoryTree(File currentFile, ConfixFileWriter fileWriter) 
		throws IOException
	{
		if(currentFile.isDirectory())
		{
			fileWriter.write(uiDriver, currentFile);
			String[] fileList =  currentFile.list();
			for(int i = 0; i< fileList.length; i++)
			{
				if(ignoredDirs.contains(fileList[i]))
				{					
					continue; // Don't generate a Makefile.py file in this directory
				}
				File f = new File(currentFile, fileList[i]);
				traverseDirectoryTree(f, fileWriter);
			}
		}
	}

    
    public static void printVersion(UserInterfaceDriver uiDriver)
    {
        uiDriver.println("+");
        uiDriver.println("+ Confix Files Generator, " + Constants.CCMTOOLS_VERSION_TEXT);
        uiDriver.println("+");
        uiDriver.println("+");
        uiDriver.println(Constants.CCMTOOLS_COPYRIGHT_TEXT);
    }
}
