package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.module.ModuleTest;


public class InterfaceModuleTest extends InterfaceTest
{
    public InterfaceModuleTest()
        throws FileNotFoundException
    {
        super(InterfaceModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceModuleTest.class);
    }
    
         
    public void testInterfaceModule() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   interface IFace { " +
                "       void foo();" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0) instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)module.getContentss().get(0);
        assertEquals("IDL:world/IFace:1.0", CcmModelHelper.getRepositoryId(iface));
    }
    
    public void testInterfaceModule2() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   enum Bool {false, true};" +

                "   interface IFace { " +
                "       void f1(in Bool b);" +
                "       void f2(in world::Bool b);" +
                "       void f3(in ::world::Bool b);" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        //assertTrue(module.getContentss().get(0) instanceof MEnumDef);
        MEnumDef enumeration = (MEnumDef)module.getContentss().get(0);
        assertEquals("IDL:world/Bool:1.0", CcmModelHelper.getRepositoryId(enumeration));
        
        assertTrue(module.getContentss().get(1) instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)module.getContentss().get(1);
        assertEquals("IDL:world/IFace:1.0", CcmModelHelper.getRepositoryId(iface));
    }


    public void testInterfaceModule3() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "enum Bool {false, true};" +

                "module world {" +
                "   interface IFace { " +
                "       void f1(in Bool b);" +
                "       void f2(in ::Bool b);" +
                "   };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0) instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)module.getContentss().get(0);
        assertEquals("IDL:world/IFace:1.0", CcmModelHelper.getRepositoryId(iface));
    }


}
