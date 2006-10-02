package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class ValuetypeForwardDeclarationTest extends ValuetypeTest
{
    public ValuetypeForwardDeclarationTest()
        throws FileNotFoundException
    {
        super(ValuetypeForwardDeclarationTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeForwardDeclarationTest.class);
    }
    
    
    public void testValuetypeForwardDeclaration() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype OneValue;" +

                "valuetype Value : OneValue { };" +

                "valuetype OneValue {" +
                "   public long longMember;" +
                "};", "Value");

        MValueDef base = getBaseValuetype(value, "OneValue");        
        assertEquals(base.getIdentifier(), "OneValue");
        PrimitiveTest.checkLongType(getMemberType(base, 0, "longMember"));               
    }                
    
    public void testCustomValuetypeForwardDeclaration() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype OneValue;" +

                "valuetype Value : OneValue { };" +

                "custom valuetype OneValue {" +
                "   public long longMember;" +
                "};", "Value");

        MValueDef base = getBaseValuetype(value, "OneValue");        
        assertEquals(base.getIdentifier(), "OneValue");
        assertTrue(base.isCustom());
        PrimitiveTest.checkLongType(getMemberType(base, 0, "longMember"));               
    }
    
    public void testAbstractValuetypeForwardDeclaration() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype OneValue;" +
                
                "valuetype Value : OneValue { };" +
                
                "abstract valuetype OneValue {" +
                "   attribute long longAttr;" +
                "};", "Value");

        MValueDef base = getAbstractBase(value, 0, "OneValue");        
        assertEquals(base.getIdentifier(), "OneValue");
        assertTrue(base.isAbstract());
        PrimitiveTest.checkLongType(getAttributeType(base, 0, "longAttr"));               
    }                
}
