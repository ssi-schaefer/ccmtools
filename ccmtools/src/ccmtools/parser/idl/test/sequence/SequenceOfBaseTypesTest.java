package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class SequenceOfBaseTypesTest extends SequenceTest
{
    public SequenceOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(SequenceOfBaseTypesTest.class.getName());
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
        PrimitiveTest.checkFloatType((MTyped)seq);
    }

    public void testSequenceOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<double> SeqDouble;");

        assertEquals(alias.getIdentifier(), "SeqDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkDoubleType((MTyped)seq);
    }

    public void testSequenceOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long double> SeqLDouble;");

        assertEquals(alias.getIdentifier(), "SeqLDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongDoubleType((MTyped)seq);        
    }
    
    
    public void testSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<short> SeqShort;");

        assertEquals(alias.getIdentifier(), "SeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkShortType((MTyped)seq);        
    }

    public void testSequenceOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long> SeqLong;");

        assertEquals(alias.getIdentifier(), "SeqLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongType((MTyped)seq);     
    }

    public void testSequenceOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long long> SeqLLong;");

        assertEquals(alias.getIdentifier(), "SeqLLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongLongType((MTyped)seq);
    }             

    
    public void testSequenceOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned short> SeqUShort;");

        assertEquals(alias.getIdentifier(), "SeqUShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();        
        PrimitiveTest.checkUnsignedShortType((MTyped)seq);
    }

    public void testSequenceOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long> SeqULong;");

        assertEquals(alias.getIdentifier(), "SeqULong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();     
        PrimitiveTest.checkUnsignedLongType((MTyped)seq);
    }

    public void testSequenceOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long long> SeqULLong;");

        assertEquals(alias.getIdentifier(), "SeqULLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkUnsignedLongLongType((MTyped)seq);
    }

    
    public void testSequenceOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<char> SeqChar;");

        assertEquals(alias.getIdentifier(), "SeqChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkCharType((MTyped)seq);        
    }
    
    public void testSequenceOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wchar> SeqWChar;");

        assertEquals(alias.getIdentifier(), "SeqWChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkWideCharType((MTyped)seq);        
    }
    
    
    public void testSequenceOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<boolean> SeqBoolean;");

        assertEquals(alias.getIdentifier(), "SeqBoolean");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkBooleanType((MTyped)seq);
    }
    
    
    public void testSequenceOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<octet> SeqOctet;");

        assertEquals(alias.getIdentifier(), "SeqOctet");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkOctetType((MTyped)seq);
    }
    
    
    public void testSequenceOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<any> SeqAny;");

        assertEquals(alias.getIdentifier(), "SeqAny");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkAnyType((MTyped)seq);
    }
    
    
    public void testSequenceOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<Object> SeqObject;");

        assertEquals(alias.getIdentifier(), "SeqObject");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkObjectType((MTyped)seq);
    }
    
    
    public void testSequenceOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<ValueBase> SeqValueBase;");

        assertEquals(alias.getIdentifier(), "SeqValueBase");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkValueBaseType((MTyped)seq);        
    }

    
    public void testSequenceOfNativeType() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                "native AID;" + 
                "typedef sequence<AID> SeqNative;");

        assertEquals(alias.getIdentifier(), "SeqNative");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkNativeType(seq.getIdlType(), "AID");        
    }

}
