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
			catch(world.europe.austria.ErrorException e)
			{
			    e.printStackTrace();
			    assert(false);
			}

			try
			{
			    int result = iface.foo("Error");
			    assert(false);
			}
			catch(world.europe.austria.ErrorException e)
			{
			    System.out.println("!!catched ErrorException");
			    for(int i = 0; i < e.info.length; i++)
			    {
				System.out.println(e.info[i].code + ": " +
						   e.info[i].message);
			    }
			}

			try
			{
			    int result = iface.foo("SuperError");
			    assert(false);
			}
			catch(world.europe.austria.SuperError e)
			{
			    System.out.println("!!catched SuperError");
			}


			try
			{
			    int result = iface.foo("FatalError");
			    assert(false);
			}
			catch(world.europe.austria.FatalError e)
			{
			    System.out.println("!!catched FatalError");
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
