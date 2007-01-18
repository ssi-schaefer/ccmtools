package ccmtools.parser.cpp;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Here we test a Java implementation of a simple C PreProcessor (cpp) stolen from
 * the http://jonathan.objectweb.org/ project (org.objectweb.jonathan.tools.JPP). 
 * 
 * Bugs: 
 *  - There must be a space between #include and "" (e.g. #include"xy.idl" is an error)
 *  
 */
public class CppTest 
	extends TestCase
{
	private String ccmtoolsDir;
	private String testDir;

	public CppTest(String name)
	{
		super(name);
		ccmtoolsDir = System.getProperty("user.dir");
		testDir = ccmtoolsDir + File.separator + "test" + File.separator + "CppGenerator";
	}
	
    public static Test suite()
    {
    	    return new TestSuite(CppTest.class);
    }

    
    // ------------------------------------------------------------------------
    // C Preprocessor Test
    // ------------------------------------------------------------------------

    public void testIncludeNested() throws IOException
    {
        String inputDir = testDir + File.separator + "include_nested";
        String inputFile = inputDir + File.separator + "Hello.idl";
        String includePath = inputDir;
        
        Vector includes = new Vector();
        includes.add(includePath);
        
        Hashtable names = new Hashtable();
        
        JPP jpp = new JPP(inputFile, System.out, includes, names);
        jpp.preprocess();

        
    }
}
