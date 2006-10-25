package ccmtools.test.CppLocalComponents;

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
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
        return new junit.framework.TestSuite(ReceptacleTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    
    public void testReceptacleAttribute() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_attribute test");
    }

    public void testReceptacleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_exception test");
    }

    public void testReceptacleInheritance() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_inheritance test");
    }

    public void testReceptacleMultiple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_multiple test");
    }

    public void testReceptacleNotConnected() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_not_connected test");
    }

    public void testReceptacleObject() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_object test");
    }
    
    public void testReceptacleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/receptacle_types test");
    }
}
