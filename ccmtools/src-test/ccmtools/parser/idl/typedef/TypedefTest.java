package ccmtools.parser.idl.typedef;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserManager;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class TypedefTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public TypedefTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        
    
    /*
     * Utility Methods
     */
    
    public MAliasDef parseSource(String sourceLine) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
