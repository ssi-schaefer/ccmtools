import java.util.List;
import java.util.ArrayList;

import world.ccm.local.*;
import Components.ccm.local.*;
import ccm.local.*;

import java.util.logging.*;

import ccm.local.AssemblyFactory;

public class Client
{
	public static void main(String[] args)
	{
	    System.out.println("component assembly nasted test case:");

	    // Configure Logger
	    Logger logger = Logger.getLogger("test");
	    logger.setLevel(Level.FINE);	    
	    Handler handler = new ConsoleHandler();
	    handler.setLevel(Level.ALL);
	    handler.setFormatter(new ccm.local.MinimalFormatter());
	    logger.addHandler(handler);
	    ccm.local.ServiceLocator.instance().setLogger(logger);

	    // Deploy local Java component
	    try
	    {
		SuperTestHomeDeployment.deploy("SuperTestHome", 
	      	       new AssemblyFactory(world.Assembly.class));
		BasicTestHomeDeployment.deploy("BasicTestHome");
		UserTestHomeDeployment.deploy("UserTestHome");
	    }
	    catch (java.lang.Exception e)
	    {
		e.printStackTrace();
	    }
	    
	    // Use local Java component
	    try
	    {
		SuperTestHome home = 
		    (SuperTestHome) ccm.local.HomeFinder.instance().find_home_by_name("SuperTestHome");
		SuperTest component = home.create();
		component.configuration_complete();
		
		System.out.println("Facet Void Types Test...");

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
		    Person p1 = new Person();
		    p1.setId(3);
		    p1.setName("Egon");
		    Holder<Person> p2 = new Holder<Person>(new Person(23, "Andrea"));
		    Holder<Person> p3 = new Holder<Person>();
		    
		    Person result = userType.f2(p1, p2, p3);
		    
		    assert(p3.getValue().getName().equals("Andrea"));
		    assert(p3.getValue().getId() == 23);
		    assert(p2.getValue().getName().equals("Egon"));
		    assert(p2.getValue().getId() == 3);
		    assert(result.getName().equals("EgonAndrea"));
		    assert(result.getId() == 3 + 23);
		}
		
		{ // struct Address { string street; long number; Person resident; }
		    Person egon = new Person(3, "Egon");
		    Address p1 = new Address("Waltendorf", 7, egon);
		    
		    Person andrea = new Person(23, "Andrea");
		    Holder<Address> p2 = new Holder<Address>(new Address("Petersgasse", 17, andrea));
		    
		    Holder<Address> p3 = new Holder<Address>();
		    
		    Address result = userType.f3(p1, p2, p3);
		    
		    assert(p3.getValue().getStreet().equals("Petersgasse"));
		    assert(p3.getValue().getNumber() == 17);
		    assert(p3.getValue().getResident().getName().equals("Andrea"));
		    assert(p3.getValue().getResident().getId() == 23);
		    
		    assert(p2.getValue().getStreet().equals("Waltendorf"));
		    assert(p2.getValue().getNumber() == 7);
		    assert(p2.getValue().getResident().getName().equals("Egon"));
		    assert(p2.getValue().getResident().getId() == 3);
		    
		    assert(result.getStreet().equals("WaltendorfPetersgasse"));
		    assert(result.getNumber() == 24);
		    assert(result.getResident().getName().equals("EgonAndrea"));
		    assert(result.getResident().getId() == 26);
		}

		{ // typedef sequence<long> LongList
		    List<Integer> p1 = new ArrayList<Integer>();
		    List<Integer> p2Value = new ArrayList<Integer>();
		    for (int i = 0; i < 5; i++)
		    {
			p1.add(i);
			p2Value.add(i + i);
		    }

		    Holder<List<Integer>> p2 = new Holder<List<Integer>>(p2Value);
		    Holder<List<Integer>> p3 = new Holder<List<Integer>>();
		    
		    List<Integer> result = userType.f4(p1, p2, p3);
		    
		    for (int i = 0; i < result.size(); i++)
		    {
			assert(result.get(i) == i);
		    }
		    for (int i = 0; i < p2.getValue().size(); i++)
		    {
			assert(p2.getValue().get(i) == i);
		    }
		    for (int i = 0; i < p3.getValue().size(); i++)
		    {
			assert(p3.getValue().get(i) == i + i);
		    }
		}
		
		{ // typedef sequence<string> StringList
		    List<String> p1 = new ArrayList<String>();
		    List<String> p2Value = new ArrayList<String>();
		    for (int i = 0; i < 5; i++)
		    {
			p1.add("Egon");
			p2Value.add("Andrea");
		    }
		    
		    Holder<List<String>> p2 = new Holder<List<String>>(p2Value);
		    Holder<List<String>> p3 = new Holder<List<String>>();
		    
		    List<String> result = userType.f5(p1, p2, p3);
		    
		    for (int i = 0; i < result.size(); i++)
		    {
			assert(result.get(i).equals("Test"));
		    }
		    for (int i = 0; i < p2.getValue().size(); i++)
		    {
			assert(p2.getValue().get(i).equals("Egon"));
		    }
		    for (int i = 0; i < p3.getValue().size(); i++)
		    {
			assert(p3.getValue().get(0).equals("Andrea"));
		    }
		}

		{ // typedef sequence<Person> PersonList
		    
		    List<Person> p1 = new ArrayList<Person>();
		    List<Person> p2Value = new ArrayList<Person>();
		    for (int i = 0; i < 5; i++)
		    {
			Person p = new Person(i, "Andrea");
			p1.add(p);
			p2Value.add(new Person(i + i, "Egon"));
		    }
		    Holder<List<Person>> p2 = new Holder<List<Person>>(p2Value);
		    Holder<List<Person>> p3 = new Holder<List<Person>>();
		    
		    List<Person> result = userType.f6(p1, p2, p3);
		    
		    for (int i = 0; i < result.size(); i++)
		    {
			assert(result.get(i).getName().equals("Test"));
			assert(result.get(i).getId() == i);
		    }
		    for (int i = 0; i < p2.getValue().size(); i++)
		    {
			assert(p2.getValue().get(i).getName().equals("Andrea"));
			assert(p2.getValue().get(i).getId() == i);
		    }
		    for (int i = 0; i < p3.getValue().size(); i++)
		    {
			assert(p3.getValue().get(i).getName().equals("Egon"));
			assert(p3.getValue().get(i).getId() == i + i);
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
	    SuperTestHomeDeployment.undeploy("SuperTestHome");
	    SuperTestHomeDeployment.undeploy("BasicTestHome");
	    SuperTestHomeDeployment.undeploy("UserTestHome");
	}
}
