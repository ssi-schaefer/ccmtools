package ccmtools.parser.idl.home;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.parser.idl.enumeration.EnumTest;
import ccmtools.parser.idl.exception.ExceptionTest;
import ccmtools.parser.idl.metamodel.CcmModelHelper;
import ccmtools.parser.idl.metamodel.BaseIDL.MExceptionDef;
import ccmtools.parser.idl.metamodel.BaseIDL.MParameterDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MComponentDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MFactoryDef;
import ccmtools.parser.idl.metamodel.ComponentIDL.MHomeDef;
import ccmtools.parser.idl.primitive.PrimitiveTest;


public class HomeFactoryTest extends HomeTest
{
    public HomeFactoryTest()
        throws FileNotFoundException
    {
        super(HomeFactoryTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(HomeFactoryTest.class);
    }
    
     
    public void testDefaultFactory() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "component MyComponent { };" +
                "home MyHome manages MyComponent" +
                "{" +
                "   factory firstCreate();" +
                "   factory secondCreate();" +
                "};", "MyHome");

        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertNotNull(home.getComponent());
        MComponentDef component = home.getComponent();
        assertEquals("MyComponent", component.getIdentifier());
        assertEquals("IDL:MyComponent:1.0", CcmModelHelper.getRepositoryId(component));
        
        {
            assertTrue(home.getFactories().get(0) instanceof MFactoryDef);
            MFactoryDef factory = (MFactoryDef)home.getFactories().get(0);
            assertEquals("firstCreate", factory.getIdentifier());
            assertTrue(factory.getParameters().size() == 0);
        }
        {
            assertTrue(home.getFactories().get(1) instanceof MFactoryDef);
            MFactoryDef factory = (MFactoryDef)home.getFactories().get(1);
            assertEquals("secondCreate", factory.getIdentifier());
            assertTrue(factory.getParameters().size() == 0);
        }
    }    
    
    public void testFactoryWithParameters() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                "component MyComponent { };" +
                "home MyHome manages MyComponent" +
                "{" +
                "   factory createComponent(in long id, in string name);" +
                "};", "MyHome");

        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertTrue(home.getFactories().get(0) instanceof MFactoryDef);
        MFactoryDef factory = (MFactoryDef)home.getFactories().get(0);
        assertEquals("createComponent", factory.getIdentifier());
        
        {
            assertTrue(factory.getParameters().get(0) instanceof MParameterDef);
            MParameterDef p = (MParameterDef)factory.getParameters().get(0);
            PrimitiveTest.checkLongType(p.getIdlType());
            assertEquals("createComponent", p.getOperation().getIdentifier());
        }
        {
            assertTrue(factory.getParameters().get(1) instanceof MParameterDef);
            MParameterDef p = (MParameterDef)factory.getParameters().get(1);
            PrimitiveTest.checkStringType(p.getIdlType());
            assertEquals("createComponent", p.getOperation().getIdentifier());
        }        
    }
    
    public void testFactoryWithExceptions() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                ExceptionTest.getEmptyExceptionSource() +
                ExceptionTest.getSimpleExceptionSource() +
                
                "component MyComponent { };" +
                
                "home MyHome manages MyComponent" +
                "{" +
                "   factory createComponent() raises(EmptyException, SimpleException);" +
                "};", "MyHome");

        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertTrue(home.getFactories().get(0) instanceof MFactoryDef);
        MFactoryDef factory = (MFactoryDef)home.getFactories().get(0);
        assertEquals("createComponent", factory.getIdentifier());
        
        assertTrue(factory.getExceptionDefs().get(0) instanceof MExceptionDef);
        ExceptionTest.checkEmptyException((MExceptionDef) factory.getExceptionDefs().get(0));
        
        assertTrue(factory.getExceptionDefs().get(1) instanceof MExceptionDef);
        ExceptionTest.checkSimpleException((MExceptionDef) factory.getExceptionDefs().get(1));        
    }

    public void testFactoryWithParametersAndExceptions() throws CcmtoolsException
    {
        MHomeDef home = parseSource(
                EnumTest.getEnumColorSource()+
                ExceptionTest.getSimpleExceptionSource() +
                
                "component MyComponent { };" +
                
                "home MyHome manages MyComponent" +
                "{" +
                "   factory createComponent(in Color c) raises(SimpleException);" +
                "};", "MyHome");
    
        assertEquals("IDL:MyHome:1.0", CcmModelHelper.getRepositoryId(home));
        
        assertTrue(home.getFactories().get(0) instanceof MFactoryDef);
        MFactoryDef factory = (MFactoryDef)home.getFactories().get(0);
        assertEquals("createComponent", factory.getIdentifier());
        
        assertTrue(factory.getParameters().get(0) instanceof MParameterDef);
        MParameterDef p = (MParameterDef)factory.getParameters().get(0);
        EnumTest.checkEnumColor(p.getIdlType());
        assertEquals("createComponent", p.getOperation().getIdentifier());
        
        assertTrue(factory.getExceptionDefs().get(0) instanceof MExceptionDef);
        ExceptionTest.checkSimpleException((MExceptionDef) factory.getExceptionDefs().get(0)); 
    }
}
