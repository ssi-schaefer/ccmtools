package ccmtools.parser.idl.test.struct;

import junit.framework.Test;
import junit.framework.TestCase;

public class StructTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(StructTestSuite.class.getName());	
        suite.addTest(EmptyStructTest.suite());
        suite.addTest(StructOfBaseTypesTest.suite());
        suite.addTest(StructOfTempleateTypesTest.suite());
        suite.addTest(StructOfConstructedTypesTest.suite());
		return suite;
	}
}
