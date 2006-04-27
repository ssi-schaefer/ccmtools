package ccmtools.generator.java.test.remote;

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
		testDir = ccmtoolsDir + "/test/JavaRemoteComponents";
	}
	
    public static Test suite()
    {
    	return new TestSuite(ComponentTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Component test cases
    // ------------------------------------------------------------------------
    
	public void testAttributes()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/component_attributes test");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testDynamicProvide()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/component_dynamic_provide test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	public void testDynamicConnect()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/component_dynamic_connect test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
