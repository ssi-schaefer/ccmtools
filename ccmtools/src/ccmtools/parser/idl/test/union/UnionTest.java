package ccmtools.parser.idl.test.union;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MEnumDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.metamodel.BaseIDL.MPrimitiveKind;
import ccmtools.metamodel.BaseIDL.MStringDef;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class UnionTest extends TestCase
{
    private UserInterfaceDriver uiDriver;
    
    public UnionTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        
    
    public static Test suite()
    {
        return new TestSuite(UnionTest.class);
    }
    
 
    public void testOptionalValue() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union AgeOpt switch(boolean) { " +
                "   case TRUE: unsigned short age; " +
                "};");

        assertEquals(union.getIdentifier(), "AgeOpt");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_BOOLEAN);
     
        MUnionFieldDef member = union.getUnionMember(0);
        assertEquals(member.getIdentifier(), "age");
        assertTrue(member.getLabel() instanceof Boolean);
        assertTrue(member.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
    }
    
        
    public void testLongDiscriminator() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union UnionLongSwitch switch(long) { " +
                "   case 17:   long a;" +
                "   case 3:  string b;" +
                " };");
    
        assertEquals(union.getIdentifier(), "UnionLongSwitch");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_LONG);
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertEquals(member.getIdentifier(), "a");
            assertTrue(member.getLabel() instanceof Integer);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertEquals(member.getIdentifier(), "b");
            assertTrue(member.getLabel() instanceof Integer);
            assertTrue(member.getIdlType() instanceof MStringDef);
        }
    }

    
    
    public void testEnumDiscriminator() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "enum Color { red, green, blue };" +
                "union ColorCount switch(Color) { " +
                "   case red:   long x;" +
                "   case green: float y;" +
                "   case blue:  string z;" +
                " };");

        assertEquals(union.getIdentifier(), "ColorCount");
        assertTrue(union.getDiscriminatorType() instanceof MEnumDef);
        MEnumDef discriminator = (MEnumDef)union.getDiscriminatorType();
        assertEquals(discriminator.getIdentifier(), "Color");
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertEquals(member.getIdentifier(), "x");
            //!!!!!!!!! Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertEquals(member.getIdentifier(), "y");
            //!!!!!!!!! Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
        }
        {
            MUnionFieldDef member = union.getUnionMember(2);
            assertEquals(member.getIdentifier(), "z");
            //!!!!!!!!! Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MStringDef);
        }
        
    }
    
    
    // TODO: union XY switch(char) { case 'a': case 'b': long ab; case 'c': string c};
    
    
    
    /*
     * Utility Methods
     */
    
    public MUnionDef parseSource(String sourceLine) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MUnionDef)modelElements.get(0);
    }
}
