package ccmtools.parser.idl.home;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;


public class HomeHeaderTest extends HomeTest
{
    public HomeHeaderTest()
        throws FileNotFoundException
    {
        super(HomeHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(HomeHeaderTest.class);
    }
    
     
    public void testHomeHeader() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "component MyComponent { };" +
                "home MyHome manages MyComponent {};", "MyHome");

        assertNotNull(home.getComponent());
        MComponentDef component = home.getComponent();
        assertEquals("MyComponent", component.getIdentifier());
        assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(home.getComponent().getHomes().get(0) instanceof MHomeDef);
        MHomeDef home2 = (MHomeDef)home.getComponent().getHomes().get(0);
        assertEquals("MyHome", home2.getIdentifier());        
        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
    }         
    
}
