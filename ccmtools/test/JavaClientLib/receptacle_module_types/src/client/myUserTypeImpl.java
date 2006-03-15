package client;

import org.omg.CORBA.IntHolder;

import world.europe.austria.Address;
import world.europe.austria.Color;
import world.europe.austria.Person;
import ccm.local.Components.CCMException;

import ccm.local.Holder;


public class myUserTypeImpl 
	implements world.europe.austria.ccm.local.UserTypeInterface
{

	public myUserTypeImpl()
	{
	}

	public Color f1(Color p1, Holder<Color> p2, Holder<Color> p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f1()");
		try
		{
			p3.setValue(p2.getValue());
			p2.setValue(p1);
			Color result = Color.orange;
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	public Person f2(Person p1, Holder<Person> p2, Holder<Person> p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f2()");
		try
		{
			p3.setValue(new Person(p2.getValue().id, p2.getValue().name));
			p2.getValue().id = p1.id;
			p2.getValue().name = p1.name;
			Person result = new Person(p1.id + p3.getValue().id, 
						   p1.name + p3.getValue().name);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	public Address f3(Address p1, Holder<Address> p2, Holder<Address> p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f3()");
		try
		{
			Person pers = new Person(p1.resident.id + p2.getValue().resident.id, 
						 p1.resident.name + p2.getValue().resident.name);
			Address addr = new Address(p1.street + p2.getValue().street, 
						   p1.number + p2.getValue().number, pers);
			p3.setValue(p2.getValue());
			p2.setValue(p1);
			return addr;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public int[] f4(int[] p1, Holder<int[]> p2, Holder<int[]> p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f4()");
		try
		{
			int[] result = new int[p1.length];
			p3.setValue(new int[p1.length]);
			for (int i = 0; i < p1.length; i++)
			{
				result[i] = i;
				p3.getValue()[i] = p2.getValue()[i];
				p2.getValue()[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public String[] f5(String[] p1, Holder<String[]> p2, Holder<String[]> p3)
			throws CCMException
	{
		System.out.println("myUserTypeImpl.f5()");
		try
		{
			String[] result = new String[p1.length];		
			p3.setValue(new String[p1.length]);
			for(int i = 0; i< p1.length; i++)
			{
				result[i] = "Test";
				p3.getValue()[i] = p2.getValue()[i];
				p2.getValue()[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public Person[] f6(Person[] p1, Holder<Person[]> p2, Holder<Person[]> p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f6()");
		try
		{
			Person[] result = new Person[p1.length];
			p3.setValue(new Person[p1.length]);
			for (int i = 0; i < p1.length; i++)
			{
				Person person = new Person(i, "Test");
				result[i] = person;
				p3.getValue()[i] = p2.getValue()[i];
				p2.getValue()[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public int f7(int t1, Holder<Integer> t2, Holder<Integer> t3) 
		throws CCMException
	{
		try
		{
			System.out.println("myUserTypeImpl.f7()");
			t3.setValue(t2.getValue());
			t2.setValue(t1);
			int result = t3.getValue() + t1;
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}
}
