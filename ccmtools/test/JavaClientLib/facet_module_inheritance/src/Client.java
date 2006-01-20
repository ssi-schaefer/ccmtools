import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.ccm.local.*;
import america.ccm.local.*;
import europe.ccm.local.*;
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
	    System.out.println("OK!");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	

       // Unregister homes from the HomeFinder
        ccm.local.HomeFinder.instance().unregister_home("myTestHome");
	System.exit(0);
    }
}
