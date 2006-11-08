package ccmtools.parser.idl.enumeration;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;


public class EmptyEnumTest extends EnumTest
{
    public EmptyEnumTest()
        throws FileNotFoundException
    {
        super(EmptyEnumTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EmptyEnumTest.class);
    }
    
     
    public void testEmptyEnumError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("enum Color { };");
            fail();
        }
        catch(Exception e)
        {
            /* OK */
            System.out.println(e.getMessage());
        }
    } 
}
