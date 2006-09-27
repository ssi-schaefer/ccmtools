package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class InterfaceAttributeOfBaseTypesTest extends InterfaceTest
{
    public InterfaceAttributeOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeOfBaseTypesTest.class);
    }

    
    public void testInterfaceAttributeOfFloat() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute float floatAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkFloatType(attr);
            assertEquals(attr.getIdentifier(), "floatAttr");
        }
    }

    public void testInterfaceAttributeOfDouble() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute double doubleAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkDoubleType(attr);
            assertEquals(attr.getIdentifier(), "doubleAttr");
        }
    }

    public void testInterfaceAttributeOfLongDouble() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long double ldoubleAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkLongDoubleType(attr);
            assertEquals(attr.getIdentifier(), "ldoubleAttr");
        }
    }

    public void testInterfaceAttributeOfShort() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute short shortAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkShortType(attr);
            assertEquals(attr.getIdentifier(), "shortAttr");
        }
    }

    public void testInterfaceAttributeOfLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long longAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkLongType(attr);
            assertEquals(attr.getIdentifier(), "longAttr");
        }
    }

    public void testInterfaceAttributeOfLongLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long long llongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkLongLongType(attr);
            assertEquals(attr.getIdentifier(), "llongAttr");
        }
    }

    public void testInterfaceAttributeOfUnsignedShort() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute unsigned short ushortAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkUnsignedShortType(attr);
            assertEquals(attr.getIdentifier(), "ushortAttr");
        }
    }

    public void testInterfaceAttributeOfUnsignedLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute unsigned long ulongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkUnsignedLongType(attr);
            assertEquals(attr.getIdentifier(), "ulongAttr");
        }
    }

    public void testInterfaceAttributeOfUnsignedLongLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute unsigned long long ullongAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkUnsignedLongLongType(attr);
            assertEquals(attr.getIdentifier(), "ullongAttr");
        }
    }

    public void testInterfaceAttributeOfChar() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute char charAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkCharType(attr);
            assertEquals(attr.getIdentifier(), "charAttr");
        }
    }

    public void testInterfaceAttributeOfWChar() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute wchar wcharAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkWideCharType(attr);
            assertEquals(attr.getIdentifier(), "wcharAttr");
        }
    }

    public void testInterfaceAttributeOfBoolean() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute boolean booleanAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkBooleanType(attr);
            assertEquals(attr.getIdentifier(), "booleanAttr");
        }
    }

    public void testInterfaceAttributeOfOctet() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute octet octetAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkOctetType(attr);
            assertEquals(attr.getIdentifier(), "octetAttr");
        }
    }

    public void testInterfaceAttributeOfAny() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute any anyAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkAnyType(attr);
            assertEquals(attr.getIdentifier(), "anyAttr");
        }
    }

    public void testInterfaceAttributeOfObject() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute Object objectAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkObjectType(attr);
            assertEquals(attr.getIdentifier(), "objectAttr");
        }
    }

    public void testInterfaceAttributeOfValueBase() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute ValueBase valueBaseAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkValueBaseType(attr);
            assertEquals(attr.getIdentifier(), "valueBaseAttr");
        }        
    }
    
    public void testInterfaceAttributeOfNative() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "native AID;" + 
                "interface IFace { " +
                "   attribute AID nativeAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkNativeType(attr, "AID");
            assertEquals(attr.getIdentifier(), "nativeAttr");
        }        
    }    
}
