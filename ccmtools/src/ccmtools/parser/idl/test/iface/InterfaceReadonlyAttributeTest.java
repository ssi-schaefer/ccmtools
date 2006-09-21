package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class InterfaceReadonlyAttributeTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeTest.class);
    }
    
     
    public void testInterfaceReadonlyAttributeBaseType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute long longAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        assertTrue(attr.isReadonly());
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
    }

    public void testInterfaceReadonlyAttributeListBaseType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute long longAttr1, longAttr2;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertTrue(attr.isReadonly());
            assertEquals(attr.getIdentifier(), "longAttr1");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertTrue(attr.isReadonly());
            assertEquals(attr.getIdentifier(), "longAttr2");
        }

    }

}
