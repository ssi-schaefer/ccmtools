package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class ExceptionOfConstructedTypesTest extends ExceptionTest
{    
    public ExceptionOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(ExceptionOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ExceptionOfConstructedTypesTest.class);
    }
         

    public void testExceptionOfStructMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource(
                StructTest.getStructPersonSource() +
                "exception ExceptionOfStruct { " +
                "   Person structMember; " +
                "};");

        assertEquals(e.getIdentifier(), "ExceptionOfStruct");
        MFieldDef field = getMember(e,0);
        StructTest.checkStructPerson(field);
        assertEquals(field.getIdentifier(), "structMember");
    }


    public void testExceptionOfUnionMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource(
                UnionTest.getUnionOptionalSource() +
                "exception ExceptionOfUnion { " +
                "   UnionOptional unionMember; " +
                "};");

        assertEquals(e.getIdentifier(), "ExceptionOfUnion");
        MFieldDef field = getMember(e,0);
        UnionTest.checkUnionOptional(field);
        assertEquals(field.getIdentifier(), "unionMember");
    }

    
    public void testExceptionOfEnumMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource(
                EnumTest.getEnumColorSource() +
                "exception ExceptionOfEnum { " +
                "   Color enumMember; " +
                "};");

        assertEquals(e.getIdentifier(), "ExceptionOfEnum");
        MFieldDef field = getMember(e,0);
        EnumTest.checkEnumColor(field);
        assertEquals(field.getIdentifier(), "enumMember");
    }
}
