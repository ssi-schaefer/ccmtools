package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.union.UnionTest;


public class StructOfConstructedTypesTest extends StructTest
{
    public StructOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(StructOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructOfConstructedTypesTest.class);
    }
         

    public void testStructOfStructMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                StructTest.getStructPersonSource() +
                "struct StructOfStruct { " +
                "   Person structMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "StructOfStruct");
        MFieldDef field = struct.getMember(0);
        StructTest.checkStructPerson(field.getIdlType());
        assertEquals(field.getIdentifier(), "structMember");
    }

    public void testStructOfUnionMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                UnionTest.getUnionOptionalSource() +
                "struct StructOfUnion { " +
                "   UnionOptional unionMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "StructOfUnion");
        MFieldDef field = struct.getMember(0);
        UnionTest.checkUnionOptional(field.getIdlType());
        assertEquals(field.getIdentifier(), "unionMember");
    }

    public void testStructOfEnumMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                EnumTest.getEnumColorSource() +
                "struct StructOfEnum { " +
                "   Color enumMember; " +
                "};");

        assertEquals(struct.getIdentifier(), "StructOfEnum");
        MFieldDef field = struct.getMember(0);
        EnumTest.checkEnumColor(field.getIdlType());
        assertEquals(field.getIdentifier(), "enumMember");
    }    
}
