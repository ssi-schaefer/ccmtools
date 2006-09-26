package ccmtools.parser.idl.test.constant;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
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
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        MConstantDef constant = (MConstantDef)modelElements.get(0);            
        System.out.println(modelElements);
        return constant;
    }
    
}
