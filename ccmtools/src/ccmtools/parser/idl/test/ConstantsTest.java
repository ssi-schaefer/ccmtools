package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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
        super("IDL Parser Test - Global Constants");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(ConstantsTest.class);
    }
    
        
    public void testShortConstant()
    {       
        try
        {
            String idlSource = "const short SHORT_CONST = -7;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
            
            MConstantDef constant = (MConstantDef)modelElements.get(0);            
            assertEquals(constant.getIdentifier(), "SHORT_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_SHORT);
            Short constValue = (Short)constant.getConstValue();
            assertEquals(constValue.intValue(), -7);           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testUnsignedShortConstant()
    {       
        try
        {
            String idlSource = "const unsigned short USHORT_CONST = 7;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
            
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "USHORT_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_USHORT);
            Short constValue = (Short)constant.getConstValue();
            assertEquals(constValue.intValue(), 7);           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    
    public void testLongConstant()
    {       
        try
        {
            String idlSource = "const long LONG_CONST = -7777;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "LONG_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_LONG);            
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), -7777);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 

    public void testUnsignedLongConstant()
    {       
        try
        {
            String idlSource = "const unsigned long ULONG_CONST = 7777;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "ULONG_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_ULONG);            
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 7777);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 

        
    public void testLongLongConstant()
    {       
        try
        {
            String idlSource = "const long long LONG_LONG_CONST = -7777777;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "LONG_LONG_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_LONGLONG);            
            Long constValue = (Long)constant.getConstValue();
            assertEquals(constValue.intValue(), -7777777);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 

    public void testUnsignedLongLongConstant()
    {       
        try
        {
            String idlSource = "const unsigned long long ULONG_LONG_CONST = 7777777;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "ULONG_LONG_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_ULONGLONG);
            Long constValue = (Long)constant.getConstValue();
            assertEquals(constValue.intValue(), 7777777);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
    
    
    public void testFloatConstant()
    {       
        try
        {
            String idlSource = "const float FLOAT_CONST = 3.14;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "FLOAT_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_FLOAT);
            Float constValue = (Float)constant.getConstValue();
            assertEquals(constValue.floatValue(), 3.14, FLOAT_DELTA);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
        
    public void testDoubleConstant()
    {       
        try
        {
            String idlSource = "const double DOUBLE_CONST = 3.1415926;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "DOUBLE_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_DOUBLE);
            Double constValue = (Double)constant.getConstValue();
            assertEquals(constValue.floatValue(), 3.1415926, DOUBLE_DELTA);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 
    
    public void testLongDoubleConstant()
    {       
        try
        {
            String idlSource = "const long double LDOUBLE_CONST = 3.1415926;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "LDOUBLE_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_LONGDOUBLE);
            Double constValue = (Double)constant.getConstValue();
            assertEquals(constValue.floatValue(), 3.1415926, DOUBLE_DELTA);          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 


    public void testCharConstant()
    {       
        try
        {
            String idlSource = "const char CHAR_CONST = 'c';";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "CHAR_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_CHAR);
            Character constValue = (Character)constant.getConstValue();
            assertEquals(constValue.charValue(), 'c');          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testWideCharConstant()
    {       
        try
        {
            String idlSource = "const wchar WCHAR_CONST = L'c';";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "WCHAR_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_WCHAR);
            Character constValue = (Character)constant.getConstValue();
            assertEquals(constValue.charValue(), 'c');          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 

    
    public void testStringConstant()
    {       
        try
        {
            String idlSource = "const string STRING_CONST = \"1234567890\";";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "STRING_CONST");
            assertTrue(constant.getIdlType() instanceof MStringDef);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    public void testBoundedStringConstant()
    {       
        try
        {
            String idlSource = "const string<5> BSTRING_CONST = \"1234567890\";";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "BSTRING_CONST");
            assertTrue(constant.getIdlType() instanceof MStringDef);
            MStringDef s = (MStringDef)constant.getIdlType();
            assertEquals(s.getBound().intValue(), 5);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    
    public void testWideStringConstant()
    {       
        try
        {
            String idlSource = "const wstring WSTRING_CONST = L\"1234567890\";";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "WSTRING_CONST");
            assertTrue(constant.getIdlType() instanceof MWstringDef);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }

    public void testBoundedWideStringConstant()
    {       
        try
        {
            String idlSource = "const wstring<5> BWSTRING_CONST = L\"1234567890\";";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "BWSTRING_CONST");
            assertTrue(constant.getIdlType() instanceof MWstringDef);
            MWstringDef s = (MWstringDef)constant.getIdlType();
            assertEquals(s.getBound().intValue(), 5);
            String constValue = (String)constant.getConstValue();
            assertEquals(constValue, "1234567890");          
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }
    
    
    public void testOctetConstant()
    {       
        try
        {
            String idlSource = "const octet OCTET_CONST = 0715;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "OCTET_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_OCTET);            
            Integer constValue = (Integer)constant.getConstValue();
            assertEquals(constValue.intValue(), 0715); // = 461 dec       
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 


    public void testBooleanConstant()
    {       
        try
        {                             
            String idlSource = "const boolean BOOLEAN_CONST = TRUE;";
            MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, idlSource);
            List modelElements = ccmModel.getContentss();
            System.out.println(modelElements);
                
            MConstantDef constant = (MConstantDef)modelElements.get(0);
            assertEquals(constant.getIdentifier(), "BOOLEAN_CONST");
            MPrimitiveKind kind = ((MPrimitiveDef)constant.getIdlType()).getKind();
            assertEquals(kind, MPrimitiveKind.PK_BOOLEAN);            
            Boolean constValue = (Boolean)constant.getConstValue();
            assertEquals(constValue.booleanValue(),true);        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail();
        }
    } 

}
