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
	    component.connect_voidTypeOut(new wamas.myVoidTypeImpl());
	    component.connect_basicTypeOut(new wamas.myBasicTypeImpl()); 
	    component.connect_userTypeOut(new wamas.myUserTypeImpl());
	    component.configuration_complete();

	    
	    /*
	     * Test VoidTypeInterface facet
	     */
	    VoidTypeInterface voidType = component.provide_voidTypeIn();
	    {
		voidType.f1(7);
	    }

	    {
		int result = voidType.f2();
		assert(result == 7);
	    }

	    

	    /*
	     * Test BasicTypeInterface facet
	     */
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


	    /*
	     * Test UserTypeInterface facet
	     */
	    UserTypeInterface userType = component.provide_userTypeIn();
	    {
		Person p1 = new Person(3, "Egon");
		PersonHolder p2 = new PersonHolder(new Person(23, "Andrea"));
		PersonHolder p3 = new PersonHolder();
		
		Person result = userType.f2(p1, p2, p3);
		
		assert(p3.value.name.equals("Andrea"));
		assert(p3.value.id == 23);
		assert(p2.value.name.equals("Egon"));
		assert(p2.value.id == 3);
		assert(result.name.equals("EgonAndrea"));
		assert(result.id == 3+23);
	    }
	    
	    {
		String[] p1 = {"Egon0", "Egon1", "Egon2"};
		String[] sa2 = {"Andrea0", "Andrea1", "Andrea2"};
		StringListHolder p2 = new StringListHolder(sa2);
		StringListHolder p3 = new StringListHolder();
		
		String[] result = userType.f5(p1, p2, p3);
		
		assert(p3.value[0].equals("Andrea0"));
		assert(p3.value[1].equals("Andrea1"));
		assert(p3.value[2].equals("Andrea2"));
		
		assert(p2.value[0].equals("Egon0"));
		assert(p2.value[1].equals("Egon1"));
		assert(p2.value[2].equals("Egon2"));
		
		assert(result[0].equals("Test"));
		assert(result[1].equals("Test"));
		assert(result[2].equals("Test"));
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
