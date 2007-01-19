package ccmtools.parser.idl.component;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MProvidesDef;


public class ComponentForwardDeclarationTest extends ComponentTest
{
    public ComponentForwardDeclarationTest()
        throws FileNotFoundException
    {
        super(ComponentForwardDeclarationTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(ComponentForwardDeclarationTest.class);
    }
    
     
    public void testComponentForwardDeclaration() throws CcmtoolsException
    {
        MComponentDef component = parseSource(
                "component MyComponent; " +
                "interface IFace {}; " +
                "component MyComponent { " +
                "   provides IFace port;" +
                "};", "MyComponent");
        
        {
            assertTrue(component.getFacets().get(0) instanceof MProvidesDef);
            MProvidesDef facet = (MProvidesDef)component.getFacets().get(0);
            assertEquals("port", facet.getIdentifier());           
            
            assertTrue(facet.getProvides() instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)facet.getProvides();
            assertEquals("IFace", iface.getIdentifier());
        }        
    }             
    
    
}
