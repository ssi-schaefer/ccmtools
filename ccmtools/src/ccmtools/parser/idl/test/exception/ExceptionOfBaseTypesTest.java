package ccmtools.parser.idl.test.exception;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MExceptionDef;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


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
            PrimitiveTest.checkFloatType(field);
            assertEquals(field.getIdentifier(), "floatMember");
        }
        {
            MFieldDef field = getMember(e,1);
            PrimitiveTest.checkDoubleType(field);
            assertEquals(field.getIdentifier(), "doubleMember");
        }
        {
            MFieldDef field = getMember(e,2);
            PrimitiveTest.checkLongDoubleType(field);
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
            PrimitiveTest.checkShortType(field);
            assertEquals(field.getIdentifier(), "shortMember");
        }
        {
            MFieldDef field = getMember(e,1);
            PrimitiveTest.checkLongType(field);    
            assertEquals(field.getIdentifier(), "longMember");
        }
        {
            MFieldDef field = getMember(e,2);
            PrimitiveTest.checkLongLongType(field);
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
            PrimitiveTest.checkUnsignedShortType(field);
            assertEquals(field.getIdentifier(), "ushortMember");
        }
        {
            MFieldDef field = getMember(e,1);
            PrimitiveTest.checkUnsignedLongType(field);
            assertEquals(field.getIdentifier(), "ulongMember");
        }
        {
            MFieldDef field = getMember(e,2);
            PrimitiveTest.checkUnsignedLongLongType(field);
            assertEquals(field.getIdentifier(), "ullongMember");
        }
    }    
    

    public void testExceptionOfCharMembers() throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { char charMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkCharType(field);
        assertEquals(field.getIdentifier(), "charMember");
    }


    public void testExceptionOfWideCharMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { wchar wcharMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkWideCharType(field);
        assertEquals(field.getIdentifier(), "wcharMember");        
    }
    
    
    public void testExceptionOfBooleanMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { boolean booleanMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkBooleanType(field);
        assertEquals(field.getIdentifier(), "booleanMember");
    }
    

    public void testExceptionOfOctedMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { octet octetMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkOctetType(field);
        assertEquals(field.getIdentifier(), "octetMember");
    }
    

    public void testExceptionOfAnyMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { any anyMember; };");

        assertEquals(e.getIdentifier(), "Ex");
        
        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkAnyType(field);
        assertEquals(field.getIdentifier(), "anyMember");
    }
    

    public void testExceptionOfObjectMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { Object objectMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkObjectType(field);
        assertEquals(field.getIdentifier(), "objectMember");
    }
    
    
    public void testExceptionOfValueBaseMembers() 
        throws CcmtoolsException
    {
        MExceptionDef e = parseSource("exception Ex { ValueBase valueBaseMember; };");

        assertEquals(e.getIdentifier(), "Ex");

        MFieldDef field = getMember(e,0);
        PrimitiveTest.checkValueBaseType(field);
        assertEquals(field.getIdentifier(), "valueBaseMember");
    }
}
