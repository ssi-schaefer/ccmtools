package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.metamodel.BaseIDL.MAttributeDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.struct.StructTest;


public class ComponentAttributeTest extends ComponentTest
{
    public ComponentAttributeTest()
        throws FileNotFoundException
    {
        super(ComponentAttributeTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentAttributeTest.class);
    }
    
     
    public void testComponentWithAttributes() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                EnumTest.getEnumColorSource() +
                StructTest.getStructPersonSource() +
                "component AComponent {" +
                "   attribute short shortAttr;" +
                "   attribute string stringAttr;" +
                "   attribute Color enumAttr;" +
                "   attribute Person structAttr;" + 
                "};", "AComponent");

        {
            assertTrue(component.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)component.getContentss().get(0);
            assertEquals("shortAttr", attr.getIdentifier());           
            PrimitiveTest.checkShortType(attr.getIdlType());
        }
        {
            assertTrue(component.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)component.getContentss().get(1);
            assertEquals("stringAttr", attr.getIdentifier());           
            PrimitiveTest.checkStringType(attr.getIdlType());
        }
        {
            assertTrue(component.getContentss().get(2) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)component.getContentss().get(2);
            assertEquals("enumAttr", attr.getIdentifier());           
            EnumTest.checkEnumColor(attr.getIdlType());
        }
        {
            assertTrue(component.getContentss().get(3) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)component.getContentss().get(3);
            assertEquals("structAttr", attr.getIdentifier());           
            StructTest.checkStructPerson(attr.getIdlType());
        }
        
    }         
}
