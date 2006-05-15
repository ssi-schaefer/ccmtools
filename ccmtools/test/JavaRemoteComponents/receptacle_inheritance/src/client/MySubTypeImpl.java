package client;

import Components.ccm.local.CCMException;

public class MySubTypeImpl
    implements world.america.ccm.local.SubType
{
    private int attr1;
    private int attr2;
    private int attr3;


    public int attr1()
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr1() - get");
        return this.attr1;
    }

    public void attr1(int value)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr1() - set");
        this.attr1 = value;
    }


    public int attr2()
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr2() - get");
        return this.attr2;
    }
    public void attr2(int value)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr2() - set");
        this.attr2 = value;
    }


    public int attr3()
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr3() - get");
        return this.attr3;
    }

    public void attr3(int value)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.attr3() - set");
        this.attr3 = value;
    }

    public int op1 (String str)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.op1()");
        return str.length();
    }


    public int op2 (String str)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.op2()");
        return str.length();
    }


    public int op3 (String str)
        throws CCMException
    {
        System.out.println("MySubTypeImpl.op3()");
        return str.length();
    }
};
