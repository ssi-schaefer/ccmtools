package ccmtools.generator.java.test.local;

import junit.framework.Test;
import junit.framework.TestCase;

public class JavaLocalTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Java Local Components Test Suite");	

		suite.addTest(ComponentTest.suite());
		return suite;
	}
}
