package ccmtools.test.CppLocalComponents;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

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
    
    public void testModuleMixed() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/module_mixed test");
    }

    public void testModuleNested() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/module_nested test");
    }

    public void testModuleReopen() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/module_reopen test");
    }
}
