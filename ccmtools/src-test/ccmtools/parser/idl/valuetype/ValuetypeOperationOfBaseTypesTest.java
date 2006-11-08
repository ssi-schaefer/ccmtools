package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class ValuetypeOperationOfBaseTypesTest extends ValuetypeTest
{
    public ValuetypeOperationOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeOperationOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeOperationOfBaseTypesTest.class);
    }
    
    
    public void testValuetypeAttributesOfFloat() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                "   float f1(in float p1, inout float p2, out float p3);" + 
                "   double f2(in double p1, inout double p2, out double p3);" +
                "   long double f3(in long double p1, inout long double p2, out long double p3);" +
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            PrimitiveTest.checkFloatType(op);
            PrimitiveTest.checkFloatType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            PrimitiveTest.checkFloatType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            PrimitiveTest.checkFloatType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
        {
            MOperationDef op = getOperationType(value, 1, "f2");
            PrimitiveTest.checkDoubleType(op);
            PrimitiveTest.checkDoubleType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            PrimitiveTest.checkDoubleType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            PrimitiveTest.checkDoubleType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
        {
            MOperationDef op = getOperationType(value, 2, "f3");
            PrimitiveTest.checkLongDoubleType(op);
            PrimitiveTest.checkLongDoubleType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            PrimitiveTest.checkLongDoubleType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            PrimitiveTest.checkLongDoubleType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }    
    }
    
    
    public void testValuetypeOperationOfNativeType() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "native AID;" + 
                "valuetype Value {" +
                "   AID foo(in AID p1, inout AID p2, out AID p3);" +
                "};", "Value");
        
        MOperationDef op = getOperationType(value, 0, "foo");
        PrimitiveTest.checkNativeType(op, "AID");
        PrimitiveTest.checkNativeType(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN), "AID");
        PrimitiveTest.checkNativeType(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT), "AID");
        PrimitiveTest.checkNativeType(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT), "AID");        
    }       
}
