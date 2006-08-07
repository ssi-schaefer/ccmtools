package example;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;


public class AnyInterfaceImpl 
    extends AnyInterfacePOA
{
    protected ORB orb;

    public AnyInterfaceImpl(ORB orb)
    {
        this.orb = orb;
    }

    public void f1(Any p1)
    {
        printAny(p1);
    }

    public void printAny(Any a)
    {
        int kind = a.type().kind().value();
        switch (kind)
        {
        case TCKind._tk_boolean:
            System.out.println("any is instanceof boolean = " + a.extract_boolean());
            break;

        case TCKind._tk_char:
            System.out.println("any is instanceof char = " + a.extract_char());
            break;

        case TCKind._tk_double:
            System.out.println("any is instanceof double = " + a.extract_double());
            break;

        case TCKind._tk_long:
            System.out.println("any is instanceof long = " + a.extract_long());
            break;

        case TCKind._tk_string:
            System.out.println("any is instanceof string = " + a.extract_string());
            break;

        case TCKind._tk_struct:
            if (PersonHelper.type().equal(a.type()))
            {
                Person p = PersonHelper.extract(a);
                System.out.println("any is instanceof struct Person = {" + p.id + ", " + p.name + "}");
            }
            else
            {
                System.out.println("Unknown struct, type kind: " + a.type().kind().value());
            }
            break;

        case TCKind._tk_alias:
            Any aliasAny = a;
            if (aliasAny.type().equal(StringSequenceHelper.type()))
            {
                System.out.print("any is instanceof sequence StringSequence = [");
                String[] seq = StringSequenceHelper.extract(aliasAny);
                for (int i = 0; i < seq.length; i++)
                {
                    System.out.print(" " + seq[i]);
                }
                System.out.println(" ]");
            }
            else
            {
                System.out.println("Unknown alias, type kind: " + aliasAny.type().kind().value());
            }
            break;

        case TCKind._tk_any:
            System.out.println("any is instance of any = ");
            Any innerAny = a.extract_any();
            printAny(innerAny);
            break;
        }
    }
}
