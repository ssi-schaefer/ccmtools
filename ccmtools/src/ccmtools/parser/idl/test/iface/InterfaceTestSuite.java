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

        return suite;
	}
}
