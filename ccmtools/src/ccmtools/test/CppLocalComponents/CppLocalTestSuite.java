package ccmtools.test.CppLocalComponents;

import junit.framework.Test;
import junit.framework.TestCase;

public class CppLocalTestSuite
	extends TestCase
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Local C++ Components Test Suite");	

		suite.addTest(CliTest.suite());
		
		suite.addTest(HomeTest.suite());
		
		suite.addTest(ComponentTest.suite());
		
		suite.addTest(SupportsTest.suite());
		
		suite.addTest(FacetTest.suite());
		
		suite.addTest(ReceptacleTest.suite());
		
		suite.addTest(ModuleTest.suite());
		
		suite.addTest(IncludeTest.suite());
		
		suite.addTest(AnyTest.suite());
		
		suite.addTest(AssemblyTest.suite());
		
		return suite;
	}
}
