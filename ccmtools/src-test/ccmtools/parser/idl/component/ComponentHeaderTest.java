package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;


public class ComponentHeaderTest extends ComponentTest
{
    public ComponentHeaderTest()
        throws FileNotFoundException
    {
        super(ComponentHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentHeaderTest.class);
    }
    
     
    public void testEmptyComponent() throws CcmtoolsException
    {
        MComponentDef component = parseSource("component EmptyComponent { };");

        assertEquals(component.getIdentifier(), "EmptyComponent");
        assertEquals(component.getContentss().size(), 0);
    }         
    
    public void testComponentHeaderInheritance() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "component BaseComponent { }; " +
                "component SubComponent : BaseComponent {};", "SubComponent");

        assertTrue(component.getBases().get(0) instanceof MComponentDef);
        MComponentDef base = (MComponentDef) component.getBases().get(0);
        assertEquals(base.getIdentifier(), "BaseComponent");
        assertEquals(base.getContentss().size(), 0);
    }         
    

    public void testComponentHeaderSupports() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "interface Interface1 { };" +
                "interface Interface2 { };" +
                "component EmptyComponent supports Interface1, Interface2 {};", "EmptyComponent");

        {
            assertTrue(component.getSupportss().get(0) instanceof MInterfaceDef);        
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(0);
            assertEquals(iface.getIdentifier(), "Interface1");
        }
        {
            assertTrue(component.getSupportss().get(1) instanceof MInterfaceDef);        
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(1);
            assertEquals(iface.getIdentifier(), "Interface2");
        }
    }         

    public void testComponentHeaderInheritanceAndSupports() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "component BaseComponent { }; " +
                "interface Interface1 { };" +
                "interface Interface2 { };" +                
                "component SubComponent " +
                "   : BaseComponent " +
                "   supports Interface1, Interface2 {};", "SubComponent");
        {
            assertTrue(component.getBases().get(0) instanceof MComponentDef);
            MComponentDef base = (MComponentDef) component.getBases().get(0);
            assertEquals(base.getIdentifier(), "BaseComponent");
            assertEquals(base.getContentss().size(), 0);
        } 
        {
            assertTrue(component.getSupportss().get(0) instanceof MInterfaceDef);        
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(0);
            assertEquals(iface.getIdentifier(), "Interface1");
        }
        {
            assertTrue(component.getSupportss().get(1) instanceof MInterfaceDef);        
            MInterfaceDef iface = (MInterfaceDef)component.getSupportss().get(1);
            assertEquals(iface.getIdentifier(), "Interface2");
        }        
    }       
}
