import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.europe.austria.ccm.local.*;
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

       	    world.europe.austria.ccm.remote.TestHomeDeployment.deploy("myTestHome");
	    TestHomeClientLibDeployment.deploy("myTestHome");
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
	    ccm.local.Components.HomeFinder homeFinder = ccm.local.HomeFinder.instance();
            TestHome home = (TestHome) homeFinder.find_home_by_name("myTestHome");
            Test component = home.create();
            component.configuration_complete();
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
	    TestHomeClientLibDeployment.undeploy("myTestHome");
	    world.europe.austria.ccm.remote.TestHomeDeployment.undeploy("myTestHome");
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
