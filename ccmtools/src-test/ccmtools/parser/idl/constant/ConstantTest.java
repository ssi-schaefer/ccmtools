package ccmtools.parser.idl.constant;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MConstantDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ConstantTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    public final static float FLOAT_DELTA = 0.001f;
    public final static double DOUBLE_DELTA = 0.000001;
    
    public ConstantTest(String title)
        throws FileNotFoundException
    {
        super(title);        
        uiDriver = new ConsoleDriver();
    }
        
    
    /*
     * Utility Methods
     */
    
    public MConstantDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        MConstantDef constant = (MConstantDef)modelElements.get(0);            
        System.out.println(modelElements);
        return constant;
    }

    public static MConstantDef parseSource(String sourceCode, String id) throws CcmtoolsException, FileNotFoundException
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
                return (MConstantDef)element;
            }
        }
        return null;
    }
}
