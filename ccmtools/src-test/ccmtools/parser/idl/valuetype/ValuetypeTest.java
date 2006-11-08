package ccmtools.parser.idl.valuetype;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MIDLType;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.metamodel.BaseIDL.MTyped;
import ccmtools.metamodel.BaseIDL.MValueBoxDef;
import ccmtools.metamodel.BaseIDL.MValueDef;
import ccmtools.metamodel.BaseIDL.MValueMemberDef;
import ccmtools.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class ValuetypeTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public ValuetypeTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }

    
    
    public static String getValuetypePersonSource()
    {
        return "valuetype Person { public long id; public string name; };";
    }

    public static void checkValuetypePerson(MTyped typed)
    {
        assertTrue(typed.getIdlType() instanceof MValueDef);
        MValueDef type = (MValueDef)typed.getIdlType();
        checkValuetypePerson(type);
    }
    
    public static void checkValuePerson(MIDLType idlType)
    {
        assertTrue(idlType instanceof MValueDef);
        MValueDef type = (MValueDef)idlType;
        checkValuetypePerson(type);
    }
    
    public static void checkValuetypePerson(MValueDef value)
    {
        assertEquals(value.getIdentifier(), "Person");
//        {
//            assertTrue(value.getMember(0) instanceof MFieldDef);
//            MFieldDef field = (MFieldDef)value.getMember(0);
//            assertTrue(field.getIdlType() instanceof MPrimitiveDef);
//            MPrimitiveDef type = (MPrimitiveDef)field.getIdlType();
//            assertEquals(type.getKind(), MPrimitiveKind.PK_LONG);
//            assertEquals(field.getIdentifier(), "id");
//        }
//        {
//            assertTrue(value.getMember(1) instanceof MFieldDef);
//            MFieldDef field = (MFieldDef)value.getMember(1);
//            assertTrue(field.getIdlType() instanceof MStringDef);
//            assertEquals(field.getIdentifier(), "name");
//        }
    }
    
    
    /*
     * Utility Methods
     */

    public MValueBoxDef parseBoxSource(String sourceCode, String id) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MValueBoxDef)element;
            }
        }
        return null;
    }
    
    public MValueBoxDef parseBoxSource(String sourceCode) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MValueBoxDef) modelElements.get(0);
    }

    
    public MValueDef parseSource(String sourceCode) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MValueDef)modelElements.get(0);
    }
    
    
    public MValueDef parseSource(String sourceCode, String id) throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println("#" + modelElements.size() + ":  " + modelElements);
        for(Iterator i = modelElements.iterator(); i.hasNext(); )
        {
            MContained element = (MContained)i.next();
            if(element.getIdentifier().equals(id))
            {
                return (MValueDef)element;
            }
        }
        return null;
    }
    
    
    public MValueDef getBaseValuetype(MValueDef value, String id)
    {
        assertTrue(value.getBase() instanceof MValueDef);
        MValueDef base = (MValueDef)value.getBase();
        assertEquals(base.getIdentifier(), id);
        return base;
    }

    
    public MInterfaceDef getSupportedInterface(MValueDef value, int index, String id)
    {
        assertTrue(value.getInterfaceDefs().get(index) instanceof MInterfaceDef);
        MInterfaceDef iface = (MInterfaceDef)value.getInterfaceDefs().get(index);
        assertEquals(iface.getIdentifier(), id);
        return iface;
    }
    
    
    public MValueMemberDef getMember(MValueDef value, int index, String id)
    {
        assertTrue(value.getContentss().get(index) instanceof MValueMemberDef);
        MValueMemberDef member = (MValueMemberDef)value.getContentss().get(index);
        assertEquals(member.getIdentifier(), id);
        return member;
    }
    
    
    public MIDLType getMemberType(MValueDef value, int index, String id)
    {
        return getMember(value,index,id).getIdlType();
    }
    
    
    public MFactoryDef getFactoryType(MValueDef value, int index, String id)
    {
        assertTrue(value.getContentss().get(index) instanceof MFactoryDef);
        MFactoryDef factory = (MFactoryDef)value.getContentss().get(index);
        assertEquals(factory.getIdentifier(), id);
        return factory;
    }
    
    
    public MIDLType getParameterType(MFactoryDef factory, int index, String id)
    {
        MParameterDef attr = (MParameterDef)factory.getParameters().get(index);
        assertEquals(attr.getIdentifier(), id);
        return attr.getIdlType();
    }
    
    
    public MAttributeDef getAttributeType(MValueDef value, int index, String id)
    {
        assertTrue(value.getContentss().get(index) instanceof MAttributeDef);
        MAttributeDef attr = (MAttributeDef)value.getContentss().get(index);
        assertEquals(attr.getIdentifier(), id);
        return attr;
    }

    
    public MOperationDef getOperationType(MValueDef value, int index, String id)
    {
        assertTrue(value.getContentss().get(index) instanceof MOperationDef);
        MOperationDef op = (MOperationDef)value.getContentss().get(index);
        assertEquals(op.getIdentifier(), id);
        return op;
    }
    
    public MIDLType getParameterType(MOperationDef op, int index, String id, MParameterMode dir)
    {
        MParameterDef param = (MParameterDef)op.getParameters().get(index);
        assertEquals(param.getIdentifier(), id);
        assertEquals(param.getDirection(), dir);
        return param.getIdlType();
    }

    
    public MValueDef getAbstractBase(MValueDef value, int index, String id)
    {
        assertTrue(value.getAbstractBases().get(index) instanceof MValueDef);
        MValueDef abstractBase = (MValueDef)value.getAbstractBases().get(index);
        assertEquals(abstractBase.getIdentifier(), id);
        assertTrue(abstractBase.isAbstract());
        return abstractBase;
    }
}
