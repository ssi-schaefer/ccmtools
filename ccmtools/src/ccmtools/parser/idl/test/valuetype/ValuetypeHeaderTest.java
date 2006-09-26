package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;


public class ValuetypeHeaderTest extends ValuetypeTest
{
    public ValuetypeHeaderTest()
        throws FileNotFoundException
    {
        super(ValuetypeHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeHeaderTest.class);
    }
    
    
    public void testEmptyValuetype() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype EmptyValue { };");
        
        assertEquals(value.getIdentifier(), "EmptyValue");   
        assertEquals(value.getContentss().size(),0);
    }                
    
    
    public void testCustomValuetype() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "custom valuetype CustomValue { };");
        
        assertEquals(value.getIdentifier(), "CustomValue");
        assertTrue(value.isCustom());
        assertEquals(value.getContentss().size(),0);
    }       
}
