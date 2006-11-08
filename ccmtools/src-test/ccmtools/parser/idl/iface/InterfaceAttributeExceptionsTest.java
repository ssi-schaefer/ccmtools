package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.exception.ExceptionTest;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceAttributeExceptionsTest extends InterfaceTest
{
    public InterfaceAttributeExceptionsTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeExceptionsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeExceptionsTest.class);
    }
    
     
    public void testInterfaceAttributeGetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                ExceptionTest.getEmptyExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr getraises(EmptyException, SimpleException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        ExceptionTest.checkEmptyException((MExceptionDef)attr.getGetExceptions().get(0));
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getGetExceptions().get(1));
    }
    
    
    public void testInterfaceAttributeSetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                ExceptionTest.getEmptyExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr setraises(EmptyException, SimpleException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        ExceptionTest.checkEmptyException((MExceptionDef)attr.getSetExceptions().get(0));
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getSetExceptions().get(1));
    }
    
    
    public void testInterfaceAttributeGetAndSetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr getraises(SimpleException) setraises(SimpleException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getGetExceptions().get(0));
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getSetExceptions().get(0));
    }

    
    public void testInterfaceAttributeSetAndGetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr setraises(SimpleException) getraises(SimpleException);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getGetExceptions().get(0));
        ExceptionTest.checkSimpleException((MExceptionDef)attr.getSetExceptions().get(0));        
    }
}
