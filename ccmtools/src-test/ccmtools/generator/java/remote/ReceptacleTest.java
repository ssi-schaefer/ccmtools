package ccmtools.generator.java.remote;

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
		testDir = ccmtoolsDir + "/test/JavaRemoteComponents";
	}
	
    public static Test suite()
    {
    	return new TestSuite(ReceptacleTest.class);
    }

    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    
	public void testReceptacleAttributes()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_attributes test");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleConstants()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_constants test");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleExceptions()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_exceptions test");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleInheritance()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_inheritance test");
		}
		catch (Exception e)
		{
			fail();
		}
	}

	public void testReceptacleSimple()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_simple test");
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
			executeCommandLine("make -C " + testDir + "/receptacle_types test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	public void testReceptacleMultiple()
	{
		try
		{
			executeCommandLine("make -C " + testDir + "/receptacle_multiple test");
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
