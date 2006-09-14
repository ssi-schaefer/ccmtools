package ccmtools.parser.idl.test.struct;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class StructTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public StructTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        

    /*
     * Utility Methods
     */
    
    public MStructDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MStructDef)modelElements.get(0);
    }
}
