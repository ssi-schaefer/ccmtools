/*
 * Created on Aug 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ccmtools.CppGeneratorTest;

import ccmtools.CcmtoolsTestCase;

/**
 * @author eteinik
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CppGeneratorSimpleTest extends CcmtoolsTestCase{

    private String ccmtools_dir;

    public CppGeneratorSimpleTest(String name)
    {
        super(name);
        // get current working directory (this is where build.xml is executed)
        ccmtools_dir = System.getProperty("user.dir"); 
    }
    
    public void testFacetSimple()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/facet_rename";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/facet_rename";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("idl3mirror -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Display.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Console.idl"
				+ " " + sandbox_dir + "/idl3/component/HConsole.idl"
				+ " " + sandbox_dir + "/idl3/component/Console_mirror.idl"
				+ " " + sandbox_dir + "/idl3/component/HConsole_mirror.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Console.idl");
	    /*
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
		*/
	}
	catch(Exception e) {
	    fail();
	}
    }

    
    
}
