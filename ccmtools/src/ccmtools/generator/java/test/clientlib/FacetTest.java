package ccmtools.generator.java.test.clientlib;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.test.CcmtoolsTestCase;

public class FacetTest 
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public FacetTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/JavaClientLib";
    }

    public static Test suite()
    {
    	return new TestSuite(FacetTest.class);
    }
    
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------

//    public void testFacetModuleAttribute()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }

    public void testFacetModuleConstants()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_constants test-build");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetModuleException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_exception test-build");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetModuleInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_inheritance test-build");
        }
        catch(Exception e) {
            fail();
        }
    }

//    public void testFacetModuleSimple()
//    {
//        try {
//            executeCommandLine("make -C " + testDir + "/facet_module_simple test-build");
//        }
//        catch(Exception e) {
//            fail();
//        }
//    }
    
    public void testFacetModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_types test-build");
        }
        catch(Exception e) {
            fail();
        }
    }
}
