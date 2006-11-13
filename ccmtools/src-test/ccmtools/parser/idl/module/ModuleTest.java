package ccmtools.parser.idl.module;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MModuleDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ModuleTest extends TestCase
{
    public ModuleTest(String title)
        throws FileNotFoundException
    {
        super(title);
    }

    
    /*
     * Utility Methods
     */
    
    public static MModuleDef parseSource(String sourceCode) 
        throws CcmtoolsException, FileNotFoundException
    {
        UserInterfaceDriver uiDriver= new ConsoleDriver();
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MModuleDef)modelElements.get(0);
    }
    
    public static MModuleDef parseSource(String sourceCode, String id) 
        throws CcmtoolsException, FileNotFoundException
    {
        UserInterfaceDriver uiDriver= new ConsoleDriver();
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MModuleDef)element;
            }
        }
        return null;
    }
}
