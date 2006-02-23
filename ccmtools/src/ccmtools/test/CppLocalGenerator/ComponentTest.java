package ccmtools.test.CppLocalGenerator;

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
    //    make -C attribute_types test
    
    public void testAttributeTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/attribute_types test");
        }
        catch(Exception e) {
            fail();
        }
    }
    
    
    // ------------------------------------------------------------------------
    // Supports test cases
    // ------------------------------------------------------------------------
    //    make -C supports_attribute test
    //    make -C supports_exception test
    //    make -C supports_inheritance test
    //    make -C supports_types test
    
    public void testSupportsAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_attribute test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsException()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_exception test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsInheritance()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_inheritance test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testSupportsTypes()
    {
        try {
            executeCommandLine("make -C " + testDir + "/supports_types test");
        }
        catch(Exception e) {
            fail();
        }
    }    
}
