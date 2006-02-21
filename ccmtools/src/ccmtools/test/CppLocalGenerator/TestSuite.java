package ccmtools.test.CppLocalGenerator;

import junit.framework.Test;
import junit.framework.TestCase;

public class TestSuite
	extends TestCase
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = 
			new junit.framework.TestSuite("Local C++ Components Test Suite");	

		suite.addTest(TypesTest.suite());
		return suite;
	}
}
