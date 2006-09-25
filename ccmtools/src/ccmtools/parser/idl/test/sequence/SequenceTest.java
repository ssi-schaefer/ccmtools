package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class SequenceTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public SequenceTest(String title)
        throws FileNotFoundException
    {
        super(title);        
        uiDriver = new ConsoleDriver();
    }
        
    public static String getSimpleSequenceSource()
    {
        return "typedef sequence<long> SimpleSequence;";
    }
    
    public static void checkSimpleSequence(MTyped type)
    {
        checkSimpleSequence(type.getIdlType());
    }

    public static void checkSimpleSequence(MIDLType idlType)
    {
        assertTrue(idlType instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)idlType;
        assertEquals(alias.getIdentifier(), "SimpleSequence");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongType((MTyped)seq);        
    }

    
    /*
     * Utility Methods
     */
    
    public MAliasDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
