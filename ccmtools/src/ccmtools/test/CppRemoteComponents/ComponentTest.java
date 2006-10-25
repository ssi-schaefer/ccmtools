package ccmtools.test.CppRemoteComponents;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

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
    
    public void testAttributeSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/attribute_simple test");
    }
    
    public void testAttributeModuleSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/attribute_module_simple test");
    }

    public void testAttributeTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/attribute_types test");
    }
    
    public void testAttributeModuleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/attribute_module_types test");
    }
    

    
    // ------------------------------------------------------------------------
    // Misc test cases
    // ------------------------------------------------------------------------

    public void testComponentDistributed() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/component_distributed test");
    }      
}
