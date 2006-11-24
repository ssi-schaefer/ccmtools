package ccmtools.parser.idl.home;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.CcmModelHelper;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.struct.StructTest;


public class HomeAttributesTest extends HomeTest
{
    public HomeAttributesTest()
        throws FileNotFoundException
    {
        super(HomeAttributesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(HomeAttributesTest.class);
    }
    
     
    public void testHomeAttributes() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                EnumTest.getEnumColorSource() +
                StructTest.getStructPersonSource() +
                "component MyComponent { };" +
                "home MyHome manages MyComponent" +
                "{" +
                "   attribute short shortAttr;" +
                "   attribute string stringAttr;" +
                "   attribute Color enumAttr;" +
                "   attribute Person structAttr;" + 
                "};", "MyHome");

        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
        
        {
            assertTrue(home.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)home.getContentss().get(0);
            assertEquals("shortAttr", attr.getIdentifier());           
            PrimitiveTest.checkShortType(attr.getIdlType());
        }
        {
            assertTrue(home.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)home.getContentss().get(1);
            assertEquals("stringAttr", attr.getIdentifier());           
            PrimitiveTest.checkStringType(attr.getIdlType());
        }
        {
            assertTrue(home.getContentss().get(2) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)home.getContentss().get(2);
            assertEquals("enumAttr", attr.getIdentifier());           
            EnumTest.checkEnumColor(attr.getIdlType());
        }
        {
            assertTrue(home.getContentss().get(3) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)home.getContentss().get(3);
            assertEquals("structAttr", attr.getIdentifier());           
            StructTest.checkStructPerson(attr.getIdlType());
        }
    }    
}
