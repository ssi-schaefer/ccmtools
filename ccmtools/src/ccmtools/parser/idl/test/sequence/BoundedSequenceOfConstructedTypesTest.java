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


public class BoundedSequenceOfConstructedTypesTest extends SequenceTest
{
    public BoundedSequenceOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(BoundedSequenceOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(BoundedSequenceOfConstructedTypesTest.class);
    }

    
    public void testBoundedSequenceOfStruct() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                StructTest.getStructPersonSource() +
                "typedef sequence<Person,5> SeqStruct;");

        assertEquals(alias.getIdentifier(), "SeqStruct");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 5);
        
        StructTest.checkStructPerson(seq.getIdlType());
    }

    public void testBoundedSequenceOfUnion() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                UnionTest.getUnionOptionalSource() +
                "typedef sequence<UnionOptional,5> SeqUnion;");

        assertEquals(alias.getIdentifier(), "SeqUnion");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 5);
        
        UnionTest.checkUnionOptional(seq.getIdlType());
    }
    
    public void testBoundedSequenceOfEnum() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                EnumTest.getEnumColorSource() +
                "typedef sequence<Color,5> SeqEnum;");

        assertEquals(alias.getIdentifier(), "SeqEnum");
        assertTrue(alias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef) alias.getIdlType();
        assertEquals(seq.getBound().longValue(), 5);
        
        EnumTest.checkEnumColor(seq.getIdlType());
    }
}
