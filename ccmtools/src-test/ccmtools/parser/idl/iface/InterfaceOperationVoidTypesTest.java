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


public class InterfaceOperationVoidTypesTest extends InterfaceTest
{
    public InterfaceOperationVoidTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationVoidTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationVoidTypesTest.class);
    }
    
         
    public void testInterfaceOperationVoidVoid() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   void foo();" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
    }

    public void testInterfaceOperationVoidLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   void foo(in long p);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");
        PrimitiveTest.checkVoidType(op.getIdlType());
        
        assertTrue(op.getParameters().get(0) instanceof MParameterDef);
        MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
        assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
        PrimitiveTest.checkLongType(parameter.getIdlType());
        assertEquals(parameter.getIdentifier(), "p");
    }
    
    public void testInterfaceOperationLongVoid() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   long foo();" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");
        PrimitiveTest.checkLongType(op.getIdlType());
        
        assertEquals(op.getParameters().size(), 0);
    }    
}
