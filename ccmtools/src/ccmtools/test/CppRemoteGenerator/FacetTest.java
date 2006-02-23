package ccmtools.test.CppRemoteGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

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
    
    public void testFacetSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleSimple()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_simple test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    public void testFacetModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
        
    public void testFacetConstants()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_constants test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
