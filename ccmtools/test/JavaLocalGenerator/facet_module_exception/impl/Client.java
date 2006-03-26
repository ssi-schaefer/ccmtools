import world.europe.austria.ccm.local.*;

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

			/*
			 * Facet Exception Test Cases
			 */

			System.out.println(">> Facet exception test ...");
			IFace iface = component.provide_iface();
			
			try
			{
			    int result = iface.foo("0123456789");
			    assert(result == 10);
			}
			catch(ErrorException e)
			{
			    e.printStackTrace();
			    assert(false);
			}

			try
			{
			    int result = iface.foo("Error");
			    assert(false);
			}
			catch(ErrorException e)
			{
			    System.out.println("catched: " + e.getMessage());
			    for(int i = 0; i < e.getInfo().size(); i++)
			    {
				System.out.println(e.getInfo().get(i).getCode() + ": " +
						   e.getInfo().get(i).getMessage());
			    }
			}

			try
			{
			    int result = iface.foo("SuperError");
			    assert(false);
			}
			catch(SuperError e)
			{
			    System.out.println("catched: " + e.getMessage());
			}


			try
			{
			    int result = iface.foo("FatalError");
			    assert(false);
			}
			catch(FatalError e)
			{
			    System.out.println("catched: " + e.getMessage());
			}
			
			System.out.println(">> OK!");

			component.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Undeploy local Java component
			TestHomeDeployment.undeploy("TestHome");
		}
	}
}
