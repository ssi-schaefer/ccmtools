package ccmtools.generator.java.test.local;

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
        testDir = ccmtoolsDir + "/test/JavaLocalGenerator";
    }

    public static Test suite()
    {
    	return new TestSuite(FacetTest.class);
    }
    
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    
    public void testFacetModuleAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_attributes test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetModuleConstants()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_constants test");
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

    public void testFacetModuleInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_inheritance test");
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
    
    public void testFacetModuleTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_module_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
