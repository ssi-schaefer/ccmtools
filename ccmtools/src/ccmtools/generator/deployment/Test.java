package ccmtools.generator.deployment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.JDOMException;

import ccmtools.generator.deployment.metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.generator.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.generator.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.generator.deployment.metamodel.ComponentPackageDescription;
import ccmtools.generator.deployment.metamodel.ComponentPortDescription;
import ccmtools.generator.deployment.metamodel.DeploymentFactory;
import ccmtools.generator.deployment.metamodel.ImplementationArtifactDescription;
import ccmtools.generator.deployment.metamodel.MonolithicImplementationDescription;
import ccmtools.generator.deployment.metamodel.NamedImplementationArtifact;
import ccmtools.generator.deployment.metamodel.PackagedComponentImplementation;
import ccmtools.generator.deployment.metamodel.impl.CCMComponentPortKind;


public class Test
{
    private static final String ccmtoolsDir = System.getProperty("user.dir");
    private static String testDir = ccmtoolsDir + "/test/Deployment/Metamodel";
    
    public static void main(String[] args)
    {
        {
            // Save a Deployment model as an XMI 2.1 file 
            try 
            {                
                ComponentPackageDescription model = createDeploymentModel();
                DeploymentToXmiMapper mapper = new DeploymentToXmiMapper();
                mapper.saveModel(new File(testDir, "example.xml"), model);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        {
            // Load a Deployment model from an XMI 2.1 file
            // Save the same model to a new XMI 2.1 file
            // Print the xmi on the console
            // Check the association between ComponentImplementationDescription
            // and ComponentInterfaceDescription.
            try 
            {
                XmiToDeploymentMapper mapper = new XmiToDeploymentMapper();
                ComponentPackageDescription loadedModel = 
                    mapper.loadModel(new File(testDir, "example.xml"));
                
                DeploymentToXmiMapper xmiMapper = new DeploymentToXmiMapper();
                xmiMapper.saveModel(new File(testDir, "example.tmp.xml"), loadedModel);
                
                String xmi = xmiMapper.modelToString(loadedModel);
                System.out.println("loaded model:");
                System.out.println(xmi);
                
                // Check for implements association
                for(Iterator i=loadedModel.getImplementations().iterator(); i.hasNext();) 
                {
                    PackagedComponentImplementation impl = 
                        (PackagedComponentImplementation)i.next();
                    ComponentInterfaceDescription cid = 
                        impl.getReferencedImplementation().getImplements();
                    System.out.println("getRealizes = " + cid);
                }
            }
            catch(JDOMException e) 
            {
                System.out.println(e.getMessage());
            }
            catch(IOException e) 
            {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private static ComponentPackageDescription createDeploymentModel()
    {
        DeploymentFactory factory = DeploymentFactory.instance;
        
        ImplementationArtifactDescription implAD = 
            factory.createImplementationArtifactDescription();
        implAD.setLabel("label");
        implAD.setUUID("UUID");
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
        compAAD.setLabel("label");
        compAAD.setUUID("UUID");
        compAAD.setSpecifcType("IDL:wamas/stocktake/StocktakeAssembly:1.0");
        compAAD.getLocations().add("wamas/stocktake/assembly/stocktake_assembly.h");

        ComponentPortDescription facet = 
            factory.createComponentPortDescription();
        facet.setKind(CCMComponentPortKind.Facet);
        facet.setName("BaseExt");
        facet.setSpecificType("IDL:wamas/stocktake/BaseExt:1.0");
        facet.getSupportedType().add("IDL:wamas/stocktake/BaseExt:1.0");
        facet.setProvider(true);
        facet.setExclusiveProvider(true);
        facet.setExclusiveUser(false);
        facet.setOptional(false);
        
        ComponentPortDescription receptacle = 
            factory.createComponentPortDescription();
        receptacle.setKind(CCMComponentPortKind.SimplexReceptacle);
        receptacle.setName("BaseUser");
        receptacle.setSpecificType("IDL:wamas/stocktake/BaseUser:1.0");
        receptacle.getSupportedType().add("IDL:wamas/stocktake/BaseUser:1.0");
        receptacle.setProvider(false);
        receptacle.setExclusiveProvider(false);
        receptacle.setExclusiveUser(false);
        receptacle.setOptional(false);
        
        ComponentInterfaceDescription compID = 
            factory.createComponentInterfaceDescription();
        compID.setLabel("label");
        compID.setUUID("UUID");
        compID.setSpecificType("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedType().add("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedType().add("IDL:wamas/stocktake/Main:1.0");
        compID.getIdlFile().add("wamas/stocktake/MainHome.idl");
        compID.getIdlFile().add("wamas/stocktake/Main.idl");
        compID.getPort().add(facet);
        compID.getPort().add(receptacle);
        
        
        ComponentImplementationDescription compImplDesc = 
            factory.createComponentImplementationDescription();
        compImplDesc.setLabel("label");
        compImplDesc.setUUID("UUID");
        compImplDesc.setMonolithicImpl(monoID);
        compImplDesc.setAssemblyImpl(compAAD);
        compImplDesc.setImplements(compID);

        PackagedComponentImplementation packCompImpl = 
            factory.createPackagedComponentImplementation();
        packCompImpl.setName("myMainHome");
        packCompImpl.setReferencedImplementation(compImplDesc);

        ComponentPackageDescription compPackageDescription = 
            factory.createComponentPackageDescription();
        compPackageDescription.setLabel("label");
        compPackageDescription.setUUID("UUID");
        compPackageDescription.getImplementations().add(packCompImpl);
        compPackageDescription.setRealizes(compID);

        return compPackageDescription;
    }
}
