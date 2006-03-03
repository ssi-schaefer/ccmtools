package ccmtools.generator.java.test.local;

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
		testDir = ccmtoolsDir + "/test/JavaLocalGenerator";
	}
	
    public static Test suite()
    {
    	return new TestSuite(SupportsTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    
    public void testSupportsAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_attributes test");
        }
        catch(Exception e) {
            fail();
        }
    }

    
    public void testSupportsConstants()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_constants test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_exceptions test");
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
    
        
    public void testSupportsSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_simple test");
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
