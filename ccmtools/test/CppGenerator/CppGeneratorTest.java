/* CCM Tools : CppGenerator test cases
 * Egon Teiniker <egon.teiniker@salomon.at>
 * Copyright (C) 2002, 2003, 2004 Salomon Automation
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.test.CppGenerator;

import ccmtools.test.CcmtoolsTestCase;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;


/***
 * This test case is derived from CcmtoolsTestCase class and implements
 * tests for idl3, idl3mirror, c++local, c++local-test code generators.
 * These tests check the code generation process, and the runtime behavior 
 * of the generated code (using Confix' _check_*.cc files).  
 ***/
public class CppGeneratorTest extends CcmtoolsTestCase
{
    private String ccmtools_dir;

    public CppGeneratorTest(String name)
    {
	super(name);
	// get current working directory (this is where build.xml is executed)
	ccmtools_dir = System.getProperty("user.dir"); 
    }


    public void testVersionOption()
    {
	runCcmtoolsGenerate("--version");
    }

    public void testHelpOption()
    {
	runCcmtoolsGenerate("--help");
    }
    
    /*
    public void testAttributeTypes()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/attribute_types";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/attribute_types";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Color.idl"
				+ " " + sandbox_dir + "/idl3/interface/Console.idl"
				+ " " + sandbox_dir + "/idl3/interface/Map.idl"
				+ " " + sandbox_dir + "/idl3/interface/Pair.idl"
				+ " " + sandbox_dir + "/idl3/interface/doubleArray.idl"
				+ " " + sandbox_dir + "/idl3/interface/time_t.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl");
	    
	    copyFile(test_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc",
		     sandbox_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc");
	    
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }


    public void testSupportsTypes()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/supports_types";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/supports_types";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Color.idl"
				+ " " + sandbox_dir + "/idl3/interface/Console.idl"
				+ " " + sandbox_dir + "/idl3/interface/Map.idl"
				+ " " + sandbox_dir + "/idl3/interface/Pair.idl"
				+ " " + sandbox_dir + "/idl3/interface/TypeTest.idl"
				+ " " + sandbox_dir + "/idl3/interface/doubleArray.idl"
				+ " " + sandbox_dir + "/idl3/interface/time_t.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl");
	    
	    copyFile(test_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc",
		     sandbox_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc");
	    
	    copyFile(test_dir + "/impl/MyObject.cc", sandbox_dir + "/impl/MyObject.cc");
	    copyFile(test_dir + "/impl/MyObject.h", sandbox_dir + "/impl/MyObject.h");
	    copyFile(test_dir + "/impl/Test_impl.cc",sandbox_dir + "/impl/Test_impl.cc");
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }
    */
    
    /*
    public void testFacetTypes()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/facet_types";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/facet_types";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("idl3mirror -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Color.idl"
				+ " " + sandbox_dir + "/idl3/interface/Console.idl"
				+ " " + sandbox_dir + "/idl3/interface/Map.idl"
				+ " " + sandbox_dir + "/idl3/interface/Pair.idl"
				+ " " + sandbox_dir + "/idl3/interface/TypeTest.idl"
				+ " " + sandbox_dir + "/idl3/interface/doubleArray.idl"
				+ " " + sandbox_dir + "/idl3/interface/time_t.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome.idl"
				+ " " + sandbox_dir + "/idl3/component/Test_mirror.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome_mirror.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl");
	    
	    //	    copyFile(test_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc",
	    //		     sandbox_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc");
	    
	    copyFile(test_dir + "/impl/MyObject.cc", sandbox_dir + "/impl/MyObject.cc");
	    copyFile(test_dir + "/impl/MyObject.h", sandbox_dir + "/impl/MyObject.h");
	    copyFile(test_dir + "/impl/Test_mirror_impl.cc", 
		     sandbox_dir + "/impl/Test_mirror_impl.cc");
	    copyFile(test_dir + "/impl/Test_type_test_impl.cc", 
		     sandbox_dir + "/impl/Test_type_test_impl.cc");
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }
    */

    /*
    public void testReceptacleTypes()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/receptacle_types";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/receptacle_types";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("idl3mirror -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Color.idl"
				+ " " + sandbox_dir + "/idl3/interface/Console.idl"
				+ " " + sandbox_dir + "/idl3/interface/Map.idl"
				+ " " + sandbox_dir + "/idl3/interface/Pair.idl"
				+ " " + sandbox_dir + "/idl3/interface/TypeTest.idl"
				+ " " + sandbox_dir + "/idl3/interface/doubleArray.idl"
				+ " " + sandbox_dir + "/idl3/interface/time_t.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome.idl"
				+ " " + sandbox_dir + "/idl3/component/Test_mirror.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome_mirror.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl");
	    
	    copyFile(test_dir + "/impl/MyObject.cc", sandbox_dir + "/impl/MyObject.cc");
	    copyFile(test_dir + "/impl/MyObject.h", sandbox_dir + "/impl/MyObject.h");
	    copyFile(test_dir + "/impl/Test_impl.cc",sandbox_dir + "/impl/Test_impl.cc");
	    copyFile(test_dir + "/impl/Test_mirror_type_test_mirror_impl.cc", 
		     sandbox_dir + "/impl/Test_mirror_type_test_mirror_impl.cc");
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }

    */

    public void testReceptacleMultiple()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/receptacle_multiple";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/receptacle_multiple";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("idl3mirror -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface" 
				+ " " + sandbox_dir + "/idl3/interface/Console.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome.idl"
				+ " " + sandbox_dir + "/idl3/component/Test_mirror.idl"
				+ " " + sandbox_dir + "/idl3/component/TestHome_mirror.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/interface"
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/Test.idl");
	    
	    copyFile(test_dir + "/impl/Test_impl.cc",sandbox_dir + "/impl/Test_impl.cc");
	    copyFile(test_dir + "/impl/Test_mirror_console_mirror_impl.cc", 
		     sandbox_dir + "/impl/Test_mirror_console_mirror_impl.cc");
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }


    public void testModuleNested()
    {
	String test_dir = ccmtools_dir + "/test/CppGenerator/module_nested";
	String sandbox_dir = ccmtools_dir + "/test/CppGenerator/sandbox/module_nested";

	try {
	    runCcmtoolsGenerate("idl3 -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("idl3mirror -o " + sandbox_dir + "/idl3" 
				+ " " + test_dir + "/Test.idl");
	    
	    runCcmtoolsGenerate("c++local -a -o " + sandbox_dir
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/world/europe/austria/Test.idl"
				+ " " + sandbox_dir + "/idl3/component/world/europe/austria/TestHome.idl"
				+ " " + sandbox_dir + "/idl3/component/world/europe/austria/Test_mirror.idl"
				+ " " + sandbox_dir + "/idl3/component/world/europe/austria/TestHome_mirror.idl");
	    
	    runCcmtoolsGenerate("c++local-test -o " + sandbox_dir 
				+ " -I" + sandbox_dir + "/idl3/component"
				+ " " + sandbox_dir + "/idl3/component/world/europe/austria/Test.idl");
	    
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --bootstrap --configure --make --targets=check") ;
	    runConfix("--packageroot=" + sandbox_dir 
		      + " --make --targets=clean") ;
	}
	catch(Exception e) {
	    fail();
	}
    }     


    // TODO: implement other test cases
}
