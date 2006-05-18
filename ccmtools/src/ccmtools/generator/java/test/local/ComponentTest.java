package ccmtools.generator.java.test.local;

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
		testDir = ccmtoolsDir + "/test/JavaLocalComponents";
	}
	
    public static Test suite()
    {
    	return new TestSuite(ComponentTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Component test cases
    // ------------------------------------------------------------------------
    
	public void testAssemblyNested()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/component_assembly_nested test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	public void testBuildFromInstall()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/build_from_install test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
