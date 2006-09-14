package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.ParserHelper;
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
               
    
    /*
     * Utility Methods
     */
    
    public MExceptionDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
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
