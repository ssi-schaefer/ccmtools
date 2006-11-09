package ccmtools.parser.idl.home;

import junit.framework.Test;
import junit.framework.TestCase;

public class HomeTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(HomeTestSuite.class.getName());	
        
        suite.addTest(HomeHeaderTest.suite());
 
        return suite;
	}
}
