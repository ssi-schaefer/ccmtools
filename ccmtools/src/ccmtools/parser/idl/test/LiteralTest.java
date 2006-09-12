package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class LiteralTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    private final float FLOAT_DELTA = 0.001f;
    private final double DOUBLE_DELTA = 0.000001;
    
    public LiteralTest()
        throws FileNotFoundException
    {
        super("IDL Literal Of Base Types Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralTest.class);
    }
    
     
    public void testIntegerLiteral()
        throws CcmtoolsException
    {       
        {
            MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 12;");                
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 12);
        }
    } 
    
    public void testOctalLiteral()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 014;");
        Integer constValue = (Integer)constant.getConstValue();
        assertEquals(constValue.intValue(), 12);          
    } 

    public void testHexLiteral()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 0xc;");
        Integer constValue = (Integer)constant.getConstValue();
        assertEquals(constValue.intValue(), 12);                          
    }

    
    
    public void testStringLiteral() throws CcmtoolsException
    {

        MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testStringConcatLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello\" \" World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testBoundedStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const string<10> STRING_LITERAL = \"0123456789\";");
        assertTrue(constant.getIdlType() instanceof MStringDef);
        MStringDef s = (MStringDef) constant.getIdlType();
        assertEquals(s.getBound().longValue(), 10);
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "0123456789");
    }

    
    public void testWideStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testWideStringConcatLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello\" L\" World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testBoundedWideStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring<10> STRING_LITERAL = L\"0123456789\";");
        assertTrue(constant.getIdlType() instanceof MWstringDef);
        MWstringDef s = (MWstringDef) constant.getIdlType();
        assertEquals(s.getBound().longValue(), 10);
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "0123456789");
    }

    
    
    public void testCharLiteral() throws CcmtoolsException
    {
        {
            MConstantDef constant = parseSource("const char CHAR_LITERAL = 'c';");
            Character constValue = (Character) constant.getConstValue();
            assertEquals(constValue.charValue(), 'c');
        }

        // {
        // MConstantDef constant = parseSource("const char CHAR_LITERAL =
        // '\\t';");
        // Character constValue = (Character)constant.getConstValue();
        // assertEquals(constValue.charValue(), '\t');
        //   }
    }     

    
    // TODO: wide_character_literal
    
    // TODO: fixed_pt_literal

    
    public void testFloatLiteral()
        throws CcmtoolsException
    {       
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = -3.14;");                
            Float constValue = (Float)constant.getConstValue();            
            assertEquals(constValue.floatValue(), -3.14, FLOAT_DELTA);
        }           
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = .1;");                
            Float constValue = (Float)constant.getConstValue();            
            assertEquals(constValue.floatValue(), .1, FLOAT_DELTA);
        }
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = 5.0e-3;");                
            Float constValue = (Float)constant.getConstValue();            
            assertEquals(constValue.floatValue(), 5.0e-3, FLOAT_DELTA);
        }
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = 5.0E3;");                
            Float constValue = (Float)constant.getConstValue();            
            assertEquals(constValue.floatValue(), 5.0E3, FLOAT_DELTA);
        }            
    }     
    
    public void testDoubleLiteral()
        throws CcmtoolsException
    {       
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = -3.1415;");                
            Double constValue = (Double)constant.getConstValue();            
            assertEquals(constValue.doubleValue(), -3.1415, DOUBLE_DELTA);
        }           
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = .1;");                
            Double constValue = (Double)constant.getConstValue();            
            assertEquals(constValue.doubleValue(), .1, DOUBLE_DELTA);
        }
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = 5.0e-10;");                
            Double constValue = (Double)constant.getConstValue();            
            assertEquals(constValue.doubleValue(), 5.0e-10, DOUBLE_DELTA);
        }
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = 5.0E10;");                
            Double constValue = (Double)constant.getConstValue();            
            assertEquals(constValue.doubleValue(), 5.0E10, DOUBLE_DELTA);
        }            
    }     

    
    public void testBooleanLiteral() throws CcmtoolsException
    {
        {
            MConstantDef constant = parseSource("const boolean BOOLEAN_LITERAL = FALSE;");
            Boolean constValue = (Boolean) constant.getConstValue();
            assertEquals(constValue.booleanValue(), false);
        }
        {
            MConstantDef constant = parseSource("const boolean BOOLEAN_LITERAL = TRUE;");
            Boolean constValue = (Boolean) constant.getConstValue();
            assertEquals(constValue.booleanValue(), true);
        }
    }     
    
        
    
    
    /*
     * Utility Methods
     */
    
    private MConstantDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        MConstantDef constant = (MConstantDef)modelElements.get(0);
        System.out.println(modelElements);
        return constant;
    }
}
