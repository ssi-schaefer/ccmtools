package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceAttributeListTest extends InterfaceTest
{
    public InterfaceAttributeListTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeListTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeListTest.class);
    }
    
         
    public void testInterfaceAttributeList() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long longAttr1, longAttr2, longAttr3;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "longAttr1");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "longAttr2");
        }
        {
            assertTrue(iface.getContentss().get(2) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(2);
            PrimitiveTest.checkLongType(attr.getIdlType());
            assertEquals(attr.getIdentifier(), "longAttr3");
        }
    }
}
