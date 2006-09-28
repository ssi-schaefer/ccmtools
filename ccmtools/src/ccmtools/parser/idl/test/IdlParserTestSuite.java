package ccmtools.parser.idl.test;

import junit.framework.Test;
import junit.framework.TestCase;
import ccmtools.parser.idl.test.array.ArrayTestSuite;
import ccmtools.parser.idl.test.constant.ConstantTestSuite;
import ccmtools.parser.idl.test.enumeration.EnumTestSuite;
import ccmtools.parser.idl.test.exception.ExceptionTestSuite;
import ccmtools.parser.idl.test.forward.ForwardDeclarationTestSuite;
import ccmtools.parser.idl.test.iface.InterfaceTestSuite;
import ccmtools.parser.idl.test.literal.LiteralTestSuite;
import ccmtools.parser.idl.test.notsupported.NotSupportedTest;
import ccmtools.parser.idl.test.sequence.SequenceTestSuite;
import ccmtools.parser.idl.test.struct.StructTestSuite;
import ccmtools.parser.idl.test.typedef.TypedefTestSuite;
import ccmtools.parser.idl.test.union.UnionTestSuite;
import ccmtools.parser.idl.test.valuetype.ValuetypeTestSuite;

public class IdlParserTestSuite
	extends TestCase	
{
    public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite("IDL Parser Test Suite");	

        suite.addTest(LiteralTestSuite.suite());
		
        suite.addTest(ConstantTestSuite.suite());
        
        suite.addTest(EnumTestSuite.suite());
        
        suite.addTest(StructTestSuite.suite());
        
        suite.addTest(TypedefTestSuite.suite());
        
        suite.addTest(SequenceTestSuite.suite());
        
        suite.addTest(ArrayTestSuite.suite());
        
        suite.addTest(ExceptionTestSuite.suite());

        suite.addTest(UnionTestSuite.suite());
        
        suite.addTest(InterfaceTestSuite.suite());
        
        suite.addTest(ValuetypeTestSuite.suite());
        
        suite.addTest(ForwardDeclarationTestSuite.suite());
        
        suite.addTest(NotSupportedTest.suite());

        return suite;
	}
}
