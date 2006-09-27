package ccmtools.parser.idl.test.notsupported;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class NotSupportedTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public NotSupportedTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
    
    public static Test suite()
    {
        return new TestSuite(NotSupportedTest.class);
    }
        
    
    public void testRepoIdDeclaration() throws CcmtoolsException
    {
        try
        {                
            parseSource("typeid SomeType \"id\";");
            fail();
        }
        catch(Exception e)
        {
            // OK
            System.out.println(e.getMessage());
        }
    }

    
    public void testRepoIdPrefixDeclaration() throws CcmtoolsException
    {
        try
        {                
            parseSource("typeprefix SomeType \"xyz\";");
            fail();
        }
        catch(Exception e)
        {
            // OK
            System.out.println(e.getMessage());
        }
    }
    
    
    
    
    /*
     * Utility Methods
     */
    
    public void parseSource(String sourceLine) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
    }
}
