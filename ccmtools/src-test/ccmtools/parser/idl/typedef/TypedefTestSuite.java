package ccmtools.parser.idl.typedef;

import junit.framework.Test;
import junit.framework.TestCase;

public class TypedefTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(TypedefTestSuite.class.getName());	

        suite.addTest(TypedefDefinedInTest.suite());
        
        suite.addTest(TypedefOfBaseTypesTest.suite());
        suite.addTest(TypedefOfTemplateTypesTest.suite());
        suite.addTest(TypedefOfConstructedTypesTest.suite());
        
        suite.addTest(TypedefOfScopedNameTest.suite());
		
        return suite;
	}
}
