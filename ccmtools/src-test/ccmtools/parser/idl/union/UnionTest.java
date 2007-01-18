package ccmtools.parser.idl.union;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class UnionTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public UnionTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        
    
    public static Test suite()
    {
        return new TestSuite(UnionTest.class);
    }
    
 
    public static String getUnionOptionalSource()
    {
        return  "union UnionOptional switch(boolean) { " +
                "   case TRUE: unsigned short a; " +
                "};";
    }
    
    public static void checkUnionOptional(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MUnionDef);
        MUnionDef union = (MUnionDef)typed.getIdlType();
        checkUnionOptional(union);
    }
    
    public static void checkUnionOptional(MIDLType idlType)
    {
        assertTrue(idlType instanceof MUnionDef);
        MUnionDef union = (MUnionDef)idlType;
        checkUnionOptional(union);
    }

    public static void checkUnionOptional(MUnionDef union)
    {
        assertEquals(union.getIdentifier(), "UnionOptional");
        PrimitiveTest.checkBooleanType(union.getDiscriminatorType());
     
        MUnionFieldDef member = union.getUnionMember(0);
        assertTrue(member.getLabel() instanceof Boolean);
        Boolean label = (Boolean)member.getLabel();
        assertEquals(label.booleanValue(), true);

        PrimitiveTest.checkUnsignedShortType(member);
        assertEquals(member.getIdentifier(), "a");
    }
       
    
    /*
     * Utility Methods
     */
    
    public MUnionDef parseSource(String sourceLine) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MUnionDef)modelElements.get(0);
    }

    public MUnionDef parseSource(String sourceLine, String id) throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MUnionDef)element;
            }
        }        
        return null;
    }

}
