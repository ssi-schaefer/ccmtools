package ccmtools.parser.idl.enumeration;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.constant.ConstantTest;
import ccmtools.parser.idl.module.ModuleTest;


public class EnumModuleTest extends EnumTest
{
    public EnumModuleTest()
        throws FileNotFoundException
    {
        super(EnumModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumModuleTest.class);
    }

    
    public void testEnum() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                    EnumTest.getEnumColorSource() +
                "};", "world");
        
        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));     
        
        assertTrue(module.getContentss().get(0) instanceof MEnumDef);
        MEnumDef enumeration = (MEnumDef)module.getContentss().get(0);
        assertEquals("IDL:world/Color:1.0", CcmModelHelper.getRepositoryId(enumeration));
        checkEnumColor(enumeration);
    }
    
    public void testEnumConstant() throws CcmtoolsException, FileNotFoundException
    {
        MConstantDef constant = ConstantTest.parseSource(
                "module world {" +
                    EnumTest.getEnumColorSource() + 
                "};" +
                
                "const world::Color ENUM_CONST = world::red;", "ENUM_CONST");

        assertTrue(constant.getIdlType() instanceof MEnumDef);
        ScopedName constValue = (ScopedName) constant.getConstValue();
        assertEquals(constValue, new ScopedName("world::red"));
    }
}
