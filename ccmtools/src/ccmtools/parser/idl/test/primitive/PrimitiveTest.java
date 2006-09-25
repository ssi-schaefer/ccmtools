package ccmtools.parser.idl.test.primitive;

import junit.framework.TestCase;
import ccmtools.metamodel.BaseIDL.MFixedDef;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MWstringDef;

public class PrimitiveTest extends TestCase
{
    public static void checkVoidType(MTyped typed)
    {
        checkVoidType(typed.getIdlType());
    }
    public static void checkVoidType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_VOID);
    }    
    
    
    public static void checkFloatType(MTyped typed)
    {
        checkFloatType(typed.getIdlType());
    }
    public static void checkFloatType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }    
    
    
    public static void checkDoubleType(MTyped typed)
    {
        checkDoubleType(typed.getIdlType());
    }
    public static void checkDoubleType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }

    
    public static void checkLongDoubleType(MTyped typed)
    {
        checkLongDoubleType(typed.getIdlType());
    }
    public static void checkLongDoubleType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }
    
    
    public static void checkShortType(MTyped typed)
    {
        checkShortType(typed.getIdlType());
    }
    public static void checkShortType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_SHORT);
    }

    
    public static void checkLongType(MTyped typed)
    {
        checkLongType(typed.getIdlType());
    }
    public static void checkLongType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
    }


    public static void checkLongLongType(MTyped typed)
    {
        checkLongLongType(typed.getIdlType());
    }
    public static void checkLongLongType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }
    

    public static void checkUnsignedShortType(MTyped typed)
    {
        checkUnsignedShortType(typed.getIdlType());
    }
    public static void checkUnsignedShortType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }
    

    public static void checkUnsignedLongType(MTyped typed)
    {
        checkUnsignedLongType(typed.getIdlType());
    }
    public static void checkUnsignedLongType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }

    
    public static void checkUnsignedLongLongType(MTyped typed)
    {
        checkUnsignedLongLongType(typed.getIdlType());
    }
    public static void checkUnsignedLongLongType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONGLONG);
    }
    
    
    public static void checkCharType(MTyped typed)
    {
        checkCharType(typed.getIdlType());
    }
    public static void checkCharType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_CHAR);
    }

    
    public static void checkWideCharType(MTyped typed)
    {
        checkWideCharType(typed.getIdlType());
    }    
    public static void checkWideCharType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_WCHAR);
    }
    
    
    public static void checkBooleanType(MTyped typed)
    {
        checkBooleanType(typed.getIdlType());
    }    
    public static void checkBooleanType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_BOOLEAN);
    }
    
    
    public static void checkOctetType(MTyped typed)
    {
        checkOctetType(typed.getIdlType());
    }
    public static void checkOctetType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }
    
    
    public static void checkAnyType(MTyped typed)
    {
        checkAnyType(typed.getIdlType());
    }
    public static void checkAnyType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }
    
    
    public static void checkObjectType(MTyped typed)
    {
        checkObjectType(typed.getIdlType());
    }
    public static void checkObjectType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }
    
    
    public static void checkValueBaseType(MTyped typed)
    {
        checkValueBaseType(typed.getIdlType());
    }
    public static void checkValueBaseType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)idlType;
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
    }
    
    
    public static void checkStringType(MTyped typed)
    {
        checkStringType(typed.getIdlType());
    }
    public static void checkStringType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MStringDef);
    }

    
    public static void checkBoundedStringType(MTyped typed, int bound)
    {
        checkBoundedStringType(typed.getIdlType(), bound);
    }
    public static void checkBoundedStringType(MIDLType idlType, int bound)
    {
        assertTrue(idlType instanceof MStringDef);
        MStringDef type = (MStringDef)idlType;
        assertEquals(type.getBound().longValue(), bound);
    }
    
    
    public static void checkWideStringType(MTyped typed)
    {
        checkWideStringType(typed.getIdlType());
    }
    public static void checkWideStringType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MWstringDef);
    }

    
    public static void checkBoundedWideStringType(MTyped typed, int bound)
    {
        checkBoundedWideStringType(typed.getIdlType(), bound);
    }
    public static void checkBoundedWideStringType(MIDLType idlType, int bound)
    {
        assertTrue(idlType instanceof MWstringDef);
        MWstringDef type = (MWstringDef)idlType;
        assertEquals(type.getBound().longValue(), bound);
    }

    
    public static void checkFixedType(MTyped typed)
    {
        checkFixedType(typed.getIdlType());
    }
    public static void checkFixedType(MIDLType idlType)
    {
        assertTrue(idlType instanceof MFixedDef);
        MFixedDef type = (MFixedDef)idlType;
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 3);
    }
}
