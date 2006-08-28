package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ConstantsTest extends TestCase
{
    private UserInterfaceDriver uiDriver;

    
    public ConstantsTest()
        throws FileNotFoundException
    {
        super("IDL Parser Test - Global Constants");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(ConstantsTest.class);
    }
    
    
    
    public void testShortConstants()
    {       
        try
        {
            String idlSource = "const short SHORT_CONST = -3;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
            
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "SHORT_CONST");
            Short constValue = (Short)constant.getConstValue();
            assertEquals(constValue.intValue(), -3);           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    
}
