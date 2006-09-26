package ccmtools.parser.idl.test.iface;

import java.io.FileNotFoundException;

import junit.framework.Test;
import junit.framework.TestSuite;
import ccmtools.CcmtoolsException;
import ccmtools.metamodel.BaseIDL.MInterfaceDef;


public class InterfaceForwardDeclarationTest extends InterfaceTest
{
    public InterfaceForwardDeclarationTest()
        throws FileNotFoundException
    {
        super(InterfaceForwardDeclarationTest.class.getName());
    }
        
    public static Test suite()
    {
        return new TestSuite(InterfaceForwardDeclarationTest.class);
    }
    
     
    public void testInterfaceForwardDeclaration() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "interface IFace;" + 
                InterfaceTest.getIFaceSource()
            );

        InterfaceTest.checkIFace(iface);
    }             
    
    
    public void testAbstractInterfaceForwardDeclaration() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "abstract interface IFace;" +
                "abstract "+ InterfaceTest.getIFaceSource());
        
        assertTrue(iface.isAbstract());
        InterfaceTest.checkIFace(iface);
    }        
    
    
    public void testLocalInterfaceForwardDeclaration() throws CcmtoolsException
    {
        MInterfaceDef iface = parseSource(
                "local interface IFace;" +
                "local " + InterfaceTest.getIFaceSource());
        
        assertTrue(iface.isLocal());
        InterfaceTest.checkIFace(iface);
    }        
}
