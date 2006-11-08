package ccmtools.parser.idl.forward;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.struct.StructTest;
import ccmtools.parser.idl.union.UnionTest;


public class UnionForwardDeclarationTest extends StructTest
{
    public UnionForwardDeclarationTest()
        throws FileNotFoundException
    {
        super(UnionForwardDeclarationTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(UnionForwardDeclarationTest.class);
    }
         

    public void testStructOfStruct() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "union UnionOptional;" +
                "struct StructOfUnion { " +
                "   UnionOptional p; " +
                "};" +
                UnionTest.getUnionOptionalSource(), "StructOfUnion");

        assertEquals(struct.getIdentifier(), "StructOfUnion");                        
        {
            MFieldDef field = struct.getMember(0);
            UnionTest.checkUnionOptional(field);
        }    
    }
}
