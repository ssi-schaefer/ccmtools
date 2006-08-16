package ccmtools.test.CppRemoteGenerator;

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
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }
    
    public static Test suite()
    {
    	return new junit.framework.TestSuite(ReceptacleTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Receptacle test cases
    // ------------------------------------------------------------------------

    public void testReceptacleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testReceptacleModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_simple test");
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
    
    public void testReceptacleModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_exception test");
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
    
    public void testReceptacleModuleInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_inheritance test");
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
    
    public void testReceptacleModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/receptacle_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
