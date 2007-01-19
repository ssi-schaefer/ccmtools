package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class AbstractValuetypeAttributeOfBaseTypesTest extends ValuetypeTest
{
    public AbstractValuetypeAttributeOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(AbstractValuetypeAttributeOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(AbstractValuetypeAttributeOfBaseTypesTest.class);
    }
    
    
    public void testAbstractValuetypeAttributesOfFloat() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute float floatAttr;" + 
                "   attribute double doubleAttr;" +
                "   attribute long double ldoubleAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkFloatType(getAttributeType(value, 0, "floatAttr"));
        PrimitiveTest.checkDoubleType(getAttributeType(value, 1, "doubleAttr"));
        PrimitiveTest.checkLongDoubleType(getAttributeType(value, 2, "ldoubleAttr"));
    }
        
    public void testAbstractValuetypeAttributesOfInteger() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute short shortAttr;" + 
                "   attribute long longAttr;" +
                "   attribute long long llongAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkShortType(getAttributeType(value, 0, "shortAttr"));
        PrimitiveTest.checkLongType(getAttributeType(value, 1, "longAttr"));
        PrimitiveTest.checkLongLongType(getAttributeType(value, 2, "llongAttr"));
    }
        
    public void testAbstractValuetypeAttributesOfUnsignedInteger() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute unsigned short ushortAttr;" + 
                "   attribute unsigned long ulongAttr;" +
                "   attribute unsigned long long ullongAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkUnsignedShortType(getAttributeType(value, 0, "ushortAttr"));
        PrimitiveTest.checkUnsignedLongType(getAttributeType(value, 1, "ulongAttr"));
        PrimitiveTest.checkUnsignedLongLongType(getAttributeType(value, 2, "ullongAttr"));
    }
    
    public void testAbstractValuetypeAttributeOfCharType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute char charAttr;" +
                "   attribute wchar wcharAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkCharType(getAttributeType(value, 0, "charAttr"));
        PrimitiveTest.checkWideCharType(getAttributeType(value, 1, "wcharAttr"));
    }                
    
    public void testAbstractValuetypeAttributeOfBooleanType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute boolean booleanAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkBooleanType(getAttributeType(value, 0, "booleanAttr"));
    }                
    
    public void testAbstractValuetypeAttributeOfOctetType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute octet octetAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkOctetType(getAttributeType(value, 0, "octetAttr"));
    }                
    
    public void testAbstractValuetypeAttributeOfAnyType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute any anyAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkAnyType(getAttributeType(value, 0, "anyAttr"));
    }                
    
    public void testAbstractValuetypeAttributeOfObjectType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute Object objectAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkObjectType(getAttributeType(value, 0, "objectAttr"));
    }                
    
    public void testAbstractValuetypeAttributeOfValueBaseType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype Value {" +
                "   attribute ValueBase valueBaseAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkValueBaseType(getAttributeType(value, 0, "valueBaseAttr"));
    }                

    
    public void testAbstractValuetypeAttributeOfNativeType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "native AID;" + 
                "abstract valuetype Value {" +
                "   attribute AID nativeAttr;" +
                "};", "Value");
        
        assertTrue(value.isAbstract());
        PrimitiveTest.checkNativeType(getAttributeType(value, 0, "nativeAttr"), "AID");
    }       
}
