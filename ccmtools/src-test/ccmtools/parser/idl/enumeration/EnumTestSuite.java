package ccmtools.parser.idl.enumeration;

import junit.framework.Test;
import junit.framework.TestCase;

public class EnumTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(EnumTestSuite.class.getName());	
        
        suite.addTest(EnumDefinedInTest.suite());
        suite.addTest(EmptyEnumTest.suite());
        suite.addTest(EnumColorTest.suite());
		return suite;
	}
}
