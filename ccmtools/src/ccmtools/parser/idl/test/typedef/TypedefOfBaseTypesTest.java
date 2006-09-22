package ccmtools.parser.idl.test.typedef;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAliasDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;


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
        PrimitiveTest.checkFloatType((MTyped)alias);   
    }             
    
    public void testTypedefOfDouble() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef double DoubleType;");

        assertEquals(alias.getIdentifier(), "DoubleType");
        PrimitiveTest.checkDoubleType((MTyped)alias);
    }             
    
    public void testTypedefOfLongDouble() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long double LDoubleType;");

        assertEquals(alias.getIdentifier(), "LDoubleType");
        PrimitiveTest.checkLongDoubleType((MTyped)alias);        
    }             
    
         
    public void testTypedefOfShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef short ShortType;");
        
        assertEquals(alias.getIdentifier(), "ShortType");
        PrimitiveTest.checkShortType((MTyped)alias);        
    }                
    
    public void testTypedefOfLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long LongType;");

        assertEquals(alias.getIdentifier(), "LongType");
        PrimitiveTest.checkLongType((MTyped)alias);        
    }
        
    public void testTypedefOfLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef long long LLongType;");

        assertEquals(alias.getIdentifier(), "LLongType");
        PrimitiveTest.checkLongLongType((MTyped)alias);        
    }
    
    
    public void testTypedefOfUnsignedShort() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned short UShortType;");

        assertEquals(alias.getIdentifier(), "UShortType");
        PrimitiveTest.checkUnsignedShortType((MTyped)alias);        
    }                

    public void testTypedefOfUnsignedLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long ULongType;");

        assertEquals(alias.getIdentifier(), "ULongType");
        PrimitiveTest.checkUnsignedLongType((MTyped)alias);        
    }                
    
    public void testTypedefOfUnsignedLongLong() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef unsigned long long ULLongType;");

        assertEquals(alias.getIdentifier(), "ULLongType");
        PrimitiveTest.checkUnsignedLongLongType((MTyped)alias);        
    }                    
    
    
    public void testTypedefOfChar() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef char CharType;");

        assertEquals(alias.getIdentifier(), "CharType");
        PrimitiveTest.checkCharType((MTyped)alias);        
    }             
        
    public void testTypedefOfWChar() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef wchar WCharType;");

        assertEquals(alias.getIdentifier(), "WCharType");
        PrimitiveTest.checkWideCharType((MTyped)alias);     
    }             

            
    public void testTypedefOfBoolean() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef boolean BooleanType;");

        assertEquals(alias.getIdentifier(), "BooleanType");
        PrimitiveTest.checkBooleanType((MTyped)alias);        
    }             
    
    
    public void testTypedefOfOctet() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef octet OctetType;");

        assertEquals(alias.getIdentifier(), "OctetType");
        PrimitiveTest.checkOctetType((MTyped)alias);    
    }             
    
    
    public void testTypedefOfAny() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef any AnyType;");

        assertEquals(alias.getIdentifier(), "AnyType");
        PrimitiveTest.checkAnyType((MTyped)alias);        
    }             
    
    
    public void testTypedefOfObject() 
        throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef Object ObjectType;");

        assertEquals(alias.getIdentifier(), "ObjectType");
        PrimitiveTest.checkObjectType((MTyped)alias);    
    }

    
    public void testTypedefOfValueBase() throws CcmtoolsException
    {
        MAliasDef alias = parseSource("typedef ValueBase ValueBaseType;");

        assertEquals(alias.getIdentifier(), "ValueBaseType");
        PrimitiveTest.checkValueBaseType((MTyped)alias);        
    }             
}
