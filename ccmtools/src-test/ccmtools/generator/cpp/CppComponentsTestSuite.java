package ccmtools.generator.cpp;

import junit.framework.Test;
import junit.framework.TestCase;
import ccmtools.generator.cpp.local.CppLocalTestSuite;
import ccmtools.generator.cpp.remote.CppRemoteTestSuite;

public class CppComponentsTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("C++ Components Test Suite");	

		suite.addTest(CppLocalTestSuite.suite());
		suite.addTest(CppRemoteTestSuite.suite());

		return suite;
	}
}
