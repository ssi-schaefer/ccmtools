package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;


public class ValuetypeDefinedInTest extends ValuetypeTest
{
    public ValuetypeDefinedInTest()
        throws FileNotFoundException
    {
        super(ValuetypeDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeDefinedInTest.class);
    }
    
    
    public void testValuetypeDefinedIn() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   attribute float floatAttr;" + 
                "   attribute double doubleAttr;" +
                "   attribute long double ldoubleAttr;" +
                "};", "Value");
        
        // Each contained element has to know its container
        assertNotNull(value.getDefinedIn());
    }
}
