package client;

public class MyIFace
    implements world.ccm.local.IFace
{
    private String prefix;

    public MyIFace(String prefix)
    {
	this.prefix = prefix; 
    }

    public int foo(String str)

    {
        System.out.println(prefix + ": MyIFace.foo(" + str + ")");
        return str.length();
    }
}
