package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.module.ModuleTest;


public class StructModuleTest extends StructTest
{
    public StructModuleTest()
        throws FileNotFoundException
    {
        super(StructModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructModuleTest.class);
    }
        

    public void testModuleStruct1() throws Exception
    {
        try
        {
            MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module europe {" +
                "       struct Person { long id; string name; };" +
                "       struct Address { long id; ::Person person; };" +
                "   };" +
                "};", 
                "europe");
            fail();
        }
        catch(Exception e)
        {
            // OK
        }
    }
    
    public void testModuleStruct2() throws Exception
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module europe {" +
                "       struct Person { long id; string name; };" +
                "       struct Address { long id; Person person; };" +
                "   };" +
                "};", 
                "europe");
    }
    
    public void testModuleStruct3() throws Exception
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module europe {" +
                "       struct Person { long id; string name; };" +
                "       struct Address { long id; ::world::europe::Person person; };" +
                "   };" +
                "};", 
                "europe");
    }
        
    public void testModuleStruct4() throws Exception
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module europe {" +
                "       struct Person { long id; string name; };" +
                "       struct Address { long id; world::europe::Person person; };" +
                "   };" +
                "};", 
                "europe");
    }
    
    
    public void testModuleStruct5() throws Exception
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module america {" +
                "       struct Person { long id; string name; };" +
                "   };" +
                
                "   module europe {" +
                "       struct Address { long id; ::world::america::Person person; };" +
                "   };" +
                "};", 
                "europe");
    }
    
    public void testModuleStruct6() throws Exception
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   module america {" +
                "       struct Person { long id; string name; };" +
                "   };" +
                
                "   module europe {" +
                "       struct Address { long id; america::Person person; };" +
                "   };" +
                "};", 
                "europe");
    }

    
  
    public void testModuleStruct7() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "struct Person { long id; string name; };" +
                "module world {" +
                "   struct Address { long id; Person person; };" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));        
        
        assertTrue(module.getContentss().get(0) instanceof MStructDef);
        MStructDef struct = (MStructDef)module.getContentss().get(0);
        assertEquals("IDL:world/Address:1.0", CcmModelHelper.getRepositoryId(struct));
    
        assertTrue(struct.getMember(1) instanceof MFieldDef);
        MFieldDef field = (MFieldDef)struct.getMember(1);        
        assertTrue(field.getIdlType() instanceof MStructDef);
        MStructDef innerStruct = (MStructDef)field.getIdlType();
        assertEquals("IDL:Person:1.0", CcmModelHelper.getRepositoryId(innerStruct));
        
    }
    
    public void testModuleStruct8() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "module world {" +
                "   struct Person { long id; string name; };" +
                "};" +
                "struct Address { long id; world::Person person; };" +
                "", "Address");
        
        assertEquals("IDL:Address:1.0", CcmModelHelper.getRepositoryId(struct)); 
        
        assertTrue(struct.getMember(1) instanceof MFieldDef);
        MFieldDef field = (MFieldDef)struct.getMember(1);
        assertTrue(field.getIdlType() instanceof MStructDef);
        MStructDef innerStruct = (MStructDef)field.getIdlType();
        assertEquals("IDL:world/Person:1.0", CcmModelHelper.getRepositoryId(innerStruct));        
    }
    

}
