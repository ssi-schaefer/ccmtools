package ccmtools.parser.idl.sequence;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ParserManager;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MContainer;
import ccmtools.parser.idl.metamodel.BaseIDL.MIDLType;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;
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
        
    public static String getLongSequenceSource()
    {
        return "typedef sequence<long> LongSequence;";
    }
    
    public static void checkLongSequence(MTyped type)
    {
        checkLongSequence(type.getIdlType());
    }

    public static void checkLongSequence(MIDLType idlType)
    {
        assertTrue(idlType instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)idlType;
        assertEquals(alias.getIdentifier(), "LongSequence");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongType((MTyped)seq);        
    }


    public static String getBoundedLongSequenceSource()
    {
        return "typedef sequence<long,7> BoundedLongSequence;";
    }
    
    public static void checkBoundedLongSequence(MTyped type)
    {
        checkBoundedLongSequence(type.getIdlType());
    }

    public static void checkBoundedLongSequence(MIDLType idlType)
    {
        assertTrue(idlType instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)idlType;
        assertEquals(alias.getIdentifier(), "BoundedLongSequence");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        PrimitiveTest.checkLongType((MTyped)seq);   
        assertEquals(seq.getBound().intValue(),7);
    }

    
    
    /*
     * Utility Methods
     */
    
    public MAliasDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserManager.loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MAliasDef)modelElements.get(0);
    }
}
