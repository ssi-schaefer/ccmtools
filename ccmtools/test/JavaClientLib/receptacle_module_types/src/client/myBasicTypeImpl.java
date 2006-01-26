package client;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.ShortHolder;
import ccm.local.Components.CCMException;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.DoubleHolder;
import org.omg.CORBA.CharHolder;
import org.omg.CORBA.StringHolder;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ByteHolder;

public class myBasicTypeImpl
    implements world.europe.austria.ccm.local.BasicTypeInterface
{

    public myBasicTypeImpl()
    {
    }

    public short f1 (short p1, ShortHolder p2, ShortHolder p3)
    {
    	System.out.println("myBasicTypeImpl.f1()");
		p3.value = p2.value;
		p2.value = p1;
		short result = (short) (p3.value + p1);
		return result;
    }
    

    public int f2(int p1, IntHolder p2, IntHolder p3) 
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.f2()");
		p3.value = p2.value;
		p2.value = p1;
		int result = p3.value + p1;
		return result;
	}


    public short f3(short p1, ShortHolder p2, ShortHolder p3)
	{
		System.out.println("myBasicTypeImpl.f3()");
		p3.value = p2.value;
		p2.value = p1;
		short result = (short) (p3.value + p1);
		return result;
	}


    public int f4(int p1, IntHolder p2, IntHolder p3)
	{
		System.out.println("myBasicTypeImpl.f4()");
		p3.value = p2.value;
		p2.value = p1;
		int result = p3.value + p1;
		return result;
	}


    public float f5(float p1, FloatHolder p2, FloatHolder p3)
	{
		System.out.println("myBasicTypeImpl.f5()");
		p3.value = p2.value;
		p2.value = p1;
		float result = p3.value + p1;
		return result;
	}


    public double f6(double p1, DoubleHolder p2, DoubleHolder p3)
	{
		System.out.println("myBasicTypeImpl.f6()");
		p3.value = p2.value;
		p2.value = p1;
		double result = p3.value + p1;
		return result;
	}


    public char f7(char p1, CharHolder p2, CharHolder p3)
	{
		System.out.println("myBasicTypeImpl.f7()");
		p3.value = p2.value;
		p2.value = p1;
		char result = (char) (p3.value + p1);
		return result;
	}


    public String f8(String p1, StringHolder p2, StringHolder p3) 
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.8()");
		p3.value = p2.value;
		p2.value = p1;
		String result = p3.value + p1;
		return result;
	}

    public boolean f9(boolean p1, BooleanHolder p2, BooleanHolder p3)
	{
		System.out.println("myBasicTypeImpl.9()");
		p3.value = p2.value;
		p2.value = p1;
		boolean result = p3.value && p1;
		return result;
	}


    public byte f10(byte p1, ByteHolder p2, ByteHolder p3)
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.f10()");
		try
		{
			p3.value = p2.value;
			p2.value = p1;
			byte result = (byte) (p3.value + p1);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}
}
