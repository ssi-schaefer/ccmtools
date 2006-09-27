package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ValuetypeTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public ValuetypeTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        

    public static String getValuePersonSource()
    {
        return "valuetype Person { public long id; public string name; };";
    }

    public static void checkStructPerson(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MValueDef);
        MValueDef type = (MValueDef)typed.getIdlType();
        checkStructPerson(type);
    }
    
    public static void checkValuePerson(MIDLType idlType)
    {
        assertTrue(idlType instanceof MValueDef);
        MValueDef type = (MValueDef)idlType;
        checkStructPerson(type);
    }
    
    public static void checkStructPerson(MValueDef value)
    {
        assertEquals(value.getIdentifier(), "Person");
//        {
//            assertTrue(value.getMember(0) instanceof MFieldDef);
//            MFieldDef field = (MFieldDef)value.getMember(0);
//            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
//            MPrimitiveDef type = (MPrimitiveDef)field.getIdlType();
//            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
//            assertEquals(field.getIdentifier(), "id");
//        }
//        {
//            assertTrue(value.getMember(1) instanceof MFieldDef);
//            MFieldDef field = (MFieldDef)value.getMember(1);
//            assertTrue(field.getIdlType() instanceof MStringDef);
//            assertEquals(field.getIdentifier(), "name");
//        }
    }
    
    
    /*
     * Utility Methods
     */

    public MValueBoxDef parseBoxSource(String sourceCode) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MValueBoxDef) modelElements.get(0);
    }

    
    public MValueDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MValueDef)modelElements.get(0);
    }
    
    public MValueDef parseSource(String sourceCode, String id) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MValueDef)element;
            }
        }
        return null;
    }
}
