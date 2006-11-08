package ccmtools.parser.idl.literal;

import ccmtools.parser.idl.literal.LiteralErrorTest;
import ccmtools.parser.idl.literal.LiteralOfBaseTypesTest;
import junit.framework.Test;
import junit.framework.TestCase;

public class LiteralTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(LiteralTestSuite.class.getName());	
		suite.addTest(LiteralErrorTest.suite());
        suite.addTest(LiteralOfBaseTypesTest.suite());
        suite.addTest(LiteralOfTemplateTypesTest.suite());
		return suite;
	}
}
