package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;


public class ExceptionOfBaseTypesTest extends ExceptionTest
{
    public ExceptionOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(ExceptionOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ExceptionOfBaseTypesTest.class);
    }
    

    public void testExceptionOfFloatMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex {" + 
                                                   "float floatMember;" +
                                                   "double doubleMember;" +
                                                   "long double ldoubleMember;" +
                                                   "};");

        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef)field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_FLOAT);
            assertEquals(field.getIdentifier(), "floatMember");
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef) field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_DOUBLE);
            assertEquals(field.getIdentifier(), "doubleMember");
        }
        {
            MFieldDef field = getMember(e,2);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef) field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
            assertEquals(field.getIdentifier(), "ldoubleMember");
        }
    }
    
    
    public void testExceptionOfSignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MExceptionDef e = parseSource("exception Ex {" +
                                                   "short shortMember;" +
                                                   "long  longMember;" +
                                                   "long long llongMember;" +
                                                   "};");         
        assertEquals(e.getIdentifier(), "Ex");                        
        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef) field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_SHORT);
            assertEquals(field.getIdentifier(), "shortMember");
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef) field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(field.getIdentifier(), "longMember");
        }
        {
            MFieldDef field = getMember(e,2);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p = (MPrimitiveDef) field.getIdlType();
            assertEquals(p.getKind(), MPrimitiveKind.PK_LONGLONG);
            assertEquals(field.getIdentifier(), "llongMember");
        }
    } 


    public void testExceptionOfUnsignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MExceptionDef e = parseSource("exception Ex {" +
                                                   "unsigned short ushortMember;" +
                                                   "unsigned long ulongMember;" +
                                                   "unsigned long long ullongMember;" +
                                                   "};"); 
    
        assertEquals(e.getIdentifier(), "Ex");
        {
            MFieldDef field = getMember(e,0);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p0 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p0.getKind(), MPrimitiveKind.PK_USHORT);
            assertEquals(field.getIdentifier(), "ushortMember");
        }
        {
            MFieldDef field = getMember(e,1);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p1 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p1.getKind(), MPrimitiveKind.PK_ULONG);
            assertEquals(field.getIdentifier(), "ulongMember");
        }
        {
            MFieldDef field = getMember(e,2);
            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef p2 = (MPrimitiveDef) field.getIdlType();
            assertEquals(p2.getKind(), MPrimitiveKind.PK_ULONGLONG);
            assertEquals(field.getIdentifier(), "ullongMember");
        }
    }    
    

    public void testExceptionOfCharMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { char charMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef c = (MPrimitiveDef) field.getIdlType();
        assertEquals(c.getKind(), MPrimitiveKind.PK_CHAR);
        assertEquals(field.getIdentifier(), "charMember");
    }


    public void testExceptionOfWideCharMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { wchar wcharMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef w = (MPrimitiveDef)field.getIdlType();
        assertEquals(w.getKind(), MPrimitiveKind.PK_WCHAR);
        assertEquals(field.getIdentifier(), "wcharMember");        
    }
    
    
    public void testExceptionOfBooleanMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { boolean booleanMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_BOOLEAN);
        assertEquals(field.getIdentifier(), "booleanMember");
    }
    

    public void testExceptionOfOctedMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { octet octetMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_OCTET);
        assertEquals(field.getIdentifier(), "octetMember");
    }
    

    public void testExceptionOfAnyMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { any anyMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)field.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_ANY);
        assertEquals(field.getIdentifier(), "anyMember");
    }
    

    public void testExceptionOfObjectMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { Object objectMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef a = (MPrimitiveDef)field.getIdlType();
        assertEquals(a.getKind(), MPrimitiveKind.PK_OBJREF);
        assertEquals(field.getIdentifier(), "objectMember");
    }
    
    
    public void testExceptionOfValueBaseMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { ValueBase valueBaseMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        assertTrue(field.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef b = (MPrimitiveDef)field.getIdlType();
        assertEquals(b.getKind(), MPrimitiveKind.PK_VALUEBASE);
        assertEquals(field.getIdentifier(), "valueBaseMember");
    }
}
