package client;


import ccm.local.Components.CCMException;

public class myBasicTypeImpl
    implements world.europe.austria.ccm.local.BasicTypeInterface
{

    public myBasicTypeImpl()
    {
    }

    public short f1 (short p1, org.omg.CORBA.ShortHolder p2, org.omg.CORBA.ShortHolder p3)
    {
	System.out.println("myBasicTypeImpl.f1()");
	return 0;
    }
    

    public int f2 (int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3);
	throws CCMException
    {
	System.out.println("myBasicTypeImpl.f2()");
	p3.value = p2.value;
	p2.value = p1;
	int result = p3.value + p1;
	return result;
    }


    public short f3 (short p1, org.omg.CORBA.ShortHolder p2, org.omg.CORBA.ShortHolder p3)
    {
	System.out.println("myBasicTypeImpl.f3()");
	return 0;
    }


    public int f4 (int p1, org.omg.CORBA.IntHolder p2, org.omg.CORBA.IntHolder p3)
    {
	System.out.println("myBasicTypeImpl.f4()");
	return 0;
    }


    public float f5 (float p1, org.omg.CORBA.FloatHolder p2, org.omg.CORBA.FloatHolder p3)
    {
	System.out.println("myBasicTypeImpl.f5()");
	return 0.0;
    }


    public double f6 (double p1, org.omg.CORBA.DoubleHolder p2, org.omg.CORBA.DoubleHolder p3)
    {
	System.out.println("myBasicTypeImpl.f6()");
	return 0.0;
    }


    public char f7 (char p1, org.omg.CORBA.CharHolder p2, org.omg.CORBA.CharHolder p3)
    {
	System.out.println("myBasicTypeImpl.f7()");
	return 0;
    }


    public String f8 (String p1, org.omg.CORBA.StringHolder p2, org.omg.CORBA.StringHolder p3)
	throws CCMException
    {
	System.out.println("myBasicTypeImpl.8()");
	p3.value = p2.value;
	p2.value = p1;
	String result = p3.value + p1;
	return result;
    }

    public boolean f9 (boolean p1, org.omg.CORBA.BooleanHolder p2, org.omg.CORBA.BooleanHolder p3)
    {
	System.out.println("myBasicTypeImpl.9()");
	return false;
    }


    public byte f10 (byte p1, org.omg.CORBA.ByteHolder p2, org.omg.CORBA.ByteHolder p3)
    {
	System.out.println("myBasicTypeImpl.f10()");
	return 0;
    }
}
