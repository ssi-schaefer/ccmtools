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
                "interface IFace { " +
                "   readonly attribute long longAttr raises(SimpleError);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        MExceptionDef ex = (MExceptionDef)attr.getGetExceptions().iterator().next();
        ExceptionTest.checkSimpleException(ex);
    }
}
