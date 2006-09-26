package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.exception.ExceptionTest;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class InterfaceReadonlyAttributeExceptionsTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeExceptionsTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeExceptionsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeExceptionsTest.class);
    }
    
     
    public void testInterfaceAttributeRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                ExceptionTest.getEmptyExceptionSource() +
                "interface IFace { " +
                "   readonly attribute long longAttr raises(SimpleException, EmptyException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getGetExceptions().get(0));
        ExceptionTest.checkEmptyException((MExceptionDef)attr.getGetExceptions().get(1));
    }
}
