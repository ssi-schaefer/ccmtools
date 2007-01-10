package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class InterfaceConstantsTest extends InterfaceTest
{
    public InterfaceConstantsTest()
        throws FileNotFoundException
    {
        super(InterfaceConstantsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceConstantsTest.class);
    }

    
    public void testInterfaceConstantOfString() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   const string id = \"001\";" +
                "};", "IFace");

        assertTrue(iface.getContentss().get(0) instanceof MConstantDef);
        MConstantDef constant = (MConstantDef)iface.getContentss().get(0);        
        PrimitiveTest.checkStringType(constant);
        assertEquals(constant.getIdentifier(), "id");
        assertEquals("001", (String)constant.getConstValue());
    }
    

    public void testInterfaceConstantInheritance() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface Base { " +
                "   const string id = \"001\";" +
                "};" +
                "interface IFace { " +
                "   const string id = \"002\";" +
                "};", "IFace");

        assertTrue(iface.getContentss().get(0) instanceof MConstantDef);
        MConstantDef constant = (MConstantDef)iface.getContentss().get(0);        
        PrimitiveTest.checkStringType(constant);
        assertEquals(constant.getIdentifier(), "id");
        assertEquals("002", (String)constant.getConstValue());        
    }


}
