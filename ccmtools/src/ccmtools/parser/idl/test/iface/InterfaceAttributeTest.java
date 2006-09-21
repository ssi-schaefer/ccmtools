package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class InterfaceAttributeTest extends InterfaceTest
{
    public InterfaceAttributeTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeTest.class);
    }
    
     
    public void testInterfaceAttributeBaseType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long longAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
        
        PrimitiveTest.checkLongType(attr.getIdlType());
        assertEquals(attr.getIdentifier(), "longAttr");
    }

    
    public void testInterfaceAttributeListBaseType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   attribute long longAttr1, longAttr2;" +
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
    }

}
