package ccmtools.parser.idl.iface;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MAttributeDef;
import ccmtools.metamodel.BaseIDL.MContained;
import ccmtools.metamodel.BaseIDL.MContainer;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;
import ccmtools.metamodel.BaseIDL.MOperationDef;
import ccmtools.metamodel.BaseIDL.MParameterDef;
import ccmtools.metamodel.BaseIDL.MParameterMode;
import ccmtools.parser.idl.ParserHelper;
import ccmtools.parser.idl.primitive.PrimitiveTest;
import ccmtools.ui.ConsoleDriver;
import ccmtools.ui.UserInterfaceDriver;


public class InterfaceTest extends TestCase
{
    protected UserInterfaceDriver uiDriver;
    
    
    public InterfaceTest(String title)
        throws FileNotFoundException
    {
        super(title);
        uiDriver = new ConsoleDriver();
    }
        

    public static String getIFaceSource()
    {
        return  "interface IFace {" +
                "   attribute string id;" +
                "   long foo(in string p);" +
                "};";
    }
    
    public static void checkIFace(MInterfaceDef iface)
    {
        assertEquals(iface.getIdentifier(), "IFace");
        {
            assertTrue(iface.getContentss().get(0) instanceof MAttributeDef);
            MAttributeDef attr = (MAttributeDef)iface.getContentss().get(0);        
            PrimitiveTest.checkStringType(attr);
            assertEquals(attr.getIdentifier(), "id");
        }
        {
            assertTrue(iface.getContentss().get(1) instanceof MOperationDef);
            MOperationDef op = (MOperationDef) iface.getContentss().get(1);
            assertEquals(op.getIdentifier(), "foo");
            PrimitiveTest.checkLongType(op.getIdlType());
            {
                assertTrue(op.getParameters().get(0) instanceof MParameterDef);
                MParameterDef parameter = (MParameterDef)op.getParameters().get(0);
                assertEquals(parameter.getDirection(), MParameterMode.PARAM_IN);
                PrimitiveTest.checkStringType(parameter.getIdlType());
                assertEquals(parameter.getIdentifier(), "p");            
            }
        }
    }

    
    
    /*
     * Utility Methods
     */
    
    public MInterfaceDef parseSource(String sourceCode) 
        throws CcmtoolsException
    {
        System.out.println("[" + sourceCode + "]");
        MContainer ccmModel = ParserHelper.getInstance().loadCcmModel(uiDriver, sourceCode);
        List modelElements = ccmModel.getContentss();
        System.out.println(modelElements);
        return (MInterfaceDef)modelElements.get(0);
    }
    
    public MInterfaceDef parseSource(String sourceCode, String id) 
        throws CcmtoolsException
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
                return (MInterfaceDef)element;
            }
        }
        return null;
    }
}
