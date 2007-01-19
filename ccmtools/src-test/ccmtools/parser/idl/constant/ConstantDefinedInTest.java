package ccmtools.parser.idl.constant;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MConstantDef;

public class ConstantDefinedInTest extends ConstantTest
{
    public ConstantDefinedInTest() throws FileNotFoundException
    {
        super(ConstantDefinedInTest.class.getName());
    }

    public static Test suite()
    {
        return new TestSuite(ConstantDefinedInTest.class);
    }

    
    public void testConstantDefinedIn() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const float FLOAT_CONST = 3.14;");

        // Each contained element has to know its container
        assertNotNull(constant.getDefinedIn());        
    }
}
