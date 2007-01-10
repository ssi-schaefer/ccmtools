package client;

public class MyIFace
    implements world.IFace
{
    public int foo(String str)

    {
        System.out.println("MyIFace.foo(" + str + ")");
        return str.length();
    }
};
