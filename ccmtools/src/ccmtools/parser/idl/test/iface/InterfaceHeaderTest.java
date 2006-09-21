package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;


public class InterfaceHeaderTest extends InterfaceTest
{
    public InterfaceHeaderTest()
        throws FileNotFoundException
    {
        super(InterfaceHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceHeaderTest.class);
    }
    
     
    public void testEmptyInterface() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource("interface EnterpriseComponent { };");

        assertEquals(iface.getIdentifier(), "EnterpriseComponent");
        assertEquals(iface.getContentss().size(), 0);
    }         
    
    public void testAbstractInterface() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource("abstract interface EnterpriseComponent { };");
        
        assertEquals(iface.getIdentifier(), "EnterpriseComponent");
        assertTrue(iface.isAbstract());
        assertEquals(iface.getContentss().size(), 0);
    }                
    
    public void testLocalInterface() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource("local interface EnterpriseComponent { };");

        assertEquals(iface.getIdentifier(), "EnterpriseComponent");
        assertTrue(iface.isLocal());
        assertEquals(iface.getContentss().size(), 0);
    }

    public void testBaseInterfaces() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface BaseA { };" +
                "interface BaseB { };" +
                "interface SubInterface : BaseA, BaseB {};", "SubInterface");

        assertEquals(iface.getIdentifier(), "SubInterface");
        {
            assertTrue(iface.getBases().get(0) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(0);
            assertEquals(base.getIdentifier(), "BaseA");
        }
        {
            assertTrue(iface.getBases().get(1) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(1);
            assertEquals(base.getIdentifier(), "BaseB");
        }
    }

    public void testAbstractBaseInterfaces() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface BaseA { };" +
                "interface BaseB { };" +
                "abstract interface SubInterface : BaseA, BaseB {};", "SubInterface");

        assertEquals(iface.getIdentifier(), "SubInterface");
        assertTrue(iface.isAbstract());
        {
            assertTrue(iface.getBases().get(0) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(0);
            assertEquals(base.getIdentifier(), "BaseA");
        }
        {
            assertTrue(iface.getBases().get(1) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(1);
            assertEquals(base.getIdentifier(), "BaseB");
        }
    }

    
    public void testLocalBaseInterfaces() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface BaseA { };" +
                "interface BaseB { };" +
                "local interface SubInterface : BaseA, BaseB {};", "SubInterface");

        assertEquals(iface.getIdentifier(), "SubInterface");
        assertTrue(iface.isLocal());
        {
            assertTrue(iface.getBases().get(0) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(0);
            assertEquals(base.getIdentifier(), "BaseA");
        }
        {
            assertTrue(iface.getBases().get(1) instanceof MInterfaceDef);
            MInterfaceDef base = (MInterfaceDef)iface.getBases().get(1);
            assertEquals(base.getIdentifier(), "BaseB");
        }
    }
}
