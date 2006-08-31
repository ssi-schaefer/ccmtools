package ccmtools.parser.idl.test;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.metamodel.BaseIDL.MWstringDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class StructTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public StructTest()
        throws FileNotFoundException
    {
        super("IDL Struct Test");
        
        uiDriver = new ConsoleDriver();
    }
        
    public static Test suite()
    {
        return new TestSuite(StructTest.class);
    }
    
     
    public void testEmptyStructError() 
        throws CcmtoolsException
    {
        try
        {
            parseSource("struct Person { };");
            fail();
        }
        catch (Exception e)
        {
            /* OK */
            System.out.println(e.getMessage());
        }
    }                


    public void testStructSignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                                   "short shortMember;" +
                                                   "long  longMember;" +
                                                   "long long llongMember;" +
                                                   "};");         
        assertEquals(struct.getIdentifier(), "s");                
        
        MFieldDef shortMember = struct.getMember(0);
        assertTrue(shortMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p0 = (MPrimitiveDef)shortMember.getIdlType();
        assertEquals(p0.getKind(), MPrimitiveKind.PK_SHORT);
        assertEquals(shortMember.getIdentifier(), "shortMember");
        
        MFieldDef longMember = struct.getMember(1);
        assertTrue(longMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p1 = (MPrimitiveDef)longMember.getIdlType();
        assertEquals(p1.getKind(), MPrimitiveKind.PK_LONG);
        assertEquals(longMember.getIdentifier(), "longMember");

        MFieldDef llongMember = struct.getMember(2);
        assertTrue(llongMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p2 = (MPrimitiveDef)llongMember.getIdlType();
        assertEquals(p2.getKind(), MPrimitiveKind.PK_LONGLONG);
        assertEquals(llongMember.getIdentifier(), "llongMember");
    } 


    public void testStructUnsignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                                   "unsigned short ushortMember;" +
                                                   "unsigned long ulongMember;" +
                                                   "unsigned long long ullongMember;" +
                                                   "};"); 
    
        assertEquals(struct.getIdentifier(), "s");      
        
        MFieldDef ushortMember = struct.getMember(0);
        assertTrue(ushortMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p0 = (MPrimitiveDef)ushortMember.getIdlType();
        assertEquals(p0.getKind(), MPrimitiveKind.PK_USHORT);
        assertEquals(ushortMember.getIdentifier(), "ushortMember");
        
        MFieldDef ulongMember = struct.getMember(1);
        assertTrue(ulongMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p1 = (MPrimitiveDef)ulongMember.getIdlType();
        assertEquals(p1.getKind(), MPrimitiveKind.PK_ULONG);
        assertEquals(ulongMember.getIdentifier(), "ulongMember");

        MFieldDef ullongMember = struct.getMember(2);
        assertTrue(ullongMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p2 = (MPrimitiveDef)ullongMember.getIdlType();
        assertEquals(p2.getKind(), MPrimitiveKind.PK_ULONGLONG);
        assertEquals(ullongMember.getIdentifier(), "ullongMember");        
    }    
    
    
    public void testStructFloatMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s {" + 
                                                   "float floatMember;" +
                                                   "double doubleMember;" +
                                                   "long double ldoubleMember;" +
                                                   "};");

        assertEquals(struct.getIdentifier(), "s");
 
        MFieldDef floatMember = struct.getMember(0);
        assertTrue(floatMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p0 = (MPrimitiveDef)floatMember.getIdlType();
        assertEquals(p0.getKind(), MPrimitiveKind.PK_FLOAT);
        assertEquals(floatMember.getIdentifier(), "floatMember");
        
        MFieldDef doubleMember = struct.getMember(1);
        assertTrue(doubleMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p1 = (MPrimitiveDef)doubleMember.getIdlType();
        assertEquals(p1.getKind(), MPrimitiveKind.PK_DOUBLE);
        assertEquals(doubleMember.getIdentifier(), "doubleMember");
        
        MFieldDef ldoubleMember = struct.getMember(2);
        assertTrue(ldoubleMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p2 = (MPrimitiveDef)ldoubleMember.getIdlType();
        assertEquals(p2.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
        assertEquals(ldoubleMember.getIdentifier(), "ldoubleMember");        
    }
       

    public void testStructCharMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { char charMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef charMember = struct.getMember(0);
        assertTrue(charMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef c = (MPrimitiveDef) charMember.getIdlType();
        assertEquals(c.getKind(), MPrimitiveKind.PK_CHAR);
        assertEquals(charMember.getIdentifier(), "charMember");
    }


    public void testStructWideCharMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wchar wcharMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef wcharMember = struct.getMember(0);
        assertTrue(wcharMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef w = (MPrimitiveDef)wcharMember.getIdlType();
        assertEquals(w.getKind(), MPrimitiveKind.PK_WCHAR);
        assertEquals(wcharMember.getIdentifier(), "wcharMember");        
    }
    
    
    public void testStructBooleanMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { boolean booleanMember; };");

        assertEquals(struct.getIdentifier(), "s");
        MFieldDef boolMember = struct.getMember(0);
        assertTrue(boolMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)boolMember.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_BOOLEAN);
        assertEquals(boolMember.getIdentifier(), "booleanMember");
    }
    

    public void testStructOctedMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { octet octetMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef octetMember = struct.getMember(0);
        assertTrue(octetMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)octetMember.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_OCTET);
        assertEquals(octetMember.getIdentifier(), "octetMember");
    }
    

    public void testStructAnyMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { any anyMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef anyMember = struct.getMember(0);
        assertTrue(anyMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)anyMember.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_ANY);
        assertEquals(anyMember.getIdentifier(), "anyMember");
    }
    

    public void testStructObjectMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { Object objectMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef objectMember = struct.getMember(0);
        assertTrue(objectMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)objectMember.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_OBJREF);
        assertEquals(objectMember.getIdentifier(), "objectMember");
    }
    
    
    public void testStructValueBaseMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { ValueBase valueBaseMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef valueBaseMember = struct.getMember(0);
        assertTrue(valueBaseMember.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)valueBaseMember.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_VALUEBASE);
        assertEquals(valueBaseMember.getIdentifier(), "valueBaseMember");
    }
    
    
    // TODO: sequence_type, 80
  

    public void testStructStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { string stringMember; string<7> bstringMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef stringMember = struct.getMember(0);
        assertTrue(stringMember.getIdlType() instanceof MStringDef);
        assertEquals(stringMember.getIdentifier(), "stringMember");

        MFieldDef bstringMember = struct.getMember(1);
        assertTrue(bstringMember.getIdlType() instanceof MStringDef);
        MStringDef s = (MStringDef) bstringMember.getIdlType();
        assertEquals(s.getBound().longValue(), 7);
        assertEquals(bstringMember.getIdentifier(), "bstringMember");
    }
   
    
    public void testStructWideStringMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wstring wstringMember; wstring<13> bwstringMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef wstringMember = struct.getMember(0);
        assertTrue(wstringMember.getIdlType() instanceof MWstringDef);
        assertEquals(wstringMember.getIdentifier(), "wstringMember");

        MFieldDef bwstringMember = struct.getMember(1);
        assertTrue(bwstringMember.getIdlType() instanceof MWstringDef);
        MWstringDef w = (MWstringDef)bwstringMember.getIdlType();
        assertEquals(w.getBound().longValue(), 13);
        assertEquals(bwstringMember.getIdentifier(), "bwstringMember");
    }
    

    public void testStructFixedMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { fixed<9,2> fixedMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef fixedMember = struct.getMember(0);
        assertTrue(fixedMember.getIdlType() instanceof MFixedDef);
        MFixedDef f = (MFixedDef)fixedMember.getIdlType();
        assertEquals(f.getDigits(), 9);
        assertEquals(f.getScale(), 2);
        assertEquals(fixedMember.getIdentifier(), "fixedMember");
    }
    
    
    // TODO: struct_type, 69 

    
    // TODO: union_type, 72
    
    
    // TODO: enum_type, 78
//    public void testStructEnumMembers() throws CcmtoolsException
//    {
//        MStructDef struct = parseSource(
//                "enum Color { red, green, blue }; " +
//                "struct s { Color enumMember; };");
//
//        assertEquals(struct.getIdentifier(), "s");
//
//    }
    
        
    /*
     * Utility Methods
     */
    
    private MStructDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MStructDef)modelElements.get(0);
    }
}
