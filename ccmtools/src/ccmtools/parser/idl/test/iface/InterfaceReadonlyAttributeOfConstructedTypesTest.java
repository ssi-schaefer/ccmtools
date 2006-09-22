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


public class InterfaceReadonlyAttributeOfConstructedTypesTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeOfConstructedTypesTest.class);
    }
    
     
    public void testInterfaceReadonlyAttributeOfStruct() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                StructTest.getStructPersonSource() +
                "interface IFace { " +
                "   readonly attribute Person structAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            StructTest.checkStructPerson(attr);
            assertEquals(attr.getIdentifier(), "structAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfUnion() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                UnionTest.getUnionOptionalSource() +
                "interface IFace { " +
                "   readonly attribute UnionOptional unionAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            UnionTest.checkUnionOptional(attr);
            assertEquals(attr.getIdentifier(), "unionAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfEnum() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                EnumTest.getEnumColorSource() +
                "interface IFace { " +
                "   readonly attribute Color enumAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            EnumTest.checkEnumColor(attr);
            assertEquals(attr.getIdentifier(), "enumAttr");
        }
    }    
}
