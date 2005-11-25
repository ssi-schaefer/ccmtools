package ccmtools.test.Deployment;

import java.io.File;

import junit.framework.TestCase;
import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentAssemblyDescription;
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
        
    public void testXmlSerialization()
    {
        try {            
            File file = new File(testDir, "example.xml");

            // save model to an XML file
            DeploymentFactory.instance.saveXml(file, model);
            
            // load example XML file
            ComponentPackageDescription testModel = DeploymentFactory.instance.loadXml(file);
              
            String s1 = model.toXml();
            String s2 = testModel.toXml();
            assertTrue(s1.equals(s2));
            
            System.out.println(testModel);
        }
        catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
    
    // Helper methods -------------------------------------------------
    
    private ComponentPackageDescription instantiateDeploymentModel()
    {
        DeploymentFactory factory = DeploymentFactory.instance;
        
        ImplementationArtifactDescription implAD = 
            factory.createImplementationArtifactDescription();
        implAD.setLabel("");
        implAD.setUUID("");
        implAD.getLocation().add("xy.h");
        implAD.getLocation().add("xy.cc");

        NamedImplementationArtifact namedIA = 
            factory.createNamedImplementationArtifact();
        namedIA.setName("xy");
        namedIA.setReferencedArtifact(implAD);

        MonolithicImplementationDescription monoID = 
            factory.createMonolithicImplementationDescription();
        monoID.getPrimaryArtifact().add(namedIA);

        ComponentAssemblyArtifactDescription compAAD = 
            factory.createComponentAssemblyArtifactDescription();
        compAAD.setLabel("");
        compAAD.setUUID("");
        compAAD.setSpectifcType("IDL:wamas/stocktake/StocktakeAssembly:1.0");
        compAAD.getLocation().add("wamas/stocktake/assembly/stocktake_assembly.h");

        ComponentAssemblyDescription compAD = 
            factory.createComponentAssemblyDescription();
        compAD.getAssemblyArtifact().add(compAAD);

        ComponentImplementationDescription compImplDesc = 
            factory.createComponentImplementationDescription();
        compImplDesc.setLabel("");
        compImplDesc.setUUID("");
        compImplDesc.setMonolithicImpl(monoID);
        compImplDesc.setAssemblyImpl(compAD);

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
        compPackageDescription.getImplementation().add(packCompImpl);
        compPackageDescription.setRealizes(compID);

        return compPackageDescription;
    }
}