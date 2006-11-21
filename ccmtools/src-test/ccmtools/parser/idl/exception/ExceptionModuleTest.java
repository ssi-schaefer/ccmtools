package ccmtools.parser.idl.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.module.ModuleTest;


public class ExceptionModuleTest extends ExceptionTest
{
    public ExceptionModuleTest()
        throws FileNotFoundException
    {
        super(ExceptionModuleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ExceptionModuleTest.class);
    }
    

    public void testExceptionModule() throws CcmtoolsException, FileNotFoundException
    {
        MModuleDef module = ModuleTest.parseSource(
                "module world {" +
                "   exception Ex {};" +
                "};", "world");

        assertEquals("IDL:world:1.0", CcmModelHelper.getRepositoryId(module));
        
        assertTrue(module.getContentss().get(0) instanceof MExceptionDef);
        MExceptionDef ex = (MExceptionDef)module.getContentss().get(0);
        assertEquals("IDL:world/Ex:1.0", CcmModelHelper.getRepositoryId(ex));
        assertEquals(ex.getMembers().size() ,0);
    }
}
