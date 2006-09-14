package ccmtools.parser.idl.test.literal;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;


public class LiteralOfBaseTypesTest extends LiteralTest
{
    public LiteralOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(LiteralOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralOfBaseTypesTest.class);
    }
    
    
    public void testFloatLiteral() throws CcmtoolsException
    {
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = -3.14;");
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), -3.14, FLOAT_DELTA);
        }
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = .1;");
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), .1, FLOAT_DELTA);
        }
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = 5.0e-3;");
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), 5.0e-3, FLOAT_DELTA);
        }
        {
            MConstantDef constant = parseSource("const float FLOAT_LITERAL = 5.0E3;");
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), 5.0E3, FLOAT_DELTA);
        }
    }
    
    public void testDoubleLiteral() throws CcmtoolsException
    {
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = -3.1415;");
            Double constValue = (Double) constant.getConstValue();
            assertEquals(constValue.doubleValue(), -3.1415, DOUBLE_DELTA);
        }
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = .1;");
            Double constValue = (Double) constant.getConstValue();
            assertEquals(constValue.doubleValue(), .1, DOUBLE_DELTA);
        }
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = 5.0e-10;");
            Double constValue = (Double) constant.getConstValue();
            assertEquals(constValue.doubleValue(), 5.0e-10, DOUBLE_DELTA);
        }
        {
            MConstantDef constant = parseSource("const double DOUBLE_LITERAL = 5.0E10;");
            Double constValue = (Double) constant.getConstValue();
            assertEquals(constValue.doubleValue(), 5.0E10, DOUBLE_DELTA);
        }
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
}
