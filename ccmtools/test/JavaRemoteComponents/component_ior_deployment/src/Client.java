import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import ccm.local.ServiceLocator;
import Components.ccm.local.HomeFinder;

import java.util.logging.*;

public class Client
{
    public static void main(String[] args)
    {
	System.out.println("component ior deployment test case:");

	// Configure Logger
	Logger logger = Logger.getLogger("test");
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
	    if(args.length == 0)
	    {
		TestHomeDeployment.deploy("TestHome");
	    }
	    else
	    {
		// Set up the ServiceLocator singleton
		ORB orb = ORB.init(args, null);
		ServiceLocator.instance().setCorbaOrb(orb);

		// Deploy remote component and return an IOR that represents
		// a CORBA reference to the component's home object.
		String ior = world.ccm.remote.TestHomeDeployment.deployToIor();
		
		// Deploy a client library component, that uses a given IOR
		// to connect to the remote component's home, and register
		// the local component's home to the local HomeFinder.
		TestHomeClientLibDeployment.deployWithIor("TestHome", ior);

		System.out.println("> Server is running...");
		// orb.run();
	    }
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
	    HomeFinder homeFinder = ccm.local.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

            IFace port = component.provide_port();

	    String s = "1234567890";
	    int size = port.foo(s);
	    assert(s.length() == size);
	    component.remove();
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
	    if(args.length == 0)
	    {
		TestHomeDeployment.undeploy("TestHome");
	    }
	    else
	    {
 		TestHomeClientLibDeployment.undeploy("TestHome");
		// Note that we don't undeploy the remote component 
		// because we have used its IOR instead of a naming 
		// service or remote HomeFinder.
	    }
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
