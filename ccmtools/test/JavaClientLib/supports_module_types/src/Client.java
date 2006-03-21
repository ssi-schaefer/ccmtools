
import java.util.List;
import java.util.ArrayList;

import org.omg.CORBA.ORB;

import world.europe.austria.ccm.local.*;
import ccm.local.ServiceLocator;
import ccm.local.Holder;

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
	    {
		component.fv1(7);
	    }
	    
	    {
		int result = component.fv2();
		assert(result == 7);
	    }
	    
	    /*
	     * Test BasicTypeInterface facet
	     */
	    { // short
		short p1 = 7;
		Holder<Short> p2 = new Holder<Short>((short) 3);
		Holder<Short> p3 = new Holder<Short>();
		
		short result = component.fb1(p1, p2, p3);
		
		assert(p2.getValue() == 7);
		assert(p3.getValue() == 3);
		assert(result == 3 + 7);
	    }
	    
	    { // long
		int p1 = 7;
		Holder<Integer> p2 = new Holder<Integer>(3);
		Holder<Integer> p3 = new Holder<Integer>();
		
		int result = component.fb2(p1, p2, p3);
		
		assert(p2.getValue() == 7);
		assert(p3.getValue() == 3);
		assert(result == 3 + 7);
	    }

	    { // unsigned short
		short p1 = 7;
		Holder<Short> p2 = new Holder<Short>((short) 3);
		Holder<Short> p3 = new Holder<Short>();
		
		short result = component.fb3(p1, p2, p3);
		
		assert(p2.getValue() == 7);
		assert(p3.getValue() == 3);
		assert(result == 3 + 7);
	    }
	    
	    { // unsigned long
		int p1 = 7;
		Holder<Integer> p2 = new Holder<Integer>(3);
		Holder<Integer> p3 = new Holder<Integer>();
		
		int result = component.fb4(p1, p2, p3);
		
		assert(p2.getValue() == 7);
		assert(p3.getValue() == 3);
		assert(result == 3 + 7);
	    }
	    
	    { // float
		float p1 = (float) 7.0;
		Holder<Float> p2 = new Holder<Float>((float) 3.0);
		Holder<Float> p3 = new Holder<Float>();
		
		float result = component.fb5(p1, p2, p3);
		
		assert(Math.abs(p2.getValue() - 7.0) < 0.001);
		assert(Math.abs(p3.getValue() - 3.0) < 0.001);
		assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
	    }
	    
	    { // double
		double p1 = 7.0;
		Holder<Double> p2 = new Holder<Double>(3.0);
		Holder<Double> p3 = new Holder<Double>();
		
		double result = component.fb6(p1, p2, p3);
		
		assert(Math.abs(p2.getValue() - 7.0) < 0.000001);
		assert(Math.abs(p3.getValue() - 3.0) < 0.000001);
		assert(Math.abs(result - (3.0 + 7.0)) < 0.000001);
	    }

	    { // char
		char p1 = (char) 7;
		Holder<Character> p2 = new Holder<Character>((char) 3);
		Holder<Character> p3 = new Holder<Character>();
		
		char result = component.fb7(p1, p2, p3);
		
		assert(p2.getValue() == (char) 7);
		assert(p3.getValue() == (char) 3);
		assert(result == (char) (3 + 7));
	    }

	    {
		String s1 = "sieben";
		Holder<String> s2 = new Holder<String>("drei");
		Holder<String> s3 = new Holder<String>();
		
		String result = component.fb8(s1, s2, s3);
		
		assert(s2.getValue().equals("sieben"));
		assert(s3.getValue().equals("drei"));
		assert(result.equals("dreisieben"));
	    }

	    { // boolean
		boolean p1 = true;
		Holder<Boolean> p2 = new Holder<Boolean>(false);
		Holder<Boolean> p3 = new Holder<Boolean>();
		
		boolean result = component.fb9(p1, p2, p3);
		
		assert(p2.getValue() == true);
		assert(p3.getValue() == false);
		assert(result == false && true);
	    }
	    
	    { // octet
		byte p1 = (byte) 7;
		Holder<Byte> p2 = new Holder<Byte>((byte) 3);
		Holder<Byte> p3 = new Holder<Byte>();

		byte result = component.fb10(p1, p2, p3);

		assert(p2.getValue() == (byte) 7);
		assert(p3.getValue() == (byte) 3);
		assert(result == (byte) (3 + 7));
	    }
	    

	    /*
	     * Test UserTypeInterface facet
	     */
	    { // enum Color {red, green, blue, black, orange}
		world.europe.austria.Color p1 = world.europe.austria.Color.red;
		Holder<world.europe.austria.Color> p2 = 
		    new Holder<world.europe.austria.Color>(world.europe.austria.Color.blue);
		Holder<world.europe.austria.Color> p3 = 
		    new Holder<world.europe.austria.Color>();
		
		world.europe.austria.Color result = component.fu1(p1, p2, p3);
		
		assert(p2.getValue() == world.europe.austria.Color.red);
		assert(p3.getValue() == world.europe.austria.Color.blue);
		assert(result == world.europe.austria.Color.orange);
	    }
	    
	    { // struct Person { long id; string name; }
		Person p1 = new Person(3, "Egon");
		Holder<Person> p2 = new Holder<Person>(new Person(23, "Andrea"));
		Holder<Person> p3 = new Holder<Person>();
		
		Person result = component.fu2(p1, p2, p3);
		
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
		
		Address result = component.fu3(p1, p2, p3);
		
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
		List<Integer> p1 = new ArrayList<Integer>(5);
		List<Integer> p2Value = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++)
		{
			p1.add(i);
			p2Value.add(i + i);
		}
		
		Holder<List<Integer>> p2 = new Holder<List<Integer>>(p2Value);
		Holder<List<Integer>> p3 = new Holder<List<Integer>>();
		
		List<Integer> result = component.fu4(p1, p2, p3);
		
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
		
		List<String> result = component.fu5(p1, p2, p3);
		
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
		
		List<Person> p1 = new ArrayList<Person>(5);
		List<Person> p2Value = new ArrayList<Person>(5);
		for (int i = 0; i < 5; i++)
		{
		    p1.add(new Person(i, "Andrea"));
		    p2Value.add(new Person(i + i, "Egon"));
		}
		Holder<List<Person>> p2 = new Holder<List<Person>>(p2Value);
		Holder<List<Person>> p3 = new Holder<List<Person>>();
		
		List<Person> result = component.fu6(p1, p2, p3);
		
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
		
		int result = component.fu7(p1, p2, p3);
		
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
