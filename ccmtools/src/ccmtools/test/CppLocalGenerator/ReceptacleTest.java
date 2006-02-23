package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
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
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	return new junit.framework.TestSuite(ReceptacleTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------
    
    public void testReceptacleAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_inheritance test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleMultiple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_multiple test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleNotConnected()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_not_connected test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testReceptacleObject()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_object test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
