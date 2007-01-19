package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.struct.StructTest;


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
        MValueBoxDef value = parseBoxSource(
                "valuetype LongValue long;", 
                "LongValue");
        
        PrimitiveTest.checkLongType(value.getIdlType());
    }                
    // TODO: implement tests for the other base types
    
    
    // Template types
    public void testValueBoxOfSequence() throws CcmtoolsException
    {
        MValueBoxDef value = parseBoxSource(
                "valuetype SeqValue sequence<long>;",
                "SeqValue");

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
                "valuetype StructValue Person;",
                "StructValue");

        StructTest.checkStructPerson(value.getIdlType());
    }
    // TODO: implement tests of the other constructed types
}
