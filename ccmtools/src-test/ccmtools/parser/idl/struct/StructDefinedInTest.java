package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MStructDef;


public class StructDefinedInTest extends StructTest
{
    public StructDefinedInTest()
        throws FileNotFoundException
    {
        super(StructDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructDefinedInTest.class);
    }
    
   
    public void testStructOfFloatMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s {" + 
                                        "   float floatMember;" +
                                        "   double doubleMember;" +
                                        "   long double ldoubleMember;" +
                                        "};");

        // Each contained element has to know its container
        assertNotNull(struct.getDefinedIn());
    }
}
