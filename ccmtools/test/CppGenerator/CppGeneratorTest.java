package ccmtools.test.CppGenerator;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

public class CppGeneratorTest extends TestCase
{

    // TODO: pass commandline String and calculate String[] from it
    public void runCcmtoolsGenerate(List args)
    {
	String[] parameters = new String[args.size()];
	for(int i=0; i<args.size(); i++) {
	    parameters[i] = (String)args.get(i);
	}
	System.out.print(">>> ccmtools-generate");
	for(int i=0; i< parameters.length ; i++)
	    System.out.print(" " + parameters[i]);
	System.out.println();
	ccmtools.UI.ConsoleCodeGenerator.main(parameters);
    }

    public void createMakefile(String package_root)
    {
	try {
	    File outputFile = new File(package_root + File.separator + "Makefile.py");
	    FileWriter out = new FileWriter(outputFile);
	    out.close();
	}
	catch(Exception e) {
	    fail("Can't create Makefile.py!");
	}
    }

    public void copyFile(String from, String to)
    {
	try {
	    File inputFile = new File(from);
	    File outputFile = new File(to);
	    FileReader in = new FileReader(inputFile);
	    FileWriter out = new FileWriter(outputFile);
	    int c;
	    while ((c = in.read()) != -1)
	    out.write(c);
	    in.close();
	    out.close();
	    System.out.print(">>> copy " + from);
	}
	catch(Exception e) {
	    fail("Can't copy file!");
	}
    }

    public void testVersionOption()
    {
	// ccmtools-generate --version
	List args = new ArrayList();
	args.add("--version");
	runCcmtoolsGenerate(args);
    }

    public void testHelpOption()
    {
	// ccmtools-generate --help
	List args = new ArrayList();
	args.add("--help");
	runCcmtoolsGenerate(args);
    }

    public void testFacetTypes()
    {
	String test_dir = "test/CppGenerator/facet_types";
	String sandbox_dir = "test/CppGenerator/sandbox/facet_types";
	{ 
	    // ccmtools-generate idl3 -o xxx/idl3 Test.idl
	    List args = new ArrayList();
	    args.add("idl3");
	    args.add("-o");
	    args.add(sandbox_dir + "/idl3");
	    args.add(test_dir + "/Test.idl");
	    runCcmtoolsGenerate(args);
	}

	{
	    // ccmtools-generate idl3mirror -o xxx/idl3 Test.idl 
	    List args = new ArrayList();
	    args.add("idl3mirror");
	    args.add("-o");
	    args.add(sandbox_dir + "/idl3");
	    args.add(test_dir + "/Test.idl");
	    runCcmtoolsGenerate(args);
	}

	{
	    // ccmtools-generate c++local -o xxx -Ixxx/idl3/interface xxx/idl3/interface/*.idl
	    List args = new ArrayList();
	    args.add("c++local");
	    args.add("-o");
	    args.add(sandbox_dir);
	    args.add("-I" + sandbox_dir + "/idl3/interface");
	    args.add(sandbox_dir + "/idl3/interface/Color.idl");
	    args.add(sandbox_dir + "/idl3/interface/Console.idl");
	    args.add(sandbox_dir + "/idl3/interface/Map.idl");
	    args.add(sandbox_dir + "/idl3/interface/Pair.idl");
	    args.add(sandbox_dir + "/idl3/interface/TypeTest.idl");
	    args.add(sandbox_dir + "/idl3/interface/doubleArray.idl");
	    args.add(sandbox_dir + "/idl3/interface/time_t.idl");
	    runCcmtoolsGenerate(args);
	}

	{
	    // ccmtools-generate c++local -a -o xxx -Ixxx/idl3/interface 
	    //  -Ixxx/idl3/component xxx/idl3/component/*.idl
	    List args = new ArrayList();
	    args.add("c++local");
	    args.add("-a");
	    args.add("-o");
	    args.add(sandbox_dir);
	    args.add("-I" + sandbox_dir + "/idl3/interface");
	    args.add("-I" + sandbox_dir + "/idl3/component");
	    args.add(sandbox_dir + "/idl3/component/Test.idl");
	    args.add(sandbox_dir + "/idl3/component/TestHome.idl");
	    args.add(sandbox_dir + "/idl3/component/Test_mirror.idl");
	    args.add(sandbox_dir + "/idl3/component/TestHome_mirror.idl");
	    runCcmtoolsGenerate(args);
	}
	
	{
	    // ccmtools-generate c++local-test -o xxx -Ixxx/idl3/interface 
	    // -Ixxx/idl3/component xxx/idl3/component/Test.idl
    	    List args = new ArrayList();
	    args.add("c++local-test");
	    args.add("-o");
	    args.add(sandbox_dir);
	    args.add("-I" + sandbox_dir + "/idl3/interface");
	    args.add("-I" + sandbox_dir + "/idl3/component");
	    args.add(sandbox_dir + "/idl3/component/Test.idl");
	    runCcmtoolsGenerate(args);
	}   

	createMakefile(sandbox_dir);

	copyFile(test_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc",
		 sandbox_dir + "/test/_check_CCM_Local_CCM_Session_Test.cc");
	
	copyFile(test_dir + "/impl/MyObject.cc", sandbox_dir + "/impl/MyObject.cc");
	copyFile(test_dir + "/impl/MyObject.h", sandbox_dir + "/impl/MyObject.h");
	copyFile(test_dir + "/impl/Test_mirror_impl.cc", 
		 sandbox_dir + "/impl/Test_mirror_impl.cc");
	copyFile(test_dir + "/impl/Test_type_test_impl.cc", 
		 sandbox_dir + "/impl/Test_type_test_impl.cc");

    }
}
