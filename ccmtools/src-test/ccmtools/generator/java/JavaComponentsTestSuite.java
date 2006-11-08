package ccmtools.generator.java;

import junit.framework.Test;
import junit.framework.TestCase;
import ccmtools.generator.java.local.JavaLocalTestSuite;
import ccmtools.generator.java.remote.JavaRemoteTestSuite;

public class JavaComponentsTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Java Components Test Suite");	

		suite.addTest(JavaRemoteTestSuite.suite());
		suite.addTest(JavaLocalTestSuite.suite());

		return suite;
	}
}
