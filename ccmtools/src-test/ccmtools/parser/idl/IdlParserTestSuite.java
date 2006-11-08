package ccmtools.parser.idl;

import junit.framework.Test;
import junit.framework.TestCase;
import ccmtools.parser.idl.array.ArrayTestSuite;
import ccmtools.parser.idl.constant.ConstantTestSuite;
import ccmtools.parser.idl.enumeration.EnumTestSuite;
import ccmtools.parser.idl.exception.ExceptionTestSuite;
import ccmtools.parser.idl.forward.ForwardDeclarationTestSuite;
import ccmtools.parser.idl.iface.InterfaceTestSuite;
import ccmtools.parser.idl.literal.LiteralTestSuite;
import ccmtools.parser.idl.notsupported.NotSupportedTest;
import ccmtools.parser.idl.sequence.SequenceTestSuite;
import ccmtools.parser.idl.struct.StructTestSuite;
import ccmtools.parser.idl.typedef.TypedefTestSuite;
import ccmtools.parser.idl.union.UnionTestSuite;
import ccmtools.parser.idl.valuetype.ValuetypeTestSuite;

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
