package ccmtools.parser.idl.module;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MModuleDef;
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
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MModuleDef)modelElements.get(0);
    }
    
    public static MModuleDef parseSource(String sourceCode, String id) 
        throws CcmtoolsException, FileNotFoundException
    {
        UserInterfaceDriver uiDriver= new ConsoleDriver();
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
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
    
    public static List<MModuleDef> parseSourceList(String sourceCode) 
        throws CcmtoolsException, FileNotFoundException
    {
        UserInterfaceDriver uiDriver = new ConsoleDriver();
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        
        List<MModuleDef> result = new ArrayList<MModuleDef>();
        for(Iterator i = ccmModel.getContentss().iterator(); i.hasNext();)
        {
            Object o = i.next();
            if(o instanceof MModuleDef)
            {
                result.add((MModuleDef)o);
            }
        }
        Collections.reverse(result);
        System.out.println(result);
        return result;
    }
}
