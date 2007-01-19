package ccmtools.parser.idl.array;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.metamodel.BaseIDL.MAliasDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MArrayDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MUnionDef;
import ccmtools.parser.idl.struct.StructTest;
import ccmtools.parser.idl.union.UnionTest;


public class ArrayOfConstructedTypesTest extends ArrayTest
{
    public ArrayOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(ArrayOfConstructedTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ArrayOfConstructedTypesTest.class);
    }

    
    public void testArrayOfStruct() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                StructTest.getStructPersonSource() +
                "typedef Person ArrayStruct[7];");

        assertEquals(alias.getIdentifier(), "ArrayStruct");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MStructDef);
        MStructDef struct = (MStructDef)array.getIdlType();
        StructTest.checkStructPerson(struct);
    }
       
    public void testArrayOfUnion() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                UnionTest.getUnionOptionalSource() +
                "typedef UnionOptional ArrayUnion[7];");

        assertEquals(alias.getIdentifier(), "ArrayUnion");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MUnionDef);
        MUnionDef union = (MUnionDef)array.getIdlType();
        UnionTest.checkUnionOptional(union);    
    }
    
    public void testArrayOfEnum() throws CcmtoolsException
    {
        MAliasDef alias = parseSource(
                EnumTest.getEnumColorSource() +
                "typedef Color ArrayEnum[7];");

        assertEquals(alias.getIdentifier(), "ArrayEnum");        
        assertTrue(alias.getIdlType() instanceof MArrayDef);
        MArrayDef array = (MArrayDef) alias.getIdlType();
        assertEquals(array.getBounds().get(0), 7);
        
        assertTrue(array.getIdlType() instanceof MEnumDef);
        MEnumDef enumeration = (MEnumDef)array.getIdlType();
        EnumTest.checkEnumColor(enumeration);    
    }   
}
