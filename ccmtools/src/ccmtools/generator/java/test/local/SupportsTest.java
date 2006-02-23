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
    


}
