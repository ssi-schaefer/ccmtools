package client;

import Components.ccm.local.CCMException;

public class MyVoidTypeImpl
    implements world.europe.austria.ccm.local.VoidTypeInterface
{
    private int attr;
    
    public MyVoidTypeImpl()
    {
    }
    
    public void f1(int p1)
	throws CCMException
    {
	System.out.println("MyVoidTypeImpl.f1()");
	attr = p1;
    }
    
    public int f2()
	throws CCMException
    {
	System.out.println("MyVoidTypeImpl.f2()");
	return attr;
    }
}
