package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceReadonlyAttributeOfBaseTypesTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeOfBaseTypesTest.class);
    }
    
     
    public void testInterfaceReadonlyAttributeOfFloat() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute float floatAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkFloatType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "floatAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfDouble() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute double doubleAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkDoubleType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "doubleAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfLongDouble() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute long double ldoubleAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkLongDoubleType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "ldoubleAttr");
        }
    }
    
    
    public void testInterfaceReadonlyAttributeOfShort() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute short shortAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkShortType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "shortAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute long longAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "longAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfLongLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute long long llongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkLongLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "llongAttr");
        }
    }


    public void testInterfaceReadonlyAttributeOfUnsignedShort() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute unsigned short ushortAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkUnsignedShortType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "ushortAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfUnsignedLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute unsigned long ulongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkUnsignedLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "ulongAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfUnsignedLongLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute unsigned long long ullongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkUnsignedLongLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "ullongAttr");
        }
    }

    
    public void testInterfaceReadonlyAttributeOfChar() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute char charAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkCharType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "charAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfWideChar() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute wchar wcharAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkWideCharType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "wcharAttr");
        }
    }

    public void testInterfaceReadonlyAttributeOfBoolean() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute boolean booleanAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkBooleanType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "booleanAttr");
        }
    }

    public void testInterfaceReadonlyAttributeOfOctet() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute octet octetAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkOctetType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "octetAttr");
        }
    }


    public void testInterfaceReadonlyAttributeOfAny() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute any anyAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkAnyType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "anyAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfObject() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute Object objectAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkObjectType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "objectAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfValueBase() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute ValueBase valueBaseAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkValueBaseType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "valueBaseAttr");
        }
    }    

    public void testInterfaceReadonlyAttributeOfNativeType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "native AID;" + 
                "interface IFace { " +
                "   readonly attribute AID nativeAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkNativeType(attr.getIdlType(), "AID");
            assertEquals(attr.getIdentifier(), "nativeAttr");
        }
    }    

}
