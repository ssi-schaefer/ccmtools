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
	    component.connect_outVoidType(new client.myVoidTypeImpl());
	    component.connect_outBasicType(new client.myBasicTypeImpl());
	    component.connect_outUserType(new client.myUserTypeImpl());
	    component.configuration_complete();




	    component.disconnect_outUserType();
	    component.disconnect_outBasicType();
	    component.disconnect_outVoidType();
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
