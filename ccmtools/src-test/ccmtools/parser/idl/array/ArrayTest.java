package ccmtools.parser.idl.array;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ArrayTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public ArrayTest(String title)
        throws FileNotFoundException
    {
        super(title);        
        uiDriver = new ConsoleDriver();
    }
        

    /*
     * Utility Methods
     */
    
    public MAliasDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
