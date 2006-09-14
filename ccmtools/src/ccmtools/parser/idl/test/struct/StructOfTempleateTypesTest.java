package ccmtools.parser.idl.test.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


public class StructOfTempleateTypesTest extends StructTest
{
    public StructOfTempleateTypesTest()
        throws FileNotFoundException
    {
        super(StructOfTempleateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructOfTempleateTypesTest.class);
    }
         

    public void testStructOfSequenceMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { sequence<short> SeqShortMember; sequence<long,7> BSeqLongMember; };");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            assertEquals(seq.getBound().longValue(), 7);
            MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
        }
    }

    
    public void testStructOfStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { string stringMember; string<7> bstringMember; };");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MStringDef);
            assertEquals(field.getIdentifier(), "stringMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MStringDef);
            MStringDef s = (MStringDef) field.getIdlType();
            assertEquals(s.getBound().longValue(), 7);
            assertEquals(field.getIdentifier(), "bstringMember");
        }
    }
   
    
    public void testStructOfWideStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wstring wstringMember; wstring<13> bwstringMember; };");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MWstringDef);
            assertEquals(field.getIdentifier(), "wstringMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MWstringDef);
            MWstringDef w = (MWstringDef) field.getIdlType();
            assertEquals(w.getBound().longValue(), 13);
            assertEquals(field.getIdentifier(), "bwstringMember");
        }
    }
    

    public void testStructOfFixedMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { fixed<9,2> fixedMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MFixedDef);
        MFixedDef f = (MFixedDef)field.getIdlType();
        assertEquals(f.getDigits(), 9);
        assertEquals(f.getScale(), 2);
        assertEquals(field.getIdentifier(), "fixedMember");
    }
}
