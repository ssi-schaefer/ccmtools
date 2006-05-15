package client;

import Components.ccm.local.CCMException;

public class MyConstantsImpl
    implements world.ccm.local.Constants
{
    public MyConstantsImpl()
    {
    }

    public boolean getBooleanValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getBooleanValue()");
        return world.ccm.local.Constants.BOOLEAN_CONST;
    }

    public byte getOctetValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getOctetValue()");
        return world.ccm.local.Constants.OCTET_CONST;
    }

    public short getShortValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getShortValue()");
        return world.ccm.local.Constants.SHORT_CONST;
    }

    public short getUnsignedShortValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedShortValue()");
        return world.ccm.local.Constants.USHORT_CONST;
    }

    public int getLongValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getLongValue()");
        return world.ccm.local.Constants.LONG_CONST;
    }

    public int getUnsignedLongValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedLongValue()");
        return world.ccm.local.Constants.ULONG_CONST;
    }

    public char getCharValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getCharValue()");
        return world.ccm.local.Constants.CHAR_CONST;
    }

    public String getStringValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getStringValue()");
        return world.ccm.local.Constants.STRING_CONST;
    }

    public float getFloatValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getFloatValue()");
        return world.ccm.local.Constants.FLOAT_CONST;
    }

    public double getDoubleValue ()
        throws CCMException
    {
        System.out.println("MyConstantsImpl.getDoubleValue()");
        return world.ccm.local.Constants.DOUBLE_CONST;
    }
};
