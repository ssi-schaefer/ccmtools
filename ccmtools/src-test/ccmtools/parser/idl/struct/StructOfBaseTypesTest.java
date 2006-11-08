package ccmtools.parser.idl.struct;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MFieldDef;
import ccmtools.metamodel.BaseIDL.MStructDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


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
                                        "   float floatMember;" +
                                        "   double doubleMember;" +
                                        "   long double ldoubleMember;" +
                                        "};");

        assertEquals(struct.getIdentifier(), "s");
        {
            MFieldDef field = struct.getMember(0);            
            PrimitiveTest.checkFloatType(field);
            assertEquals(field.getIdentifier(), "floatMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            PrimitiveTest.checkDoubleType(field);
            assertEquals(field.getIdentifier(), "doubleMember");
        }
        {
            MFieldDef field = struct.getMember(2);
            PrimitiveTest.checkLongDoubleType(field);
            assertEquals(field.getIdentifier(), "ldoubleMember");
        }
    }
    
    
    public void testStructOfSignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                        "   short shortMember;" +
                                        "   long  longMember;" +
                                        "   long long llongMember;" +
                                        "};");         
        
        assertEquals(struct.getIdentifier(), "s");                
        {
            MFieldDef field = struct.getMember(0);
            PrimitiveTest.checkShortType(field);
            assertEquals(field.getIdentifier(), "shortMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            PrimitiveTest.checkLongType(field);
            assertEquals(field.getIdentifier(), "longMember");
        }
        {
            MFieldDef field = struct.getMember(2);
            PrimitiveTest.checkLongLongType(field);
            assertEquals(field.getIdentifier(), "llongMember");
        }
    } 


    public void testStructOfUnsignedIntegerMembers() 
        throws CcmtoolsException
    {       
        MStructDef struct = parseSource("struct s {" +
                                        "   unsigned short ushortMember;" +
                                        "   unsigned long ulongMember;" +
                                        "   unsigned long long ullongMember;" +
                                        "};"); 
    
        assertEquals(struct.getIdentifier(), "s");      
        {
            MFieldDef field = struct.getMember(0);
            PrimitiveTest.checkUnsignedShortType(field);
            assertEquals(field.getIdentifier(), "ushortMember");
        }
        {
            MFieldDef field = struct.getMember(1);
            PrimitiveTest.checkUnsignedLongType(field);
            assertEquals(field.getIdentifier(), "ulongMember");
        }
        {
            MFieldDef field = struct.getMember(2);
            PrimitiveTest.checkUnsignedLongLongType(field);
            assertEquals(field.getIdentifier(), "ullongMember");
        }
    }    
    

    public void testStructOfCharMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { char charMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkCharType(field);
        assertEquals(field.getIdentifier(), "charMember");
    }


    public void testStructOfWideCharMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { wchar wcharMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkWideCharType(field);
        assertEquals(field.getIdentifier(), "wcharMember");        
    }
    
    
    public void testStructOfBooleanMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { boolean booleanMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkBooleanType(field);
        assertEquals(field.getIdentifier(), "booleanMember");
    }
    

    public void testStructOfOctedMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { octet octetMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkOctetType(field);
        assertEquals(field.getIdentifier(), "octetMember");
    }
    

    public void testStructOfAnyMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { any anyMember; };");

        assertEquals(struct.getIdentifier(), "s");
        
        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkAnyType(field);
        assertEquals(field.getIdentifier(), "anyMember");
    }
    

    public void testStructOfObjectMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { Object objectMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkObjectType(field);
        assertEquals(field.getIdentifier(), "objectMember");
    }
    
    
    public void testStructOfValueBaseMembers() 
        throws CcmtoolsException
    {
        MStructDef struct = parseSource("struct s { ValueBase valueBaseMember; };");

        assertEquals(struct.getIdentifier(), "s");

        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkValueBaseType(field);
        assertEquals(field.getIdentifier(), "valueBaseMember");
    }
    
    
    public void testStructOfNativeMembers() throws CcmtoolsException
    {
        MStructDef struct = parseSource(
                "native AID;" + 
                "struct StructNative { AID aid; };");

        assertEquals(struct.getIdentifier(), "StructNative");

        MFieldDef field = struct.getMember(0);
        PrimitiveTest.checkNativeType(field, "AID");
        assertEquals(field.getIdentifier(), "aid");
    }

}
