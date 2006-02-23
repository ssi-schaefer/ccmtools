package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

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
    
    public void testAnyPlugin()
    {
        try {
            executeCommandLine("make -C " + testDir + "/any_plugin test");
        }
        catch(Exception e) {
            fail();
        }
    }  
}
