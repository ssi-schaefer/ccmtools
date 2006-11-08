package ccmtools.parser.idl.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;


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

        PrimitiveTest.checkFloatType((MTyped)array);
    }

    public void testArrayOfDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef double ArrayDouble[7];");

        assertEquals(alias.getIdentifier(), "ArrayDouble");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkDoubleType((MTyped)array);
    }

    public void testArrayOfLongDouble() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long double ArrayLDouble[7];");

        assertEquals(alias.getIdentifier(), "ArrayLDouble");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkLongDoubleType((MTyped)array);
    }
    

    public void testArrayOfShort() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef short ArrayShort[3];");

        assertEquals(alias.getIdentifier(), "ArrayShort");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        PrimitiveTest.checkShortType((MTyped)array);
    }

    public void testArrayOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long ArrayLong[3];");

        assertEquals(alias.getIdentifier(), "ArrayLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        PrimitiveTest.checkLongType((MTyped)array);
    }

    public void testArrayOfLongLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long long ArrayLLong[3];");

        assertEquals(alias.getIdentifier(), "ArrayLLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 3);

        PrimitiveTest.checkLongLongType((MTyped)array);
    }             

    
    public void testArrayOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned short ArrayUShort[7];");

        assertEquals(alias.getIdentifier(), "ArrayUShort");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkUnsignedShortType((MTyped)array);
    }

    public void testArrayOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long ArrayULong[7];");

        assertEquals(alias.getIdentifier(), "ArrayULong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkUnsignedLongType((MTyped)array);
    }

    public void testArrayOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long long ArrayULLong[7];");

        assertEquals(alias.getIdentifier(), "ArrayULLong");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkUnsignedLongLongType((MTyped)array);
    }

    
    public void testArrayOfChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef char ArrayChar[7];");

        assertEquals(alias.getIdentifier(), "ArrayChar");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkCharType((MTyped)array);
    }
    
    public void testArrayOfWChar() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wchar ArrayWChar[7];");

        assertEquals(alias.getIdentifier(), "ArrayWChar");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkWideCharType((MTyped)array);
    }
    
    
    public void testArrayOfBoolean() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef boolean ArrayBoolean[7];");

        assertEquals(alias.getIdentifier(), "ArrayBoolean");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkBooleanType((MTyped)array);
    }
    
    
    public void testArrayOfOctet() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef octet ArrayOctet[7];");

        assertEquals(alias.getIdentifier(), "ArrayOctet");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkOctetType((MTyped)array);
    }
    
    
    public void testArrayOfAny() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef any ArrayAny[7];");

        assertEquals(alias.getIdentifier(), "ArrayAny");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkAnyType((MTyped)array);
    }
    
    
    public void testArrayOfObject() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef Object ArrayObject[7];");

        assertEquals(alias.getIdentifier(), "ArrayObject");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkObjectType((MTyped)array);    
    }
    
    
    public void testArrayOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef ValueBase ArrayValueBase[777];");

        assertEquals(alias.getIdentifier(), "ArrayValueBase");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 777);
        
        PrimitiveTest.checkValueBaseType((MTyped)array);
    }
    
    
    public void testStructOfNativeMembers() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                "native AID;" + 
                "typedef AID ArrayNative[666];");

        assertEquals(alias.getIdentifier(), "ArrayNative");

        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 666);

        PrimitiveTest.checkNativeType(array.getIdlType(), "AID");
    }    
}
