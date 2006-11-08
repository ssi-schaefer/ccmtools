package ccmtools.parser.idl.enumeration;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MEnumDef;


public class EnumColorTest extends EnumTest
{
    public EnumColorTest()
        throws FileNotFoundException
    {
        super(EnumColorTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumColorTest.class);
    }

    
    public void testEnum() throws CcmtoolsException
    {
        MEnumDef enumeration = parseSource(getEnumColorSource());
        checkEnumColor(enumeration);
    }
}
