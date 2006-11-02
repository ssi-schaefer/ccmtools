package ccmtools.test.CppRemoteComponents;

import junit.framework.Test;
import junit.framework.TestCase;

public class CppRemoteTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Remote C++ Components Test Suite");	

		suite.addTest(ComponentTest.suite());
		
		suite.addTest(SupportsTest.suite());
		
		suite.addTest(FacetTest.suite());
		
		suite.addTest(ReceptacleTest.suite());
		
		return suite;
	}
}
