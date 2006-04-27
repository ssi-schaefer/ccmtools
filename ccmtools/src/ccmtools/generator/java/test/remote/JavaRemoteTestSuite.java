package ccmtools.generator.java.test.remote;

import junit.framework.Test;
import junit.framework.TestCase;

public class JavaRemoteTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Java Remote Components Test Suite");	

		suite.addTest(ComponentTest.suite());
		suite.addTest(SupportsTest.suite());
		suite.addTest(FacetTest.suite());
		suite.addTest(ReceptacleTest.suite());
		return suite;
	}
}
