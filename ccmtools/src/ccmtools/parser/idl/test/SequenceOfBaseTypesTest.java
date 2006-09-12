package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class SequenceOfBaseTypesTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public SequenceOfBaseTypesTest()
        throws FileNotFoundException
    {
        super("IDL Sequence Of BaseTypes Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(SequenceOfBaseTypesTest.class);
    }

    
    public void testSequenceOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<float> SeqFloat;");

        assertEquals(alias.getIdentifier(), "SeqFloat");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }

    public void testSequenceOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<double> SeqDouble;");

        assertEquals(alias.getIdentifier(), "SeqDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }

    public void testSequenceOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long double> SeqLDouble;");

        assertEquals(alias.getIdentifier(), "SeqLDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }
    
    
    public void testSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<short> SeqShort;");

        assertEquals(alias.getIdentifier(), "SeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
    }

    public void testSequenceOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long> SeqLong;");

        assertEquals(alias.getIdentifier(), "SeqLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
     
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
    }

    public void testSequenceOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long long> SeqLLong;");

        assertEquals(alias.getIdentifier(), "SeqLLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }             

    
    public void testSequenceOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned short> SeqUShort;");

        assertEquals(alias.getIdentifier(), "SeqUShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }

    public void testSequenceOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long> SeqULong;");

        assertEquals(alias.getIdentifier(), "SeqULong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
     
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }

    public void testSequenceOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long long> SeqULLong;");

        assertEquals(alias.getIdentifier(), "SeqULLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONGLONG);
    }

    
    public void testSequenceOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<char> SeqChar;");

        assertEquals(alias.getIdentifier(), "SeqChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_CHAR);
    }
    
    public void testSequenceOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wchar> SeqWChar;");

        assertEquals(alias.getIdentifier(), "SeqWChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_WCHAR);
    }
    
    
    public void testSequenceOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<boolean> SeqBoolean;");

        assertEquals(alias.getIdentifier(), "SeqBoolean");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_BOOLEAN);
    }
    
    
    public void testSequenceOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<octet> SeqOctet;");

        assertEquals(alias.getIdentifier(), "SeqOctet");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }
    
    
    public void testSequenceOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<any> SeqAny;");

        assertEquals(alias.getIdentifier(), "SeqAny");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }
    
    
    public void testSequenceOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<Object> SeqObject;");

        assertEquals(alias.getIdentifier(), "SeqObject");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }
    
    
    public void testSequenceOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<ValueBase> SeqValueBase;");

        assertEquals(alias.getIdentifier(), "SeqValueBase");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
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
