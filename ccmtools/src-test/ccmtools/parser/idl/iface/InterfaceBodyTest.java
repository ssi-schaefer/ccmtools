package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.constant.ConstantTest;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.exception.ExceptionTest;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MConstantDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.parser.idl.metamodel.BaseIDL.MStringDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.struct.StructTest;
import ccmtools.parser.idl.union.UnionTest;


public class InterfaceBodyTest extends InterfaceTest
{
    public InterfaceBodyTest()
        throws FileNotFoundException
    {
        super(InterfaceBodyTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceBodyTest.class);
    }
    
     
    public void testInterfaceTypedef() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   typedef long LongType;" +
                "   typedef string StringType;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");        
        {
            assertTrue(iface.getContentss().get(0) instanceof MAliasDef);
            MAliasDef alias = (MAliasDef)iface.getContentss().get(0);
            PrimitiveTest.checkLongType((MTyped)alias);
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAliasDef);
            MAliasDef alias = (MAliasDef)iface.getContentss().get(1);
            PrimitiveTest.checkStringType((MTyped)alias);
        }        
    }      
    
    public void testInterfaceStruct() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                StructTest.getStructPersonSource() +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        
        assertTrue(iface.getContentss().get(0) instanceof MStructDef);
        MStructDef type = (MStructDef)iface.getContentss().get(0);
        StructTest.checkStructPerson(type);        
    }      
    
    public void testInterfaceUnion() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                UnionTest.getUnionOptionalSource() +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        
        assertTrue(iface.getContentss().get(0) instanceof MUnionDef);
        MUnionDef type = (MUnionDef)iface.getContentss().get(0);
        UnionTest.checkUnionOptional(type);        
    }   
    
    public void testInterfaceEnum() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                EnumTest.getEnumColorSource() +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        
        assertTrue(iface.getContentss().get(0) instanceof MEnumDef);
        MEnumDef type = (MEnumDef)iface.getContentss().get(0);
        EnumTest.checkEnumColor(type);        
    }   
    
    public void testInterfaceForwardDcl() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   struct Person;"  +
                "};" +
                StructTest.getStructPersonSource(), "IFace");

        assertEquals(iface.getIdentifier(), "IFace");
        
        assertTrue(iface.getContentss().get(0) instanceof MStructDef);
        MStructDef type = (MStructDef)iface.getContentss().get(0);
        StructTest.checkStructPerson(type);        
    }      
    
    
    public void testInterfaceConstants() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                EnumTest.getEnumColorSource() +
                "interface IFace { " +
                "   const float FLOAT_CONST = 3.14;"  +
                "   const string STRING_CONST = \"1234567890\";" +
                "   const Color ENUM_CONST = red;" +
                "};" +
                StructTest.getStructPersonSource(), "IFace");

        assertEquals(iface.getIdentifier(), "IFace");        
        {
            assertTrue(iface.getContentss().get(0) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)iface.getContentss().get(0);            
            assertTrue(constant.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveKind kind = ((MPrimitiveDef) constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_FLOAT);
            Float constValue = (Float) constant.getConstValue();
            assertEquals(constValue.floatValue(), 3.14, ConstantTest.FLOAT_DELTA);
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)iface.getContentss().get(1);
            assertEquals(constant.getIdentifier(), "STRING_CONST");
            assertTrue(constant.getIdlType() instanceof MStringDef);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");        
        }
        {
            assertTrue(iface.getContentss().get(2) instanceof MConstantDef);
            MConstantDef constant = (MConstantDef)iface.getContentss().get(2);            
            assertTrue(constant.getIdlType() instanceof MEnumDef);
            ScopedName constValue = (ScopedName)constant.getConstValue();
            assertEquals(constValue, new ScopedName("red"));      
        }
    }          

    
    public void testInterfaceException() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                ExceptionTest.getSimpleExceptionSource() +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        
        assertTrue(iface.getContentss().get(0) instanceof MExceptionDef);
        ExceptionTest.checkSimpleException((MExceptionDef)iface.getContentss().get(0));        
    }      
}
