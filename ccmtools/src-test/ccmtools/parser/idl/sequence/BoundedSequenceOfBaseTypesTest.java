package ccmtools.parser.idl.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class BoundedSequenceOfBaseTypesTest extends SequenceTest
{
    public BoundedSequenceOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(BoundedSequenceOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(BoundedSequenceOfBaseTypesTest.class);
    }

    
    public void testBoundedSequenceOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<float,7> SeqFloat;");

        assertEquals(alias.getIdentifier(), "SeqFloat");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkFloatType((MTyped)seq);
    }

    public void testBoundedSequenceOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<double,7> SeqDouble;");

        assertEquals(alias.getIdentifier(), "SeqDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkDoubleType((MTyped)seq);
    }

    public void testBoundedSequenceOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long double , 7> SeqLDouble;");

        assertEquals(alias.getIdentifier(), "SeqLDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkLongDoubleType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<short ,7> SeqShort;");

        assertEquals(alias.getIdentifier(), "SeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkShortType((MTyped)seq);
    }

    public void testBoundedSequenceOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long, 7> SeqLong;");

        assertEquals(alias.getIdentifier(), "SeqLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkLongType((MTyped)seq);
    }

    public void testBoundedSequenceOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long long,7> SeqLLong;");

        assertEquals(alias.getIdentifier(), "SeqLLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkLongLongType((MTyped)seq);
    }             

    
    public void testBoundedSequenceOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned short,7> SeqUShort;");

        assertEquals(alias.getIdentifier(), "SeqUShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkUnsignedShortType((MTyped)seq);
    }

    public void testBoundedSequenceOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long,7> SeqULong;");

        assertEquals(alias.getIdentifier(), "SeqULong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkUnsignedLongType((MTyped)seq);
    }

    public void testBoundedSequenceOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long long,8> SeqULLong;");

        assertEquals(alias.getIdentifier(), "SeqULLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 8);
        PrimitiveTest.checkUnsignedLongLongType((MTyped)seq);
    }

    
    public void testBoundedSequenceOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<char,7> SeqChar;");

        assertEquals(alias.getIdentifier(), "SeqChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkCharType((MTyped)seq);
    }
    
    public void testBoundedSequenceOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wchar,7> SeqWChar;");

        assertEquals(alias.getIdentifier(), "SeqWChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkWideCharType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<boolean,7> SeqBoolean;");

        assertEquals(alias.getIdentifier(), "SeqBoolean");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkBooleanType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<octet,7> SeqOctet;");

        assertEquals(alias.getIdentifier(), "SeqOctet");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkOctetType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<any,7> SeqAny;");

        assertEquals(alias.getIdentifier(), "SeqAny");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkAnyType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<Object,7> SeqObject;");

        assertEquals(alias.getIdentifier(), "SeqObject");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        PrimitiveTest.checkObjectType((MTyped)seq);
    }
    
    
    public void testBoundedSequenceOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<ValueBase,777> SeqValueBase;");

        assertEquals(alias.getIdentifier(), "SeqValueBase");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 777);
        PrimitiveTest.checkValueBaseType((MTyped)seq);
    }


    public void testBoundedSequenceOfNativeType() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                "native AID;" + 
                "typedef sequence<AID,666> SeqNative;");

        assertEquals(alias.getIdentifier(), "SeqNative");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 666);
        PrimitiveTest.checkNativeType(seq.getIdlType(), "AID");
    }

}
