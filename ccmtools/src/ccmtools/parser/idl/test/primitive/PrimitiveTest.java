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
    public static void checkFloatType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
    }
    
    
    public static void checkDoubleType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_DOUBLE);
    }

    
    public static void checkLongDoubleType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGDOUBLE);
    }
    
    
    public static void checkShortType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
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
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_LONGLONG);
    }
    
    public static void checkUnsignedShortType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }
    
    public static void checkUnsignedLongType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ULONG);
    }
    
    public static void checkUnsignedLongLongType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
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
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
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
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OCTET);
    }
    
    public static void checkAnyType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_ANY);
    }
    
    public static void checkObjectType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_OBJREF);
    }
    
    public static void checkValueBaseType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)typed.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_VALUEBASE);
    }
    
    public static void checkStringType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MStringDef);
    }
    
    public static void checkBoundedStringType(MTyped typed, int bound)
    {
        assertTrue(typed.getIdlType() instanceof MStringDef);
        MStringDef type = (MStringDef)typed.getIdlType();
        assertEquals(type.getBound().longValue(), bound);
    }
    
    public static void checkWideStringType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MWstringDef);
    }

    public static void checkBoundedWideStringType(MTyped typed, int bound)
    {
        assertTrue(typed.getIdlType() instanceof MWstringDef);
        MWstringDef type = (MWstringDef)typed.getIdlType();
        assertEquals(type.getBound().longValue(), bound);
    }
    
    public static void checkFixedType(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MFixedDef);
        MFixedDef type = (MFixedDef)typed.getIdlType();
        assertEquals(type.getDigits(), 9);
        assertEquals(type.getScale(), 3);
    }
    
    
}
