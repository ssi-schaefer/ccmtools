package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class ModuleTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public ModuleTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	return new junit.framework.TestSuite(ModuleTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Module test cases
    // ------------------------------------------------------------------------
    
    public void testModuleMixed()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_mixed test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testModuleNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testModuleReopen()
    {
        try {
            executeCommandLine("make -C " + testDir + "/module_reopen test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
