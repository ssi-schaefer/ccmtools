package ccmtools.parser.idl.test.sequence;

import junit.framework.Test;
import junit.framework.TestCase;

public class SequenceTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(SequenceTestSuite.class.getName());	
        suite.addTest(SequenceOfBaseTypesTest.suite());
        suite.addTest(SequenceOfTemplateTypesTest.suite());
        suite.addTest(SequenceOfConstructedTypesTest.suite());
        
        suite.addTest(BoundedSequenceOfBaseTypesTest.suite());
        suite.addTest(BoundedSequenceOfTemplateTypesTest.suite());
        suite.addTest(BoundedSequenceOfConstructedTypesTest.suite());
        
        return suite;
	}
}
