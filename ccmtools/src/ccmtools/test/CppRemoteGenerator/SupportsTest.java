package ccmtools.test.CppRemoteGenerator;

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
    
    public void testSupportsSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testSupportsModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_simple test");
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
    
    public void testSupportsModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_exception test");
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
    
    public void testSupportsModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
