import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.europe.austria.ccm.local.*;
import ccm.local.ServiceLocator;

import java.util.logging.*;

public class Client
{
    private static final int NUMBER_OF_CALLS = 3;

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
	    
	    for(int i = 0; i< NUMBER_OF_CALLS; i++)
	    {
		System.out.println("  create, use and remove component #" + i);
		ccm.local.Components.HomeFinder homeFinder = 
		    ccm.local.HomeFinder.instance();
		TestHome home = 
		    (TestHome) homeFinder.find_home_by_name("myTestHome");
		
		Test component = home.create();
		component.connect_out_port(new client.myI2());
		// run the test in the component's ccm_activate() method
		component.configuration_complete(); 
		
		I2 iface = component.provide_in_port();
		
		{
		    String s = "Hello from the Java Client!";
		    int size = iface.op1(s);
		    assert(s.length() == size);
		}
		
		component.disconnect_out_port();
		component.remove();
	    }
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
