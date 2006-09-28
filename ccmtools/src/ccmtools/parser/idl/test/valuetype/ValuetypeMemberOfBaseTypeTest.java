package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class ValuetypeMemberOfBaseTypeTest extends ValuetypeTest
{
    public ValuetypeMemberOfBaseTypeTest()
        throws FileNotFoundException
    {
        super(ValuetypeMemberOfBaseTypeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeMemberOfBaseTypeTest.class);
    }
    
    
    public void testValuetypeMemberOfFloatType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public float floatMember;" +
                "   public double doubleMember;" +
                "   public long double ldoubleMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkFloatType(getMemberType(value, 0, "floatMember"));
        PrimitiveTest.checkDoubleType(getMemberType(value, 1, "doubleMember"));
        PrimitiveTest.checkLongDoubleType(getMemberType(value, 2, "ldoubleMember"));
    }                

    public void testValuetypeMemberOfIntegerType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public short shortMember;" +
                "   public long longMember;" +
                "   public long long llongMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkShortType(getMemberType(value, 0, "shortMember"));
        PrimitiveTest.checkLongType(getMemberType(value, 1, "longMember"));
        PrimitiveTest.checkLongLongType(getMemberType(value, 2, "llongMember"));
    }                
    
    public void testValuetypeMemberOfUnsignedIntegerType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public unsigned short ushortMember;" +
                "   public unsigned long ulongMember;" +
                "   public unsigned long long ullongMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkUnsignedShortType(getMemberType(value, 0, "ushortMember"));
        PrimitiveTest.checkUnsignedLongType(getMemberType(value, 1, "ulongMember"));
        PrimitiveTest.checkUnsignedLongLongType(getMemberType(value, 2, "ullongMember"));
    }                

    public void testValuetypeMemberOfCharType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public char charMember;" +
                "   public wchar wcharMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkCharType(getMemberType(value, 0, "charMember"));
        PrimitiveTest.checkWideCharType(getMemberType(value, 1, "wcharMember"));
    }                
    
    public void testValuetypeMemberOfBooleanType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public boolean booleanMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkBooleanType(getMemberType(value, 0, "booleanMember"));
    }                
    
    public void testValuetypeMemberOfOctetType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public octet octetMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkOctetType(getMemberType(value, 0, "octetMember"));
    }                
    
    public void testValuetypeMemberOfAnyType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public any anyMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkAnyType(getMemberType(value, 0, "anyMember"));
    }                
    
    public void testValuetypeMemberOfObjectType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public Object objectMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkObjectType(getMemberType(value, 0, "objectMember"));
    }                
    
    public void testValuetypeMemberOfValueBaseType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public ValueBase valueBaseMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkValueBaseType(getMemberType(value, 0, "valueBaseMember"));
    }                
    
    public void testValuetypeMemberOfNativeType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "native AID;" + 
                "valuetype Value {" +
                "   public AID nativeMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkNativeType(getMemberType(value, 0, "nativeMember"), "AID");
    }       
}
