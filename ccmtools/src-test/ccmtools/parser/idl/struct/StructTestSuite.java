package ccmtools.parser.idl.struct;

import junit.framework.Test;
import junit.framework.TestCase;

public class StructTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(StructTestSuite.class.getName());	
        
        suite.addTest(StructDefinedInTest.suite());
        suite.addTest(EmptyStructTest.suite());
        suite.addTest(StructOfBaseTypesTest.suite());
        suite.addTest(StructOfTempleateTypesTest.suite());
        suite.addTest(StructOfConstructedTypesTest.suite());
        
        suite.addTest(StructModuleTest.suite());
        
		return suite;
	}
}
