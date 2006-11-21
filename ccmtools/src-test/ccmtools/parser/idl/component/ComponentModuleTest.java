package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.metamodel.ComponentIDL.MComponentDef;
import ccmtools.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.metamodel.ComponentIDL.MUsesDef;
import ccmtools.parser.idl.module.ModuleTest;


public class ComponentModuleTest extends ComponentTest
{
    public ComponentModuleTest()
        throws FileNotFoundException
    {
        super(ComponentModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentModuleTest.class);
    }
    
     
    public void testComponentModuleTest1() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   interface IFace1 {}; " +
                
                "   component AComponent {" +
                "       provides IFace1 port1;" +
                "       provides world::IFace1 port2;" +
                "       provides ::world::IFace1 port3;" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(1)  instanceof MComponentDef);
        MComponentDef component = (MComponentDef)module.getContentss().get(1);
        assertEquals("IDL:world/AComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(component.getFacets().get(0) instanceof MProvidesDef);
        MProvidesDef facet = (MProvidesDef)component.getFacets().get(0);
        assertEquals("port1", facet.getIdentifier());           
            
        assertTrue(facet.getProvides() instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)facet.getProvides();
        assertEquals("IDL:world/IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
    }

    public void testComponentModuleTest2() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   interface IFace1 {}; " +
                
                "   component AComponent {" +
                "       uses IFace1 port1;" +
                "       uses world::IFace1 port2;" +
                "       uses ::world::IFace1 port3;" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(1)  instanceof MComponentDef);
        MComponentDef component = (MComponentDef)module.getContentss().get(1);
        assertEquals("IDL:world/AComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(component.getReceptacles().get(0) instanceof MUsesDef);
        MUsesDef receptacle = (MUsesDef)component.getReceptacles().get(0);
        assertEquals("port1", receptacle.getIdentifier());           
            
        assertTrue(receptacle.getUses() instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)receptacle.getUses();
        assertEquals("IDL:world/IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
    }
    
    
    public void testComponentModuleTest3() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "interface IFace1 {}; " +
                
                "module world {" +                
                "   component AComponent {" +
                "       provides IFace1 port1;" +
                "       provides ::IFace1 port2;" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0)  instanceof MComponentDef);
        MComponentDef component = (MComponentDef)module.getContentss().get(0);
        assertEquals("IDL:world/AComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(component.getFacets().get(0) instanceof MProvidesDef);
        MProvidesDef facet = (MProvidesDef)component.getFacets().get(0);
        assertEquals("port1", facet.getIdentifier());           
            
        assertTrue(facet.getProvides() instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)facet.getProvides();
        assertEquals("IDL:IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
    }

    
    public void testComponentModuleTest4() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "interface IFace1 {}; " +
                
                "module world {" +                
                "   component AComponent {" +
                "       uses IFace1 port1;" +
                "       uses ::IFace1 port2;" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0)  instanceof MComponentDef);
        MComponentDef component = (MComponentDef)module.getContentss().get(0);
        assertEquals("IDL:world/AComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(component.getReceptacles().get(0) instanceof MUsesDef);
        MUsesDef receptacle = (MUsesDef)component.getReceptacles().get(0);
        assertEquals("port1", receptacle.getIdentifier());           
            
        assertTrue(receptacle.getUses() instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)receptacle.getUses();
        assertEquals("IDL:IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
    }

}
