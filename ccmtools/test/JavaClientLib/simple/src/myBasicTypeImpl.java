import org.omg.CORBA.IntHolder;
import org.omg.CORBA.StringHolder;

import ccm.local.Components.CCMException;
import wamas.ccm.local.BasicTypeInterface;

public class myBasicTypeImpl
    implements BasicTypeInterface
{

    public myBasicTypeImpl()
    {
    }
    
    public int f2(int p1, IntHolder p2, IntHolder p3) 
	throws CCMException
    {
	System.out.println("enter myBasicTypeImpl.f2()");
	p3.value = p2.value;
	p2.value = p1;
	int result = p3.value + p1;
	System.out.println("leave myBasicTypeImpl.f2()");
	return result;
    }
    
    public String f8(String p1, StringHolder p2, StringHolder p3) 
	throws CCMException
    {
	System.out.println("leave myBasicTypeImpl.f8()");		
	p3.value = p2.value;
	p2.value = p1;
	String result = p3.value + p1;
	System.out.println("leave myBasicTypeImpl.f8()");
	return result;
    }
}
