package ccmtools.parser.idl.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDef;


public class EmptyExceptionTest extends ExceptionTest
{
    public EmptyExceptionTest()
        throws FileNotFoundException
    {
        super(EmptyExceptionTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EmptyExceptionTest.class);
    }
    

    public void testEmptyException() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex {};");

        assertEquals(e.getIdentifier(), "Ex");
        assertEquals(e.getMembers().size() ,0);
    }
}
