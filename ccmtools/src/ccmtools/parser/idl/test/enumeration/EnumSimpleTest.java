package ccmtools.parser.idl.test.enumeration;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MEnumDef;


public class EnumSimpleTest extends EnumTest
{
    public EnumSimpleTest()
        throws FileNotFoundException
    {
        super(EnumSimpleTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(EnumSimpleTest.class);
    }
    
     
    public void testEnum() 
        throws CcmtoolsException
    {       
        MEnumDef enumeration = parseSource("enum Color { red, green, blue };"); 
        assertEquals(enumeration.getIdentifier(), "Color");
        assertEquals(enumeration.getMember(0), "red");
        assertEquals(enumeration.getMember(1), "green");
        assertEquals(enumeration.getMember(2), "blue");
    } 
}
