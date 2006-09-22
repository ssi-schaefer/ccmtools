package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
import ccmtools.parser.idl.test.sequence.SequenceTest;


public class InterfaceReadonlyAttributeOfTemplateTypesTest extends InterfaceTest
{
    public InterfaceReadonlyAttributeOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceReadonlyAttributeOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceReadonlyAttributeOfTemplateTypesTest.class);
    }
    
     
    public void testInterfaceReadonlyAttributeOfSequence() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getSimpleSequenceSource() +
                "interface IFace { " +
                "   readonly attribute SimpleSequence seqAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            SequenceTest.checkSimpleSequence(attr);
            assertEquals(attr.getIdentifier(), "seqAttr");
        }
    }
    

    public void testInterfaceReadonlyAttributeOfString() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute string stringAttr;" +
                "   readonly attribute string<7> bstringAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkStringType(attr);
            assertEquals(attr.getIdentifier(), "stringAttr");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkBoundedStringType(attr, 7);
            assertEquals(attr.getIdentifier(), "bstringAttr");
        }
    }


    public void testInterfaceReadonlyAttributeOfWideString() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace { " +
                "   readonly attribute wstring wstringAttr;" +
                "   readonly attribute wstring<7> bwstringAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkWideStringType(attr);
            assertEquals(attr.getIdentifier(), "wstringAttr");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkBoundedWideStringType(attr, 7);
            assertEquals(attr.getIdentifier(), "bwstringAttr");
        }
    }
    
    public void testInterfaceReadonlyAttributeOfFixed() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getSimpleSequenceSource() +
                "interface IFace { " +
                "   readonly attribute fixed<9,3> fixedAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);
            assertTrue(attr.isReadonly());        
            PrimitiveTest.checkFixedType(attr);
            assertEquals(attr.getIdentifier(), "fixedAttr");
        }
    }    
}
