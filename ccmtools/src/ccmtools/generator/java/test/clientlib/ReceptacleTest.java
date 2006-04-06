package ccmtools.generator.java.test.clientlib;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.test.CcmtoolsTestCase;

public class ReceptacleTest 
	extends CcmtoolsTestCase
{
	private String ccmtoolsDir;

	private String testDir;

	public ReceptacleTest(String name)
	{
		super(name);
		// get current working directory (this is where build.xml is executed)
		ccmtoolsDir = System.getProperty("user.dir");
		testDir = ccmtoolsDir + "/test/JavaClientLib";
	}
	
    public static Test suite()
    {
    	return new TestSuite(ReceptacleTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    
//	public void testReceptacleModuleAttributes()
//	{
//		try
//		{
//			executeCommandLine("make -C " + testDir + "/");
//		}
//		catch (Exception e)
//		{
//			fail();
//		}
//	}

	public void testReceptacleModuleConstants()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_module_constants test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleModuleException()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_module_exception test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleModuleInheritance()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_module_inheritance test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleModuleSimple()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_module_simple test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleModuleTypes()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_module_types test-build");
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
//	public void testReceptacleMultiple()
//	{
//		try
//		{
//			executeCommandLine("make -C " + testDir + "/receptacle_module_multiple test-build");
//		}
//		catch (Exception e)
//		{
//			fail();
//		}
//	}
}
