package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import ccmtools.test.CcmtoolsTestCase;

public class AssemblyTest
	extends CcmtoolsTestCase
{
    private String ccmtoolsDir;
    private String testDir;

    public AssemblyTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/CppGenerator";
    }

    public static Test suite()
    {
    	return new junit.framework.TestSuite(AssemblyTest.class);
    }
	
    
    // ------------------------------------------------------------------------
    // Assembly test cases
    // ------------------------------------------------------------------------
    //    make -C assembly_nested test
    
    public void testAssemblyNested()
    {
        try {
            executeCommandLine("make -C " + testDir + "/assembly_nested test");
        }
        catch(Exception e) {
            fail();
        }
    }
}
