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
			    world.Color value = world.Color.blue;
			    component.color_value(value);
			    world.Color result = component.color_value();
			    assert(result == value);
			}
			{
			    // struct Person { long id; string name; }
			    world.Person value =
				new world.Person(3, "Egon");
			    component.person_value(value);
			    world.Person result = component.person_value();
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
			    component.address_value(value);
			    world.Address result = component.address_value();
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
			    List<world.Person> value =
				new ArrayList<world.Person>(10);
			    for(int i = 0; i< value.size(); i++)
			    {
				value.add(new world.Person(i, "Andrea"));
			    }
			    component.personList_value(value);
			    List<world.Person> result =
				component.personList_value();
			    for(int i = 0; i<result.size(); i++)
			    {
				assert(result.get(i).id == value.get(i).id);
				assert(result.get(i).name.equals(value.get(i).name));
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
