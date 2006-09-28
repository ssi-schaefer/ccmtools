package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class ValuetypeMemberOfConstructedTypeTest extends ValuetypeTest
{
    public ValuetypeMemberOfConstructedTypeTest()
        throws FileNotFoundException
    {
        super(ValuetypeMemberOfConstructedTypeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeMemberOfConstructedTypeTest.class);
    }
    
    
    public void testValuetypeMemberOfStructType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                StructTest.getStructPersonSource() +
                "valuetype Value {" +
                "   public Person structMember;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        StructTest.checkStructPerson(getMemberType(value, 0, "structMember"));
    }                


    public void testValuetypeMemberOfUnionType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                UnionTest.getUnionOptionalSource() +
                "valuetype Value {" +
                "   public UnionOptional unionMember;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        UnionTest.checkUnionOptional(getMemberType(value, 0, "unionMember"));
    }                

    
    public void testValuetypeMemberOfEnum() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                EnumTest.getEnumColorSource() +
                "valuetype Value {" +
                "   public Color enumMember;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        EnumTest.checkEnumColor(getMemberType(value, 0, "enumMember"));
    }                
}
