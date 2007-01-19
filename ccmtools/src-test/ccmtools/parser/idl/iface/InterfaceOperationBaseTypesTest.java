package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceOperationBaseTypesTest extends InterfaceTest
{
    public InterfaceOperationBaseTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationBaseTypesTest.class);
    }

    
    public void testInterfaceOperationOfFloatTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   float op(in float p1, inout float p2, out float p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkFloatType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkFloatType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkFloatType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkFloatType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfDoubleTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   double op(in double p1, inout double p2, out double p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkDoubleType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfLongDoubleTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   long double op(in long double p1, inout long double p2, out long double p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkLongDoubleType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkLongDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkLongDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkLongDoubleType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    
        
    public void testInterfaceOperationOfShortTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   short op(in short p1, inout short p2, out short p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkShortType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfLongTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   long op(in long p1, inout long p2, out long p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkLongType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfLongLongTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   long long op(in long long p1, inout long long p2, out long long p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkLongLongType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }


    
    public void testInterfaceOperationOfUnsignedShortTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   unsigned short op(in unsigned short p1, inout unsigned short p2, out unsigned short p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkUnsignedShortType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkUnsignedShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkUnsignedShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkUnsignedShortType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfUnsignedLongTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   unsigned long op(in unsigned long p1, inout unsigned long p2, out unsigned long p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkUnsignedLongType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkUnsignedLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkUnsignedLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkUnsignedLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfUnsignedLongLongTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   unsigned long long op(in unsigned long long p1, " +
                "                         inout unsigned long long p2, " +
                "                         out unsigned long long p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkUnsignedLongLongType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkUnsignedLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkUnsignedLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkUnsignedLongLongType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }
    
    
    public void testInterfaceOperationOfCharTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   char op(in char p1, inout char p2, out char p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkCharType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    public void testInterfaceOperationOfWideCharTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   wchar op(in wchar p1, inout wchar p2, out wchar p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkWideCharType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkWideCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkWideCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkWideCharType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    
    public void testInterfaceOperationOfBooleanTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   boolean op(in boolean p1, inout boolean p2, out boolean p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkBooleanType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkBooleanType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkBooleanType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkBooleanType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    
    public void testInterfaceOperationOfOctetTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   octet op(in octet p1, inout octet p2, out octet p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkOctetType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkOctetType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkOctetType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkOctetType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    
    public void testInterfaceOperationOfAnyTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   any op(in any p1, inout any p2, out any p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkAnyType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkAnyType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkAnyType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkAnyType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }
    
    
    public void testInterfaceOperationOfObjectTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   Object op(in Object p1, inout Object p2, out Object p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkObjectType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkObjectType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkObjectType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkObjectType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }


    public void testInterfaceOperationOfValueBaseTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   ValueBase op(in ValueBase p1, inout ValueBase p2, out ValueBase p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkValueBaseType(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkValueBaseType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkValueBaseType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkValueBaseType(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }


    public void testInterfaceOperationOfNativeTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "native AID;" + 
                "interface IFace { " +
                "   AID op(in AID p1, inout AID p2, out AID p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkNativeType(op.getIdlType(), "AID");

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkNativeType(parameter.getIdlType(), "AID");
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkNativeType(parameter.getIdlType(), "AID");
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkNativeType(parameter.getIdlType(), "AID");
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

}
