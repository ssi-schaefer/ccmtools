package ccmtools.parser.idl.union;

import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class UnionDiscriminatorTest extends UnionTest
{
    public UnionDiscriminatorTest(String title)
        throws FileNotFoundException
    {
        super(title);
    }
        
    
    public static Test suite()
    {
        return new TestSuite(UnionDiscriminatorTest.class);
    }
    
 
    public void testOptionalValue() throws CcmtoolsException
    {
        MUnionDef union = parseSource(getUnionOptionalSource());
        checkUnionOptional(union);
    }
    
    
    public void testLongDiscriminator() throws CcmtoolsException
    {
        MUnionDef union = parseSource(
                "union UnionLongSwitch switch(long) { " +
                "   case 17:   long a;" +
                "   case 3:  string b;" +
                " };");
    
        assertEquals(union.getIdentifier(), "UnionLongSwitch");
        PrimitiveTest.checkLongType(union.getDiscriminatorType());
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof Integer);
            Integer label = (Integer)member.getLabel();
            assertEquals(label.intValue(), 17);
            
            PrimitiveTest.checkLongType(member);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Integer);
            Integer label = (Integer)member.getLabel();
            assertEquals(label.intValue(), 3);
            
            PrimitiveTest.checkStringType(member);
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
        EnumTest.checkEnumColor(union.getDiscriminatorType());
        {
            MUnionFieldDef member = union.getUnionMember(0);
            ScopedName label = (ScopedName)member.getLabel();
            assertEquals(label, new ScopedName("red"));
            PrimitiveTest.checkLongType(member);
            assertEquals(member.getIdentifier(), "x");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof ScopedName);
            ScopedName label = (ScopedName)member.getLabel();
            assertEquals(label, new ScopedName("green"));
            PrimitiveTest.checkFloatType(member);
            assertEquals(member.getIdentifier(), "y");
        }
        {
            MUnionFieldDef member = union.getUnionMember(2);
            ScopedName label = (ScopedName)member.getLabel();
            assertEquals(label, new ScopedName("blue"));
            PrimitiveTest.checkStringType(member);
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
        PrimitiveTest.checkCharType(union.getDiscriminatorType());
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'A');
            PrimitiveTest.checkLongType(member);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'B');
            PrimitiveTest.checkStringType(member);
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
        PrimitiveTest.checkCharType(union.getDiscriminatorType());
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof List);
            List labels = (List)member.getLabel();
            assertEquals(labels.get(0), 'A');
            assertEquals(labels.get(1), 'B');
            assertEquals(labels.get(2), 'C');
            PrimitiveTest.checkStringType(member);
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
        PrimitiveTest.checkCharType(union.getDiscriminatorType());
        {
            MUnionFieldDef member = union.getUnionMember(0);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'A');
            PrimitiveTest.checkLongType(member);
            assertEquals(member.getIdentifier(), "a");
        }
        {
            MUnionFieldDef member = union.getUnionMember(1);
            assertTrue(member.getLabel() instanceof Character);
            Character c = (Character)member.getLabel();
            assertEquals(c.charValue(), 'B');
            PrimitiveTest.checkStringType(member);
            assertEquals(member.getIdentifier(), "b");
        }
        {
            MUnionFieldDef member = union.getUnionMember(2);
            assertTrue(member.getLabel() instanceof String);
            String label = (String)member.getLabel();
            assertEquals(label, "default");
            PrimitiveTest.checkWideStringType(member);
            assertEquals(member.getIdentifier(), "c");
        }
    }    
}
