package ccmtools.deployment;

import junit.framework.TestCase;
import ccmtools.generator.deployment.metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.generator.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.generator.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.generator.deployment.metamodel.ComponentPackageDescription;
import ccmtools.generator.deployment.metamodel.DeploymentFactory;
import ccmtools.generator.deployment.metamodel.ImplementationArtifactDescription;
import ccmtools.generator.deployment.metamodel.MonolithicImplementationDescription;
import ccmtools.generator.deployment.metamodel.NamedImplementationArtifact;
import ccmtools.generator.deployment.metamodel.PackagedComponentImplementation;

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
        System.out.println("setUp()");
        model = instantiateDeploymentModel();
    }
    
    public void tearDown()
    {
        System.out.println("tearDown()");
        model = null;
    }
    

    // Test cases for the deployment model ---------------------
        
//    public void testXmlLoad()
//    {
//        try {            
//            File file = new File(testDir, "Order.xml");
//            ModelElement Order = ModelFactory.instance.loadXml(file);
//            System.out.println(Order.toXml());
//        }
//    }
    
//    public void testXmlSave()
//    {
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
//    }
    
    
    
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
        compID.getSupportedType().add("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedType().add("IDL:wamas/stocktake/Main:1.0");
        compID.getIdlFile().add("wamas/stocktake/MainHome.idl");
        compID.getIdlFile().add("wamas/stocktake/Main.idl");

        ComponentPackageDescription compPackageDescription = 
            factory.createComponentPackageDescription();
        compPackageDescription.setLabel("");
        compPackageDescription.setUUID("");
        compPackageDescription.getImplementations().add(packCompImpl);
        compPackageDescription.setRealizes(compID);

        return compPackageDescription;
    }
}