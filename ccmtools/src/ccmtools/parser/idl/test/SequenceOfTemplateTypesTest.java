package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class SequenceOfTemplateTypesTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public SequenceOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super("IDL Sequence Of TemplateTypes Test");
        
        uiDriver = new ConsoleDriver();
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
        
        assertTrue(innerSeq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef element = (MPrimitiveDef)innerSeq.getIdlType();
        assertEquals(element.getKind(), MPrimitiveKind.PK_SHORT);
    }

    
    public void testSequenceOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string> SeqString;");

        assertEquals(alias.getIdentifier(), "SeqString");        
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MStringDef);
    }
    
    public void testSequenceOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<string<6> > SeqBString;"); 

        assertEquals(alias.getIdentifier(), "SeqBString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MStringDef);
        MStringDef type = (MStringDef)seq.getIdlType();
        assertEquals(type.getBound().longValue(), 6);
    }
    
    public void testSequenceOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring> SeqWString;");

        assertEquals(alias.getIdentifier(), "SeqWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MWstringDef);
    }
    
    public void testSequenceOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wstring<7> > SeqBWString;");

        assertEquals(alias.getIdentifier(), "SeqBWString");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MWstringDef);
        MWstringDef type = (MWstringDef)seq.getIdlType();
        assertEquals(type.getBound().longValue(), 7);
    }

    public void testSequenceOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<fixed<9,3> > SeqFixed;");

        assertEquals(alias.getIdentifier(), "SeqFixed");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
     
        assertTrue(seq.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef)seq.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 3);
    }
    
    
    /*
     * Utility Methods
     */
    
    private MAliasDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
