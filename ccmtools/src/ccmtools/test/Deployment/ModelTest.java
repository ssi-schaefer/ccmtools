package ccmtools.test.Deployment;

import junit.framework.TestCase;
import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;

public class ModelTest extends TestCase
{
    private final String ccmtoolsDir = System.getProperty("user.dir");
    private String testDir = ccmtoolsDir + "/test/Deployment/Metamodel";
    
    private ComponentPackageDescription model;
    
    public ModelTest(String name)
    {
        super(name);
    }
    
    public void setUp()
    {
        model = instantiateDeploymentModel();
    }
    
    public void tearDown()
    {
        model = null;
    }
    

    // Test cases for the deployment model ---------------------
        
    public void testXmlLoad()
    {
//        try {            
//            File file = new File(testDir, "Order.xml");
//            ModelElement Order = ModelFactory.instance.loadXml(file);
//            System.out.println(Order.toXml());
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            fail(e.getMessage());
//        }
    }
    
    public void testXmlSave()
    {
//        try {
//            ModelFactory factory = ModelFactory.instance;
//            ModelElement sequence = factory.createModelElement("Sequence");
//            for(int i=0; i<10;i++) {
//                ModelElement item = factory.createModelElement("Item");
//                item.addElementAttribute("index", Integer.toString(i));
//                item.setElementText(Integer.toBinaryString(i));
//                sequence.addElementChild(item);
//            }
//            File file = new File(testDir, "Sequence.xml");
//            factory.saveXml(file, sequence);
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            fail(e.getMessage());
//        }
    }
    
    public void testDeploymentModelSerialization()
    {
//        try {            
//            ModelFactory factory = new DeploymentFactoryImpl();
//            
//            File file = new File(testDir, "example.xml");
//
//            // save model to an XML file
//            factory.saveXml(file, model);
//            
//            // load example XML file
//            ComponentPackageDescription testModel 
//                = (ComponentPackageDescription) factory.loadXml(file);
//              
//            String s1 = model.toXml();
//            String s2 = testModel.toXml();
//            assertTrue(s1.equals(s2));
//            
//            System.out.println(testModel);
//        }
//        catch(Exception e) {
//            fail(e.getMessage());
//        }
    }
    
    
    // Helper methods -------------------------------------------------
    
    private ComponentPackageDescription instantiateDeploymentModel()
    {
        DeploymentFactory factory = DeploymentFactory.instance;
        
        ImplementationArtifactDescription implAD = 
            factory.createImplementationArtifactDescription();
        implAD.setLabel("");
        implAD.setUUID("");
        implAD.getLocations().add("xy.h");
        implAD.getLocations().add("xy.cc");

        NamedImplementationArtifact namedIA = 
            factory.createNamedImplementationArtifact();
        namedIA.setName("xy");
        namedIA.setReferencedArtifact(implAD);

        MonolithicImplementationDescription monoID = 
            factory.createMonolithicImplementationDescription();
        monoID.getPrimaryArtifacts().add(namedIA);

        ComponentAssemblyArtifactDescription compAAD = 
            factory.createComponentAssemblyArtifactDescription();
        compAAD.setLabel("");
        compAAD.setUUID("");
        compAAD.setSpecifcType("IDL:wamas/stocktake/StocktakeAssembly:1.0");
        compAAD.getLocations().add("wamas/stocktake/assembly/stocktake_assembly.h");

        ComponentImplementationDescription compImplDesc = 
            factory.createComponentImplementationDescription();
        compImplDesc.setLabel("");
        compImplDesc.setUUID("");
        compImplDesc.setMonolithicImpl(monoID);
        compImplDesc.setAssemblyImpl(compAAD);

        PackagedComponentImplementation packCompImpl = 
            factory.createPackagedComponentImplementation();
        packCompImpl.setName("myMainHome");
        packCompImpl.setReferencedImplementation(compImplDesc);

        ComponentInterfaceDescription compID = 
            factory.createComponentInterfaceDescription();
        compID.setLabel("");
        compID.setUUID("");
        compID.setSpecificType("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedTypes().add("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedTypes().add("IDL:wamas/stocktake/Main:1.0");
        compID.getIdlFiles().add("wamas/stocktake/MainHome.idl");
        compID.getIdlFiles().add("wamas/stocktake/Main.idl");

        ComponentPackageDescription compPackageDescription = 
            factory.createComponentPackageDescription();
        compPackageDescription.setLabel("");
        compPackageDescription.setUUID("");
        compPackageDescription.getImplementations().add(packCompImpl);
        compPackageDescription.setRealizes(compID);

        return compPackageDescription;
    }
}