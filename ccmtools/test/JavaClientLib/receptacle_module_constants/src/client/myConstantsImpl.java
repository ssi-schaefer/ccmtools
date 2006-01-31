package client;

import ccm.local.Components.CCMException;

public class myConstantsImpl
    implements world.ccm.local.Constants
{
    public myConstantsImpl()
    {
    }


    public boolean getBooleanValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getBooleanValue()");
	return world.ccm.local.Constants.BOOLEAN_CONST;
    }


    public byte getOctetValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getOctetValue()");
	return world.ccm.local.Constants.OCTET_CONST;
    }


    public short getShortValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getShortValue()");
	return world.ccm.local.Constants.SHORT_CONST;
    }


    public short getUnsignedShortValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getUnsignedShortValue()");
	return world.ccm.local.Constants.USHORT_CONST;
    }


    public int getLongValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getLongValue()");
	return world.ccm.local.Constants.LONG_CONST;
    }


    public int getUnsignedLongValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getUnsignedLongValue()");
	return world.ccm.local.Constants.ULONG_CONST;
    }


    public char getCharValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getCharValue()");
	return world.ccm.local.Constants.CHAR_CONST;
    }


    public String getStringValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getStringValue()");
	return world.ccm.local.Constants.STRING_CONST;
    }


    public float getFloatValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getFloatValue()");
	return world.ccm.local.Constants.FLOAT_CONST;
    }


    public double getDoubleValue ()
        throws ccm.local.Components.CCMException
    {
	System.out.println("myConstantsImpl.getDoubleValue()");
	return world.ccm.local.Constants.DOUBLE_CONST;
    }
};
