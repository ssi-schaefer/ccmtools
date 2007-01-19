package ccmtools.parser.idl.home;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;


public class HomeHeaderTest extends HomeTest
{
    public HomeHeaderTest()
        throws FileNotFoundException
    {
        super(HomeHeaderTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(HomeHeaderTest.class);
    }
    
     
    public void testHomeHeader() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "component MyComponent { };" +
                "home MyHome manages MyComponent {};", "MyHome");

        assertNotNull(home.getComponent());
        MComponentDef component = home.getComponent();
        assertEquals("MyComponent", component.getIdentifier());
        assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        assertTrue(home.getComponent().getHomes().get(0) instanceof MHomeDef);
        MHomeDef home2 = (MHomeDef)home.getComponent().getHomes().get(0);
        assertEquals("MyHome", home2.getIdentifier());        
        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
    }         
    
    
    public void testHomeHeaderInheritance() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "component MyComponent { };" +
                "home MyHome manages MyComponent {};" +

                "component AnotherComponent {};" +
                "home SubHome : MyHome manages AnotherComponent {};", "SubHome");

        {
            assertEquals("IDL:SubHome:1.0", CcmModelHelper.getRepositoryId(home));
            assertNotNull(home.getComponent());
            MComponentDef component = home.getComponent();
            assertEquals("IDL:AnotherComponent:1.0", CcmModelHelper.getRepositoryId(component));
        }
        {
            assertNotNull(home.getBases().get(0));
            MHomeDef baseHome = (MHomeDef)home.getBases().get(0);
            assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(baseHome));
            
            assertNotNull(baseHome.getComponent());
            MComponentDef component = baseHome.getComponent();
            assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        }
    }         

    
    public void testHomeHeaderSupportedInterfaces() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "interface Interface1 { };" +
                "interface Interface2 { };" +
                "component MyComponent { };" +
                "home MyHome " +
                "   supports Interface1, Interface2" +
                "   manages MyComponent {};" , "MyHome");

        {
            assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
            assertNotNull(home.getComponent());
            MComponentDef component = home.getComponent();
            assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        }
        assertNotNull(home.getSupportss()); 
        {   
            assertTrue(home.getSupportss().get(0) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)home.getSupportss().get(0);                   
            assertEquals("IDL:Interface1:1.0", CcmModelHelper.getRepositoryId(iface));            
        }
        {   
            assertTrue(home.getSupportss().get(1) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)home.getSupportss().get(1);                   
            assertEquals("IDL:Interface2:1.0", CcmModelHelper.getRepositoryId(iface));            
        }    
    }         

    
    public void testHomeHeaderSupportedInterfacesAndInheritance() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "interface Interface1 { };" +
                "interface Interface2 { };" +
                
                "component MyComponent { };" +
                "home MyHome manages MyComponent {};" +
                
                "component SubComponent { };" +                
                "home SubHome : MyHome" +
                "   supports Interface1, Interface2" +
                "   manages SubComponent {};" , "SubHome");

        {
            assertEquals("IDL:SubHome:1.0", CcmModelHelper.getRepositoryId(home));
            assertNotNull(home.getComponent());
            MComponentDef component = home.getComponent();
            assertEquals("IDL:SubComponent:1.0", CcmModelHelper.getRepositoryId(component));
        }
        assertNotNull(home.getSupportss()); 
        {   
            assertTrue(home.getSupportss().get(0) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)home.getSupportss().get(0);                   
            assertEquals("IDL:Interface1:1.0", CcmModelHelper.getRepositoryId(iface));            
        }
        {   
            assertTrue(home.getSupportss().get(1) instanceof MInterfaceDef);
            MInterfaceDef iface = (MInterfaceDef)home.getSupportss().get(1);                   
            assertEquals("IDL:Interface2:1.0", CcmModelHelper.getRepositoryId(iface));            
        }    
        {
            assertNotNull(home.getBases().get(0));
            MHomeDef baseHome = (MHomeDef)home.getBases().get(0);
            assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(baseHome));
            
            assertNotNull(baseHome.getComponent());
            MComponentDef component = baseHome.getComponent();
            assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        }
    }         
    
}
