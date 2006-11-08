package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.sequence.SequenceTest;


public class ValuetypeMemberOfTemplateTypeTest extends ValuetypeTest
{
    public ValuetypeMemberOfTemplateTypeTest()
        throws FileNotFoundException
    {
        super(ValuetypeMemberOfTemplateTypeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeMemberOfTemplateTypeTest.class);
    }
    
    
    public void testValuetypeMemberOfSequenceType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                SequenceTest.getBoundedLongSequenceSource() +
                SequenceTest.getLongSequenceSource() +
                "valuetype Value {" +
                "   public LongSequence longSeqMember;" +
                "   public BoundedLongSequence blongSeqMember;" +
                "   public sequence<short> shortSeqMember; " +
                "   public sequence<short,7> bshortSeqMember; " +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        SequenceTest.checkLongSequence(getMemberType(value, 0, "longSeqMember"));
        SequenceTest.checkBoundedLongSequence(getMemberType(value, 1, "blongSeqMember"));
        {
            MSequenceDef seq = (MSequenceDef)getMemberType(value, 2, "shortSeqMember");
            PrimitiveTest.checkShortType(seq.getIdlType());
        }
        {
            MSequenceDef seq = (MSequenceDef)getMemberType(value, 3, "bshortSeqMember");
            assertEquals(seq.getBound().intValue(),7);
            PrimitiveTest.checkShortType(seq.getIdlType());
        }
    }                


    public void testValuetypeMemberOfStringType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public string stringMember;" +
                "   public string<4> bstringMember;" +
                "   public wstring wstringMember; " +
                "   public wstring<5> bwstringMember; " +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkStringType(getMemberType(value, 0, "stringMember"));
        PrimitiveTest.checkBoundedStringType(getMemberType(value, 1, "bstringMember"),4);
        PrimitiveTest.checkWideStringType(getMemberType(value, 2, "wstringMember"));
        PrimitiveTest.checkBoundedWideStringType(getMemberType(value, 3, "bwstringMember"),5);
    }                

    
    public void testValuetypeMemberOfFixedType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   public fixed<7,2> fixedMember;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkFixedType(getMemberType(value, 0, "fixedMember"), 7,2);
    }                
}
