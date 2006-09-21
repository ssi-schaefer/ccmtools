package ccmtools.parser.idl.test.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class SequenceOfConstructedTypesTest extends SequenceTest
{
    public SequenceOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(SequenceOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(SequenceOfConstructedTypesTest.class);
    }

    
    public void testSequenceOfStruct() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                StructTest.getStructPersonSource() +
                "typedef sequence<Person> SeqStruct;");

        assertEquals(alias.getIdentifier(), "SeqStruct");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        StructTest.checkStructPerson(seq.getIdlType());
    }

    public void testSequenceOfUnion() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                UnionTest.getUnionOptionalSource() +
                "typedef sequence<UnionOptional> SeqUnion;");

        assertEquals(alias.getIdentifier(), "SeqUnion");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        UnionTest.checkUnionOptional(seq.getIdlType());
    }
    
    public void testSequenceOfEnum() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                EnumTest.getEnumColorSource() +
                "typedef sequence<Color> SeqEnum;");

        assertEquals(alias.getIdentifier(), "SeqEnum");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        
        EnumTest.checkEnumColor(seq.getIdlType());
    }
}
