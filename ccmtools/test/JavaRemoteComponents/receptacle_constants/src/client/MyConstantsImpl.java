package client;

import ccm.local.Components.CCMException;

public class MyConstantsImpl
    implements world.ccm.local.Constants
{
    public MyConstantsImpl()
    {
    }

    public boolean getBooleanValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getBooleanValue()");
        return world.ccm.local.Constants.BOOLEAN_CONST;
    }

    public byte getOctetValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getOctetValue()");
        return world.ccm.local.Constants.OCTET_CONST;
    }

    public short getShortValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getShortValue()");
        return world.ccm.local.Constants.SHORT_CONST;
    }

    public short getUnsignedShortValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedShortValue()");
        return world.ccm.local.Constants.USHORT_CONST;
    }

    public int getLongValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getLongValue()");
        return world.ccm.local.Constants.LONG_CONST;
    }

    public int getUnsignedLongValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getUnsignedLongValue()");
        return world.ccm.local.Constants.ULONG_CONST;
    }

    public char getCharValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getCharValue()");
        return world.ccm.local.Constants.CHAR_CONST;
    }

    public String getStringValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getStringValue()");
        return world.ccm.local.Constants.STRING_CONST;
    }

    public float getFloatValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getFloatValue()");
        return world.ccm.local.Constants.FLOAT_CONST;
    }

    public double getDoubleValue ()
        throws ccm.local.Components.CCMException
    {
        System.out.println("MyConstantsImpl.getDoubleValue()");
        return world.ccm.local.Constants.DOUBLE_CONST;
    }
};
