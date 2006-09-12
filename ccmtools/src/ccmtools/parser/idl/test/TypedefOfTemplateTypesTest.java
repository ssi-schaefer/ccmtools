package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class TypedefOfTemplateTypesTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public TypedefOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super("IDL Struct Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefOfTemplateTypesTest.class);
    }

    
    // typedef sequence<short> SeqShort; see SequenceOf* test cases
    
    
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

    
    /*
     * Utility Methods
     */
    
    private MAliasDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
