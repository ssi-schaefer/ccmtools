package client;

import ccm.local.Components.CCMException;

public class myI2
    implements world.europe.austria.ccm.local.I2
{
    public int op1(String str)

    {
	System.out.println("myI2.op1(" + str + ")");
	return str.length();
    }
};
