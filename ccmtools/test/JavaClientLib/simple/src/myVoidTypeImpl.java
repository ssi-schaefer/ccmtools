package wamas;

import wamas.ccm.local.VoidTypeInterface;
import ccm.local.Components.CCMException;

public class myVoidTypeImpl
	implements VoidTypeInterface
{
	private int attr;
	
	public myVoidTypeImpl()
	{
	}

	public void f1(int p1) 
		throws CCMException
	{
		System.out.println("enter myVoidTypeImpl.f1()");
		attr = p1;
		System.out.println("leave myVoidTypeImpl.f1()");
	}

	public int f2() 
		throws CCMException
	{
		System.out.println("      myVoidTypeImpl.f2()");
		return attr;
	}
}
