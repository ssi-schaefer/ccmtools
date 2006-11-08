package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceOperationContextTest extends InterfaceTest
{
    public InterfaceOperationContextTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationContextTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationContextTest.class);
    }
    
         
    public void testInterfaceOperationWithContext() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   void foo() context (\"CCM_LOCAL\", \"CCM_REMOTE\");" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");     
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
        assertEquals(op.getContexts(), "CCM_LOCAL CCM_REMOTE");        
    }

    public void testInterfaceOperationOnewayWithContext() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   oneway void foo() context (\"CCM_LOCAL\", \"CCM_REMOTE\");" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");     
        assertTrue(op.isOneway());
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
        assertEquals(op.getContexts(), "CCM_LOCAL CCM_REMOTE");        
    }
}
