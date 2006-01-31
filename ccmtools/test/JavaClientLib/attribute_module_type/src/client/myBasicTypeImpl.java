package client;

import world.europe.austria.ccm.local.*;

public class myBasicTypeImpl
    implements BasicTypeInterface
{
    private short shortValue;
    private int longValue;
    private short ushortValue;
    private int ulongValue;
    private float floatValue;
    private double doubleValue;
    private char charValue;
    private String stringValue;
    private boolean booleanValue;
    private byte octetValue;


    public short short_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.short_value() - get");
	return shortValue;
    }

    public void short_value(short value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.short_value() - set");
	shortValue = value;
    }


    public int long_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.long_value() - get");
	return longValue;
    }

    public void long_value(int value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.long_value() - set");
	longValue = value;
    }


    public short ushort_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.ushort_value() - get");
	return ushortValue;
    }

    public void ushort_value(short value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.ushort_value() - set");
	ushortValue = value;
    }


    public int ulong_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.ulong_value() - get");
	return ulongValue;
    }

    public void ulong_value(int value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.ulong_value() - set");
	ulongValue = value;
    }


    public float float_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.float_value() - get");
	return floatValue;
    }

    public void float_value(float value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.float_value() - set");
	floatValue = value;
    }


    public double double_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.double_value() - get");
	return doubleValue;
    }

    public void double_value(double value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.double_value() - set");
	doubleValue = value;
    }


    public char char_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.char_value() - get");
	return charValue;
    }

    public void char_value(char value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.char_value() - set");
	charValue = value;
    }


    public String string_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.string_value() - get");
	return stringValue;
    }

    public void string_value(String value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.string_value() - set");
	stringValue = value;
    }


    public boolean boolean_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.boolean_value() - get");
	return booleanValue;
    }

    public void boolean_value(boolean value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.boolean_value() - set");
	booleanValue = value;
    }


    public byte octet_value()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.octet_value() - get");
	return octetValue;
    }

    public void octet_value(byte value)
        throws ccm.local.Components.CCMException
    {
	System.out.println("myBasicTypeImpl.octet_value() - set");
	octetValue = value;
    }
}
