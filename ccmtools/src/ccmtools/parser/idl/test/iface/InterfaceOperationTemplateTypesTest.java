package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.parser.idl.test.sequence.SequenceTest;


public class InterfaceOperationTemplateTypesTest extends InterfaceTest
{
    public InterfaceOperationTemplateTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationTemplateTypesTest.class);
    }

    
    public void testInterfaceOperationOfSequenceTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getLongSequenceSource() +
                "interface IFace { " +
                "   LongSequence op(in LongSequence p1, inout LongSequence p2, out LongSequence p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        SequenceTest.checkLongSequence(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            SequenceTest.checkLongSequence(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            SequenceTest.checkLongSequence(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            SequenceTest.checkLongSequence(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }

    
    public void testInterfaceOperationOfStringTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   string op1(in string p1, inout string p2, out string p3);" +
                "   string<7> op2(in string<7> p1, inout string<7> p2, out string<7> p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
            MOperationDef op = (MOperationDef) iface.getContentss().get(0);
            assertEquals(op.getIdentifier(), "op1");
            PrimitiveTest.checkStringType(op.getIdlType());

            {
                assertTrue(op.getParameters().get(0) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(0);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
                PrimitiveTest.checkStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p1");
            }
            {
                assertTrue(op.getParameters().get(1) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(1);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
                PrimitiveTest.checkStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p2");
            }
            {
                assertTrue(op.getParameters().get(2) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(2);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
                PrimitiveTest.checkStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p3");
            }
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MOperationDef);
            MOperationDef op = (MOperationDef) iface.getContentss().get(1);
            assertEquals(op.getIdentifier(), "op2");
            PrimitiveTest.checkBoundedStringType(op.getIdlType(),7);

            {
                assertTrue(op.getParameters().get(0) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(0);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
                PrimitiveTest.checkBoundedStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p1");
            }
            {
                assertTrue(op.getParameters().get(1) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(1);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
                PrimitiveTest.checkBoundedStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p2");
            }
            {
                assertTrue(op.getParameters().get(2) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(2);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
                PrimitiveTest.checkBoundedStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p3");
            }
        }
    }

    
    public void testInterfaceOperationOfWideStringTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   wstring op1(in wstring p1, inout wstring p2, out wstring p3);" +
                "   wstring<7> op2(in wstring<7> p1, inout wstring<7> p2, out wstring<7> p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
            MOperationDef op = (MOperationDef) iface.getContentss().get(0);
            assertEquals(op.getIdentifier(), "op1");
            PrimitiveTest.checkWideStringType(op.getIdlType());
            {
                assertTrue(op.getParameters().get(0) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(0);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
                PrimitiveTest.checkWideStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p1");
            }
            {
                assertTrue(op.getParameters().get(1) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(1);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
                PrimitiveTest.checkWideStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p2");
            }
            {
                assertTrue(op.getParameters().get(2) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(2);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
                PrimitiveTest.checkWideStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p3");
            }
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MOperationDef);
            MOperationDef op = (MOperationDef) iface.getContentss().get(1);
            assertEquals(op.getIdentifier(), "op2");
            PrimitiveTest.checkBoundedWideStringType(op.getIdlType(),7);
            {
                assertTrue(op.getParameters().get(0) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(0);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
                PrimitiveTest.checkBoundedWideStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p1");
            }
            {
                assertTrue(op.getParameters().get(1) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(1);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
                PrimitiveTest.checkBoundedWideStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p2");
            }
            {
                assertTrue(op.getParameters().get(2) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef) op.getParameters().get(2);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
                PrimitiveTest.checkBoundedWideStringType(parameter.getIdlType(),7);
                assertEquals(parameter.getIdentifier(), "p3");
            }
        }                
    }

    
    public void testInterfaceOperationOfFixedTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   fixed<9,3> op(in fixed<9,3> p1, inout fixed<9,3> p2, out fixed<9,3> p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        PrimitiveTest.checkFixedType(op.getIdlType(),9,3);

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            PrimitiveTest.checkFixedType(parameter.getIdlType(),9,3);
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            PrimitiveTest.checkFixedType(parameter.getIdlType(),9,3);
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            PrimitiveTest.checkFixedType(parameter.getIdlType(),9,3);
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }
}
