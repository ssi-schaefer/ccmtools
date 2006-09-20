package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class SequenceOfTemplateTypesTest extends SequenceTest
{
    public SequenceOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(SequenceOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(SequenceOfTemplateTypesTest.class);
    }

    
    public void testSequenceOfSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<sequence<short> > SeqSeqShort;");

        assertEquals(alias.getIdentifier(), "SeqSeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MSequenceDef);        
        MSequenceDef innerSeq = (MSequenceDef)seq.getIdlType();
        
        PrimitiveTest.checkShortType(innerSeq);   
    }

    
    public void testSequenceOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string> SeqString;");

        assertEquals(alias.getIdentifier(), "SeqString");        
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        PrimitiveTest.checkStringType(seq);
    }
    
    public void testSequenceOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string<6> > SeqBString;"); 

        assertEquals(alias.getIdentifier(), "SeqBString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        PrimitiveTest.checkBoundedStringType(seq, 6);
    }
    
    public void testSequenceOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring> SeqWString;");

        assertEquals(alias.getIdentifier(), "SeqWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        PrimitiveTest.checkWideStringType(seq);
    }
    
    public void testSequenceOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring<7> > SeqBWString;");

        assertEquals(alias.getIdentifier(), "SeqBWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        PrimitiveTest.checkBoundedWideStringType(seq, 7);
    }

    public void testSequenceOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<fixed<9,3> > SeqFixed;");

        assertEquals(alias.getIdentifier(), "SeqFixed");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        PrimitiveTest.checkFixedType(seq);
    }
}
