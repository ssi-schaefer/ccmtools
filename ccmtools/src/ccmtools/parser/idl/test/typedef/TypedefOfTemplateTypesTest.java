package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class TypedefOfTemplateTypesTest extends TypedefTest
{
    public TypedefOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(TypedefOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefOfTemplateTypesTest.class);
    }

    
    // typedef sequence<short> SeqShort; 
    // siehe sequence test cases
    
       
    public void testTypedefOfString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string StringType;");

        assertEquals(alias.getIdentifier(), "StringType");
        PrimitiveTest.checkStringType(alias);
    }             
        
    public void testTypedefOfBoundedString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string<7> BStringType;");

        assertEquals(alias.getIdentifier(), "BStringType");
        PrimitiveTest.checkBoundedStringType(alias, 7);
    }             
    
    
    public void testTypedefOfWString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring WStringType;");

        assertEquals(alias.getIdentifier(), "WStringType");
        PrimitiveTest.checkWideStringType(alias);        
    }             
    
    public void testTypedefOfBoundedWString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring<13> BWStringType;");

        assertEquals(alias.getIdentifier(), "BWStringType");
        PrimitiveTest.checkBoundedWideStringType(alias, 13);       
    }             
    
        
    public void testTypedefOfFixed() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef fixed<9,3> FixedType;");

        assertEquals(alias.getIdentifier(), "FixedType");
        PrimitiveTest.checkFixedType(alias);        
    }             
}
