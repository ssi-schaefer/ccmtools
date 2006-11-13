package ccmtools.parser.idl.module;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;


public class ModuleComponentTest extends ModuleTest
{
    public ModuleComponentTest()
        throws FileNotFoundException
    {
        super(ModuleComponentTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ModuleComponentTest.class);
    }
    
    public void testModuleComponent() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = parseSource(
                "module world {" +
                "   module europe {" +
                "       component EmptyComponent { };" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0) instanceof MModuleDef);
        MModuleDef innerModule = (MModuleDef)module.getContentss().get(0);
        assertEquals("IDL:world/europe:1.0", CcmModelHelper.getRepositoryId(innerModule));
        
        assertTrue(innerModule.getContentss().get(0) instanceof MComponentDef);
        MComponentDef component = (MComponentDef)innerModule.getContentss().get(0);
        assertEquals("IDL:world/europe/EmptyComponent:1.0", CcmModelHelper.getRepositoryId(component));        
    }      
    
}
