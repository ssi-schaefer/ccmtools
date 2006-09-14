package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


public class ExceptionOfTempleateTypesTest extends ExceptionTest
{    
    public ExceptionOfTempleateTypesTest()
        throws FileNotFoundException
    {
        super(ExceptionOfTempleateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ExceptionOfTempleateTypesTest.class);
    }
         

    public void testExceptionOfSequenceMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { sequence<short> SeqShortMember; sequence<long,7> BSeqLongMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            assertEquals(seq.getBound().longValue(), 7);
            MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
        }
    }

    
    public void testExceptionOfStringMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { string stringMember; string<7> bstringMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MStringDef);
            assertEquals(field.getIdentifier(), "stringMember");
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MStringDef);
            MStringDef s = (MStringDef) field.getIdlType();
            assertEquals(s.getBound().longValue(), 7);
            assertEquals(field.getIdentifier(), "bstringMember");
        }
    }
   
    
    public void testExceptionOfWideStringMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { wstring wstringMember; wstring<13> bwstringMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MWstringDef);
            assertEquals(field.getIdentifier(), "wstringMember");
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MWstringDef);
            MWstringDef w = (MWstringDef) field.getIdlType();
            assertEquals(w.getBound().longValue(), 13);
            assertEquals(field.getIdentifier(), "bwstringMember");
        }
    }
    

    public void testExceptionOfFixedMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { fixed<9,2> fixedMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MFixedDef);
        MFixedDef f = (MFixedDef)field.getIdlType();
        assertEquals(f.getDigits(), 9);
        assertEquals(f.getScale(), 2);
        assertEquals(field.getIdentifier(), "fixedMember");
    }
}
