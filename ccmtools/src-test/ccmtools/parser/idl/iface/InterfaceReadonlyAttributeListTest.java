package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceReadonlyAttributeListTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeListTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeListTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeListTest.class);
    }
    
    
    public void testInterfaceReadonlyAttributeList() throws CcmtoolsException
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
