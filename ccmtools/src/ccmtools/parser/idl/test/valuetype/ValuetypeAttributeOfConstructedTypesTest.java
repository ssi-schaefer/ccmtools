package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class ValuetypeAttributeOfConstructedTypesTest extends ValuetypeTest
{
    public ValuetypeAttributeOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeAttributeOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeAttributeOfConstructedTypesTest.class);
    }
    
    
    public void testValuetypeAttributeOfStructType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                StructTest.getStructPersonSource() +
                "valuetype Value {" +
                "   attribute Person structAttr;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        StructTest.checkStructPerson(getAttributeType(value, 0, "structAttr"));
    }             

    public void testValuetypeAttributeOfUnionType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                UnionTest.getUnionOptionalSource() +
                "valuetype Value {" +
                "   attribute UnionOptional unionAttr;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        UnionTest.checkUnionOptional(getAttributeType(value, 0, "unionAttr"));
    }                

    
    public void testValuetypeAttributeOfEnum() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                EnumTest.getEnumColorSource() +
                "valuetype Value {" +
                "   attribute Color enumAttr;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        EnumTest.checkEnumColor(getAttributeType(value, 0, "enumAttr"));
    }                
}
