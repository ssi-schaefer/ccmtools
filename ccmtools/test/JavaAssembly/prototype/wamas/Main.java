package wamas;

class Main
{
    public static void main(String[] args)
    {
        try
        {
            H3 home = (H3)H3Deployment.create();
            C3 component = home.create();
            System.out.println("C3.b = "+component.b());
            wamas.Test.I1 c3i1 = component.provide_i1();
            System.out.println("C3->I1.value() = "+c3i1.value());

            wamas.Test.H1 h1 = (wamas.Test.H1)wamas.Test.H1Deployment.create();
            wamas.Test.C1 c1 = h1.create();
            wamas.Test.I1 c1i1 = c1.provide_i1();
            System.out.println("C1->I1.value() = "+c1i1.value());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
