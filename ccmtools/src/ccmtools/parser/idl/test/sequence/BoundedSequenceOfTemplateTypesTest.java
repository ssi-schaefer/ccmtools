package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


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
        
        assertTrue(innerSeq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef element = (MPrimitiveDef)innerSeq.getIdlType();
        assertEquals(element.getKind(), MPrimitiveKind.PK_SHORT);            
    }

    
    public void testBoundedSequenceOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string, 7> BSeqString;");

        assertEquals(alias.getIdentifier(), "BSeqString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MStringDef);
    }
    
    public void testBoundedSequenceOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string<6>,7> BSeqBString;");

        assertEquals(alias.getIdentifier(), "BSeqBString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MStringDef);
        MStringDef type = (MStringDef)seq.getIdlType();
        assertEquals(type.getBound().longValue(), 6);
    }
    
    public void testBoundedSequenceOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring,7> BSeqWString;");

        assertEquals(alias.getIdentifier(), "BSeqWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MWstringDef);
    }
    
    public void testBoundedSequenceOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring<6>,7> BSeqBWString;");

        assertEquals(alias.getIdentifier(), "BSeqBWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MWstringDef);
        MWstringDef type = (MWstringDef)seq.getIdlType();
        assertEquals(type.getBound().longValue(), 6);
    }

    public void testBoundedSequenceOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<fixed<9,3>,7> BSeqFixed;");

        assertEquals(alias.getIdentifier(), "BSeqFixed");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef)seq.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 3);
    }
}
