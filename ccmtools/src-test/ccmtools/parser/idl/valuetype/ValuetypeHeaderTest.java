package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;


public class ValuetypeHeaderTest extends ValuetypeTest
{
    public ValuetypeHeaderTest()
        throws FileNotFoundException
    {
        super(ValuetypeHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeHeaderTest.class);
    }
    
    
    public void testEmptyValuetype() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype EmptyValue { };");
        
        assertEquals(value.getIdentifier(), "EmptyValue");   
        assertEquals(value.getContentss().size(),0);
    }                
    
    
    public void testCustomValuetype() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "custom valuetype CustomValue { };");
        
        assertEquals(value.getIdentifier(), "CustomValue");
        assertTrue(value.isCustom());
        assertEquals(value.getContentss().size(),0);
    }       
    
    
    public void testValuetypeSingleInheritance() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype EmptyValue { };" +
                "valuetype SubValue : EmptyValue { };");
        
        assertEquals(value.getIdentifier(), "SubValue");
        MValueDef base = getBaseValuetype(value, "EmptyValue");
        assertEquals(base.getContentss().size(), 0);    
    }    

    
    public void testValuetypeSingleAbstractInheritance() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype OneValue { };" +
                "valuetype SubValue : OneValue { };");
        
        assertEquals(value.getIdentifier(), "SubValue");
        getAbstractBase(value, 0, "OneValue");
    }    

    
    public void testValuetypeMultipleAbstractInheritance() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "abstract valuetype OneValue { };" +
                "abstract valuetype AnotherValue { };" +
                "valuetype SubValue : OneValue, AnotherValue { };");
        
        assertEquals(value.getIdentifier(), "SubValue");
        getAbstractBase(value, 0, "OneValue");
        getAbstractBase(value, 1, "AnotherValue");
    }    

    
    public void testValuetypeMultipleInheritanceError() throws CcmtoolsException
    {
        try
        {
            parseSource(
                "valuetype OneValue { };" +
                "valuetype AnotherValue { };" +
                "valuetype SubValue : OneValue, AnotherValue { };");
        
            fail();
        }
        catch(Exception e)
        {
            // OK
            System.out.println(">> " + e.getMessage());
        }
    }    

    
    public void testValuetypeSingleTruncatableInheritance() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype EmptyValue { };" +
                "valuetype SubValue : truncatable EmptyValue { };", "SubValue");
        
        assertTrue(value.isTruncatable());       
        MValueDef base = getBaseValuetype(value, "EmptyValue");
        assertEquals(base.getContentss().size(), 0);    
    }    
    
    public void testValuetypeMultipleTruncatableInheritanceError() throws CcmtoolsException
    {
        try
        {
            parseSource(
                "valuetype OneValue { };" +
                "valuetype AnotherValue { };" +
                "valuetype SubValue : truncatable OneValue, AnotherValue { };");
        
            fail();
        }
        catch(Exception e)
        {
            // OK
            System.out.println(">> " + e.getMessage());
        }
    }    

    
    public void testValuetypeSupportedInterfaces() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "interface OneIFace { };" +
                "interface AnotherIFace { };" +
                "valuetype Value supports OneIFace, AnotherIFace { };", "Value");
              
        assertEquals(value.getContentss().size(),0);
        {
            MInterfaceDef iface = getSupportedInterface(value,0,"OneIFace");
            assertEquals(iface.getContentss().size(), 0);
        }
        {
            MInterfaceDef iface = getSupportedInterface(value,1, "AnotherIFace");
            assertEquals(iface.getContentss().size(), 0);
        }
    }    

    
    public void testValuetypeInheritanceAndSupportedInterfaces() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype OneValue { };" +
                "interface OneIFace { };" +
                "interface AnotherIFace { };" +
                "valuetype Value : OneValue supports OneIFace, AnotherIFace { };", "Value");
        
        assertEquals(value.getContentss().size(),0);
        
        MValueDef base = getBaseValuetype(value, "OneValue");
        assertEquals(base.getContentss().size(), 0);          
        {            
            MInterfaceDef iface = getSupportedInterface(value, 0, "OneIFace");
            assertEquals(iface.getContentss().size(), 0);
        }
        {
            MInterfaceDef iface = getSupportedInterface(value, 1, "AnotherIFace");
            assertEquals(iface.getContentss().size(), 0);
        }        
    }    
}
