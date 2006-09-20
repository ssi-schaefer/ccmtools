package ccmtools.parser.idl.test.forward;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.test.struct.StructTest;


public class StructForwardDeclarationTest extends StructTest
{
    public StructForwardDeclarationTest()
        throws FileNotFoundException
    {
        super(StructForwardDeclarationTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructForwardDeclarationTest.class);
    }
         

    public void testStructOfStruct() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "struct Person;" +
                "struct StructOfStruct { Person p; };" +
                StructTest.getStructPersonSource(), "StructOfStruct");

        assertEquals(struct.getIdentifier(), "StructOfStruct");                        
        {
            MFieldDef field = struct.getMember(0);
            StructTest.checkStructPerson(field);
        }    
    }
}
