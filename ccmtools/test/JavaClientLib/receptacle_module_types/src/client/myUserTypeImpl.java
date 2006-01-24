package client;

import ccm.local.Components.CCMException;

public class myUserTypeImpl
	implements world.europe.austria.ccm.local.UserTypeInterface
{

    public myUserTypeImpl()
    {
    }
    
    public world.europe.austria.Color f1 (world.europe.austria.Color p1, 
				   world.europe.austria.ColorHolder p2, 
				   world.europe.austria.ColorHolder p3)
    {
	System.out.println("myUserTypeImpl.f1()");
    }

    
    public world.europe.austria.Person f2 (world.europe.austria.Person p1, 
				    world.europe.austria.PersonHolder p2, 
				    world.europe.austria.PersonHolder p3)
	throws CCMException
    {
	System.out.println("myUserTypeImpl.f2()");
	p3.value = new Person(p2.value.id, p2.value.name);
	
	p2.value.id = p1.id;
	p2.value.name = p1.name;
	
	Person result = new Person(p1.id + p3.value.id, p1.name + p3.value.name);
	return result;
    }


    public world.europe.austria.Address f3 (world.europe.austria.Address p1, 
				     world.europe.austria.AddressHolder p2, 
				     world.europe.austria.AddressHolder p3)
    {
	System.out.println("myUserTypeImpl.f3()");
    }

    
    public int[] f4 (int[] p1, world.europe.austria.LongListHolder p2, 
	      world.europe.austria.LongListHolder p3)
    {
	System.out.println("myUserTypeImpl.f4()");
    }


    public String[] f5 (String[] p1, world.europe.austria.StringListHolder p2, 
		 world.europe.austria.StringListHolder p3)
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
	    
	    String[] result = {"Test", "Test", "Test"};
	    return result;
	}
	catch(Exception e) 
	{
	    e.printStackTrace();
	    throw new CCMException();
	}
    }


    public world.europe.austria.Person[] f6 (world.europe.austria.Person[] p1, 
				      world.europe.austria.PersonListHolder p2, 
				      world.europe.austria.PersonListHolder p3)
    {
	System.out.println("myUserTypeImpl.f6()");
    }


    public int f7 (int t1, org.omg.CORBA.IntHolder t2, org.omg.CORBA.IntHolder t3)
    {
	System.out.println("myUserTypeImpl.f7()");
    }
}
