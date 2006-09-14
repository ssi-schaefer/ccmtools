package ccmtools.parser.idl.test.literal;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;


public class LiteralErrorTest extends LiteralTest
{
    public LiteralErrorTest()
        throws FileNotFoundException
    {
        super(LiteralErrorTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralErrorTest.class);
    }
    
    public void testBoundedStringLiteralError() throws CcmtoolsException
    {
        try
        {
            parseSource("const string<7> STRING_LITERAL = \"01234567\";");
            fail();
        }
        catch (Exception e)
        {

        }
    }
    
    public void testBoundedWideStringLiteralError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("const wstring<7> STRING_LITERAL = L\"01234567\";");
            fail();
        }
        catch (Exception e)
        {

        }
    }
}
