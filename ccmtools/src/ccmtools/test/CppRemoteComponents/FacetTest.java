package ccmtools.test.CppRemoteComponents;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.test.CcmtoolsTestCaseException;

public class FacetTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public FacetTest(String name)
    {
        super(name);
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppRemoteGenerator";
    }
    
    public static Test suite()
    {
    	return new junit.framework.TestSuite(FacetTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    
    public void testFacetSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_simple test");
    }
    
    public void testFacetModuleSimple() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_module_simple test");
    }
    
    public void testFacetException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_exception test");
    }
    
    public void testFacetModuleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_module_exception test");
    }
    
    public void testFacetInheritance()throws CcmtoolsTestCaseException 
    {
        executeCommandLine("make -C " + testDir + "/facet_inheritance test");
    }
    
    public void testFacetModuleInheritance()throws CcmtoolsTestCaseException 
    {
        executeCommandLine("make -C " + testDir + "/facet_module_inheritance test");
    }
    
    public void testFacetTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_types test");
    }
    
    public void testFacetModuleTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_module_types test");
    }
        
    public void testFacetConstants() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_constants test");
    }
}
