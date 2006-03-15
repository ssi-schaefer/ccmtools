package client;

import ccm.local.Components.CCMException;
import ccm.local.Holder;

public class myBasicTypeImpl
    implements world.europe.austria.ccm.local.BasicTypeInterface
{

    public myBasicTypeImpl()
    {
    }

    public short f1 (short p1, Holder<Short> p2, Holder<Short> p3)
    {
    	System.out.println("myBasicTypeImpl.f1()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		short result = (short) (p3.getValue() + p1);
		return result;
    }
    

    public int f2(int p1, Holder<Integer> p2, Holder<Integer> p3) 
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.f2()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		int result = p3.getValue() + p1;
		return result;
	}


    public short f3(short p1, Holder<Short> p2, Holder<Short> p3)
	{
		System.out.println("myBasicTypeImpl.f3()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		short result = (short) (p3.getValue() + p1);
		return result;
	}


    public int f4(int p1, Holder<Integer> p2, Holder<Integer> p3)
	{
		System.out.println("myBasicTypeImpl.f4()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		int result = p3.getValue() + p1;
		return result;
	}


    public float f5(float p1, Holder<Float> p2, Holder<Float> p3)
	{
		System.out.println("myBasicTypeImpl.f5()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		float result = p3.getValue() + p1;
		return result;
	}


    public double f6(double p1, Holder<Double> p2, Holder<Double> p3)
	{
		System.out.println("myBasicTypeImpl.f6()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		double result = p3.getValue() + p1;
		return result;
	}


    public char f7(char p1, Holder<Character> p2, Holder<Character> p3)
	{
		System.out.println("myBasicTypeImpl.f7()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		char result = (char) (p3.getValue() + p1);
		return result;
	}


    public String f8(String p1, Holder<String> p2, Holder<String> p3) 
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.8()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		String result = p3.getValue() + p1;
		return result;
	}

    public boolean f9(boolean p1, Holder<Boolean> p2, Holder<Boolean> p3)
	{
		System.out.println("myBasicTypeImpl.9()");
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		boolean result = p3.getValue() && p1;
		return result;
	}


    public byte f10(byte p1, Holder<Byte> p2, Holder<Byte> p3)
    	throws CCMException
	{
		System.out.println("myBasicTypeImpl.f10()");
		try
		{
			p3.setValue(p2.getValue());
			p2.setValue(p1);
			byte result = (byte) (p3.getValue() + p1);
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CCMException(e.getMessage());
		}
	}
}
