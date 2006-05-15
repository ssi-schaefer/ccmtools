import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.ccm.local.*;
import world.europe.ccm.local.*;
import world.europe.austria.ccm.local.*;
import ccm.local.*;

import ccm.local.ServiceLocator;
import Components.ccm.local.HomeFinder;

import java.util.logging.*;
import java.util.List;
import java.util.ArrayList;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	System.out.println("Test Client");

	// Configure Logger
	Logger logger = Logger.getLogger("ccm.local");
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

	    // Set up the ServiceLocator singleton
	    ORB orb = ORB.init(args, null);
	    ServiceLocator.instance().setCorbaOrb(orb);

       	    world.europe.austria.ccm.remote.TestHomeDeployment.deploy("myTestHome");
	    TestHomeClientLibDeployment.deploy("myTestHome");
	    System.out.println("> Server is running...");
	    // orb.run();
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
            TestHome home = (TestHome) homeFinder.find_home_by_name("myTestHome");
            Test component = home.create();
            component.configuration_complete();

	    /*
	     * Facet Attribute Test Cases
	     */
	    
	    System.out.print("Facet Attributes (Basic Types) Test...");
	    BasicTypeInterface basicType = component.provide_basicType();
	    {
		short value = -7;
		basicType.short_value(value);
		short result = basicType.short_value();
		assert(result == value);
	    }
	    {
		int value = -7777;
		basicType.long_value(value);
		int result = basicType.long_value();
		assert(result == value);
	    }
	    {
		short value = 7;
		basicType.ushort_value(value);
		short result = basicType.ushort_value();
		assert(result == value);
	    }
	    {
		int value = 7777;
		basicType.ulong_value(value);
		int result = basicType.ulong_value();
		assert(result == value);
	    }
	    {
		float value = (float)-77.77;
		basicType.float_value(value);
		float result = basicType.float_value();
		assert(result == value);
	    }
	    {
		double value = -77.7777;
		basicType.double_value(value);
		double result = basicType.double_value();
		assert(result == value);
	    }
	    {
		char value = 'x';
		basicType.char_value(value);
		char result = basicType.char_value();
		assert(result == value);
	    }
	    {
		String value = "0123456789";
		basicType.string_value(value);
		String result = basicType.string_value();
		assert(result.equals(value));
	    }
	    {
		boolean value = true;
		basicType.boolean_value(value);
		boolean result = basicType.boolean_value();
		assert(result == value);
	    }
	    {
		byte value = (byte)0xff;
		basicType.octet_value(value);
		byte result = basicType.octet_value();
		assert(result == value);
	    }
	    System.out.println("OK!");
	    

	    System.out.print("Facet Attributes (User Types) Test...");
	    UserTypeInterface userType = component.provide_userType();
	    {
		// enum Color {red, green, blue, black, orange}
		Color value = Color.blue;
		userType.color_value(value);
		Color result = userType.color_value();
		assert(result == value);
	    }
	    {
		// struct Person { long id; string name; }
		Person value = new Person(3, "Egon");
		userType.person_value(value);
		Person result = userType.person_value();
		assert(result.getId() == value.getId());
		assert(result.getName().equals(value.getName()));
	    }
	    {
		// struct Address { string street; long number; Person resident; }
		String street = "Waltendorf";
		int number = 7;
		Person resident = new Person(3, "Egon");
		Address value = new Address(street, number, resident);
		userType.address_value(value);
		Address result = userType.address_value();
		assert(result.getStreet().equals(value.getStreet()));
		assert(result.getNumber() == value.getNumber());
		assert(result.getResident().getId() == value.getResident().getId());
		assert(result.getResident().getName().equals(value.getResident().getName()));
	    }
	    
	    {
		// typedef sequence<long> LongList
		List<Integer> value = new ArrayList<Integer>(10);
		for(int i = 0; i< value.size(); i++)
		{
		    value.add(i);
		}
		userType.longList_value(value);
		List<Integer> result = userType.longList_value();
		for(int i = 0; i<result.size(); i++)
		{
		    assert(result.get(i) == value.get(i));
		}
	    }
	    {
		// typedef sequence<string> StringList
		List<String> value = new ArrayList<String>(10);
		for(int i = 0; i< value.size(); i++)
		{
		    value.add("Egon");
		}
		userType.stringList_value(value);
		List<String> result = userType.stringList_value();
		for(int i = 0; i<result.size(); i++)
		{
		    assert(result.get(i).equals(value.get(i)));
		}
	    }
	    
	    {
		// typedef sequence<Person> PersonList
		List<Person> value = new ArrayList<Person>(10);
		for(int i = 0; i< value.size(); i++)
		{
		    value.add(new Person(i, "Andrea"));
		}
		userType.personList_value(value);
		List<Person> result = userType.personList_value();
		for(int i = 0; i<result.size(); i++)
		{
		    assert(result.get(i).getId() == value.get(i).getId());
		    assert(result.get(i).getName().equals(value.get(i).getName()));
		}
	    }
	    
	    {
		// typedef long time_t;
		int value = -7777;
		userType.time_t_value(value);
		int result = userType.time_t_value();
		assert(result == value);
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
	    TestHomeClientLibDeployment.undeploy("myTestHome");
	    world.europe.austria.ccm.remote.TestHomeDeployment.undeploy("myTestHome");
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
