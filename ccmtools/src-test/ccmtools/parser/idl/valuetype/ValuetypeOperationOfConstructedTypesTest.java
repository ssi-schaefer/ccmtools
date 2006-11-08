package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.struct.StructTest;
import ccmtools.parser.idl.union.UnionTest;


public class ValuetypeOperationOfConstructedTypesTest extends ValuetypeTest
{
    public ValuetypeOperationOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeOperationOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeOperationOfConstructedTypesTest.class);
    }
    
    
    public void testValuetypeOperationOfStruct() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                StructTest.getStructPersonSource() +
                "valuetype Value {" +
                "   Person f1(in Person p1, inout Person p2, out Person p3);" + 
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            StructTest.checkStructPerson(op);
            StructTest.checkStructPerson(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            StructTest.checkStructPerson(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            StructTest.checkStructPerson(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
    }
    
    
    public void testValuetypeOperationOfUnion() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                UnionTest.getUnionOptionalSource() +
                "valuetype Value {" +
                "   UnionOptional f1(in UnionOptional p1, inout UnionOptional p2, out UnionOptional p3);" + 
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            UnionTest.checkUnionOptional(op);
            UnionTest.checkUnionOptional(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            UnionTest.checkUnionOptional(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            UnionTest.checkUnionOptional(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
    }
    
    
    public void testValuetypeOperationOfEnum() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                EnumTest.getEnumColorSource() +
                "valuetype Value {" +
                "   Color f1(in Color p1, inout Color p2, out Color p3);" + 
                "};", "Value");
        
        {
            MOperationDef op = getOperationType(value, 0, "f1");
            EnumTest.checkEnumColor(op);
            EnumTest.checkEnumColor(getParameterType(op, 0, "p1", MParameterMode.PARAM_IN));
            EnumTest.checkEnumColor(getParameterType(op, 1, "p2", MParameterMode.PARAM_INOUT));
            EnumTest.checkEnumColor(getParameterType(op, 2, "p3", MParameterMode.PARAM_OUT));
        }
    }    
}
