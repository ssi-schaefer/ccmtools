package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class TypedefOfBaseTypesTest extends TypedefTest
{
    public TypedefOfBaseTypesTest()
        throws FileNotFoundException
    {
        super(TypedefOfBaseTypesTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(TypedefOfBaseTypesTest.class);
    }
    
    
    public void testTypedefOfFloat() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef float FloatType;");

        assertEquals(alias.getIdentifier(), "FloatType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }             
    
    public void testTypedefOfDouble() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef double DoubleType;");

        assertEquals(alias.getIdentifier(), "DoubleType");

        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }             
    
    public void testTypedefOfLongDouble() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long double LDoubleType;");

        assertEquals(alias.getIdentifier(), "LDoubleType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }             
    
         
    public void testTypedefOfShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef short ShortType;");
        
        assertEquals(alias.getIdentifier(), "ShortType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
    }                
    
    public void testTypedefOfLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long LongType;");

        assertEquals(alias.getIdentifier(), "LongType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
    }
        
    public void testTypedefOfLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long long LLongType;");

        assertEquals(alias.getIdentifier(), "LLongType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }
    
    
    public void testTypedefOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned short UShortType;");

        assertEquals(alias.getIdentifier(), "UShortType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }                

    public void testTypedefOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long ULongType;");

        assertEquals(alias.getIdentifier(), "ULongType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }                
    
    public void testTypedefOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long long ULLongType;");

        assertEquals(alias.getIdentifier(), "ULLongType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONGLONG);
    }                    
    
    
    public void testTypedefOfChar() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef char CharType;");

        assertEquals(alias.getIdentifier(), "CharType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_CHAR);
    }             
        
    public void testTypedefOfWChar() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wchar WCharType;");

        assertEquals(alias.getIdentifier(), "WCharType");
     
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_WCHAR);
    }             

            
    public void testTypedefOfBoolean() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef boolean BooleanType;");

        assertEquals(alias.getIdentifier(), "BooleanType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_BOOLEAN);
    }             
    
    
    public void testTypedefOfOctet() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef octet OctetType;");

        assertEquals(alias.getIdentifier(), "OctetType");
    
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }             
    
    
    public void testTypedefOfAny() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef any AnyType;");

        assertEquals(alias.getIdentifier(), "AnyType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }             
    
    
    public void testTypedefOfObject() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef Object ObjectType;");

        assertEquals(alias.getIdentifier(), "ObjectType");
    
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }

    
    public void testTypedefOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef ValueBase ValueBaseType;");

        assertEquals(alias.getIdentifier(), "ValueBaseType");
        
        assertTrue(alias.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef) alias.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
    }             
}
