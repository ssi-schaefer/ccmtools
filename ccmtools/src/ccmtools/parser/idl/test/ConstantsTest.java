package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ConstantsTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    private final float FLOAT_DELTA = 0.001f;
    private final double DOUBLE_DELTA = 0.000001;
    
    public ConstantsTest()
        throws FileNotFoundException
    {
        super("IDL Constants Test");        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(ConstantsTest.class);
    }
    
        
    public void testShortConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const short SHORT_CONST = -7;");
        assertEquals(constant.getIdentifier(), "SHORT_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_SHORT);
        Short constValue = (Short)constant.getConstValue();
        assertEquals(constValue.intValue(), -7);           
    }
    
    public void testUnsignedShortConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const unsigned short USHORT_CONST = 7;");
        assertEquals(constant.getIdentifier(), "USHORT_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_USHORT);
        Short constValue = (Short)constant.getConstValue();
        assertEquals(constValue.intValue(), 7);           
    }

    
    public void testLongConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const long LONG_CONST = -7777;");
        assertEquals(constant.getIdentifier(), "LONG_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_LONG);            
        Integer constValue = (Integer)constant.getConstValue();
        assertEquals(constValue.intValue(), -7777);          
    } 

    public void testUnsignedLongConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const unsigned long ULONG_CONST = 7777;");
        assertEquals(constant.getIdentifier(), "ULONG_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_ULONG);            
        Integer constValue = (Integer)constant.getConstValue();
        assertEquals(constValue.intValue(), 7777);          
    } 

        
    public void testLongLongConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const long long LONG_LONG_CONST = -7777777;");
        assertEquals(constant.getIdentifier(), "LONG_LONG_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_LONGLONG);            
        Long constValue = (Long)constant.getConstValue();
        assertEquals(constValue.intValue(), -7777777);          
    } 

    public void testUnsignedLongLongConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const unsigned long long ULONG_LONG_CONST = 7777777;");
        assertEquals(constant.getIdentifier(), "ULONG_LONG_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_ULONGLONG);
        Long constValue = (Long)constant.getConstValue();
        assertEquals(constValue.intValue(), 7777777);          
    } 
    

    public void testCharConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const char CHAR_CONST = 'c';");
        assertEquals(constant.getIdentifier(), "CHAR_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_CHAR);
        Character constValue = (Character)constant.getConstValue();
        assertEquals(constValue.charValue(), 'c');          
    }
    
    public void testWideCharConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const wchar WCHAR_CONST = L'c';");
        assertEquals(constant.getIdentifier(), "WCHAR_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_WCHAR);
        Character constValue = (Character)constant.getConstValue();
        assertEquals(constValue.charValue(), 'c');          
    } 

    

    public void testBooleanConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const boolean BOOLEAN_CONST = TRUE;");
        assertEquals(constant.getIdentifier(), "BOOLEAN_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_BOOLEAN);            
        Boolean constValue = (Boolean)constant.getConstValue();
        assertEquals(constValue.booleanValue(),true);        
    } 
    
    
    
    public void testFloatConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const float FLOAT_CONST = 3.14;");
        assertEquals(constant.getIdentifier(), "FLOAT_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_FLOAT);
        Float constValue = (Float)constant.getConstValue();
        assertEquals(constValue.floatValue(), 3.14, FLOAT_DELTA);          
    } 
        
    public void testDoubleConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const double DOUBLE_CONST = 3.1415926;");
        assertEquals(constant.getIdentifier(), "DOUBLE_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_DOUBLE);
        Double constValue = (Double)constant.getConstValue();
        assertEquals(constValue.floatValue(), 3.1415926, DOUBLE_DELTA);          
    } 
    
    public void testLongDoubleConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const long double LDOUBLE_CONST = 3.1415926;");
        assertEquals(constant.getIdentifier(), "LDOUBLE_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_LONGDOUBLE);
        Double constValue = (Double)constant.getConstValue();
        assertEquals(constValue.floatValue(), 3.1415926, DOUBLE_DELTA);          
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
    
    
    // TODO: scoped_name
    
        
    public void testOctetConstant()
        throws CcmtoolsException
    {       
        MConstantDef constant = parseSource("const octet OCTET_CONST = 0715;");
        assertEquals(constant.getIdentifier(), "OCTET_CONST");
        MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
        assertEquals(kind, MPrimitiveKind.PK_OCTET);            
        Integer constValue = (Integer)constant.getConstValue();
        assertEquals(constValue.intValue(), 0715); // = 461 dec       
    } 


    
    /*
     * Utility Methods
     */
    
    private MConstantDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        MConstantDef constant = (MConstantDef)modelElements.get(0);            
        System.out.println(modelElements);
        return constant;
    }
    
}
