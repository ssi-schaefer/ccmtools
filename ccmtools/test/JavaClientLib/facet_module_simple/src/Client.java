import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.ccm.local.*;
import ccm.local.ServiceLocator;


public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	if(args.length == 0)
	{
	    System.out.println("Client using a local CCM component (test mode)");
	    isTest = true;
	}
	else
	{
	    System.out.println("Client using a clientlib to access the remote CCM component");
	    isTest = false;
	}


        try {
            // Deploy ClientLib component
	    if(isTest)
	    {
		TestHomeDeployment.deploy("TestHome:1.0");
	    }
	    else
	    {
		// Set up the ServiceLocator singleton
		ORB orb = ORB.init(args, null);
		ServiceLocator.instance().setCorbaOrb(orb);

		TestHomeClientLibDeployment.deploy("TestHome:1.0");
	    }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	
	try
	{
	    System.out.println("Client");
	    
	    ccm.local.Components.HomeFinder homeFinder = 
		ccm.local.HomeFinder.instance();
	    TestHome home = 
		(TestHome) homeFinder.find_home_by_name("TestHome:1.0");
	    
	    Test component = home.create();	    
	    component.configuration_complete();
	    
	    I2 iface = component.provide_my_facet();
	    
	    {
		String s = "1234567890";
		int size = iface.op1(s);
		assert(s.length() == size);
	    }
	    
	    component.remove();
	    System.out.println("OK!");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    // Undeploy ClientLib component
	    if(isTest)
	    {
		TestHomeDeployment.undeploy("TestHome:1.0");
	    }
	    else
	    {
		TestHomeClientLibDeployment.undeploy("TestHome:1.0");

		// Tear down the ServiceLocator singleton
		ServiceLocator.instance().destroy();
	    }
	}
    }
}
