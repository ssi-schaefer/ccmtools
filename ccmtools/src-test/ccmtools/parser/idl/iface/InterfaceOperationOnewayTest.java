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


public class InterfaceOperationOnewayTest extends InterfaceTest
{
    public InterfaceOperationOnewayTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationOnewayTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationOnewayTest.class);
    }
    
         
    public void testInterfaceOperationOnewayVoidVoid() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   oneway void foo();" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");
        assertTrue(op.isOneway());
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
    }

    public void testInterfaceOperationOnewayVoidLong() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   oneway void foo(in long p);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");
        assertTrue(op.isOneway());
        PrimitiveTest.checkVoidType(op.getIdlType());
        
        assertTrue(op.getParameters().get(0) instanceof MParameterDef);
        MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
        assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
        PrimitiveTest.checkLongType(parameter.getIdlType());
        assertEquals(parameter.getIdentifier(), "p");
    }
    
    
    public void testInterfaceOperationOnewayLongVoid() throws CcmtoolsException
    {
        try
        {
            parseSource(
                "interface IFace { " +
                "   oneway long foo();" +
                "};");
            fail(); // shound not reach this line
        }
        catch(Exception e)
        {
            // OK
            System.out.println(e.getMessage());
        }
    }     
}
