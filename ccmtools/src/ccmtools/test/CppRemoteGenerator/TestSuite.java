package ccmtools.test.CppRemoteGenerator;

import junit.framework.Test;
import junit.framework.TestCase;

public class TestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Remote C++ Components Test Suite");	

		suite.addTest(TypesTest.suite());
		suite.addTest(SimpleTest.suite());
		return suite;
	}
}
