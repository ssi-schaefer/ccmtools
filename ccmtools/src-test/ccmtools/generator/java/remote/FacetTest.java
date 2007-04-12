package ccmtools.generator.java.remote;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.test.CcmtoolsTestCase;

public class FacetTest 
	extends CcmtoolsTestCase
{
	private final String SEP = File.separator;
    private String ccmtoolsDir;
    private String testDir;

    public FacetTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtoolsDir = System.getProperty("user.dir");
        testDir = ccmtoolsDir + "/test/JavaRemoteComponents";
    }

    public static Test suite()
    {
    		return new TestSuite(FacetTest.class);
    }
    
    
    // ------------------------------------------------------------------------
    // Facet test cases
    // ------------------------------------------------------------------------
    
    public void testFacetAttribute()
    {
        try {
            executeCommandLine("make -C " + testDir + "/facet_attributes test");
        }
        catch(Exception e) {
            fail();
        }
    }

    public void testFacetConstants()
    {
        try 
        {
            executeCommandLine("make -C " + testDir + "/facet_constants test");
        }
        catch(Exception e) 
        {
            fail();
        }
    }

    public void testFacetException()
    {
        try 
        {
            executeCommandLine("make -C " + testDir + "/facet_exceptions test");
        }
        catch(Exception e) 
        {
            fail();
        }
    }

    public void testFacetInheritance()
    {
        try 
        {
            executeCommandLine("make -C " + testDir + "/facet_inheritance test");
        }
        catch(Exception e) 
        {
            fail();
        }
    }

    public void testFacetSimple()
    {
        try 
        {        	
            executeCommandLine("ant -f " + testDir + SEP + "facet_simple" + SEP + "Makefile.xml");            
        }
        catch(Exception e) 
        {
            fail();
        }
    }
    
    public void testFacetTypes()
    {
        try 
        {
            executeCommandLine("make -C " + testDir + "/facet_types test");
        }
        catch(Exception e) 
        {
            fail();
        }
    }
}
