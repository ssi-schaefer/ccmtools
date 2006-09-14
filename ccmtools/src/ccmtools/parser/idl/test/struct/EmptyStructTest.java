package ccmtools.parser.idl.test.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;


public class EmptyStructTest extends StructTest
{
    public EmptyStructTest()
        throws FileNotFoundException
    {
        super(EmptyStructTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EmptyStructTest.class);
    }
    
     
    public void testEmptyStructError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("struct Person { };");
            fail();
        }
        catch (Exception e)
        {
            /* OK */
            System.out.println(e.getMessage());
        }
    }                
}
