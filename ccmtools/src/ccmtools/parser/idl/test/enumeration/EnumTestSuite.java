package ccmtools.parser.idl.test.enumeration;

import junit.framework.Test;
import junit.framework.TestCase;

public class EnumTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(EnumTestSuite.class.getName());	
        suite.addTest(EmptyEnumTest.suite());
        suite.addTest(EnumSimpleTest.suite());
		return suite;
	}
}
