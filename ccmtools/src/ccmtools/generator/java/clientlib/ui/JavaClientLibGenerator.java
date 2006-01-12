package ccmtools.generator.java.clientlib.ui;

import java.io.IOException;
import java.util.logging.Logger;

import ccmtools.CcmtoolsException;
import ccmtools.UI.Driver;

public class JavaClientLibGenerator
{
	/** UI driver for generator messages */
	protected Driver uiDriver;
	
	/** Command line parameters */
	protected JavaClientLibParameters parameters;
	
	/** Java standard logger object */
	protected Logger logger;
	
	
	public JavaClientLibGenerator(CommandLineParameters parameters, Driver uiDriver)
	{
		this.uiDriver = uiDriver;
		this.parameters = (JavaClientLibParameters)parameters;
		
        logger = Logger.getLogger("ccm.generator.java.clientlib");
        logger.fine("JavaClientLibGenerator()");
	}
	
	
	public void generate()
		throws CcmtoolsException
	{
		
	}
	
}
