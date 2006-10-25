package ccmtools.test.CppLocalComponents;

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
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
        return new junit.framework.TestSuite(SupportsTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    
    public void testSupportsAttribute() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_attribute test");
    }

    public void testSupportsException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_exception test");
    }

    public void testSupportsInheritance() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_inheritance test");
    }

    public void testSupportsTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/supports_types test");
    }    
}
