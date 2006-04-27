import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import ccm.local.ServiceLocator;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	System.out.println("Test Client");

	// Configure Logger
	Logger logger = Logger.getLogger("ccm.local");
	logger.setLevel(Level.FINER);
	Handler handler = new ConsoleHandler();
	handler.setLevel(Level.ALL);
	handler.setFormatter(new ccm.local.MinimalFormatter());
	logger.addHandler(handler);
	ccm.local.ServiceLocator.instance().setLogger(logger);
	
        try 
	{
	    /**
	     * Server-side code (Part 1)
	     */

	    // Set up the ServiceLocator singleton
	    ORB orb = ORB.init(args, null);
	    ServiceLocator.instance().setCorbaOrb(orb);
       	    world.ccm.remote.TestHomeDeployment.deploy("TestHome");
	    System.out.println("> Server is running...");
	    // orb.run();
	}
        catch(Exception e) 
        {
            e.printStackTrace();
        }


        try 
	{
	    /**
	     * Client-side code (co-located with clientlib)
	     **/
	    TestHomeClientLibDeployment.deploy("TestHome");
	    ccm.local.Components.HomeFinder homeFinder = ccm.local.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

	    System.out.println("Component Dynamic Provides Test...");
	    {
		IFace iface = (IFace)component.provide_facet("port");
		String s = "1234567890";
		int size = iface.foo(s);
		assert(s.length() == size);
	    }
	    System.out.println("OK!");

	    component.remove();
	    TestHomeClientLibDeployment.undeploy("TestHome");
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }

	
	try
	{

	    /**
	     * Server-side code (Part 2)
	     */
	    world.ccm.remote.TestHomeDeployment.undeploy("TestHome");
	    System.out.println("OK!");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{

	    // Tear down the ServiceLocator singleton
	    ServiceLocator.instance().destroy();
	}
    }
}
