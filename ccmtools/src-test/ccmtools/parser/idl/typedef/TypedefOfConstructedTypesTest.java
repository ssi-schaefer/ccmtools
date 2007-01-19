package ccmtools.parser.idl.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.struct.StructTest;
import ccmtools.parser.idl.union.UnionTest;


public class TypedefOfConstructedTypesTest extends TypedefTest
{
    public TypedefOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(TypedefOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefOfConstructedTypesTest.class);
    }

       
    public void testTypedefOfStruct() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                StructTest.getStructPersonSource() + 
                "typedef Person StructType;");

        assertEquals(alias.getIdentifier(), "StructType");
        StructTest.checkStructPerson(alias.getIdlType());
    }

    public void testTypedefOfUnion() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                UnionTest.getUnionOptionalSource() + 
                "typedef UnionOptional UnionType;");

        assertEquals(alias.getIdentifier(), "UnionType");
        UnionTest.checkUnionOptional(alias.getIdlType());
    }             

    public void testTypedefOfEnum() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                EnumTest.getEnumColorSource() + 
                "typedef Color EnumType;");

        assertEquals(alias.getIdentifier(), "EnumType");
        EnumTest.checkEnumColor(alias.getIdlType());
    }         
}
