import europe.ccm.local.*;
import america.ccm.local.*;
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
			 * Facet Inheritance Test Cases
			 */

			System.out.println(">> Facet inheritance test ...");
			SubType iface = component.provide_iface();
			
			{
			    int value = 1;
			    iface.attr1(value);
			    int result = iface.attr1();
			    assert(value == result);
			}
			{
			    int value = 2;
			    iface.attr2(value);
			    int result = iface.attr2();
			    assert(value == result);
			}
			{
			    int value = 3;
			    iface.attr3(value);
			    int result = iface.attr3();
			    assert(value == result);
			}


			{
			    String s = "1234567890";
			    int size = iface.op1(s);
			    assert(s.length() == size);
			}
			
			{
			    String s = "1234567890";
			    int size = iface.op2(s);
			    assert(s.length() == size);
			}
			
			{
			    String s = "1234567890";
			    int size = iface.op3(s);
			    assert(s.length() == size);
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
