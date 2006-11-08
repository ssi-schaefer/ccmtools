package ccmtools.parser.idl.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;


public class ArrayDefinedInTest extends ArrayTest
{
    public ArrayDefinedInTest()
        throws FileNotFoundException
    {
        super(ArrayDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ArrayDefinedInTest.class);
    }

    public void testArrayDefinedIn() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float ArrayFloat[7][2];");
        
        // Each contained element has to know its container
        assertNotNull(alias.getDefinedIn());
    }
}
