
import java.util.List;
import java.util.ArrayList;

import ccm.local.*;

import org.omg.CORBA.ORB;

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
	    component.configuration_complete();
	    
	    /*
	     * Test VoidTypeInterface facet
	     */
		VoidTypeInterface voidType = component.provide_voidType();
			
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

		{ // String
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
		

		/*
		 * Test UserTypeInterface facet
		 */
		UserTypeInterface userType = component.provide_userType();

		{ // enum Color {red, green, blue, black, orange}
		    world.europe.austria.Color p1 = world.europe.austria.Color.red;
		    Holder<world.europe.austria.Color> p2 = 
			new Holder<world.europe.austria.Color>(world.europe.austria.Color.blue);
		    Holder<world.europe.austria.Color> p3 = new Holder<world.europe.austria.Color>();
		    
		    world.europe.austria.Color result = userType.f1(p1, p2, p3);
		    
		    assert(p2.getValue() == world.europe.austria.Color.red);
		    assert(p3.getValue() == world.europe.austria.Color.blue);
		    assert(result == world.europe.austria.Color.orange);
		}
		
		{ // struct Person { long id; string name; }
		    world.europe.austria.Person p1 = new world.europe.austria.Person(3, "Egon");
		    Holder<world.europe.austria.Person> p2 = 
			new Holder<world.europe.austria.Person>(new world.europe.austria.Person(23, "Andrea"));
		    Holder<world.europe.austria.Person> p3 = new Holder<world.europe.austria.Person>();
		    
		    world.europe.austria.Person result = userType.f2(p1, p2, p3);
		    
		    assert(p3.getValue().name.equals("Andrea"));
		    assert(p3.getValue().id == 23);
		    assert(p2.getValue().name.equals("Egon"));
		    assert(p2.getValue().id == 3);
		    assert(result.name.equals("EgonAndrea"));
		    assert(result.id == 3 + 23);
		}

		{ // struct Address { string street; long number; Person resident; }
		    world.europe.austria.Person egon = new world.europe.austria.Person(3, "Egon");
		    world.europe.austria.Address p1 = new world.europe.austria.Address("Waltendorf", 7, egon);
		    
		    world.europe.austria.Person andrea = new world.europe.austria.Person(23, "Andrea");
		    Holder<world.europe.austria.Address> p2 = 
			new Holder<world.europe.austria.Address>(
			new world.europe.austria.Address("Petersgasse", 17, andrea));
		    
		    Holder<world.europe.austria.Address> p3 = 
			new Holder<world.europe.austria.Address>();
		    
		    world.europe.austria.Address result = userType.f3(p1, p2, p3);
		    
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
		    List<Integer> p1 = new ArrayList<Integer>(5);
		    List<Integer> p2Value = new ArrayList<Integer>(5);
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
		    List<String> p1 = new ArrayList<String>(5);
		    List<String> p2Value = new ArrayList<String>(5);
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
			assert(p3.getValue().get(i).equals("Andrea"));
		    }
		}

		{ // typedef sequence<Person> PersonList
		    
		    List<world.europe.austria.Person> p1 = new ArrayList<world.europe.austria.Person>(5);
		    List<world.europe.austria.Person> p2Value = new ArrayList<world.europe.austria.Person>(5);
		    for (int i = 0; i < 5; i++)
		    {
			p1.add(new world.europe.austria.Person(i, "Andrea"));
			p2Value.add(new world.europe.austria.Person(i + i, "Egon"));
		    }
		    Holder<List<world.europe.austria.Person>> p2 = 
			new Holder<List<world.europe.austria.Person>>(p2Value);
		    Holder<List<world.europe.austria.Person>> p3 = 
			new Holder<List<world.europe.austria.Person>>();
		    
		    List<world.europe.austria.Person> result = userType.f6(p1, p2, p3);
		    
		    for (int i = 0; i < result.size(); i++)
		    {
			assert(result.get(i).name.equals("Test"));
			assert(result.get(i).id == i);
		    }
		    for (int i = 0; i < p2.getValue().size(); i++)
		    {
			assert(p2.getValue().get(i).name.equals("Andrea"));
			assert(p2.getValue().get(i).id == i);
		    }
		    for (int i = 0; i < p3.getValue().size(); i++)
		    {
			assert(p3.getValue().get(i).name.equals("Egon"));
			assert(p3.getValue().get(i).id == i + i);
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
