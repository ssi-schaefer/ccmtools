package ccmtools.parser.idl.sequence;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;


public class SequenceDefinedInTest extends SequenceTest
{
    public SequenceDefinedInTest()
        throws FileNotFoundException
    {
        super(SequenceDefinedInTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(SequenceDefinedInTest.class);
    }

    
    public void testSequenceDefinedIn() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<float,7> SeqFloat;");

        // Each contained element has to know its container
        assertNotNull(alias.getDefinedIn());
    }

    public void testBoundedSequenceDefinedIn() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef sequence<double,7> SeqDouble;");

        // Each contained element has to know its container
        assertNotNull(alias.getDefinedIn());
    }
}
