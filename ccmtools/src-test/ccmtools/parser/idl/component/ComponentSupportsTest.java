package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;


public class ComponentSupportsTest extends ComponentTest
{
    public ComponentSupportsTest()
        throws FileNotFoundException
    {
        super(ComponentSupportsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentSupportsTest.class);
    }
    
     
    public void testComponentSupports() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "interface IFace1 {}; " +
                "interface IFace2 {}; " +
                "component MyComponent " +
                "   supports IFace1,IFace2" +
                "{" +
                "};", "MyComponent");

        assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        {
            assertTrue(component.getSupportss().get(0) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(0);
            assertEquals("IDL:IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
        }
        {
            assertTrue(component.getSupportss().get(1) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(1);
            assertEquals("IDL:IFace2:1.0", CcmModelHelper.getRepositoryId(iface));
        }        
    }    
}
