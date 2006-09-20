package ccmtools.parser.idl.test.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


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
        
        PrimitiveTest.checkStringType(array);
    }


    public void testArrayOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string<6>  ArrayBString[7];"); 

        assertEquals(alias.getIdentifier(), "ArrayBString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkBoundedStringType(array, 6);
    }
    
    
    public void testArrayOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring ArrayWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkWideStringType(array);
    }
    
    public void testArrayOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring<3> ArrayBWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayBWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);

        PrimitiveTest.checkBoundedWideStringType(array, 3);
    }

    public void testArrayOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef fixed<9,3> ArrayFixed[7];");

        assertEquals(alias.getIdentifier(), "ArrayFixed");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        PrimitiveTest.checkFixedType(array);
    }    
}
