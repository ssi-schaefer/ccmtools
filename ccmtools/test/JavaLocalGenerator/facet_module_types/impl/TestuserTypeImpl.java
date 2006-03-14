/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_UserTypeInterfaceImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.europe.austria.ccm.local;
                 
import ccm.local.Components.*;
import ccm.local.*;
import world.europe.austria.*;

/** 
 * This class implements a component facet's methods.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 *
 * @author
 * @version
 */
public class TestuserTypeImpl 
    implements world.europe.austria.ccm.local.CCM_UserTypeInterface
{
    /** Reference to the facet's component implementation */
    private world.europe.austria.ccm.local.TestImpl component;

    public TestuserTypeImpl(world.europe.austria.ccm.local.TestImpl component)
    {
        this.component = component;
    }

    /** Business logic implementations */
    
    public world.europe.austria.Color f1(world.europe.austria.Color p1, 
					 Holder<world.europe.austria.Color> p2, 
					 Holder<world.europe.austria.Color> p3)
        throws ccm.local.Components.CCMException
    {
	p3.setValue(p2.getValue());
	p2.setValue(p1);
	return world.europe.austria.Color.orange;
    }    

    
    public world.europe.austria.Person f2(world.europe.austria.Person p1, 
					  Holder<world.europe.austria.Person> p2, 
					  Holder<world.europe.austria.Person> p3)
        throws ccm.local.Components.CCMException
    {
	world.europe.austria.Person r = 
	    new world.europe.austria.Person(p1.id + p2.getValue().id, p1.name + p2.getValue().name);	
	p3.setValue(p2.getValue());
	p2.setValue(p1);
	return r;
    }    


    public world.europe.austria.Address f3(world.europe.austria.Address p1, 
					   Holder<world.europe.austria.Address> p2, 
					   Holder<world.europe.austria.Address> p3)
        throws ccm.local.Components.CCMException
    {
	Person pers = new Person(p1.resident.id + p2.getValue().resident.id, p1.resident.name + p2.getValue().resident.name);
	Address addr = new Address(p1.street + p2.getValue().street, p1.number + p2.getValue().number, pers);
	p3.setValue(p2.getValue());
	p2.setValue(p1);
	return addr;	
    }    


    public int[] f4(int[] p1, 
		    Holder<int[]> p2, 
		    Holder<int[]> p3)
        throws ccm.local.Components.CCMException
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


    public String[] f5(String[] p1, 
		       Holder<String[]> p2, 
		       Holder<String[]> p3)
        throws ccm.local.Components.CCMException
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


    public world.europe.austria.Person[] f6(world.europe.austria.Person[] p1, 
					    Holder<world.europe.austria.Person[]> p2, 
					    Holder<world.europe.austria.Person[]> p3)
        throws ccm.local.Components.CCMException
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


    public int f7(int t1, 
		  Holder<Integer> t2, 
		  Holder<Integer> t3)
        throws ccm.local.Components.CCMException
    {
	t3.setValue(t2.getValue());
	t2.setValue(t1);
	return t3.getValue() + t1;
    }    
}
