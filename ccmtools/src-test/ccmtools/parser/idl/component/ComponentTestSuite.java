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

        
        return suite;
	}
}
