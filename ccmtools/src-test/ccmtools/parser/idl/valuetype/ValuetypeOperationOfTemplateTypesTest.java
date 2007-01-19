package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MOperationDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.sequence.SequenceTest;


public class ValuetypeOperationOfTemplateTypesTest extends ValuetypeTest
{
    public ValuetypeOperationOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeOperationOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeOperationOfTemplateTypesTest.class);
    }
    
    
    public void testValuetypeOperationOfSequence() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                SequenceTest.getBoundedLongSequenceSource() +
                SequenceTest.getLongSequenceSource() +
                "valuetype Value {" +
                "   LongSequence f1(in LongSequence p1, inout LongSequence p2, out LongSequence p3);" + 
                "   BoundedLongSequence f2(in BoundedLongSequence p1, inout BoundedLongSequence p2, out BoundedLongSequence p3);" +
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            SequenceTest.checkLongSequence(op);
            SequenceTest.checkLongSequence(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            SequenceTest.checkLongSequence(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            SequenceTest.checkLongSequence(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
        {
            MOperationDef op = getOperationType(value, 1, "f2");
            SequenceTest.checkBoundedLongSequence(op);
            SequenceTest.checkBoundedLongSequence(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            SequenceTest.checkBoundedLongSequence(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            SequenceTest.checkBoundedLongSequence(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
    }
    

    public void testValuetypeOperationOfString() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   string f1(in string p1, inout string p2, out string p3);" + 
                "   string<4> f2(in string<4> p1, inout string<4> p2, out string<4> p3);" +
                "   wstring f3(in wstring p1, inout wstring p2, out wstring p3);" +
                "   wstring<5> f4(in wstring<5> p1, inout wstring<5> p2, out wstring<5> p3);" +
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            PrimitiveTest.checkStringType(op);
            PrimitiveTest.checkStringType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            PrimitiveTest.checkStringType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            PrimitiveTest.checkStringType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
        {
            MOperationDef op = getOperationType(value, 1, "f2");
            PrimitiveTest.checkBoundedStringType(op, 4);
            PrimitiveTest.checkBoundedStringType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN), 4);
            PrimitiveTest.checkBoundedStringType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT), 4);
            PrimitiveTest.checkBoundedStringType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT), 4);
        }
        {
            MOperationDef op = getOperationType(value, 2, "f3");
            PrimitiveTest.checkWideStringType(op);
            PrimitiveTest.checkWideStringType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            PrimitiveTest.checkWideStringType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            PrimitiveTest.checkWideStringType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
        {
            MOperationDef op = getOperationType(value, 3, "f4");
            PrimitiveTest.checkBoundedWideStringType(op, 5);
            PrimitiveTest.checkBoundedWideStringType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN), 5);
            PrimitiveTest.checkBoundedWideStringType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT), 5);
            PrimitiveTest.checkBoundedWideStringType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT), 5);
        }        
    }
    
    
    public void testValuetypeOperationOfFixedType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   fixed<7,2> f1(in fixed<7,2> p1, inout fixed<7,2> p2, out fixed<7,2> p3);" + 
                "};", "Value");
        
        MOperationDef op = getOperationType(value, 0, "f1");
        PrimitiveTest.checkFixedType(op, 7, 2);
        PrimitiveTest.checkFixedType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN), 7, 2);
        PrimitiveTest.checkFixedType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT), 7, 2);
        PrimitiveTest.checkFixedType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT), 7, 2);
    }
    
}
