package ccmtools.parser.idl.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;


public class TypedefDefinedInTest extends TypedefTest
{
    public TypedefDefinedInTest()
        throws FileNotFoundException
    {
        super(TypedefDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefDefinedInTest.class);
    }
    
    
    public void testTypedefDefinedIn() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float FloatType;");

        // Each contained element has to know its container
        assertNotNull(alias.getDefinedIn());
    }             
}
