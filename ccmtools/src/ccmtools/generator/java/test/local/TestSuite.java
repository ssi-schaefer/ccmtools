package ccmtools.generator.java.test.local;

import junit.framework.Test;
import junit.framework.TestCase;

public class TestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Local Java Components Test Suite");	

		suite.addTest(FacetTest.suite());
		suite.addTest(ReceptacleTest.suite());
		return suite;
	}
}
