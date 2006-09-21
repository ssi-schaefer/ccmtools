package ccmtools.parser.idl.test.exception;

import junit.framework.Test;
import junit.framework.TestCase;

public class ExceptionTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ExceptionTestSuite.class.getName());	
        suite.addTest(EmptyExceptionTest.suite());
        suite.addTest(ExceptionOfBaseTypesTest.suite());
        suite.addTest(ExceptionOfTempleateTypesTest.suite());
        suite.addTest(ExceptionOfConstructedTypesTest.suite());

        return suite;
	}
}
