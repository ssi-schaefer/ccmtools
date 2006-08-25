package ccmtools.parser.idl.test;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.parser.idl.Identifier;
import ccmtools.parser.idl.IdentifierTable;


public class IdentifierTableTest extends TestCase
{
    public IdentifierTableTest()
    {
        super("Identifier Table Test");
    }
        
    public static Test suite()
    {
        return new TestSuite(IdentifierTableTest.class);
    }
    
    
    public void testRegister()
    {
        IdentifierTable table = new IdentifierTable();
        table.register(new Identifier("Hallo"));
        try
        {
            table.register(new Identifier("Hallo"));
            fail();
        }
        catch(Exception e)
        {
            // OK
        }
        
        try
        {
            table.register(new Identifier("haLLo"));
            fail();
        }
        catch(Exception e)
        {
            // OK
        }
        table.register(new Identifier("Gallo"));
    }
}
