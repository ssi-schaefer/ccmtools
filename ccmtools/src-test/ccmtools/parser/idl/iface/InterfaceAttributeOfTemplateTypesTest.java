package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.parser.idl.sequence.SequenceTest;


public class InterfaceAttributeOfTemplateTypesTest extends InterfaceTest
{
    public InterfaceAttributeOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(InterfaceAttributeOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceAttributeOfTemplateTypesTest.class);
    }
    
     
    public void testInterfaceAttributeOfSequenceType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getLongSequenceSource() +
                "interface IFace { " +
                "   attribute LongSequence seqAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            SequenceTest.checkLongSequence(attr);
            assertEquals(attr.getIdentifier(), "seqAttr");
        }
    }
    

    public void testInterfaceAttributeOfStringType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getLongSequenceSource() +
                "interface IFace { " +
                "   attribute string stringAttr;" +
                "   attribute string<7> bstringAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkStringType(attr);
            assertEquals(attr.getIdentifier(), "stringAttr");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);        
            PrimitiveTest.checkBoundedStringType(attr,7);
            assertEquals(attr.getIdentifier(), "bstringAttr");
        }
    }

    
    public void testInterfaceAttributeOfWideStringType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getLongSequenceSource() +
                "interface IFace { " +
                "   attribute wstring wstringAttr;" +
                "   attribute wstring<7> bwstringAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkWideStringType(attr);
            assertEquals(attr.getIdentifier(), "wstringAttr");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(1);        
            PrimitiveTest.checkBoundedWideStringType(attr,7);
            assertEquals(attr.getIdentifier(), "bwstringAttr");
        }
    }

    
    public void testInterfaceAttributeOfFixedType() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                SequenceTest.getLongSequenceSource() +
                "interface IFace { " +
                "   attribute fixed<9,3> fixedAttr;" +
                "};");

        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkFixedType(attr, 9,3);
            assertEquals(attr.getIdentifier(), "fixedAttr");
        }
    }
}
