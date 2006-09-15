package ccmtools.parser.idl.test;

import junit.framework.Test;
import junit.framework.TestCase;
import ccmtools.parser.idl.test.array.ArrayTestSuite;
import ccmtools.parser.idl.test.constant.ConstantTestSuite;
import ccmtools.parser.idl.test.enumeration.EnumTestSuite;
import ccmtools.parser.idl.test.exception.ExceptionTestSuite;
import ccmtools.parser.idl.test.literal.LiteralTestSuite;
import ccmtools.parser.idl.test.sequence.SequenceTestSuite;
import ccmtools.parser.idl.test.struct.StructTestSuite;
import ccmtools.parser.idl.test.typedef.TypedefTestSuite;

public class IdlParserTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(IdlParserTestSuite.class.getName());	

        suite.addTest(LiteralTestSuite.suite());
		
        suite.addTest(ConstantTestSuite.suite());
        
        suite.addTest(EnumTestSuite.suite());
        
        suite.addTest(StructTestSuite.suite());
        
        suite.addTest(TypedefTestSuite.suite());
        
        suite.addTest(SequenceTestSuite.suite());
        
        suite.addTest(ArrayTestSuite.suite());
        
        suite.addTest(ExceptionTestSuite.suite());
        
        return suite;
	}
}
