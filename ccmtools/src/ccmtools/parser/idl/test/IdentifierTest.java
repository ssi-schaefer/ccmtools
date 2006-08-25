package ccmtools.parser.idl.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.parser.idl.Identifier;


public class IdentifierTest extends TestCase
{
    Identifier a;
    Identifier b;
    Identifier c;
    Identifier d;
    
    public IdentifierTest()
    {
        super("Identifier Test");
    }
        
    public static Test suite()
    {
        return new TestSuite(IdentifierTest.class);
    }
    
    
    public void setUp()
    {
        a = new Identifier("Hallo");
        b = new Identifier("Hallo");
        c = new Identifier("haLLo");        
        d = new Identifier("Gallo");
    }
    
    public void testEqual()
    {

        assertEquals(a, b);  // "Hallo" == "Hallo"
        assertEquals(a, c);  // "Hallo" == "haLLo"        
    }
    
    public void testNotEqual()
    {
        try
        {
            assertEquals(a, d);
        }
        catch(AssertionFailedError e)
        {
            // "Hallo" != "Gallo"
            return;
        }
        fail();
    }
    
    public void testSetAdd()
    {
        Set<Identifier> set = new HashSet<Identifier>();
        assertTrue(set.add(a));  // "Hallo"
        assertFalse(set.add(b)); // "Hallo" == "Hallo"
        assertFalse(set.add(c)); // "Hallo" == "haLLo"  
        assertTrue(set.add(d));  // "Hallo" != "Gallo"
    }
    
    public void testSetContains()
    {
        Set<Identifier> set = new HashSet<Identifier>();
        assertTrue(set.add(a));  // "Hallo"
        
        assertTrue(set.contains(b));
        assertTrue(set.contains(c));
        assertFalse(set.contains(d));
    }
}
