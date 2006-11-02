package ccmtools.test.CppRemoteComponents;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

public class ReceptacleTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public ReceptacleTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }
    
    public static Test suite()
    {
        return new junit.framework.TestSuite(ReceptacleTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------

    public void testReceptacleSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_simple test");
    }
    
    public void testReceptacleModuleSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_module_simple test");
    }  
    
    public void testReceptacleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_exception test");
    }
    
    public void testReceptacleModuleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_module_exception test");
    }
        
    public void testReceptacleInheritance() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_inheritance test");
    }
    
    public void testReceptacleModuleInheritance() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_module_inheritance test");
    }
    
    public void testReceptacleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_types test");
    }
    
    public void testReceptacleModuleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_module_types test");
    }
}
