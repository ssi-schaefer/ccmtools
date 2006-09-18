package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


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
        
        assertTrue(alias.getIdlType() instanceof MStringDef);
    }             
        
    public void testTypedefOfBoundedString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string<7> BStringType;");

        assertEquals(alias.getIdentifier(), "BStringType");
        
        assertTrue(alias.getIdlType() instanceof MStringDef);
        MStringDef type = (MStringDef) alias.getIdlType();
        assertEquals(type.getBound().longValue(), 7);
    }             
    
    
    public void testTypedefOfWString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring WStringType;");

        assertEquals(alias.getIdentifier(), "WStringType");
        
        assertTrue(alias.getIdlType() instanceof MWstringDef);
    }             
    
    public void testTypedefOfBoundedWString() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring<13> BWStringType;");

        assertEquals(alias.getIdentifier(), "BWStringType");
        
        assertTrue(alias.getIdlType() instanceof MWstringDef);
        MWstringDef type = (MWstringDef) alias.getIdlType();
        assertEquals(type.getBound().longValue(), 13);
    }             
    
        
    public void testTypedefOfFixed() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef fixed<9,2> FixedType;");

        assertEquals(alias.getIdentifier(), "FixedType");
        
        assertTrue(alias.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef) alias.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 2);
    }             
}
