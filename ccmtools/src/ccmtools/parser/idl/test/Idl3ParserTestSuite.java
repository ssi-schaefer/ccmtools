package ccmtools.parser.idl.test;

import junit.framework.Test;
import junit.framework.TestCase;

public class Idl3ParserTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite("IDL3 Parser Test Suite");	

		suite.addTest(LiteralTest.suite());
		suite.addTest(ConstantsTest.suite());
        suite.addTest(EnumTest.suite());
        
		return suite;
	}
}
