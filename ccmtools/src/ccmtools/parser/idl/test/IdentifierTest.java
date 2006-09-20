package ccmtools.parser.idl.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.parser.idl.ScopedName;


public class IdentifierTest extends TestCase
{
    ScopedName a;
    ScopedName b;
    ScopedName c;
    ScopedName d;
    
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
        a = new ScopedName("Hallo");
        b = new ScopedName("Hallo");
        c = new ScopedName("haLLo");        
        d = new ScopedName("Gallo");
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
        Set<ScopedName> set = new HashSet<ScopedName>();
        assertTrue(set.add(a));  // "Hallo"
        assertFalse(set.add(b)); // "Hallo" == "Hallo"
        assertFalse(set.add(c)); // "Hallo" == "haLLo"  
        assertTrue(set.add(d));  // "Hallo" != "Gallo"
    }
    
    public void testSetContains()
    {
        Set<ScopedName> set = new HashSet<ScopedName>();
        assertTrue(set.add(a));  // "Hallo"
        
        assertTrue(set.contains(b));
        assertTrue(set.contains(c));
        assertFalse(set.contains(d));
    }
}
