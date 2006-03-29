import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.europe.austria.ccm.local.*;
import ccm.local.ServiceLocator;


public class Client
{
    public static void main(String[] args)
    {
        ORB orb = ORB.init(args, null);
        try {
	    // Set up the ServiceLocator singleton
            ServiceLocator.instance().setCorbaOrb(orb);
	    
	    // Deploy ClientLib component
	    TestHomeClientLibDeployment.deploy("myTestHome");
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
		(TestHome) homeFinder.find_home_by_name("myTestHome");

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
	    System.out.println("OK!");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    // Undeploy ClientLib component
	    TestHomeClientLibDeployment.undeploy("myTestHome");

	    // Tear down the ServiceLocator singleton
	    ServiceLocator.instance().destroy();	
	}
    }
}
