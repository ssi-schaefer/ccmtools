package ccmtools.parser.idl.test.constant;

import junit.framework.Test;
import junit.framework.TestCase;

public class ConstantTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ConstantTestSuite.class.getName());	
        
        suite.addTest(ConstantDefinedInTest.suite());
        suite.addTest(ConstantOfBaseTypesTest.suite());
        suite.addTest(ConstantOfTemplateTypesTest.suite());
        suite.addTest(ConstantOfConstructedTypesTest.suite());
        return suite;
	}
}
