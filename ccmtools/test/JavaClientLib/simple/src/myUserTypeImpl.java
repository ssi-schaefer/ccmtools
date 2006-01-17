package wamas;

import ccm.local.Components.CCMException;
import wamas.Person;
import wamas.PersonHolder;
import wamas.StringListHolder;
import wamas.ccm.local.UserTypeInterface;

public class myUserTypeImpl
	implements UserTypeInterface
{

	public myUserTypeImpl()
	{
	}

	public Person f2(Person p1, PersonHolder p2, PersonHolder p3) 
		throws CCMException
	{
	    System.out.println("enter myUserTypeImpl.f2()");
	    
	    p3.value = new Person(p2.value.id, p2.value.name);
		
	    p2.value.id = p1.id;
	    p2.value.name = p1.name;
	    
	    Person result = new Person(p1.id + p3.value.id, p1.name + p3.value.name);
		
	    System.out.println("leave myUserTypeImpl.f2()");
		return result;
	}

	public String[] f5(String[] p1, StringListHolder p2, StringListHolder p3) 
		throws CCMException
        {
	    try
	    {
		System.out.println("enter myUserTypeImpl.f5()");
		
		String[] p3a = new String[3];
		p3a[0] = p2.value[0];
		p3a[1] = p2.value[1];
		p3a[2] = p2.value[2];
		p3.value = p3a;
		
		p2.value = p1;
		
		String[] result = {"Test", "Test", "Test"};
		System.out.println("leave myUserTypeImpl.f5()");
		return result;
	    }
	    catch(Exception e) 
	    {
		e.printStackTrace();
		throw new CCMException();
	    }
	}

}
