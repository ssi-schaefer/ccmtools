package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MContained;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MIDLType;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.parser.idl.metamodel.BaseIDL.MStringDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class StructTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public StructTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        

    public static String getStructPersonSource()
    {
        return "struct Person { long id; string name; };";
    }

    public static void checkStructPerson(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MStructDef);
        MStructDef type = (MStructDef)typed.getIdlType();
        checkStructPerson(type);
    }
    
    public static void checkStructPerson(MIDLType idlType)
    {
        assertTrue(idlType instanceof MStructDef);
        MStructDef type = (MStructDef)idlType;
        checkStructPerson(type);
    }
    
    public static void checkStructPerson(MStructDef struct)
    {
        assertEquals(struct.getIdentifier(), "Person");
        {
            assertTrue(struct.getMember(0) instanceof MFieldDef);
            MFieldDef field = (MFieldDef)struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)field.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(field.getIdentifier(), "id");
        }
        {
            assertTrue(struct.getMember(1) instanceof MFieldDef);
            MFieldDef field = (MFieldDef)struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MStringDef);
            assertEquals(field.getIdentifier(), "name");
        }
    }
    
    
    /*
     * Utility Methods
     */
    
    public MStructDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MStructDef)modelElements.get(0);
    }
    
    public MStructDef parseSource(String sourceCode, String id) throws CcmtoolsException
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
                return (MStructDef)element;
            }
        }
        return null;
    }
    
}
