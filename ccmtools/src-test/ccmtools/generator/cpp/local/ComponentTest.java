package ccmtools.generator.cpp.local;

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
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	    return new junit.framework.TestSuite(ComponentTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Attribute test cases
    // ------------------------------------------------------------------------
    
    public void testAttributeTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/attribute_types test");
    }
    
    

    // ------------------------------------------------------------------------
    // Misc test cases
    // ------------------------------------------------------------------------

//    public void testComponentDistributed() throws CcmtoolsTestCaseException
//    {
//        executeCommandLine("make -C " + testDir + "/component_distributed test");
//    }        
}
