package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class ValuetypeMemberListTest extends ValuetypeTest
{
    public ValuetypeMemberListTest()
        throws FileNotFoundException
    {
        super(ValuetypeMemberListTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeMemberListTest.class);
    }
    
    
    public void testValuetypeMemberList() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public long a,b,c;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkLongType(getMemberType(value, 0, "a"));
        PrimitiveTest.checkLongType(getMemberType(value, 1, "b"));
        PrimitiveTest.checkLongType(getMemberType(value, 2, "c"));
    }                
}
