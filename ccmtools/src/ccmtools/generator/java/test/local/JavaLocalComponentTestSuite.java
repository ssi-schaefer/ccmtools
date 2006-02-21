package ccmtools.generator.java.test.local;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JavaLocalComponentTestSuite
{
	public static Test suite()
	{
		TestSuite suite= new TestSuite("Local Java Components Test Suite");	
		suite.addTest(FacetTest.suite());
		suite.addTest(ReceptacleTest.suite());
		return suite;
	}
}
