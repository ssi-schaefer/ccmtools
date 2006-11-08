package ccmtools.parser.idl.literal;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class LiteralTest extends TestCase
{
    public final float FLOAT_DELTA = 0.001f;
    public final double DOUBLE_DELTA = 0.000001;
    
    private UserInterfaceDriver uiDriver;

    public LiteralTest(String title)
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
