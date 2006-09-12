package ccmtools.parser.idl.test;

import junit.framework.Test;
import junit.framework.TestCase;

public class IdlParserTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite("IDL Parser Test Suite");	

        suite.addTest(LiteralErrorTest.suite());
		suite.addTest(LiteralTest.suite());
		
        suite.addTest(ConstantsTest.suite());
        
        suite.addTest(EnumErrorTest.suite());
        suite.addTest(EnumTest.suite());
        
        suite.addTest(StructErrorTest.suite());
        suite.addTest(StructOfBaseTypesTest.suite());
        suite.addTest(StructOfTempleateTypesTest.suite());
                
        suite.addTest(TypedefOfBaseTypesTest.suite());
        suite.addTest(TypedefOfTemplateTypesTest.suite());
        
        suite.addTest(SequenceOfBaseTypesTest.suite());
        suite.addTest(SequenceOfTemplateTypesTest.suite());
        
		return suite;
	}
}
