package ccmtools.parser.idl.test.constant;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


public class ConstantOfTemplateTypesTest extends ConstantTest
{
    public ConstantOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(ConstantOfTemplateTypesTest.class.getName());        
    }
        
    public static Test suite()
    {
        return new TestSuite(ConstantOfTemplateTypesTest.class);
    }
    

    public void testStringConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const string STRING_CONST = \"1234567890\";");
        assertEquals(constant.getIdentifier(), "STRING_CONST");
        assertTrue(constant.getIdlType() instanceof MStringDef);
        String constValue = (String)constant.getConstValue();
        assertEquals(constValue, "1234567890");          
    }
    
    public void testBoundedStringConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const string<5> BSTRING_CONST = \"123\";");
        assertEquals(constant.getIdentifier(), "BSTRING_CONST");
        assertTrue(constant.getIdlType() instanceof MStringDef);
        MStringDef s = (MStringDef)constant.getIdlType();
        assertEquals(s.getBound().intValue(), 5);
        String constValue = (String)constant.getConstValue();
        assertEquals(constValue, "123");          
    }
    
    
    public void testWideStringConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const wstring WSTRING_CONST = L\"1234567890\";");
        assertEquals(constant.getIdentifier(), "WSTRING_CONST");
        assertTrue(constant.getIdlType() instanceof MWstringDef);
        String constValue = (String)constant.getConstValue();
        assertEquals(constValue, "1234567890");          
    }

    public void testBoundedWideStringConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const wstring<5> BWSTRING_CONST = L\"123\";");
        assertEquals(constant.getIdentifier(), "BWSTRING_CONST");
        assertTrue(constant.getIdlType() instanceof MWstringDef);
        MWstringDef s = (MWstringDef)constant.getIdlType();
        assertEquals(s.getBound().intValue(), 5);
        String constValue = (String)constant.getConstValue();
        assertEquals(constValue, "123");          
    }
    
    
    
    // TODO: fixed_pt_const_type
}
