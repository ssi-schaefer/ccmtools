package ccmtools.parser.idl.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MArrayDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MSequenceDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class ArrayOfTemplateTypesTest extends ArrayTest
{
    public ArrayOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(ArrayOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ArrayOfTemplateTypesTest.class);
    }

    
    public void testArrayOfSequenceOfLong() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                "typedef sequence<long> SeqLong;" +
                "typedef SeqLong ArraySeq[7];");

        assertEquals(alias.getIdentifier(), "ArraySeq");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MAliasDef);
        MAliasDef innerAlias = (MAliasDef)array.getIdlType();
        assertEquals(innerAlias.getIdentifier(), "SeqLong");
        assertTrue(innerAlias.getIdlType() instanceof MSequenceDef);
        MSequenceDef seq = (MSequenceDef)innerAlias.getIdlType();

        PrimitiveTest.checkLongType((MTyped)seq);
    }
    
    
    public void testArrayOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string ArrayString[7];");

        assertEquals(alias.getIdentifier(), "ArrayString");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkStringType((MTyped)array);
    }


    public void testArrayOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string<6>  ArrayBString[7];"); 

        assertEquals(alias.getIdentifier(), "ArrayBString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkBoundedStringType((MTyped)array, 6);
    }
    
    
    public void testArrayOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring ArrayWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkWideStringType((MTyped)array);
    }
    
    public void testArrayOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring<3> ArrayBWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayBWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkBoundedWideStringType((MTyped)array, 3);
    }

    public void testArrayOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef fixed<9,3> ArrayFixed[7];");

        assertEquals(alias.getIdentifier(), "ArrayFixed");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkFixedType((MTyped)array, 9,3);
    }    
}
