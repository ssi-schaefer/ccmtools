package ccmtools.parser.idl.enumeration;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.ParserManager;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class EnumTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    public EnumTest()
        throws FileNotFoundException
    {
        this(EnumTest.class.getName());
    }

    public EnumTest(String title) 
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }

    
    /** Default test case methods */
    
    public static String getEnumColorSource()
    {
        return "enum Color { red, green, blue };";
    }
    
    public static void checkEnumColor(MEnumDef enumeration)
    {
        assertEquals(enumeration.getIdentifier(), "Color");
        assertEquals(enumeration.getMember(0), "red");
        assertEquals(enumeration.getMember(1), "green");
        assertEquals(enumeration.getMember(2), "blue");
    }
    
    public static void checkEnumColor(MTyped typed)
    {
        checkEnumColor(typed.getIdlType());
    }

    public static void checkEnumColor(MIDLType idlType)
    {
        assertTrue(idlType instanceof MEnumDef);        
        MEnumDef enumeration = (MEnumDef)idlType;
        EnumTest.checkEnumColor(enumeration);
    }

    
    
    /** Utility Methods */
    
    public MEnumDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MEnumDef)modelElements.get(0);
    }
}
