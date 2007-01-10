package client;

public class MyIFace
    implements world.IFace
{
    public int foo(String str)
    {
        System.out.println("MyIFace.op1(" + str + ")");
        return str.length();
    }
};
