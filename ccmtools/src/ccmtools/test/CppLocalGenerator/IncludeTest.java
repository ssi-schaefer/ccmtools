package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class IncludeTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public IncludeTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	return new junit.framework.TestSuite(IncludeTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Include test cases
    // ------------------------------------------------------------------------
    //    make -C include_nested test
    
    public void testIncludeNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/include_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }    
}
