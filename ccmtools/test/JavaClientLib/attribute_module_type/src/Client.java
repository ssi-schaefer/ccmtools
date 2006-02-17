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
	    component.connect_outBasicType(new client.myBasicTypeImpl());
	    component.connect_outUserType(new client.myUserTypeImpl());	    
	    component.configuration_complete();

	    /*
	     * Component Attribute Test Cases
	     */
	    
	    System.out.print("Component Attributes (Basic Types) Test...");
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
	    
	    
	    System.out.print("Component Attributes (User Types) Test...");
	    {
		// enum Color {red, green, blue, black, orange}
		world.europe.austria.Color value = world.europe.austria.Color.blue;
		component.color_value(value);     
		world.europe.austria.Color result = component.color_value();
		assert(result == value);
	    }	    
	    {
		// struct Person { long id; string name; }
		world.europe.austria.Person value = 
		    new world.europe.austria.Person(3, "Egon");
		component.person_value(value); 
		world.europe.austria.Person result = component.person_value();
		assert(result.id == value.id);
		assert(result.name.equals(value.name));
	    }
	    {
		// struct Address { string street; long number; Person resident; }
		String street = "Waltendorf";
		int number = 7;
		world.europe.austria.Person resident = 
		    new world.europe.austria.Person(3, "Egon");
		world.europe.austria.Address value = 
		    new world.europe.austria.Address(street, number, resident);
		component.address_value(value); 
		world.europe.austria.Address result = component.address_value();
		assert(result.street.equals(value.street));
		assert(result.number == value.number);
		assert(result.resident.id == value.resident.id);
		assert(result.resident.name.equals(value.resident.name));
	    }
	    {
		// typedef sequence<long> LongList
		int[] value = new int[10];
		for(int i = 0; i< value.length; i++)
		{
		    value[i] = i;
		}
		component.longList_value(value);
		int[] result = component.longList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i] == value[i]);
		}    
	    }
	    {
		// typedef sequence<string> StringList
		String[] value = new String[10];
		for(int i = 0; i< value.length; i++)
		{
		    value[i] = "Egon";
		}
		component.stringList_value(value);
		String[] result = component.stringList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i].equals(value[i]));
		}    	
	    }
	    {
		// typedef sequence<Person> PersonList
		world.europe.austria.Person[] value = 
		    new world.europe.austria.Person[10];
		for(int i = 0; i< value.length; i++)
		{
		    world.europe.austria.Person p = 
			new world.europe.austria.Person(i, "Andrea");
		    value[i] = p;
		}
		component.personList_value(value);
		world.europe.austria.Person[] result = 
		    component.personList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i].id == value[i].id);
		    assert(result[i].name.equals(value[i].name));
		}    	
	    }
	    {
		// typedef long time_t;
		int value = -7777;
		component.time_t_value(value);
		int result = component.time_t_value();
		assert(result == value);
	    }
	    System.out.println("OK!");



	    /*
	     * Facet Attribute Test Cases
	     */

	    BasicTypeInterface basicType = component.provide_inBasicType();
	    System.out.print("Facet Attributes (Basic Types) Test...");
	    
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
	    UserTypeInterface userType = component.provide_inUserType();
	    {
		// enum Color {red, green, blue, black, orange}
		world.europe.austria.Color value = world.europe.austria.Color.blue;
	        userType.color_value(value);     
		world.europe.austria.Color result = userType.color_value();
		assert(result == value);
	    }	    
	    {
		// struct Person { long id; string name; }
		world.europe.austria.Person value = 
		    new world.europe.austria.Person(3, "Egon");
		userType.person_value(value); 
		world.europe.austria.Person result = userType.person_value();
		assert(result.id == value.id);
		assert(result.name.equals(value.name));
	    }
	    {
		// struct Address { string street; long number; Person resident; }
		String street = "Waltendorf";
		int number = 7;
		world.europe.austria.Person resident = 
		    new world.europe.austria.Person(3, "Egon");
		world.europe.austria.Address value = 
		    new world.europe.austria.Address(street, number, resident);
		userType.address_value(value); 
		world.europe.austria.Address result = userType.address_value();
		assert(result.street.equals(value.street));
		assert(result.number == value.number);
		assert(result.resident.id == value.resident.id);
		assert(result.resident.name.equals(value.resident.name));
	    }
	    {
		// typedef sequence<long> LongList
		int[] value = new int[10];
		for(int i = 0; i< value.length; i++)
		{
		    value[i] = i;
		}
		userType.longList_value(value);
		int[] result = userType.longList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i] == value[i]);
		}    
	    }
	    {
		// typedef sequence<string> StringList
		String[] value = new String[10];
		for(int i = 0; i< value.length; i++)
		{
		    value[i] = "Egon";
		}
		userType.stringList_value(value);
		String[] result = userType.stringList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i].equals(value[i]));
		}    	
	    }
	    {
		// typedef sequence<Person> PersonList
		world.europe.austria.Person[] value = 
		    new world.europe.austria.Person[10];
		for(int i = 0; i< value.length; i++)
		{
		    world.europe.austria.Person p = 
			new world.europe.austria.Person(i, "Andrea");
		    value[i] = p;
		}
		userType.personList_value(value);
		world.europe.austria.Person[] result = 
		    userType.personList_value(); 
		for(int i = 0; i<result.length; i++)
		{
		    assert(result[i].id == value[i].id);
		    assert(result[i].name.equals(value[i].name));
		}    	
	    }
	    {
		// typedef long time_t;
		int value = -7777;
		userType.time_t_value(value);
		int result = userType.time_t_value();
		assert(result == value);
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
