import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import wamas.Person;
import wamas.PersonHolder;
import wamas.StringListHolder;
import wamas.ccm.local.*;
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
	    System.out.println("testBasicTypeInterfaceReceptacle");
	    
	    ccm.local.Components.HomeFinder homeFinder = 
		ccm.local.HomeFinder.instance();
	    TestHome home = 
		(TestHome) homeFinder.find_home_by_name("myTestHome");

	    Test component = home.create();
	    component.connect_basicTypeOut(new myBasicTypeImpl()); 
	    component.configuration_complete();
	    
	    BasicTypeInterface basicType = component.provide_basicTypeIn();
	    
	    {
		int p1 = 7;
		IntHolder p2 = new IntHolder(3);
		IntHolder p3 = new IntHolder();
		
		int result = basicType.f2(p1, p2, p3);
		
		assert(p2.value == 7);
		assert(p3.value == 3);
		assert(result == 3 + 7);
	    }
	    
	    {
		String s1 = "sieben";
		StringHolder s2 = new StringHolder("drei");
		StringHolder s3 = new StringHolder();
		
		String result = basicType.f8(s1, s2, s3);
		
		assert(s2.value.equals("sieben"));
		assert(s3.value.equals("drei"));
		assert(result.equals("dreisieben"));
	    }
	    
	    component.disconnect_basicTypeOut();
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
