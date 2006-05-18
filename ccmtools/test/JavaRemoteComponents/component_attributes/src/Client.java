import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import world.europe.austria.ccm.local.*;
import ccm.local.ServiceLocator;
import Components.ccm.local.HomeFinder;

import java.util.List;
import java.util.ArrayList;

import java.util.logging.*;

public class Client
{
    private static boolean isTest;

    public static void main(String[] args)
    {
	System.out.println("component attributes test case:");

	// Configure Logger
	Logger logger = Logger.getLogger("test");
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
            if(args.length == 0)
            {
                TestHomeDeployment.deploy("TestHome");
            }
            else
            {
		// Set up the ServiceLocator singleton
		ORB orb = ORB.init(args, null);
		ServiceLocator.instance().setCorbaOrb(orb);
		world.europe.austria.ccm.remote.TestHomeDeployment.deploy("TestHome");
		TestHomeClientLibDeployment.deploy("TestHome");
		System.out.println("> Server is running...");
		// orb.run();
	    }
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
            TestHome home = (TestHome) homeFinder.find_home_by_name("TestHome");
            Test component = home.create();
            component.configuration_complete();

	    /*
	     * Supported Interface Attribute Test Cases
	     */
	    
	    System.out.println("Supported Interface Attributes (Basic Types) Test...");
	    {
		short value = -7;
		component.short_value(value);
		short result = component.short_value();
		assert(result == value);
	    }
	    {
		int value = -7777;
		component.long_value(value);
		int result = component.long_value();
		assert(result == value);
	    }
	    {
		short value = 7;
		component.ushort_value(value);
		short result = component.ushort_value();
		assert(result == value);
	    }
	    {
		int value = 7777;
		component.ulong_value(value);
		int result = component.ulong_value();
		assert(result == value);
	    }
	    {
		float value = (float)-77.77;
		component.float_value(value);
		float result = component.float_value();
		assert(result == value);
	    }
	    {
		double value = -77.7777;
		component.double_value(value);
		double result = component.double_value();
		assert(result == value);
	    }
	    {
		char value = 'x';
		component.char_value(value);
		char result = component.char_value();
		assert(result == value);
	    }
	    {
		String value = "0123456789";
		component.string_value(value);
		String result = component.string_value();
		assert(result.equals(value));
	    }
	    {
		boolean value = true;
		component.boolean_value(value);
		boolean result = component.boolean_value();
		assert(result == value);
	    }
	    {
		byte value = (byte)0xff;
		component.octet_value(value);
		byte result = component.octet_value();
		assert(result == value);
	    }
	    System.out.println("OK!");
	    
	    
	    
	    System.out.println("Supported Interface Attributes (User Types) Test...");
	    {
		// enum Color {red, green, blue, black, orange}
		Color value = Color.blue;
		component.color_value(value);
		Color result = component.color_value();
		assert(result == value);
	    }
	    
	    {
		// struct Person { long id; string name; }
		Person value = new Person(3, "Egon");
		component.person_value(value);
		Person result = component.person_value();
		assert(result.getId() == value.getId());
		assert(result.getName().equals(value.getName()));
	    }
	    
	    {
		// struct Address { string street; long number; Person resident; }
		String street = "Waltendorf";
		int number = 7;
		Person resident = new Person(3, "Egon");
		Address value = new Address(street, number, resident);
		component.address_value(value);
		Address result = component.address_value();
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
		component.longList_value(value);
		List<Integer> result = component.longList_value();
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
		component.stringList_value(value);
		List<String> result = component.stringList_value();
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
		component.personList_value(value);
		List<Person> result = component.personList_value();
		for(int i = 0; i<result.size(); i++)
		{
		    assert(result.get(i).getId() == value.get(i).getId());
		    assert(result.get(i).getName().equals(value.get(i).getName()));
		}
	    }
	    
	    {
		// typedef long time_t;
		int value = -7777;
		component.time_t_value(value);
		int result = component.time_t_value();
		assert(result == value);
	    }

	    /*
                {
                    // typedef long LongArray[10]
                    int length = 10;
                    int[] value = new int[length];
                    for(int i = 0; i< value.length; i++)
                    {
                        value[i] = i;
                    }
                    component.longArray_value(value);
                    int[] result = component.longArray_value();
                    for(int i = 0; i<result.length; i++)
                    {
                        assert(result[i] == value[i]);
                    }
                }

                {
                    // typedef string StringArray[10]
                    int length = 10;
                    String[] value = new String[length];
                    for(int i = 0; i< value.length; i++)
                    {
                        value[i] = "Egon";
                    }
                    component.stringArray_value(value);
                    String[] result = component.stringArray_value();
                    for(int i = 0; i<result.length; i++)
                    {
                        assert(result[i].equals(value[i]));
                    }
                }

                {
                    // typedef Person PersonArray[10]
                    Person[] value = new Person[10];
                    for(int i = 0; i< value.length; i++)
                    {
                        value[i] = new Person(i, "Andrea");
                    }
                    component.personArray_value(value);
                    Person[] result = component.personArray_value();
                    for(int i = 0; i < result.length; i++)
                    {
                        assert(result[i].getId() == value[i].getId());
                        assert(result[i].getName().equals(value[i].getName()));
                    }
                }
	    */
	    
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
	    if(args.length == 0)
            {
                TestHomeDeployment.undeploy("TestHome");
            }
            else
            {	
		TestHomeClientLibDeployment.undeploy("TestHome");
		world.europe.austria.ccm.remote.TestHomeDeployment.undeploy("TestHome");
	    }
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
