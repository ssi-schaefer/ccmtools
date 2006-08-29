package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class EnumTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public EnumTest()
        throws FileNotFoundException
    {
        super("Literal Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumTest.class);
    }
    
     
    public void testIntegerLiteral()
    {       
        try
        {
            {
                MEnumDef enumeration = parseSource("enum Color { red, green, blue };"); 
                assertEquals(enumeration.getIdentifier(), "Color");
                assertEquals(enumeration.getMember(0), "red");
                assertEquals(enumeration.getMember(1), "green");
                assertEquals(enumeration.getMember(2), "blue");
            }            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
    
    /*
     * Utility Methods
     */
    
    private MEnumDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MEnumDef)modelElements.get(0);
    }
}
