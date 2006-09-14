package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;


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

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }

    public void testBoundedSequenceOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<double,7> SeqDouble;");

        assertEquals(alias.getIdentifier(), "SeqDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }

    public void testBoundedSequenceOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long double , 7> SeqLDouble;");

        assertEquals(alias.getIdentifier(), "SeqLDouble");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }
    
    
    public void testBoundedSequenceOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<short ,7> SeqShort;");

        assertEquals(alias.getIdentifier(), "SeqShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
    }

    public void testBoundedSequenceOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long, 7> SeqLong;");

        assertEquals(alias.getIdentifier(), "SeqLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
    }

    public void testBoundedSequenceOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<long long,7> SeqLLong;");

        assertEquals(alias.getIdentifier(), "SeqLLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }             

    
    public void testBoundedSequenceOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned short,7> SeqUShort;");

        assertEquals(alias.getIdentifier(), "SeqUShort");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }

    public void testBoundedSequenceOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long,7> SeqULong;");

        assertEquals(alias.getIdentifier(), "SeqULong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }

    public void testBoundedSequenceOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<unsigned long long,8> SeqULLong;");

        assertEquals(alias.getIdentifier(), "SeqULLong");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 8);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONGLONG);
    }

    
    public void testBoundedSequenceOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<char,7> SeqChar;");

        assertEquals(alias.getIdentifier(), "SeqChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_CHAR);
    }
    
    public void testBoundedSequenceOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<wchar,7> SeqWChar;");

        assertEquals(alias.getIdentifier(), "SeqWChar");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_WCHAR);
    }
    
    
    public void testBoundedSequenceOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<boolean,7> SeqBoolean;");

        assertEquals(alias.getIdentifier(), "SeqBoolean");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);

        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_BOOLEAN);
    }
    
    
    public void testBoundedSequenceOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<octet,7> SeqOctet;");

        assertEquals(alias.getIdentifier(), "SeqOctet");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }
    
    
    public void testBoundedSequenceOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<any,7> SeqAny;");

        assertEquals(alias.getIdentifier(), "SeqAny");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }
    
    
    public void testBoundedSequenceOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<Object,7> SeqObject;");

        assertEquals(alias.getIdentifier(), "SeqObject");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 7);
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }
    
    
    public void testBoundedSequenceOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<ValueBase,777> SeqValueBase;");

        assertEquals(alias.getIdentifier(), "SeqValueBase");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 777);
        
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) seq.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
    }
}
