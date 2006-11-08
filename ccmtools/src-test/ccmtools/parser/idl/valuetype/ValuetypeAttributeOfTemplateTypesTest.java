package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.sequence.SequenceTest;


public class ValuetypeAttributeOfTemplateTypesTest extends ValuetypeTest
{
    public ValuetypeAttributeOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeAttributeOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeAttributeOfTemplateTypesTest.class);
    }
    
    
    public void testValuetypeAttributesOfSequence() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                SequenceTest.getBoundedLongSequenceSource() +
                SequenceTest.getLongSequenceSource() +
                "valuetype Value {" +
                "   attribute LongSequence seqAttr;" + 
                "   attribute BoundedLongSequence bSeqAttr;" +
                "};", "Value");
        
        SequenceTest.checkLongSequence(getAttributeType(value, 0, "seqAttr"));
        SequenceTest.checkBoundedLongSequence(getAttributeType(value, 1, "bSeqAttr"));
    }
        
    
    public void testValuetypeAttributeOfStringType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   attribute string stringAttr;" +
                "   attribute string<4> bstringAttr;" +
                "   attribute wstring wstringAttr; " +
                "   attribute wstring<5> bwstringAttr; " +
                "};", "Value");
        
        PrimitiveTest.checkStringType(getAttributeType(value, 0, "stringAttr"));
        PrimitiveTest.checkBoundedStringType(getAttributeType(value, 1, "bstringAttr"),4);
        PrimitiveTest.checkWideStringType(getAttributeType(value, 2, "wstringAttr"));
        PrimitiveTest.checkBoundedWideStringType(getAttributeType(value, 3, "bwstringAttr"),5);
    }                
    
    
    public void testValuetypeAttributeOfFixedType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   attribute fixed<7,2> fixedAttr;" +
                "};", "Value");
        
        assertEquals(value.getIdentifier(), "Value");   
        PrimitiveTest.checkFixedType(getAttributeType(value, 0, "fixedAttr"), 7,2);
    }        
}
