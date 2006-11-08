package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;


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
        MStructDef struct = parseSource(
                "struct s { " +
                "   sequence<short> SeqShortMember; " +
                "   sequence<long,7> BSeqLongMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            PrimitiveTest.checkShortType((MTyped)seq);
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MSequenceDef);
            MSequenceDef seq = (MSequenceDef) field.getIdlType();
            assertEquals(seq.getBound().longValue(), 7);
            PrimitiveTest.checkLongType((MTyped)seq);
        }
    }

    
    public void testStructOfStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "struct s { " +
                "   string stringMember; " +
                "   string<7> bstringMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            PrimitiveTest.checkStringType(field);
        }
        {
            MFieldDef field = struct.getMember(1);
            PrimitiveTest.checkBoundedStringType(field, 7);
            assertEquals(field.getIdentifier(), "bstringMember");
        }
    }
   
    
    public void testStructOfWideStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "struct s { " +
                "   wstring wstringMember; " +
                "   wstring<13> bwstringMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            PrimitiveTest.checkWideStringType(field);
            assertEquals(field.getIdentifier(), "wstringMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            PrimitiveTest.checkBoundedWideStringType(field, 13);
            assertEquals(field.getIdentifier(), "bwstringMember");
        }
    }
    

    public void testStructOfFixedMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { fixed<9,3> fixedMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkFixedType(field, 9, 3);
        assertEquals(field.getIdentifier(), "fixedMember");
    }
}
