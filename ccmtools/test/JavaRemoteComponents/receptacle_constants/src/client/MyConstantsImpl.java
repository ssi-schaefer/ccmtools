package client;

import Components.CCMException;

public class MyConstantsImpl
    implements world.Constants
{
    public MyConstantsImpl()
    {
    }

    public boolean getBooleanValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getBooleanValue()");
        return world.Constants.BOOLEAN_CONST;
    }

    public byte getOctetValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getOctetValue()");
        return world.Constants.OCTET_CONST;
    }

    public short getShortValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getShortValue()");
        return world.Constants.SHORT_CONST;
    }

    public short getUnsignedShortValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedShortValue()");
        return world.Constants.USHORT_CONST;
    }

    public int getLongValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getLongValue()");
        return world.Constants.LONG_CONST;
    }

    public int getUnsignedLongValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedLongValue()");
        return world.Constants.ULONG_CONST;
    }

    public char getCharValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getCharValue()");
        return world.Constants.CHAR_CONST;
    }

    public String getStringValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getStringValue()");
        return world.Constants.STRING_CONST;
    }

    public float getFloatValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getFloatValue()");
        return world.Constants.FLOAT_CONST;
    }

    public double getDoubleValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getDoubleValue()");
        return world.Constants.DOUBLE_CONST;
    }
}
