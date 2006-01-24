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

public class myUserTypeImpl implements world.europe.austria.ccm.local.UserTypeInterface
{

	public myUserTypeImpl()
	{
	}

	public Color f1(Color p1, ColorHolder p2, ColorHolder p3)
	{
		System.out.println("myUserTypeImpl.f1()");
		return null;
	}

	public Person f2(Person p1, PersonHolder p2, PersonHolder p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f2()");
		p3.value = new Person(p2.value.id, p2.value.name);

		p2.value.id = p1.id;
		p2.value.name = p1.name;

		Person result = new Person(p1.id + p3.value.id, p1.name + p3.value.name);
		return result;
	}

	public Address f3(Address p1, AddressHolder p2, AddressHolder p3)
	{
		System.out.println("myUserTypeImpl.f3()");
		return null;
	}

	public int[] f4(int[] p1, LongListHolder p2, LongListHolder p3)
	{
		System.out.println("myUserTypeImpl.f4()");
		return null;
	}

	public String[] f5(String[] p1, StringListHolder p2, StringListHolder p3)
			throws CCMException
	{
		System.out.println("myUserTypeImpl.f5()");
		try
		{
			String[] p3a = new String[3];
			p3a[0] = p2.value[0];
			p3a[1] = p2.value[1];
			p3a[2] = p2.value[2];
			p3.value = p3a;

			p2.value = p1;

			String[] result =
			{
					"Test", "Test", "Test"
			};
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException();
		}
	}

	public Person[] f6(Person[] p1, PersonListHolder p2, PersonListHolder p3)
	{
		System.out.println("myUserTypeImpl.f6()");
		return null;
	}

	public int f7(int t1, IntHolder t2, IntHolder t3)
	{
		System.out.println("myUserTypeImpl.f7()");
		return 0;
	}
}
