package ccmtools.parser.idl.test.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStructDef;


public class StructOfBaseTypesTest extends StructTest
{
    public StructOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(StructOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(StructOfBaseTypesTest.class);
    }
    
   
    public void testStructOfFloatMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s {" + 
                                                   "float floatMember;" +
                                                   "double doubleMember;" +
                                                   "long double ldoubleMember;" +
                                                   "};");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p0 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p0.getKind(), MPrimitiveKind.PK_FLOAT);
            assertEquals(field.getIdentifier(), "floatMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p1 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p1.getKind(), MPrimitiveKind.PK_DOUBLE);
            assertEquals(field.getIdentifier(), "doubleMember");
        }
        {
            MFieldDef field = struct.getMember(2);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p2 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p2.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
            assertEquals(field.getIdentifier(), "ldoubleMember");
        }
    }
    
    
    public void testStructOfSignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                                   "short shortMember;" +
                                                   "long  longMember;" +
                                                   "long long llongMember;" +
                                                   "};");         
        assertEquals(struct.getIdentifier(), "s");                
        {
            MFieldDef field = struct.getMember(0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p0 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p0.getKind(), MPrimitiveKind.PK_SHORT);
            assertEquals(field.getIdentifier(), "shortMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p1 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p1.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(field.getIdentifier(), "longMember");
        }
        {
            MFieldDef field = struct.getMember(2);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p2 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p2.getKind(), MPrimitiveKind.PK_LONGLONG);
            assertEquals(field.getIdentifier(), "llongMember");
        }
    } 


    public void testStructOfUnsignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                                   "unsigned short ushortMember;" +
                                                   "unsigned long ulongMember;" +
                                                   "unsigned long long ullongMember;" +
                                                   "};"); 
    
        assertEquals(struct.getIdentifier(), "s");      
        {
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p0 = (MPrimitiveDef)field.getIdlType();
        assertEquals(p0.getKind(), MPrimitiveKind.PK_USHORT);
        assertEquals(field.getIdentifier(), "ushortMember");
        }
        {
        MFieldDef field = struct.getMember(1);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p1 = (MPrimitiveDef)field.getIdlType();
        assertEquals(p1.getKind(), MPrimitiveKind.PK_ULONG);
        assertEquals(field.getIdentifier(), "ulongMember");
        }
        {
        MFieldDef field = struct.getMember(2);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef p2 = (MPrimitiveDef)field.getIdlType();
        assertEquals(p2.getKind(), MPrimitiveKind.PK_ULONGLONG);
        assertEquals(field.getIdentifier(), "ullongMember");
        }
    }    
    

    public void testStructOfCharMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { char charMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef c = (MPrimitiveDef) field.getIdlType();
        assertEquals(c.getKind(), MPrimitiveKind.PK_CHAR);
        assertEquals(field.getIdentifier(), "charMember");
    }


    public void testStructOfWideCharMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wchar wcharMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef w = (MPrimitiveDef)field.getIdlType();
        assertEquals(w.getKind(), MPrimitiveKind.PK_WCHAR);
        assertEquals(field.getIdentifier(), "wcharMember");        
    }
    
    
    public void testStructOfBooleanMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { boolean booleanMember; };");

        assertEquals(struct.getIdentifier(), "s");
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_BOOLEAN);
        assertEquals(field.getIdentifier(), "booleanMember");
    }
    

    public void testStructOfOctedMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { octet octetMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_OCTET);
        assertEquals(field.getIdentifier(), "octetMember");
    }
    

    public void testStructOfAnyMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { any anyMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)field.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_ANY);
        assertEquals(field.getIdentifier(), "anyMember");
    }
    

    public void testStructOfObjectMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { Object objectMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)field.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_OBJREF);
        assertEquals(field.getIdentifier(), "objectMember");
    }
    
    
    public void testStructOfValueBaseMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { ValueBase valueBaseMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_VALUEBASE);
        assertEquals(field.getIdentifier(), "valueBaseMember");
    }
}
