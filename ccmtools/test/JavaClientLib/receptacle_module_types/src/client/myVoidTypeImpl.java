package client;

import ccm.local.Components.CCMException;

public class myVoidTypeImpl
	implements world.europe.austria.ccm.local.VoidTypeInterface
{
	private int attr;
	
	public myVoidTypeImpl()
	{
	}

	public void f1(int p1) 
		throws CCMException
	{
		System.out.println("myVoidTypeImpl.f1()");
		attr = p1;
	}

	public int f2() 
		throws CCMException
	{
		System.out.println("myVoidTypeImpl.f2()");
		return attr;
	}
}
