package ccmtools.parser.idl.test.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MArrayDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


public class ArrayDimensionsTest extends ArrayTest
{
    public ArrayDimensionsTest()
        throws FileNotFoundException
    {
        super(ArrayDimensionsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ArrayDimensionsTest.class);
    }

    public void testMatrixOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float ArrayFloat[7][2];");

        assertEquals(alias.getIdentifier(), "ArrayFloat");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        assertEquals(array.getBounds().get(1), 2);
        
        PrimitiveTest.checkFloatType(array);
    }
    

    public void testCubeOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float ArrayFloat[7][2][3];");

        assertEquals(alias.getIdentifier(), "ArrayFloat");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        assertEquals(array.getBounds().get(1), 2);
        assertEquals(array.getBounds().get(2), 3);
        
        PrimitiveTest.checkFloatType(array);
    }

    public void testHyperCubeOfFloat() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float ArrayFloat[5][4][3][2][1];");

        assertEquals(alias.getIdentifier(), "ArrayFloat");
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef)alias.getIdlType();
        assertEquals(array.getBounds().get(0), 5);
        assertEquals(array.getBounds().get(1), 4);
        assertEquals(array.getBounds().get(2), 3);
        assertEquals(array.getBounds().get(3), 2);
        assertEquals(array.getBounds().get(4), 1);

        PrimitiveTest.checkFloatType(array);
    }

}
