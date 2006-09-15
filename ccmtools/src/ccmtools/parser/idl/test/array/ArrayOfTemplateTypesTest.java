package ccmtools.parser.idl.test.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MSequenceDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


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

    
    // TODO: Array of Sequences
    
    
    public void testArrayOfString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string ArrayString[7];");

        assertEquals(alias.getIdentifier(), "ArrayString");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        assertTrue(array.getIdlType() instanceof MStringDef);
    }


    public void testArrayOfBoundedString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef string<6>  ArrayBString[7];"); 

        assertEquals(alias.getIdentifier(), "ArrayBString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MStringDef);
        MStringDef type = (MStringDef)array.getIdlType();
        assertEquals(type.getBound().longValue(), 6);
    }
    
    
    public void testArrayOfWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring ArrayWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MWstringDef);
    }
    
    public void testArrayOfBoundedWideString() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wstring<3> ArrayBWString[7];");

        assertEquals(alias.getIdentifier(), "ArrayBWString");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MWstringDef);
        MWstringDef type = (MWstringDef)array.getIdlType();
        assertEquals(type.getBound().longValue(), 3);
    }

    public void testArrayOfFixed() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef fixed<9,3> ArrayFixed[7];");

        assertEquals(alias.getIdentifier(), "ArrayFixed");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef)array.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 3);
    }
    
    
}
