package ccmtools.parser.idl.test;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.parser.idl.ScopedName;
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
        table.register(new ScopedName("Hallo"));
        try
        {
            table.register(new ScopedName("Hallo"));
            fail();
        }
        catch(Exception e)
        {
            // OK
        }
        
        try
        {
            table.register(new ScopedName("haLLo"));
            fail();
        }
        catch(Exception e)
        {
            // OK
        }
        table.register(new ScopedName("Gallo"));
    }
}
