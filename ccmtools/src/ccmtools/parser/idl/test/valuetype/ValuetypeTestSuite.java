package ccmtools.parser.idl.test.valuetype;

import junit.framework.Test;
import junit.framework.TestCase;

public class ValuetypeTestSuite
	extends TestCase	
{
	public static Test suite()
	{
		junit.framework.TestSuite suite = new junit.framework.TestSuite(ValuetypeTestSuite.class.getName());	

		suite.addTest(ValueBoxTest.suite());
        
        
        suite.addTest(ValuetypeHeaderTest.suite());
        suite.addTest(ValuetypeFactoryTest.suite());
        suite.addTest(ValuetypeForwardDeclarationTest.suite());
        suite.addTest(ValuetypeTypesTest.suite());
        
        suite.addTest(ValuetypeMemberVisibilityTest.suite());
        suite.addTest(ValuetypeMemberListTest.suite());
        suite.addTest(ValuetypeMemberOfBaseTypeTest.suite());
        suite.addTest(ValuetypeMemberOfTemplateTypeTest.suite());
        suite.addTest(ValuetypeMemberOfConstructedTypeTest.suite());
        
        suite.addTest(ValuetypeAttributeOfBaseTypesTest.suite());
        suite.addTest(ValuetypeAttributeOfTemplateTypesTest.suite());
        suite.addTest(ValuetypeAttributeOfConstructedTypesTest.suite());

        suite.addTest(ValuetypeOperationOfBaseTypesTest.suite());
        suite.addTest(ValuetypeOperationOfTemplateTypesTest.suite());
        suite.addTest(ValuetypeOperationOfConstructedTypesTest.suite());
        
        
        
        // Abstract value types
        suite.addTest(AbstractValuetypeTest.suite());
        suite.addTest(AbstractValuetypeAttributeOfBaseTypesTest.suite());
        
        return suite;
	}
}
