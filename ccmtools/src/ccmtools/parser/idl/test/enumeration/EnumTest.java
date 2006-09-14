package ccmtools.parser.idl.test.enumeration;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class EnumTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    public EnumTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }

    
    /*
     * Utility Methods
     */
    
    public MEnumDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MEnumDef)modelElements.get(0);
    }
}
