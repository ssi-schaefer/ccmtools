package ccmtools.generator.java.test.clientlib;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.test.CcmtoolsTestCase;

public class ComponentTest 
	extends CcmtoolsTestCase
{
	private String ccmtoolsDir;
	private String testDir;

	public ComponentTest(String name)
	{
		super(name);
		// get current working directory (this is where build.xml is executed)
		ccmtoolsDir = System.getProperty("user.dir");
		testDir = ccmtoolsDir + "/test/JavaClientLib";
	}
	
    public static Test suite()
    {
    	return new TestSuite(ComponentTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Component test cases
    // ------------------------------------------------------------------------
    
	public void testAttributeSimple()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/attribute_module_simple test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testAttributeTypes()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/attribute_module_type test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
