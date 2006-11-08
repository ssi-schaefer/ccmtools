package ccmtools.parser.idl.literal;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MConstantDef;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;


public class LiteralOfTemplateTypesTest extends LiteralTest
{
    public LiteralOfTemplateTypesTest()
        throws FileNotFoundException
    {
        super(LiteralOfTemplateTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(LiteralOfTemplateTypesTest.class);
    }
    
     
    public void testStringLiteral() throws CcmtoolsException
    {

        MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testStringConcatLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const string STRING_LITERAL = \"Hello\" \" World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testBoundedStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const string<10> STRING_LITERAL = \"0123456789\";");
        assertTrue(constant.getIdlType() instanceof MStringDef);
        MStringDef s = (MStringDef) constant.getIdlType();
        assertEquals(s.getBound().longValue(), 10);
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "0123456789");
    }

    
    public void testWideStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testWideStringConcatLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring STRING_LITERAL = L\"Hello\" L\" World\";");
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "Hello World");
    }

    public void testBoundedWideStringLiteral() throws CcmtoolsException
    {
        MConstantDef constant = parseSource("const wstring<10> STRING_LITERAL = L\"0123456789\";");
        assertTrue(constant.getIdlType() instanceof MWstringDef);
        MWstringDef s = (MWstringDef) constant.getIdlType();
        assertEquals(s.getBound().longValue(), 10);
        String constValue = (String) constant.getConstValue();
        assertEquals(constValue, "0123456789");
    }

    
    // TODO: fixed_pt_literal

}
