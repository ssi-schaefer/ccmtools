package client;

import java.util.List;
import java.util.ArrayList;

import world.europe.austria.Color;

import world.europe.austria.ccm.local.*;
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
			p3.setValue(new Person(p2.getValue().getId(), p2.getValue().getName()));
			p2.getValue().setId(p1.getId());
			p2.getValue().setName(p1.getName());
			Person result = new Person(p1.getId() + p3.getValue().getId(), 
						   p1.getName() + p3.getValue().getName());
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
			Person person = 
			    new Person(p1.getResident().getId() + p2.getValue().getResident().getId(), 
				       p1.getResident().getName() + p2.getValue().getResident().getName());
			Address addr = 
			    new Address(p1.getStreet() + p2.getValue().getStreet(), 
					p1.getNumber() + p2.getValue().getNumber(), person);
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

	
	public List<Integer> f4(List<Integer> p1, Holder<List<Integer>> p2, Holder<List<Integer>> p3)
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f4()");
		try
		{
			List<Integer> result = new ArrayList<Integer>(p1.size());
			p3.setValue(new ArrayList<Integer>(p1.size()));
			for (int i = 0; i < p1.size(); i++)
			{
				result.add(i);
				p3.getValue().add(p2.getValue().get(i));
				p2.getValue().set(i, p1.get(i));
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public List<String> f5(List<String> p1, Holder<List<String>> p2, Holder<List<String>> p3)
			throws CCMException
	{
		System.out.println("myUserTypeImpl.f5()");
		try
		{
			List<String> result = new ArrayList<String>(p1.size());		
			p3.setValue(new ArrayList<String>(p1.size()));
			for(int i = 0; i< p1.size(); i++)
			{
				result.add("Test");
				p3.getValue().add(p2.getValue().get(i));
				p2.getValue().set(i, p1.get(i));
			}
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}

	
	public List<Person> f6(List<Person> p1, Holder<List<Person>> p2, Holder<List<Person>> p3) 
		throws CCMException
	{
		System.out.println("myUserTypeImpl.f6()");
		try
		{
			List<Person> result = new ArrayList<Person>(p1.size());
			p3.setValue(new ArrayList<Person>(p1.size()));
			for (int i = 0; i < p1.size(); i++)
			{
				Person person = new Person(i, "Test");
				result.add(person);
				p3.getValue().add(p2.getValue().get(i));
				p2.getValue().set(i, p1.get(i));
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
