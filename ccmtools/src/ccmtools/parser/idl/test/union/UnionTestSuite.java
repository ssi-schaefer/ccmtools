package ccmtools.parser.idl.test.union;

import junit.framework.Test;
import junit.framework.TestCase;

public class UnionTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(UnionTestSuite.class.getName());	
//        suite.addTest(TypedefOfBaseTypesTest.suite());
//        suite.addTest(TypedefOfTemplateTypesTest.suite());
//        suite.addTest(TypedefOfScopedNameTest.suite());
		return suite;
	}
}
