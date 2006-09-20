package ccmtools.parser.idl.test.forward;

import junit.framework.Test;
import junit.framework.TestCase;

public class ForwardDeclarationTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ForwardDeclarationTestSuite.class.getName());	
        suite.addTest(StructForwardDeclarationTest.suite());
        suite.addTest(UnionForwardDeclarationTest.suite());
		return suite;
	}
}
