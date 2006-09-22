package ccmtools.parser.idl.test.iface;

import junit.framework.Test;
import junit.framework.TestCase;

public class InterfaceTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(InterfaceTestSuite.class.getName());	
        suite.addTest(InterfaceHeaderTest.suite());
        
        suite.addTest(InterfaceAttributeOfBaseTypesTest.suite());
        suite.addTest(InterfaceAttributeOfTemplateTypesTest.suite());
        suite.addTest(InterfaceAttributeOfConstructedTypesTest.suite());        
        suite.addTest(InterfaceAttributeListTest.suite());
        suite.addTest(InterfaceAttributeExceptionsTest.suite());
        
        suite.addTest(InterfaceReadonlyAttributeOfBaseTypesTest.suite());
        suite.addTest(InterfaceReadonlyAttributeOfTemplateTypesTest.suite());
        suite.addTest(InterfaceReadonlyAttributeOfConstructedTypesTest.suite());
        suite.addTest(InterfaceReadonlyAttributeListTest.suite());
        suite.addTest(InterfaceReadonlyAttributeExceptionsTest.suite());
        return suite;
	}
}
