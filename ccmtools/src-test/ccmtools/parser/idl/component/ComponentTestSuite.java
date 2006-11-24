package ccmtools.parser.idl.component;

import junit.framework.Test;
import junit.framework.TestCase;

public class ComponentTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ComponentTestSuite.class.getName());	
        
        suite.addTest(ComponentHeaderTest.suite());
        suite.addTest(ComponentForwardDeclarationTest.suite());
        suite.addTest(ComponentAttributeTest.suite());
        suite.addTest(ComponentSupportsTest.suite());
        suite.addTest(ComponentPortsTest.suite());
        
        suite.addTest(ComponentModuleTest.suite());
        
        return suite;
	}
}
