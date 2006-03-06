import world.ccm.local.*;
import ccm.local.Components.*;

import java.util.logging.*;

public class DynamicClient
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
			TestHomeDeployment.deploy("TestHome");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Use local Java component
		try
		{
			TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
			Test component = home.create();

			System.out.println("Component Dynamic Provides Test...");

			IFace iface = (IFace)component.provide_facet("inPort");
			{
			    String s = "CCM Tools";
			    int result = iface.op1(s);
			    assert(result == s.length());
			}

			System.out.println("OK!");

			component.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Undeploy local Java component
		TestHomeDeployment.undeploy("TestHome");
	}
}
