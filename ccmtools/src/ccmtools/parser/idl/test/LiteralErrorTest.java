package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class LiteralErrorTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public LiteralErrorTest()
        throws FileNotFoundException
    {
        super("IDL Literal Error Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralErrorTest.class);
    }
    
    public void testBoundedStringLiteralError() throws CcmtoolsException
    {
        try
        {
            parseSource("const string<7> STRING_LITERAL = \"01234567\";");
            fail();
        }
        catch (Exception e)
        {

        }
    }
    
    public void testBoundedWideStringLiteralError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("const wstring<7> STRING_LITERAL = L\"01234567\";");
            fail();
        }
        catch (Exception e)
        {

        }
    }
    
    
    /*
     * Utility Methods
     */
    
    private MConstantDef parseSource(String sourceCode) 
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
