package client;

import org.omg.CORBA.IntHolder;

import world.europe.austria.Address;
import world.europe.austria.AddressHolder;
import world.europe.austria.Color;
import world.europe.austria.ColorHolder;
import world.europe.austria.LongListHolder;
import world.europe.austria.Person;
import world.europe.austria.PersonHolder;
import world.europe.austria.PersonListHolder;
import world.europe.austria.StringListHolder;
import ccm.local.Components.CCMException;

public class myUserTypeImpl 
	implements world.europe.austria.ccm.local.UserTypeInterface
{

	public myUserTypeImpl()
	{
	}

	public Color f1(Color p1, ColorHolder p2, ColorHolder p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f1()");
		try
		{
			p3.value = p2.value;
			p2.value = p1;
			Color result = Color.orange;
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	public Person f2(Person p1, PersonHolder p2, PersonHolder p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f2()");
		try
		{
			p3.value = new Person(p2.value.id, p2.value.name);
			p2.value.id = p1.id;
			p2.value.name = p1.name;
			Person result = new Person(p1.id + p3.value.id, p1.name + p3.value.name);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	public Address f3(Address p1, AddressHolder p2, AddressHolder p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f3()");
		try
		{
			Person pers = new Person(p1.resident.id + p2.value.resident.id, p1.resident.name + p2.value.resident.name);
			Address addr = new Address(p1.street + p2.value.street, p1.number + p2.value.number, pers);
			p3.value = p2.value;
			p2.value = p1;
			return addr;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public int[] f4(int[] p1, LongListHolder p2, LongListHolder p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f4()");
		try
		{
			int[] result = new int[p1.length];
			p3.value = new int[p1.length];
			for (int i = 0; i < p1.length; i++)
			{
				result[i] = i;
				p3.value[i] = p2.value[i];
				p2.value[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public String[] f5(String[] p1, StringListHolder p2, StringListHolder p3)
			throws CCMException
	{
		System.out.println("myUserTypeImpl.f5()");
		try
		{
			String[] result = new String[p1.length];		
			p3.value = new String[p1.length];
			for(int i = 0; i< p1.length; i++)
			{
				result[i] = "Test";
				p3.value[i] = p2.value[i];
				p2.value[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public Person[] f6(Person[] p1, PersonListHolder p2, PersonListHolder p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f6()");
		try
		{
			Person[] result = new Person[p1.length];
			p3.value = new Person[p1.length];
			for (int i = 0; i < p1.length; i++)
			{
				Person person = new Person(i, "Test");
				result[i] = person;
				p3.value[i] = p2.value[i];
				p2.value[i] = p1[i];
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public int f7(int t1, IntHolder t2, IntHolder t3) 
		throws CCMException
	{
		try
		{
			System.out.println("myUserTypeImpl.f7()");
			t3.value = t2.value;
			t2.value = t1;
			int result = t3.value + t1;
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}
}
