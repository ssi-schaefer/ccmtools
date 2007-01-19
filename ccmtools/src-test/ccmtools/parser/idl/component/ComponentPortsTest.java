package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MUsesDef;


public class ComponentPortsTest extends ComponentTest
{
    public ComponentPortsTest()
        throws FileNotFoundException
    {
        super(ComponentPortsTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentPortsTest.class);
    }
    
     
    public void testComponentWithFacets() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "interface IFace1 {}; " +
                "interface IFace2 {}; " +
                "component AComponent {" +
                "   provides IFace1 port1;" +
                "   provides IFace2 port2;" +
                "};", "AComponent");

        {
            assertTrue(component.getFacets().get(0) instanceof MProvidesDef);
            MProvidesDef facet = (MProvidesDef)component.getFacets().get(0);
            assertEquals("port1", facet.getIdentifier());           
            assertEquals("AComponent", facet.getComponent().getIdentifier());
            
            assertTrue(facet.getProvides() instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)facet.getProvides();
            assertEquals("IFace1", iface.getIdentifier());
                        
            assertEquals("IDL:port1:1.0", CcmModelHelper.getRepositoryId(facet));
            assertEquals("IDL:AComponent:1.0", CcmModelHelper.getRepositoryId(component));
            assertEquals("IDL:IFace1:1.0", CcmModelHelper.getRepositoryId(iface));
        }
        {
            assertTrue(component.getFacets().get(1) instanceof MProvidesDef);
            MProvidesDef facet = (MProvidesDef)component.getFacets().get(1);
            assertEquals("port2", facet.getIdentifier());           
            assertEquals("AComponent", facet.getComponent().getIdentifier());
            
            assertTrue(facet.getProvides() instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)facet.getProvides();
            assertEquals("IFace2", iface.getIdentifier());
        }        
    }
    
    public void testComponentWithReceptacles() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "interface IFace1 {}; " +
                "interface IFace2 {}; " +
                "component AComponent {" +
                "   uses IFace1 port1;" +
                "   uses multiple IFace2 port2;" +
                "};", "AComponent");

        {
            assertTrue(component.getReceptacles().get(0) instanceof MUsesDef);
            MUsesDef receptacle = (MUsesDef)component.getReceptacles().get(0);
            assertEquals("port1", receptacle.getIdentifier());           
            assertEquals("AComponent", receptacle.getComponent().getIdentifier());
            
            assertTrue(receptacle.getUses() instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)receptacle.getUses();
            assertEquals("IFace1", iface.getIdentifier());
        }
        {
            assertTrue(component.getReceptacles().get(1) instanceof MUsesDef);
            MUsesDef receptacle = (MUsesDef)component.getReceptacles().get(1);
            assertEquals("port2", receptacle.getIdentifier());           
            assertEquals("AComponent", receptacle.getComponent().getIdentifier());
            
            assertTrue(receptacle.getUses() instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)receptacle.getUses();
            assertEquals("IFace2", iface.getIdentifier());
            assertTrue(receptacle.isMultiple());
        }        
    }         

}
