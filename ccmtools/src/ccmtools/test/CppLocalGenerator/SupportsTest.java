package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

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
    
    public void testSupportsAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_inheritance test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_types test");
        }
        catch(Exception e) {
            fail();
        }
    }    
}
