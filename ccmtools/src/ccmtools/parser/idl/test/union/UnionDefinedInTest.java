package ccmtools.parser.idl.test.union;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MUnionDef;


public class UnionDefinedInTest extends UnionTest
{
    public UnionDefinedInTest(String title)
        throws FileNotFoundException
    {
        super(title);
    }
        
    
    public static Test suite()
    {
        return new TestSuite(UnionDefinedInTest.class);
    }
    
 
    public void testUnionDefinedIn() throws CcmtoolsException
    {
        MUnionDef union = parseSource(getUnionOptionalSource());
        
        // Each contained element has to know its container
        assertNotNull(union.getDefinedIn());
    }    
}
