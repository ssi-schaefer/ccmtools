package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.struct.StructTest;
import ccmtools.parser.idl.test.union.UnionTest;


public class InterfaceAttributeOfConstructedTypesTest extends InterfaceTest
{
    public InterfaceAttributeOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeOfConstructedTypesTest.class);
    }
    
     
    public void testInterfaceAttributeOfStructType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                StructTest.getStructPersonSource() +
                "interface IFace { " +
                "   attribute Person structAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            StructTest.checkStructPerson(attr);
            assertEquals(attr.getIdentifier(), "structAttr");
        }
    }


    public void testInterfaceAttributeOfUnionType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                UnionTest.getUnionOptionalSource() +
                "interface IFace { " +
                "   attribute UnionOptional unionAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            UnionTest.checkUnionOptional(attr);
            assertEquals(attr.getIdentifier(), "unionAttr");
        }
    }

    
    public void testInterfaceAttributeOfEnumType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                EnumTest.getEnumColorSource() +
                "interface IFace { " +
                "   attribute Color enumAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            EnumTest.checkEnumColor(attr);
            assertEquals(attr.getIdentifier(), "enumAttr");
        }
    }
}
