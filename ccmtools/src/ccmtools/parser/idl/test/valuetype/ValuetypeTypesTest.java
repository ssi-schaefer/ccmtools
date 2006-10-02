package ccmtools.parser.idl.test.valuetype;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.test.constant.ConstantTest;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.exception.ExceptionTest;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.parser.idl.test.sequence.SequenceTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class ValuetypeTypesTest extends ValuetypeTest
{
    public ValuetypeTypesTest()
        throws FileNotFoundException
    {
        super(ValuetypeTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ValuetypeTypesTest.class);
    }
    
    
    public void testValuetypeTypedef() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                SequenceTest.getBoundedLongSequenceSource() +
                SequenceTest.getLongSequenceSource() +
                "valuetype Value {" +
                "   typedef long LongType;" +
                "   typedef string StringType;" +
                "};", "Value");
        {
            assertTrue(value.getContentss().get(0) instanceof MAliasDef);
            MAliasDef alias = (MAliasDef)value.getContentss().get(0);
            PrimitiveTest.checkLongType((MTyped)alias);
        }
        {
            assertTrue(value.getContentss().get(1) instanceof MAliasDef);
            MAliasDef alias = (MAliasDef)value.getContentss().get(1);
            PrimitiveTest.checkStringType((MTyped)alias);
        }        
    }
    
    
    public void testValuetypeStruct() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value {" +
                StructTest.getStructPersonSource() +
                "};", "Value");
        
        assertTrue(value.getContentss().get(0) instanceof MStructDef);
        MStructDef type = (MStructDef)value.getContentss().get(0);
        StructTest.checkStructPerson(type);   
    }
    
    public void testValuetypeUnion() throws CcmtoolsException
    {
        MValueDef value = parseSource(        
                "valuetype Value { " +
                UnionTest.getUnionOptionalSource() +
                "};", "Value");

        assertTrue(value.getContentss().get(0) instanceof MUnionDef);
        MUnionDef type = (MUnionDef)value.getContentss().get(0);
        UnionTest.checkUnionOptional(type);        
    }   
    
    public void testValuetypeEnum() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value { " +
                EnumTest.getEnumColorSource() +
                "};", "Value");

        assertTrue(value.getContentss().get(0) instanceof MEnumDef);
        MEnumDef type = (MEnumDef)value.getContentss().get(0);
        EnumTest.checkEnumColor(type);        
    }   
    
    public void testValuetypeForwardDcl() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value { " +
                "   struct Person;"  +
                "};" +
                StructTest.getStructPersonSource(), "Value");

        assertTrue(value.getContentss().get(0) instanceof MStructDef);
        MStructDef type = (MStructDef)value.getContentss().get(0);
        StructTest.checkStructPerson(type);        
    }      
    
    public void testValuetypeConstants() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                EnumTest.getEnumColorSource() +
                "valuetype Value { " +
                "   const float FLOAT_CONST = 3.14;"  +
                "   const string STRING_CONST = \"1234567890\";" +
                "   const Color ENUM_CONST = red;" +
                "};" +
                StructTest.getStructPersonSource(), "Value");

        {
            assertTrue(value.getContentss().get(0) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)value.getContentss().get(0);            
            assertTrue(constant.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveKind kind = ((MPrimitiveDef) constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_FLOAT);
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), 3.14, ConstantTest.FLOAT_DELTA);
        }
        {
            assertTrue(value.getContentss().get(1) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)value.getContentss().get(1);
            assertEquals(constant.getIdentifier(), "STRING_CONST");
            assertTrue(constant.getIdlType() instanceof MStringDef);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");        
        }
        {
            assertTrue(value.getContentss().get(2) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)value.getContentss().get(2);            
            assertTrue(constant.getIdlType() instanceof MEnumDef);
            ScopedName constValue = (ScopedName)constant.getConstValue();
            assertEquals(constValue, new ScopedName("red"));      
        }
    }          
    
    
    public void testValuetypeException() throws CcmtoolsException
    {
        MValueDef value = parseSource(
                "valuetype Value { " +
                ExceptionTest.getSimpleExceptionSource() +
                "};", "Value");

        assertTrue(value.getContentss().get(0) instanceof MExceptionDef);
        ExceptionTest.checkSimpleException((MExceptionDef)value.getContentss().get(0));        
    }      
}
