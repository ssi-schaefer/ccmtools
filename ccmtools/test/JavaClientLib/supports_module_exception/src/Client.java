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
	    
            // Register homes to the HomeFinder
            ccm.local.HomeFinder.instance()
		.register_home(TestHomeFactory.create(),"myTestHome");
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

	    try
	    {
		int result = component.print("0123456789");
		assert(result == 10);
	    }
	    catch(Exception e)
	    {
		e.printStackTrace();
		assert(false);
	    }
	    

	    try
	    {
		int result = component.print("SimpleError");
		assert(false);
	    }
	    catch(world.europe.austria.SimpleError e)
	    {
		System.out.println("catched SimpleError!!");
		for(int i = 0; i < e.info.length; i++)
		{
		    System.out.println(e.info[i].code + ": " + 
				       e.info[i].message);
		}
	    }
	    

	    try
	    {
		int result = component.print("SuperError");
		assert(false);
	    }
	    catch(world.europe.austria.SuperError e)
	    {
		System.out.println("catched SuperError!!");
	    }


	    try
	    {
		int result = component.print("FatalError");
		assert(false);
	    }
	    catch(world.europe.austria.FatalError e)
	    {
		System.out.println("catched FatalError!!");
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
	    // Unregister homes from the HomeFinder
	    ccm.local.HomeFinder.instance().unregister_home("myTestHome");

	    // Tear down the ServiceLocator singleton
	    ServiceLocator.instance().destroy();	
	}
    }
}
