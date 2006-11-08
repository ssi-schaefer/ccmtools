package ccmtools.generator.cpp.local;

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
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
        return new junit.framework.TestSuite(FacetTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    
    public void testFacetAttribute() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_attribute test");
    }

    public void testFacetException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_exception test");
    }

    public void testFacetInheritance() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_inheritance test");
    }

    public void testFacetRename() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_rename test");
    }

    public void testFacetTypes() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_types test");
    }

    public void testFacetModuleException() throws CcmtoolsTestCaseException
    {
        executeCommandLine("make -C " + testDir + "/facet_module_exception test");
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
