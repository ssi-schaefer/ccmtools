package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ComponentTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public ComponentTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }

    
    /*
     * Utility Methods
     */
    
    public MComponentDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MComponentDef)modelElements.get(0);
    }
    
    public MComponentDef parseSource(String sourceCode, String id) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MComponentDef)element;
            }
        }
        return null;
    }
}
