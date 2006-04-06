package ccmtools.generator.java.test.clientlib;

import junit.framework.Test;
import junit.framework.TestSuite;
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
		testDir = ccmtoolsDir + "/test/JavaClientLib";
	}
	
    public static Test suite()
    {
    	return new TestSuite(SupportsTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    
//    public void testSupportsAttribute()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }

    
//    public void testSupportsConstants()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }
    
    
    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_exception test-build");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
//    public void testSupportsInheritance()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }
    
        
    public void testSupportsSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_simple test-build");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    public void testSupportsTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_module_types test-build");
        }
        catch(Exception e) {
            fail();
        }
    }    
}
