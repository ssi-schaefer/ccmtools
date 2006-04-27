package client;

import ccm.local.Components.CCMException;

public class MyIFace
    implements world.ccm.local.IFace
{
    public int foo(String str)

    {
        System.out.println("MyIFace.op1(" + str + ")");
        return str.length();
    }
};
