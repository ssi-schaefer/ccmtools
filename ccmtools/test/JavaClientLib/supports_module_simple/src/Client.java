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

	    {
		String s = "1234567890";
		int size = component.op1(s);
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
	    TestHomeClientLibDeployment.undeploy("myTestHome");

	    // Tear down the ServiceLocator singleton
	    ServiceLocator.instance().destroy();
	}
    }
}
