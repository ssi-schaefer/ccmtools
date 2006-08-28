package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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
    
     
    public void testIntegerLiteralBaseTen()
    {       
        try
        {
            String idlSource = "const long INTEGER_LITERAL_BASE_TEN = 12;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 12);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
    
    public void testIntegerLiteralBaseEight()
    {       
        try
        {
            String idlSource = "const long INTEGER_LITERAL_BASE_TEN = 014;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 12);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     

    public void testIntegerLiteralBaseSixteen()
    {       
        try
        {
            String idlSource = "const long INTEGER_LITERAL_BASE_TEN = 0xc;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 12);          
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
            String idlSource = "const char CHAR_LITERAL = 'c';";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            Character constValue = (Character)constant.getConstValue();            
            assertEquals(constValue.charValue(), 'c');          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }     
    
}
