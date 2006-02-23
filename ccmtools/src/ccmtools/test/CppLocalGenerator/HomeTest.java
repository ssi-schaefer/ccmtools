package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class HomeTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public HomeTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	return new junit.framework.TestSuite(HomeTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Home test cases
    // ------------------------------------------------------------------------
    
    public void testHomeException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/home_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testHomeTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/home_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
