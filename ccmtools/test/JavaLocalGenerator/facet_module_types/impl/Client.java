import world.europe.austria.ccm.local.*;
import ccm.local.Components.*;
import ccm.local.*;

import world.europe.austria.Color;
import world.europe.austria.Person;
import world.europe.austria.Address;

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

			System.out.println("Facet Void Types Test...");

			/*
			 * Test VoidTypeInterface facet
			 */
			VoidTypeInterface voidType = component.provide_voidType();
			{
				voidType.f1(7);
				int result = voidType.f2();
				assert(result == 7);
			}
			System.out.println("OK!");


			/*
			 * Test BasicTypeInterface facet
			 */
			System.out.println("Facet Basic Types Test...");

			BasicTypeInterface basicType = component.provide_basicType();
			{ // short
			    short p1 = 7;
			    Holder<Short> p2 = new Holder<Short>((short) 3);
			    Holder<Short> p3 = new Holder<Short>();
			    
			    short result = basicType.f1(p1, p2, p3);
			    
			    assert(p2.getValue() == 7);
			    assert(p3.getValue() == 3);
			    assert(result == 3 + 7);
			}

			{ // long
			    int p1 = 7;
			    Holder<Integer> p2 = new Holder<Integer>(3);
			    Holder<Integer> p3 = new Holder<Integer>();

			    int result = basicType.f2(p1, p2, p3);
			    
			    assert(p2.getValue() == 7);
			    assert(p3.getValue() == 3);
			    assert(result == 3 + 7);
			}

			{ // unsigned short
			    short p1 = 7;
			    Holder<Short> p2 = new Holder<Short>((short) 3);
			    Holder<Short> p3 = new Holder<Short>();
			    
			    short result = basicType.f3(p1, p2, p3);
			    
			    assert(p2.getValue() == 7);
			    assert(p3.getValue() == 3);
			    assert(result == 3 + 7);
			}

			{ // unsigned long
			    int p1 = 7;
			    Holder<Integer> p2 = new Holder<Integer>(3);
			    Holder<Integer> p3 = new Holder<Integer>();
			    
			    int result = basicType.f4(p1, p2, p3);
			    
			    assert(p2.getValue() == 7);
			    assert(p3.getValue() == 3);
			    assert(result == 3 + 7);
			}

			{ // float
			    float p1 = (float) 7.0;
			    Holder<Float> p2 = new Holder<Float>((float) 3.0);
			    Holder<Float> p3 = new Holder<Float>();
			    
			    float result = basicType.f5(p1, p2, p3);
			    
			    assert(Math.abs(p2.getValue() - 7.0) < 0.001);
			    assert(Math.abs(p3.getValue() - 3.0) < 0.001);
			    assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
			}

			{ // double
			    double p1 = 7.0;
			    Holder<Double> p2 = new Holder<Double>(3.0);
			    Holder<Double> p3 = new Holder<Double>();
			    
			    double result = basicType.f6(p1, p2, p3);
			    
			    assert(Math.abs(p2.getValue() - 7.0) < 0.000001);
			    assert(Math.abs(p3.getValue() - 3.0) < 0.000001);
			    assert(Math.abs(result - (3.0 + 7.0)) < 0.000001);
			}

			{ // char
			    char p1 = (char) 7;
			    Holder<Character> p2 = new Holder<Character>((char) 3);
			    Holder<Character> p3 = new Holder<Character>();
			    
			    char result = basicType.f7(p1, p2, p3);
			    
			    assert(p2.getValue() == (char) 7);
			    assert(p3.getValue() == (char) 3);
			    assert(result == (char) (3 + 7));
			}

			{
			    String s1 = "sieben";
			    Holder<String> s2 = new Holder<String>("drei");
			    Holder<String> s3 = new Holder<String>();
			    
			    String result = basicType.f8(s1, s2, s3);
			    
			    assert(s2.getValue().equals("sieben"));
			    assert(s3.getValue().equals("drei"));
			    assert(result.equals("dreisieben"));
			}

			{ // boolean
			    boolean p1 = true;
			    Holder<Boolean> p2 = new Holder<Boolean>(false);
			    Holder<Boolean> p3 = new Holder<Boolean>();
			    
			    boolean result = basicType.f9(p1, p2, p3);
			    
			    assert(p2.getValue() == true);
			    assert(p3.getValue() == false);
			    assert(result == false && true);
			}

			{ // octet
			    byte p1 = (byte) 7;
			    Holder<Byte> p2 = new Holder<Byte>((byte) 3);
			    Holder<Byte> p3 = new Holder<Byte>();
			    
			    byte result = basicType.f10(p1, p2, p3);
			    
			    assert(p2.getValue() == (byte) 7);
			    assert(p3.getValue() == (byte) 3);
			    assert(result == (byte) (3 + 7));
			}
			System.out.println("OK!");


			/*
			 * Test UserTypeInterface facet
			 */
			System.out.println("Facet User Types Test...");

			UserTypeInterface userType = component.provide_userType();
			{ // enum Color {red, green, blue, black, orange}
			    Color p1 = Color.red;
			    Holder<Color> p2 = new Holder<Color>(Color.blue);
			    Holder<Color> p3 = new Holder<Color>();
			    
			    Color result = userType.f1(p1, p2, p3);
			    
			    assert(p2.getValue() == Color.red);
			    assert(p3.getValue() == Color.blue);
			    assert(result == Color.orange);
			}

			{ // struct Person { long id; string name; }
			    Person p1 = new Person(3, "Egon");
			    Holder<Person> p2 = new Holder<Person>(new Person(23, "Andrea"));
			    Holder<Person> p3 = new Holder<Person>();
			    
			    Person result = userType.f2(p1, p2, p3);
			    
			    assert(p3.getValue().name.equals("Andrea"));
			    assert(p3.getValue().id == 23);
			    assert(p2.getValue().name.equals("Egon"));
			    assert(p2.getValue().id == 3);
			    assert(result.name.equals("EgonAndrea"));
			    assert(result.id == 3 + 23);
			}

			{ // struct Address { string street; long number; Person resident;
			  // }
			    Person egon = new Person(3, "Egon");
			    Address p1 = new Address("Waltendorf", 7, egon);
			    
			    Person andrea = new Person(23, "Andrea");
			    Holder<Address> p2 = new Holder<Address>(new Address("Petersgasse", 17, andrea));
			    
			    Holder<Address> p3 = new Holder<Address>();
			    
			    Address result = userType.f3(p1, p2, p3);
			    
			    assert(p3.getValue().street.equals("Petersgasse"));
			    assert(p3.getValue().number == 17);
			    assert(p3.getValue().resident.name.equals("Andrea"));
			    assert(p3.getValue().resident.id == 23);
			    
			    assert(p2.getValue().street.equals("Waltendorf"));
			    assert(p2.getValue().number == 7);
			    assert(p2.getValue().resident.name.equals("Egon"));
			    assert(p2.getValue().resident.id == 3);
			    
			    assert(result.street.equals("WaltendorfPetersgasse"));
			    assert(result.number == 24);
			    assert(result.resident.name.equals("EgonAndrea"));
			    assert(result.resident.id == 26);
			}

			{ // typedef sequence<long> LongList
			    int[] p1 = new int[5];
			    int[] p2Value = new int[5];
			    for (int i = 0; i < 5; i++)
			    {
				p1[i] = i;
				p2Value[i] = i + i;
			    }

			    Holder<int[]> p2 = new Holder<int[]>(p2Value);
			    Holder<int[]> p3 = new Holder<int[]>();
			    
			    int[] result = userType.f4(p1, p2, p3);
			    
			    for (int i = 0; i < result.length; i++)
			    {
				assert(result[i] == i);
			    }
			    for (int i = 0; i < p2.getValue().length; i++)
			    {
				assert(p2.getValue()[i] == i);
			    }
			    for (int i = 0; i < p3.getValue().length; i++)
			    {
				assert(p3.getValue()[i] == i + i);
			    }
			}

			{ // typedef sequence<string> StringList
			    String[] p1 = new String[5];
			    String[] p2Value = new String[5];
			    for (int i = 0; i < 5; i++)
			    {
				p1[i] = "Egon";
				p2Value[i] = "Andrea";
			    }

			    Holder<String[]> p2 = new Holder<String[]>(p2Value);
			    Holder<String[]> p3 = new Holder<String[]>();
			    
			    String[] result = userType.f5(p1, p2, p3);

			    for (int i = 0; i < result.length; i++)
			    {
				assert(result[i].equals("Test"));
			    }
			    for (int i = 0; i < p2.getValue().length; i++)
			    {
				assert(p2.getValue()[i].equals("Egon"));
			    }
			    for (int i = 0; i < p3.getValue().length; i++)
			    {
				assert(p3.getValue()[0].equals("Andrea"));
			    }
			}

			{ // typedef sequence<Person> PersonList

			    Person[] p1 = new Person[5];
			    Person[] p2Value = new Person[5];
			    for (int i = 0; i < 5; i++)
			    {
				Person p = p1[i] = new Person(i, "Andrea");
				p2Value[i] = new Person(i + i, "Egon");
			    }
			    Holder<Person[]> p2 = new Holder<Person[]>(p2Value);
			    Holder<Person[]> p3 = new Holder<Person[]>();
			    
			    Person[] result = userType.f6(p1, p2, p3);

			    for (int i = 0; i < result.length; i++)
			    {
				assert(result[i].name.equals("Test"));
				assert(result[i].id == i);
			    }
			    for (int i = 0; i < p2.getValue().length; i++)
			    {
				assert(p2.getValue()[i].name.equals("Andrea"));
				assert(p2.getValue()[i].id == i);
			    }
			    for (int i = 0; i < p3.getValue().length; i++)
			    {
				assert(p3.getValue()[i].name.equals("Egon"));
				assert(p3.getValue()[i].id == i + i);
			    }
			}

			{ // typedef long time_t
			    int p1 = 7;
			    Holder<Integer> p2 = new Holder<Integer>(3);
			    Holder<Integer> p3 = new Holder<Integer>();
			    
			    int result = userType.f7(p1, p2, p3);
			    
			    assert(p2.getValue() == 7);
			    assert(p3.getValue() == 3);
			    assert(result == 3 + 7);
			}
			System.out.println("OK!");

			component.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Undeploy local Java component
		TestHomeDeployment.undeploy("TestHome");
	}
}
