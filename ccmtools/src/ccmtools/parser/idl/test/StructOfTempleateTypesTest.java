package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class StructOfTempleateTypesTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public StructOfTempleateTypesTest()
        throws FileNotFoundException
    {
        super("IDL Struct Of TemplateTypes Test");
        
        uiDriver = new ConsoleDriver();
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

        MFieldDef stringMember = struct.getMember(0);
        assertTrue(stringMember.getIdlType() instanceof MStringDef);
        assertEquals(stringMember.getIdentifier(), "stringMember");

        MFieldDef bstringMember = struct.getMember(1);
        assertTrue(bstringMember.getIdlType() instanceof MStringDef);
        MStringDef s = (MStringDef) bstringMember.getIdlType();
        assertEquals(s.getBound().longValue(), 7);
        assertEquals(bstringMember.getIdentifier(), "bstringMember");
    }
   
    
    public void testStructOfWideStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wstring wstringMember; wstring<13> bwstringMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef wstringMember = struct.getMember(0);
        assertTrue(wstringMember.getIdlType() instanceof MWstringDef);
        assertEquals(wstringMember.getIdentifier(), "wstringMember");

        MFieldDef bwstringMember = struct.getMember(1);
        assertTrue(bwstringMember.getIdlType() instanceof MWstringDef);
        MWstringDef w = (MWstringDef)bwstringMember.getIdlType();
        assertEquals(w.getBound().longValue(), 13);
        assertEquals(bwstringMember.getIdentifier(), "bwstringMember");
    }
    

    public void testStructOfFixedMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { fixed<9,2> fixedMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef fixedMember = struct.getMember(0);
        assertTrue(fixedMember.getIdlType() instanceof MFixedDef);
        MFixedDef f = (MFixedDef)fixedMember.getIdlType();
        assertEquals(f.getDigits(), 9);
        assertEquals(f.getScale(), 2);
        assertEquals(fixedMember.getIdentifier(), "fixedMember");
    }

    
    
    /*
     * Utility Methods
     */
    
    private MStructDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MStructDef)modelElements.get(0);
    }
}
