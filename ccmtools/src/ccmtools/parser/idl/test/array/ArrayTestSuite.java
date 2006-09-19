package ccmtools.parser.idl.test.array;

import junit.framework.Test;
import junit.framework.TestCase;

public class ArrayTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ArrayTestSuite.class.getName());	
        suite.addTest(ArrayOfBaseTypesTest.suite());
        suite.addTest(ArrayDimensionsTest.suite());
        suite.addTest(ArrayOfTemplateTypesTest.suite());
        suite.addTest(ArrayOfConstructedTypesTest.suite());
        return suite;
	}
}
