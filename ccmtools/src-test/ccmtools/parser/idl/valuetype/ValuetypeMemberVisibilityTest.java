package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueMemberDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class ValuetypeMemberVisibilityTest extends ValuetypeTest
{
    public ValuetypeMemberVisibilityTest()
        throws FileNotFoundException
    {
        super(ValuetypeMemberVisibilityTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeMemberVisibilityTest.class);
    }
    
    
    public void testValuetypePublicMember() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public long longMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");  
        MValueMemberDef member = getMember(value, 0, "longMember");
        assertTrue(member.isPublicMember());
        PrimitiveTest.checkLongType(member);
    }                

    public void testValuetypePrivateMember() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   private long longMember;" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        MValueMemberDef member = getMember(value, 0, "longMember");
        assertFalse(member.isPublicMember());
        PrimitiveTest.checkLongType(member);
    }                
}
