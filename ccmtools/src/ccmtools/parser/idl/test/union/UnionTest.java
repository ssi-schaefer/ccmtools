package ccmtools.parser.idl.test.union;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MUnionDef;
import ccmtools.metamodel.BaseIDL.MUnionFieldDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.parser.idl.ScopedName;
import ccmtools.parser.idl.test.enumeration.EnumTest;
import ccmtools.parser.idl.test.primitive.PrimitiveTest;
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
        MUnionDef union = parseSource(getUnionOptionalSource());
        checkUnionOptional(union);
    }
    
    public static String getUnionOptionalSource()
    {
        return  "union UnionOptional switch(boolean) { " +
                "   case TRUE: unsigned short a; " +
                "};";
    }
    
    public static void checkUnionOptional(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MUnionDef);
        MUnionDef union = (MUnionDef)typed.getIdlType();
        checkUnionOptional(union);
    }
    
    public static void checkUnionOptional(MIDLType idlType)
    {
        assertTrue(idlType instanceof MUnionDef);
        MUnionDef union = (MUnionDef)idlType;
        checkUnionOptional(union);
    }

    public static void checkUnionOptional(MUnionDef union)
    {
        assertEquals(union.getIdentifier(), "UnionOptional");
        PrimitiveTest.checkBooleanType(union.getDiscriminatorType());
     
        MUnionFieldDef member = union.getUnionMember(0);
        assertTrue(member.getLabel() instanceof Boolean);
        Boolean label = (Boolean)member.getLabel();
        assertEquals(label.booleanValue(), true);

        PrimitiveTest.checkUnsignedShortType(member);
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

    public MUnionDef parseSource(String sourceLine, String id) throws CcmtoolsException
    {
        System.out.println("[" + sourceLine + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceLine);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MUnionDef)element;
            }
        }        
        return null;
    }

}
