package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


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
        PrimitiveTest.checkFloatType((MTyped)alias);
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
        PrimitiveTest.checkShortType((MTyped)seq);
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
        
        PrimitiveTest.checkStringType((MTyped)alias);        
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
    
        PrimitiveTest.checkWideStringType((MTyped)alias);
    }             
    
    
    public void testTypedefOfMetaFixedType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                "typedef fixed<9,3> FixedType;" +
                "typedef FixedType MetaFixedType;");
        
        assertEquals(typedef.getIdentifier(), "MetaFixedType");
        
        assertTrue(typedef.getIdlType() instanceof MAliasDef);
        MAliasDef alias = (MAliasDef)typedef.getIdlType();
        assertEquals(alias.getIdentifier(), "FixedType");
        PrimitiveTest.checkFixedType((MTyped)alias);
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
                
        PrimitiveTest.checkFloatType((MTyped)alias2);
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
            PrimitiveTest.checkLongType(field);
            assertEquals(field.getIdentifier(), "id");
        }
        {
            MFieldDef field = structure.getMember(1);
            PrimitiveTest.checkStringType(field);
            assertEquals(field.getIdentifier(), "name");
        }
    }                 
    
    
    // TODO: union_type
    
    
    public void testTypedefOfEnumType() throws CcmtoolsException
    {
        MAliasDef typedef = parseSource(
                EnumTest.getEnumColorSource() +
                "typedef Color EnumType;");
        
        assertEquals(typedef.getIdentifier(), "EnumType");
        EnumTest.checkEnumColor((MTyped)typedef);
    }                 
}
