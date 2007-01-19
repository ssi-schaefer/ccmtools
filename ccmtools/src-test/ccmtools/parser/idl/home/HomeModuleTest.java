package ccmtools.parser.idl.home;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.module.ModuleTest;


public class HomeModuleTest extends HomeTest
{
    public HomeModuleTest()
        throws FileNotFoundException
    {
        super(HomeModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(HomeModuleTest.class);
    }
    
     
    public void testHomeModule1() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   component MyComponent { };" +
                "   home MyHome1 manages MyComponent {};" +
                "   home MyHome2 manages world::MyComponent {};" +
                "   home MyHome3 manages ::world::MyComponent {};" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(1)  instanceof MHomeDef);
        MHomeDef home = (MHomeDef)module.getContentss().get(1);
        assertEquals("IDL:world/MyHome1:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertTrue(home.getComponent() instanceof MComponentDef);
        MComponentDef component = home.getComponent();
        assertEquals("MyComponent", component.getIdentifier());
        assertEquals("IDL:world/MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
    }    
    
    public void testHomeModule2() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "component MyComponent { };" +
                
                "module world {" +
                "   home MyHome1 manages MyComponent {};" +
                "   home MyHome2 manages ::MyComponent {};" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0)  instanceof MHomeDef);
        MHomeDef home = (MHomeDef)module.getContentss().get(0);
        assertEquals("IDL:world/MyHome1:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertTrue(home.getComponent() instanceof MComponentDef);
        MComponentDef component = home.getComponent();
        assertEquals("MyComponent", component.getIdentifier());
        assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
    }    
}
