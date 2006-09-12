package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class EnumErrorTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public EnumErrorTest()
        throws FileNotFoundException
    {
        super("IDL Enum Error Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumErrorTest.class);
    }
    
     
    public void testEmptyEnumError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("enum Color { };");
            fail();
        }
        catch(Exception e)
        {
            /* OK */
            System.out.println(e.getMessage());
        }
    } 
    
    
    /*
     * Utility Methods
     */
    
    private MEnumDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MEnumDef)modelElements.get(0);
    }
}
