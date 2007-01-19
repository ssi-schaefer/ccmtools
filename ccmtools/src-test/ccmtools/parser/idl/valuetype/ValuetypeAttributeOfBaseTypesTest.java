package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class ValuetypeAttributeOfBaseTypesTest extends ValuetypeTest
{
    public ValuetypeAttributeOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeAttributeOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeAttributeOfBaseTypesTest.class);
    }
    
    
    public void testValuetypeAttributesOfFloat() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   attribute float floatAttr;" + 
                "   attribute double doubleAttr;" +
                "   attribute long double ldoubleAttr;" +
                "};", "Value");
        
        PrimitiveTest.checkFloatType(getAttributeType(value, 0, "floatAttr"));
        PrimitiveTest.checkDoubleType(getAttributeType(value, 1, "doubleAttr"));
        PrimitiveTest.checkLongDoubleType(getAttributeType(value, 2, "ldoubleAttr"));
    }
        
    
    public void testValuetypeAttributeOfNativeType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "native AID;" + 
                "valuetype Value {" +
                "   attribute AID nativeAttr;" +
                "};", "Value");
        
        PrimitiveTest.checkNativeType(getAttributeType(value, 0, "nativeAttr"), "AID");
    }       
}
