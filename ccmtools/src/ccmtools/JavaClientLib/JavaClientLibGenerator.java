package ccmtools.JavaClientLib;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import ccmtools.UI.Driver;

public class JavaClientLibGenerator
{
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Directory for generated output files */
	protected File outDir;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public JavaClientLibGenerator(Driver uiDriver, File outDir)
		throws IOException
	{
		this.uiDriver = uiDriver;
		this.outDir = outDir;
		
        logger = Logger.getLogger("ccm.generator.java.clientlib");
        logger.fine("JavaClientLibGenerator()");
	}
	
	
	
	
}
