import world.ccm.local.*;
import ccm.local.Components.*;

import java.util.logging.*;

public class Client
{
	public static void main(String[] args)
	{

	    // Configure Logger
	    Logger logger = Logger.getLogger("ccm.local");
	    logger.setLevel(Level.FINE);	    
	    Handler handler = new ConsoleHandler();
	    handler.setLevel(Level.ALL);
	    handler.setFormatter(new ccm.local.MinimalFormatter());
	    logger.addHandler(handler);
	    ccm.local.ServiceLocator.instance().setLogger(logger);

		// Deploy local Java component
		try
		{
			TestHomeDeployment.deploy("myTest");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Use local Java component
		try
		{
			TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("myTest");

			Test component = home.create();

			System.out.println("Supported Interface Simple Test...");
			{
       				String msg = "Hello World!";
				int len = component.op1(msg);
				assert(len == msg.length());
       			}
			System.out.println("OK!");
			component.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Undeploy local Java component
			TestHomeDeployment.undeploy("myTest");
		}
	}
}
