package ccmtools.generator.cpp.remote;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

public class SupportsTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public SupportsTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }
    
    public static Test suite()
    {
        return new junit.framework.TestSuite(SupportsTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    
    public void testSupportsSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_simple test");
    }
    
    public void testSupportsModuleSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_module_simple test");
    }
    
    public void testSupportsException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_exception test");
    }
    
    public void testSupportsModuleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_module_exception test");
    }
    
    public void testSupportsTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_types test");
    }
    
    public void testSupportsModuleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_module_types test");
    }
}
