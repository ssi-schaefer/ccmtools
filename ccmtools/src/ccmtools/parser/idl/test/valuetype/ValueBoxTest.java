package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.parser.idl.test.struct.StructTest;


public class ValueBoxTest extends ValuetypeTest
{
    public ValueBoxTest()
        throws FileNotFoundException
    {
        super(ValueBoxTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValueBoxTest.class);
    }
    
    
    // Base types 
    public void testValueBoxOfLong() throws CcmtoolsException
    {
        MValueBoxDef value = parseBoxSource("valuetype LongValue long;");
        
        assertEquals(value.getIdentifier(), "LongValue");
        PrimitiveTest.checkLongType(value.getIdlType());
    }                
    // TODO: implement tests for the other base types
    
    
    // Template types
    public void testValueBoxOfSequence() throws CcmtoolsException
    {
        MValueBoxDef value = parseBoxSource("valuetype SeqValue sequence<long>;");

        assertEquals(value.getIdentifier(), "SeqValue");
        assertTrue(value.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef)value.getIdlType();
        PrimitiveTest.checkLongType(seq.getIdlType());        
    }
    // TODO: implement tests for the other template types
    
    
    // Constructed types
    public void testValueBoxOfStruct() throws CcmtoolsException
    {
        MValueBoxDef value = parseBoxSource(
                StructTest.getStructPersonSource() +
                "valuetype StructValue Person;");

        assertEquals(value.getIdentifier(), "StructValue");
        StructTest.checkStructPerson(value.getIdlType());
    }
    // TODO: implement tests of the other constructed types
}
