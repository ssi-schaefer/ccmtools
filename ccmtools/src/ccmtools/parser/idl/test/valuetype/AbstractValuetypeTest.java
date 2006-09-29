package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;


public class AbstractValuetypeTest extends ValuetypeTest
{
    public AbstractValuetypeTest()
        throws FileNotFoundException
    {
        super(AbstractValuetypeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(AbstractValuetypeTest.class);
    }
    
    
    public void testAbstractValuetype() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value { };");
        
        assertEquals(value.getIdentifier(), "Value");
        assertTrue(value.isAbstract());
        assertEquals(value.getContentss().size(),0);
    }
    
    public void testAbstractValuetypeInheritance() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype OneValue { };" +
                "abstract valuetype AnotherValue { };" +
                "abstract valuetype Value : OneValue, AnotherValue { };", "Value");
        
        assertEquals(value.getIdentifier(), "Value");
        assertTrue(value.isAbstract());
        assertEquals(value.getContentss().size(),0);
        getAbstractBase(value, 0, "OneValue");
        getAbstractBase(value, 1, "AnotherValue");        
    }
    
    
    
    
    
}
