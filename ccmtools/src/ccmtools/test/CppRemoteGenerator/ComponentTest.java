package ccmtools.test.CppRemoteGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class ComponentTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public ComponentTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }
    
    public static Test suite()
    {
    	return new junit.framework.TestSuite(ComponentTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Attribute test cases
    // ------------------------------------------------------------------------
    
//    public void testAttributeSimple()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/attribute_simple test");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }
    
    public void testAttributeModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }

//    public void testAttributeTypes()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/attribute_types test");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }
    
    public void testAttributeModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
