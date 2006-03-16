import java.util.List;
import java.util.ArrayList;

import world.*;
import world.europe.ccm.local.*;
import world.europe.austria.ccm.local.*;

import ccm.local.Components.*;
import java.util.logging.*;

public class Client
{
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

		// Deploy local Java component
		try
		{
			TestHomeDeployment.deploy("TestHome");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Use local Java component
		try
		{
			TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
			Test component = home.create();

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
			    world.Color value = world.Color.blue;
			    userType.color_value(value);
			    world.Color result = userType.color_value();
			    assert(result == value);
			}
			{
			    // struct Person { long id; string name; }
			    world.Person value =
				new world.Person(3, "Egon");
			    userType.person_value(value);
			    world.Person result = userType.person_value();
			    assert(result.id == value.id);
			    assert(result.name.equals(value.name));
			}
			{
			    // struct Address { string street; long number; Person resident; }
			    String street = "Waltendorf";
			    int number = 7;
			    world.Person resident =
				new world.Person(3, "Egon");
			    world.Address value =
				new world.Address(street, number, resident);
			    userType.address_value(value);
			    world.Address result = userType.address_value();
			    assert(result.street.equals(value.street));
			    assert(result.number == value.number);
			    assert(result.resident.id == value.resident.id);
			    assert(result.resident.name.equals(value.resident.name));
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
			    List<world.Person> value =
				new ArrayList<world.Person>(10);
			    for(int i = 0; i< value.size(); i++)
			    {
				value.add(new world.Person(i, "Andrea"));
			    }
			    userType.personList_value(value);
			    List<world.Person> result =
				userType.personList_value();
			    for(int i = 0; i<result.size(); i++)
			    {
				assert(result.get(i).id == value.get(i).id);
				assert(result.get(i).name.equals(value.get(i).name));
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
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Undeploy local Java component
			TestHomeDeployment.undeploy("TestHome");
		}
	}
}
