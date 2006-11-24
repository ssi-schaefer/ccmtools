package ccmtools.parser.idl.module;

import junit.framework.Test;
import junit.framework.TestCase;

public class ModuleTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ModuleTestSuite.class.getName());	
        suite.addTest(ModuleReopenTest.suite());
        return suite;
	}
}
