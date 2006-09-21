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
                "interface IFace { " +
                "   attribute long longAttr getraises(SimpleError);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        MExceptionDef ex = (MExceptionDef)attr.getGetExceptions().iterator().next();
        ExceptionTest.checkSimpleException(ex);
    }
    
    
    public void testInterfaceAttributeSetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr setraises(SimpleError);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        
        MExceptionDef ex = (MExceptionDef)attr.getSetExceptions().iterator().next();
        ExceptionTest.checkSimpleException(ex);
    }
    
    
    public void testInterfaceAttributeGetAndSetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr getraises(SimpleError) setraises(SimpleError);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        {
            MExceptionDef ex = (MExceptionDef)attr.getGetExceptions().iterator().next();
            ExceptionTest.checkSimpleException(ex);
        }
        {
            MExceptionDef ex = (MExceptionDef)attr.getSetExceptions().iterator().next();
            ExceptionTest.checkSimpleException(ex);
        }
    }

    
    public void testInterfaceAttributeSetAndGetRaises() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                ExceptionTest.getSimpleExceptionSource() +
                "interface IFace { " +
                "   attribute long longAttr setraises(SimpleError) getraises(SimpleError);" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
        {
            MExceptionDef ex = (MExceptionDef)attr.getGetExceptions().iterator().next();
            ExceptionTest.checkSimpleException(ex);
        }
        {
            MExceptionDef ex = (MExceptionDef)attr.getSetExceptions().iterator().next();
            ExceptionTest.checkSimpleException(ex);
        }
    }
}
