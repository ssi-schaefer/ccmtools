package ccmtools.parser.idl.exception;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ExceptionTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    public ExceptionTest(String title)
        throws FileNotFoundException
    {
        super(title);        
        uiDriver = new ConsoleDriver();
    }
         
    
    
    public static String getEmptyExceptionSource()
    {
        return "exception EmptyException {};";
    }
    
    public static void checkEmptyException(MExceptionDef ex)
    {
        assertEquals(ex.getIdentifier(), "EmptyException");
    }

    
    public static String getSimpleExceptionSource()
    {
        return "exception SimpleException { string what; };";
    }
    
    public static void checkSimpleException(MExceptionDef ex)
    {
        assertEquals(ex.getIdentifier(), "SimpleException");
        MFieldDef field = (MFieldDef)ex.getMembers().get(0);
        PrimitiveTest.checkStringType(field);
        assertEquals(field.getIdentifier(), "what");
    }

    
    /*
     * Utility Methods
     */
    
    public MExceptionDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MExceptionDef)modelElements.get(0);
    }
    
    public MFieldDef getMember(MExceptionDef e, int i)
    {
        List members = e.getMembers();
        return (MFieldDef)members.get(i);
    }
}
