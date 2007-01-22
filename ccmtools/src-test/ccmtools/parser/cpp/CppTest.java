package ccmtools.parser.cpp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.test.CcmtoolsTestCase;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class CppTest 
    extends CcmtoolsTestCase
{
	private String ccmtoolsDir;
	private String testDir;
    
    protected UserInterfaceDriver uiDriver;
    
    
	public CppTest(String name) throws FileNotFoundException
	{
		super(name);
        uiDriver = new ConsoleDriver();
		ccmtoolsDir = System.getProperty("user.dir");
		testDir = ccmtoolsDir + File.separator + "test" + File.separator + "Cpp";
	}
	
    public static Test suite()
    {
    	    return new TestSuite(CppTest.class);
    }

    
    // ------------------------------------------------------------------------
    // C Preprocessor Test
    // ------------------------------------------------------------------------

    
    public void testGnuCpp() throws CcmtoolsException
    {
        String inputDir = testDir + File.separator + "gnu_cpp";
        String inputFile = inputDir + File.separator + "Hello.idl";
        
        ParserManager.parseIdlFile(uiDriver, inputFile, false);
    }
    
    public void testCnuCppWithError() 
    {
        String inputDir = testDir + File.separator + "gnu_cpp";
        String inputFile = inputDir + File.separator + "HelloWithError.idl";
        
        try 
        {
            ParserManager.parseIdlFile(uiDriver, inputFile, false);
            fail("Didn't handle syntax error!!!");
        }
        catch(CcmtoolsException e)
        {
            System.out.println("> expected error: " + e.getMessage());
            assertEquals("idl/Hello.idl line 18: Syntax error", e.getMessage());
        }       
    }

    
    public void testWindowsCl() throws CcmtoolsException
    {
        String inputDir = testDir + File.separator + "windows_cl";
        String inputFile = inputDir + File.separator + "Hello.idl";
        
        ParserManager.parseIdlFile(uiDriver, inputFile, false);
    }
    
    public void testWindowsClWithError() 
    {
        String inputDir = testDir + File.separator + "windows_cl";
        String inputFile = inputDir + File.separator + "HelloWithError.idl";
        
        try 
        {
            ParserManager.parseIdlFile(uiDriver, inputFile, false);
            fail("Didn't handle syntax error!!!");
        }
        catch(CcmtoolsException e)
        {
            System.out.println("> expected error: " + e.getMessage());
            assertEquals("Hello.idl line 18: Syntax error", e.getMessage());
        }       
    }

    
    public void testInternalCpp() throws IOException, CcmtoolsException
    {
        String inputDir = testDir + File.separator + "multiple_source_files" + File.separator + "idl";
        String inputFile = inputDir + File.separator + "Hello.idl";
        
        List<String> includes = new ArrayList<String>();
        includes.add(inputDir);
        PreProcessor cpp = new InternalCpp();
        cpp.process(uiDriver, inputFile, includes);
        
        ParserManager.parseIdlFile(uiDriver, inputFile);
    }

    
    public void testInternalCppIdl3Repo() throws IOException, CcmtoolsException
    {
        String inputDir = testDir + File.separator + "multiple_source_files" + File.separator + "idl3Repo";
        String inputFile = inputDir + File.separator + "component" + File.separator + "Hello.idl";
        
        List<String> includes = new ArrayList<String>();
        includes.add(inputDir + File.separator + "interface");
        includes.add(inputDir + File.separator + "component");
        PreProcessor cpp = new InternalCpp();
        cpp.process(uiDriver, inputFile, includes);
        
        ParserManager.parseIdlFile(uiDriver, inputFile);
    
        // Problem: Too many open files!!!!!
        // There is a bug in the stolen cpp implementation
        // see http://jonathan.objectweb.org/ project (org.objectweb.jonathan.tools.JPP)
    }

}
