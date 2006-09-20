package ccmtools.parser.idl.test.constant;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.test.enumeration.EnumTest;


public class ConstantOfConstructedTypesTest extends ConstantTest
{
    public ConstantOfConstructedTypesTest()
        throws FileNotFoundException
    {
        super(ConstantOfConstructedTypesTest.class.getName());        
    }
        
    public static Test suite()
    {
        return new TestSuite(ConstantOfConstructedTypesTest.class);
    }
    

    public void testEnumConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource(
                EnumTest.getEnumColorSource() +
                "const Color ENUM_CONST = red;");
        
        assertEquals(constant.getIdentifier(), "ENUM_CONST");
        assertTrue(constant.getIdlType() instanceof MEnumDef);
        ScopedName constValue = (ScopedName)constant.getConstValue();
        assertEquals(constValue, new ScopedName("red"));          
    }    
}
