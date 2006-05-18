import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import europe.ccm.local.*;
import america.ccm.local.*;
import world.ccm.local.*;
import ccm.local.ServiceLocator;
import Components.ccm.local.HomeFinder;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	System.out.println("supports inheritance test case:");

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
		world.ccm.remote.TestHomeDeployment.deploy("TestHome");
		TestHomeClientLibDeployment.deploy("TestHome");
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
	    
	    
	    /*
	     * Supported Interface Inheritance Test Cases
	     */
	    
	    System.out.println("Supported Interface Inheritance Test ...");
	    {
		int value = 1;
		component.attr1(value);
		int result = component.attr1();
		assert(value == result);
	    }
	    {
		int value = 2;
		component.attr2(value);
		int result = component.attr2();
		assert(value == result);
	    }
	    {
		int value = 3;
		component.attr3(value);
		int result = component.attr3();
		assert(value == result);
	    }
	    
	    {
		String s = "1234567890";
		int size = component.op1(s);
		assert(s.length() == size);
	    }
	    {
		String s = "1234567890";
		int size = component.op2(s);
		assert(s.length() == size);
	    }
	    {
		String s = "1234567890";
		int size = component.op3(s);
		assert(s.length() == size);
	    }
	    
	    System.out.println("OK!");

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
		world.ccm.remote.TestHomeDeployment.undeploy("TestHome");
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
