package ccmtools.generator.cpp.local;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

public class AnyTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public AnyTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	    return new junit.framework.TestSuite(AnyTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Any test cases
    // ------------------------------------------------------------------------
    
    public void testAny() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/any test");
    }  
    
    public void testAnyPlugin() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/any_plugin test");
    }  
}
