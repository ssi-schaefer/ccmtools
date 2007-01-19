package ccmtools.parser.idl.enumeration;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDef;


public class EnumDefinedInTest extends EnumTest
{
    public EnumDefinedInTest()
        throws FileNotFoundException
    {
        super(EnumDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumDefinedInTest.class);
    }

    
    public void testEnumDefinedIn() throws CcmtoolsException
    {
        MEnumDef enumeration = parseSource(getEnumColorSource());
        
        // Each contained element has to know its container
        assertNotNull(enumeration.getDefinedIn());
    }
}
