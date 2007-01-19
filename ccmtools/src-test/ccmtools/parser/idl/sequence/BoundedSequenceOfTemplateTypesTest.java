package ccmtools.parser.idl.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class BoundedSequenceOfTemplateTypesTest extends SequenceTest
{
    public BoundedSequenceOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(BoundedSequenceOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(BoundedSequenceOfTemplateTypesTest.class);
    }

    
    public void testBoundedSequenceOfSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<sequence<short>,5> BSeqSeqShort;");

        assertEquals(alias.getIdentifier(), "BSeqSeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 5);
        
        assertTrue(seq.getIdlType() instanceof MSequenceDef);       
        MSequenceDef innerSeq = (MSequenceDef)seq.getIdlType();         
        PrimitiveTest.checkShortType((MTyped)innerSeq);
    }

    
    public void testBoundedSequenceOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string, 7> BSeqString;");

        assertEquals(alias.getIdentifier(), "BSeqString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);        
        PrimitiveTest.checkStringType((MTyped)seq);
    }
    
    public void testBoundedSequenceOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string<6>,7> BSeqBString;");

        assertEquals(alias.getIdentifier(), "BSeqBString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkBoundedStringType((MTyped)seq, 6);
    }
    
    public void testBoundedSequenceOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring,7> BSeqWString;");

        assertEquals(alias.getIdentifier(), "BSeqWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkWideStringType((MTyped)seq);
    }
    
    public void testBoundedSequenceOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring<6>,7> BSeqBWString;");

        assertEquals(alias.getIdentifier(), "BSeqBWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkBoundedWideStringType((MTyped)seq, 6);
    }

    public void testBoundedSequenceOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<fixed<9,3>,7> BSeqFixed;");

        assertEquals(alias.getIdentifier(), "BSeqFixed");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkFixedType(seq.getIdlType(),9,3);
    }
}
