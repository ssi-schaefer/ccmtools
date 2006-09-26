package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.test.exception.ExceptionTest;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class InterfaceOperationExceptionsTest extends InterfaceTest
{
    public InterfaceOperationExceptionsTest()
        throws FileNotFoundException
    {
        super(InterfaceOperationExceptionsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceOperationExceptionsTest.class);
    }
    
         
    public void testInterfaceOperationWithExceptions() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getEmptyExceptionSource() +
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   void foo() raises(SimpleException, EmptyException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");     
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
    
        ExceptionTest.checkSimpleException((MExceptionDef)op.getExceptionDefs().get(0));
        ExceptionTest.checkEmptyException((MExceptionDef)op.getExceptionDefs().get(1));
    }
    
    
    public void testInterfaceOperationWithExceptionAndContext() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   void foo() raises(SimpleException) context (\"CCM_LOCAL\", \"CCM_REMOTE\");" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MOperationDef);
        MOperationDef op = (MOperationDef) iface.getContentss().get(0);
        assertEquals(op.getIdentifier(), "foo");     
        PrimitiveTest.checkVoidType(op.getIdlType());

        assertEquals(op.getParameters().size(), 0);
    
        ExceptionTest.checkSimpleException((MExceptionDef)op.getExceptionDefs().get(0));
        
        assertEquals(op.getContexts(), "CCM_LOCAL CCM_REMOTE"); 
    }
}
