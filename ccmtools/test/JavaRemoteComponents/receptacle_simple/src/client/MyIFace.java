package client;

public class MyIFace
    implements world.ccm.local.IFace
{
    public int op1(String str)

    {
        System.out.println("MyIFace.op1(" + str + ")");
        return str.length();
    }
};
