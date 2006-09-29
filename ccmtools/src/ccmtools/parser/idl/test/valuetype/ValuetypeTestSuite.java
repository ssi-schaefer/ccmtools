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
        
        suite.addTest(ValuetypeMemberVisibilityTest.suite());
        suite.addTest(ValuetypeMemberOfBaseTypeTest.suite());
        suite.addTest(ValuetypeMemberOfTemplateTypeTest.suite());
        suite.addTest(ValuetypeMemberOfConstructedTypeTest.suite());
        
        
        suite.addTest(AbstractValuetypeTest.suite());
        suite.addTest(AbstractValuetypeAttributeOfBaseTypesTest.suite());
        
        return suite;
	}
}
