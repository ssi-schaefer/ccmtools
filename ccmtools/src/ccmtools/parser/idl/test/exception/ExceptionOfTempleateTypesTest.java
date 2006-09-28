package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


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
        MExceptionDef e = parseSource(
                "exception Ex { " +
                "   sequence<short> SeqShortMember; " +
                "   sequence<long,7> BSeqLongMember; " +
                "};");

        assertEquals(e.getIdentifier(), "Ex");

        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            PrimitiveTest.checkShortType((MTyped)seq);
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            PrimitiveTest.checkLongType((MTyped)seq);
            assertEquals(seq.getBound().longValue(), 7);
        }
    }

    
    public void testExceptionOfStringMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource(
                "exception Ex { " +
                "   string stringMember; " +
                "   string<7> bstringMember; " +
                "};");

        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            PrimitiveTest.checkStringType(field);
            assertEquals(field.getIdentifier(), "stringMember");
        }
        {
            MFieldDef field = getMember(e,1);
            PrimitiveTest.checkBoundedStringType(field, 7);
            assertEquals(field.getIdentifier(), "bstringMember");
        }
    }
   
    
    public void testExceptionOfWideStringMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource(
                "exception Ex { " +
                "   wstring wstringMember; " +
                "   wstring<13> bwstringMember; " +
                "};");

        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            PrimitiveTest.checkWideStringType(field);
            assertEquals(field.getIdentifier(), "wstringMember");
        }
        {
            MFieldDef field = getMember(e,1);
            PrimitiveTest.checkBoundedWideStringType(field, 13);
            assertEquals(field.getIdentifier(), "bwstringMember");
        }
    }
    

    public void testExceptionOfFixedMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { fixed<9,3> fixedMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkFixedType(field, 9,3);
        assertEquals(field.getIdentifier(), "fixedMember");
    }
}
