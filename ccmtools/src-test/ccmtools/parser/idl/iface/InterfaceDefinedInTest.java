package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;


public class InterfaceDefinedInTest extends InterfaceTest
{
    public InterfaceDefinedInTest()
        throws FileNotFoundException
    {
        super(InterfaceDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceDefinedInTest.class);
    }
    
     
    public void testInterfaceDefinedIn() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource("interface EnterpriseComponent { };");

        // Each contained element has to know its container
        assertNotNull(iface.getDefinedIn());
    }         
}
