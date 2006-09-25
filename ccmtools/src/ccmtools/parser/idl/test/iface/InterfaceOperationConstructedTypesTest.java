package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class InterfaceOperationConstructedTypesTest extends InterfaceTest
{
    public InterfaceOperationConstructedTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationConstructedTypesTest.class);
    }

    
    public void testInterfaceOperationOfStructTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                StructTest.getStructPersonSource() +
                "interface IFace { " +
                "   Person op(in Person p1, inout Person p2, out Person p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        StructTest.checkStructPerson(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            StructTest.checkStructPerson(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            StructTest.checkStructPerson(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            StructTest.checkStructPerson(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }


    public void testInterfaceOperationOfUnionTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                UnionTest.getUnionOptionalSource() +
                "interface IFace { " +
                "    UnionOptional op(in UnionOptional p1, inout UnionOptional p2, out UnionOptional p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        UnionTest.checkUnionOptional(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            UnionTest.checkUnionOptional(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            UnionTest.checkUnionOptional(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            UnionTest.checkUnionOptional(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }


    public void testInterfaceOperationOfEnumTypes() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                EnumTest.getEnumColorSource() +
                "interface IFace { " +
                "   Color op(in Color p1, inout Color p2, out Color p3);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "op");
        EnumTest.checkEnumColor(op.getIdlType());

        {
            assertTrue(op.getParameters().get(0) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
            EnumTest.checkEnumColor(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p1");            
        }
        {
            assertTrue(op.getParameters().get(1) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(1);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_INOUT);
            EnumTest.checkEnumColor(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p2");            
        }
        {
            assertTrue(op.getParameters().get(2) instanceof MParameterDef);
            MParameterDef parameter = (MParameterDef)op.getParameters().get(2);
            assertEquals(parameter.getDirection(), MParameterMode.PARAM_OUT);
            EnumTest.checkEnumColor(parameter.getIdlType());
            assertEquals(parameter.getIdentifier(), "p3");            
        }              
    }
}
