package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.parser.idl.test.struct.StructTest;


public class ValuetypeFactoryTest extends ValuetypeTest
{
    public ValuetypeFactoryTest()
        throws FileNotFoundException
    {
        super(ValuetypeFactoryTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeFactoryTest.class);
    }
    
    
    public void testValuetypeFactory() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   factory init();" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        MFactoryDef factory = getFactoryType(value, 0, "init");
        assertEquals(factory.getParameters().size(), 0);
    }                
    
    
    public void testValuetypeFactoryWithBaseTypes() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   factory create(in float a, in double b, in long double c);" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        MFactoryDef factory = getFactoryType(value, 0, "create");            
        PrimitiveTest.checkFloatType(getParameterType(factory, 0, "a"));
        PrimitiveTest.checkDoubleType(getParameterType(factory, 1, "b"));
        PrimitiveTest.checkLongDoubleType(getParameterType(factory, 2, "c"));
    }                
    
    
    public void testValuetypeFactoryWithTemplateTypes() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   factory create(in string a, in wstring b);" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");
        MFactoryDef factory = getFactoryType(value, 0, "create"); 
        PrimitiveTest.checkStringType(getParameterType(factory, 0, "a"));
        PrimitiveTest.checkWideStringType(getParameterType(factory, 1, "b"));
    }               
    
    
    public void testValuetypeFactoryWithScopedNames() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                StructTest.getStructPersonSource() +
                "valuetype Value {" +
                "   factory create(in Person p);" +
                "};");
        
        assertEquals(value.getIdentifier(), "Value");   
        MFactoryDef factory = getFactoryType(value, 0, "create");
        StructTest.checkStructPerson(getParameterType(factory, 0, "p"));
    }                   
}
