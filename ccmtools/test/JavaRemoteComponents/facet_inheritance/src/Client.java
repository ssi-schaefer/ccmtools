import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import europe.ccm.local.*;
import america.ccm.local.*;
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

       	    world.ccm.remote.TestHomeDeployment.deploy("myTestHome");
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
	    world.ccm.remote.TestHomeDeployment.undeploy("myTestHome");
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
