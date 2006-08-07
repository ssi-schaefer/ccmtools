package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

import example.*;


public class AnyTest 
    extends TestCase
{
    private ORB orb;
    private AnyInterface objectUnderTest;
    
    public AnyTest()
    {
        super("AnyTest");
    }
    
    public void setUp()
    {
        String[] args = new String[0];
        try
        {
            // Setup ORB and the CORBA object under test.
            // Note that we use a co-located setup (client and server run in the same process).
            orb = ORB.init(args, null);
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();
            Servant servant = new AnyInterfaceImpl(orb);
            org.omg.CORBA.Object obj = poa.servant_to_reference(servant);
//            String ior = orb.object_to_string(obj);
//            store("AnyTest.ior", ior);
//            String ior = load("AnyTest.ior");
//            org.omg.CORBA.Object obj = orb.string_to_object(ior);
            objectUnderTest = AnyInterfaceHelper.narrow(obj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    public void tearDown()
    {
        orb.destroy();
    }
    
    public void testBoolean()
    {
        Any aBoolean = orb.create_any();
        aBoolean.insert_boolean(true);
        objectUnderTest.f1(aBoolean);
    }
    
    public void testLong()
    {
        Any aLong = orb.create_any();
        aLong.insert_long(7);
        objectUnderTest.f1(aLong);        
    }
    
    public void testDouble()
    {
        Any aDouble = orb.create_any();
        aDouble.insert_double(7.77);
        objectUnderTest.f1(aDouble);      
    }
    
    public void testStruct()
    {
        Any aPerson = orb.create_any();
        Person person = new Person(277, "eteinik");
        PersonHelper.insert(aPerson, person);
        objectUnderTest.f1(aPerson);
    }
    
    public void testSequence()
    {
        Any aSequence = orb.create_any();
        String[] seq = {"egon", "andrea"};
        StringSequenceHelper.insert(aSequence, seq);
        objectUnderTest.f1(aSequence);   
    }
    
    
    // Utility Methods
    
    public void store(String fileName, String ior) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        out.println(ior);
        out.close();
    }
    
    public String load(String fileName) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String ior = in.readLine();
        in.close();
        return ior;
    }
}
