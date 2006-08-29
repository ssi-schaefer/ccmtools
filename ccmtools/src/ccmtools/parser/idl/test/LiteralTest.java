package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
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
        super("Literal Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralTest.class);
    }
    
     
    public void testIntegerLiteral()
    {       
        try
        {
            {
                MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 12;");                
                Integer constValue = (Integer)constant.getConstValue();
                assertEquals(constValue.intValue(), 12);
            }
            {
                MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 014;");
                Integer constValue = (Integer)constant.getConstValue();
                assertEquals(constValue.intValue(), 12);          
            }
            {
                MConstantDef constant = parseSource("const long INTEGER_LITERAL_BASE_TEN = 0xc;");
                Integer constValue = (Integer)constant.getConstValue();
                assertEquals(constValue.intValue(), 12);                          
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
    
    
    public void testFloatLiteral()
    {       
        try
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
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     
    

    public void testDoubleLiteral()
    {       
        try
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
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     

    
    
    public void testCharLiteral()
    {       
        try
        {
            {
                MConstantDef constant = parseSource("const char CHAR_LITERAL = 'c';");                
                Character constValue = (Character)constant.getConstValue();            
                assertEquals(constValue.charValue(), 'c');
            }
//            {
//              MConstantDef constant = parseSource("const char CHAR_LITERAL = '\\t';");                
//              Character constValue = (Character)constant.getConstValue();            
//              assertEquals(constValue.charValue(), '\t');                          
//            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     

        
    public void testStringLiteral()
    {       
        try
        {
            {
                MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello World\";");                
                String constValue = (String)constant.getConstValue();            
                assertEquals(constValue, "Hello World");          
            }
//            {
//                MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello\" \" World\";");                
//                String constValue = (String)constant.getConstValue();            
//                assertEquals(constValue, "Hello World");          
//            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     
    
    
    public void testWideStringLiteral()
    {       
        try
        {
            {
                MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello World\";");                
                String constValue = (String)constant.getConstValue();            
                assertEquals(constValue, "Hello World");          
            }
//            {
//                MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello\" L\" World\";");                
//                String constValue = (String)constant.getConstValue();            
//                assertEquals(constValue, "Hello World");          
//            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
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
