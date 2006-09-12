package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class StructErrorTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public StructErrorTest()
        throws FileNotFoundException
    {
        super("IDL Struct Error Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(StructErrorTest.class);
    }
    
     
    public void testEmptyStructError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("struct Person { };");
            fail();
        }
        catch (Exception e)
        {
            /* OK */
            System.out.println(e.getMessage());
        }
    }                

    
    /*
     * Utility Methods
     */
    
    private MStructDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MStructDef)modelElements.get(0);
    }
}
