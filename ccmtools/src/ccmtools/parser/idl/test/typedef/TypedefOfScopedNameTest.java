package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


public class TypedefOfScopedNameTest extends TypedefTest
{
    public TypedefOfScopedNameTest()
        throws FileNotFoundException
    {
        super(TypedefOfScopedNameTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefOfScopedNameTest.class);
    }

    /** base types */ 
    
    public void testTypedefOfMetaFloat() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef float FloatType;" +
                "typedef FloatType MetaFloatType;");
        
        assertEquals(typedef.getIdentifier(), "MetaFloatType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "FloatType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef prim = (MPrimitiveDef)alias.getIdlType();
        assertEquals(prim.getKind(), MPrimitiveKind.PK_FLOAT);        
    }             

    
    /** template types */
    
    public void testTypedefOfSequenceType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef sequence<short> SeqShort;" +
                "typedef SeqShort SequenceType;");
        
        assertEquals(typedef.getIdentifier(), "SequenceType");
                
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "SeqShort");
        
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef)alias.getIdlType();
        assertTrue(seq.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef prim = (MPrimitiveDef)seq.getIdlType();
        assertEquals(prim.getKind(), MPrimitiveKind.PK_SHORT);        
    }             
    

    public void testTypedefOfMetaStringType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef string StringType;" +
                "typedef StringType MetaStringType;");
        
        assertEquals(typedef.getIdentifier(), "MetaStringType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "StringType");
        
        assertTrue(alias.getIdlType() instanceof MStringDef);
    }             
    

    public void testTypedefOfMetaWideStringType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef wstring WStringType;" +
                "typedef WStringType MetaWStringType;");
        
        assertEquals(typedef.getIdentifier(), "MetaWStringType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "WStringType");
        
        assertTrue(alias.getIdlType() instanceof MWstringDef);
    }             
    
    
    public void testTypedefOfMetaFixedType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef fixed<9,2> FixedType;" +
                "typedef FixedType MetaFixedType;");
        
        assertEquals(typedef.getIdentifier(), "MetaFixedType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "FixedType");
        
        assertTrue(alias.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef) alias.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 2);
    }             
    
    
    /** scoped name */
    
    public void testTypedefOfScopedNameType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef float FloatType;" +
                "typedef FloatType MetaFloatType;" +
                "typedef MetaFloatType MetaMetaFloatType;");
        
        assertEquals(typedef.getIdentifier(), "MetaMetaFloatType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "MetaFloatType");
        
        assertTrue(alias.getIdlType() instanceof MAliasDef);
        MAliasDef alias2 = (MAliasDef)alias.getIdlType();
        assertEquals(alias2.getIdentifier(), "FloatType");
                
        assertTrue(alias2.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef prim = (MPrimitiveDef)alias2.getIdlType();
        assertEquals(prim.getKind(), MPrimitiveKind.PK_FLOAT);        
    }
    
    
    /** constructed types */
    
    public void testTypedefOfStructType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "struct Person { long id; string name; };" +
                "typedef Person StructType;");
        
        assertEquals(typedef.getIdentifier(), "StructType");
        
        assertTrue(typedef.getIdlType() instanceof MStructDef);
        MStructDef structure = (MStructDef)typedef.getIdlType();
        {
            MFieldDef field = structure.getMember(0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef) field.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(field.getIdentifier(), "id");
        }
        {
            MFieldDef field = structure.getMember(1);
            assertTrue(field.getIdlType() instanceof MStringDef);
            assertEquals(field.getIdentifier(), "name");
        }
    }                 
    
    
    // TODO: union_type
    
    
    public void testTypedefOfEnumType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "enum Color {red, green, blue};" +
                "typedef Color EnumType;");
        
        assertEquals(typedef.getIdentifier(), "EnumType");
        
        assertTrue(typedef.getIdlType() instanceof MEnumDef);
        MEnumDef enumeration = (MEnumDef)typedef.getIdlType();
        assertEquals(enumeration.getIdentifier(), "Color");
        assertEquals(enumeration.getMember(0), "red");
        assertEquals(enumeration.getMember(1), "green");
        assertEquals(enumeration.getMember(2), "blue");
    }                 
}
