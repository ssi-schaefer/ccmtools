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
import ccmtools.metamodel.BaseIDL.MWstringDef;
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
                "union UnionOptional switch(boolean) { " +
                "   case TRUE: unsigned short a; " +
                "};");

        assertEquals(union.getIdentifier(), "UnionOptional");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_BOOLEAN);
     
        MUnionFieldDef member = union.getUnionMember(0);
        assertTrue(member.getLabel() instanceof Boolean);
        Boolean label = (Boolean)member.getLabel();
        assertEquals(label.booleanValue(), true);
        assertTrue(member.getIdlType() instanceof MPrimitiveDef);
        MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
        assertEquals(type.getKind(), MPrimitiveKind.PK_USHORT);
        assertEquals(member.getIdentifier(), "a");
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
            assertTrue(member.getLabel() instanceof Integer);
            Integer label = (Integer)member.getLabel();
            assertEquals(label.intValue(), 17);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Integer);
            Integer label = (Integer)member.getLabel();
            assertEquals(label.intValue(), 3);
            assertTrue(member.getIdlType() instanceof MStringDef);
            assertEquals(member.getIdentifier(), "b");
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
            //TODO Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(member.getIdentifier(), "x");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            //TODO Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_FLOAT);
            assertEquals(member.getIdentifier(), "y");
        }
        {
            MUnionFieldDef member = union.getUnionMember(2);
            //TODO Enum Constants!!!!!
            //assertTrue(member.getLabel() instanceof ???);
            assertTrue(member.getIdlType() instanceof MStringDef);
            assertEquals(member.getIdentifier(), "z");
        }
        
    }
    
    
    public void testCharDiscriminator() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union UnionCharSwitch switch(char) { " +
                "   case 'A':   long a;" +
                "   case 'B':  string b;" +
                " };");
    
        assertEquals(union.getIdentifier(), "UnionCharSwitch");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_CHAR);
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'A');
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'B');
            assertTrue(member.getIdlType() instanceof MStringDef);
            assertEquals(member.getIdentifier(), "b");
        }
    }
    
    
    public void testMultipleCases() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union MultipleCases switch(char) { " +
                "   case 'A':  " +
                "   case 'B':  " +
                "   case 'C':  string c;" +
                " };");
    
        assertEquals(union.getIdentifier(), "MultipleCases");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_CHAR);
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof List);
            List labels = (List)member.getLabel();
            assertEquals(labels.get(0), 'A');
            assertEquals(labels.get(1), 'B');
            assertEquals(labels.get(2), 'C');
            assertTrue(member.getIdlType() instanceof MStringDef);
            assertEquals(member.getIdentifier(), "c");
        }
    }

    
    public void testDefaultCase() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union UnionDefaultCase switch(char) { " +
                "   case 'A':   long a;" +
                "   case 'B':   string b;" +
                "   default :   wstring c;" +
                " };");
    
        assertEquals(union.getIdentifier(), "UnionDefaultCase");
        assertTrue(union.getDiscriminatorType() instanceof MPrimitiveDef);
        MPrimitiveDef discriminator = (MPrimitiveDef)union.getDiscriminatorType();
        assertEquals(discriminator.getKind(), MPrimitiveKind.PK_CHAR);
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'A');
            assertTrue(member.getIdlType() instanceof MPrimitiveDef);
            MPrimitiveDef type = (MPrimitiveDef)member.getIdlType();
            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'B');
            assertTrue(member.getIdlType() instanceof MStringDef);
            assertEquals(member.getIdentifier(), "b");
        }
        {
            MUnionFieldDef member = union.getUnionMember(2);
            assertTrue(member.getLabel() instanceof String);
            String label = (String)member.getLabel();
            assertEquals(label, "default");
            assertTrue(member.getIdlType() instanceof MWstringDef);
            assertEquals(member.getIdentifier(), "c");
        }
    }
    
    
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
