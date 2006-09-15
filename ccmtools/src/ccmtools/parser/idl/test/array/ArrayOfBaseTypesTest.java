package ccmtools.parser.idl.test.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;


public class ArrayOfBaseTypesTest extends ArrayTest
{
    public ArrayOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(ArrayOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ArrayOfBaseTypesTest.class);
    }

    
    public void testArrayOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float ArrayFloat[7];");

        assertEquals(alias.getIdentifier(), "ArrayFloat");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }

    public void testArrayOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef double ArrayDouble[7];");

        assertEquals(alias.getIdentifier(), "ArrayDouble");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }

    public void testArrayOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long double ArrayLDouble[7];");

        assertEquals(alias.getIdentifier(), "ArrayLDouble");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }
    

    public void testArrayOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef short ArrayShort[3];");

        assertEquals(alias.getIdentifier(), "ArrayShort");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
    }

    public void testArrayOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long ArrayLong[3];");

        assertEquals(alias.getIdentifier(), "ArrayLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
    }

    public void testArrayOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long long ArrayLLong[3];");

        assertEquals(alias.getIdentifier(), "ArrayLLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }             

    
    public void testArrayOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned short ArrayUShort[7];");

        assertEquals(alias.getIdentifier(), "ArrayUShort");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }

    public void testArrayOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long ArrayULong[7];");

        assertEquals(alias.getIdentifier(), "ArrayULong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }

    public void testArrayOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long long ArrayULLong[7];");

        assertEquals(alias.getIdentifier(), "ArrayULLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONGLONG);
    }

    
    public void testArrayOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef char ArrayChar[7];");

        assertEquals(alias.getIdentifier(), "ArrayChar");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_CHAR);
    }
    
    public void testArrayOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wchar ArrayWChar[7];");

        assertEquals(alias.getIdentifier(), "ArrayWChar");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_WCHAR);
    }
    
    
    public void testArrayOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef boolean ArrayBoolean[7];");

        assertEquals(alias.getIdentifier(), "ArrayBoolean");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_BOOLEAN);
    }
    
    
    public void testArrayOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef octet ArrayOctet[7];");

        assertEquals(alias.getIdentifier(), "ArrayOctet");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }
    
    
    public void testArrayOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef any ArrayAny[7];");

        assertEquals(alias.getIdentifier(), "ArrayAny");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }
    
    
    public void testArrayOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef Object ArrayObject[7];");

        assertEquals(alias.getIdentifier(), "ArrayObject");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }
    
    
    public void testArrayOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef ValueBase ArrayValueBase[777];");

        assertEquals(alias.getIdentifier(), "ArrayValueBase");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 777);
        
        assertTrue(array.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) array.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
    }
}
