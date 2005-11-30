package ccmtools.Deployment;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.JDOMException;

import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentAssemblyDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.DeploymentToXmiMapper;
import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;
import ccmtools.Deployment.Metamodel.XmiToDeploymentMapper;


public class Test
{
    private static final String ccmtoolsDir = System.getProperty("user.dir");
    private static String testDir = ccmtoolsDir + "/test/Deployment/Metamodel";
    
    public static void main(String[] args)
    {
        {
            try {                
                DeploymentToXmiMapper mapper = new DeploymentToXmiMapper();
                ComponentPackageDescription model = instantiateDeploymentModel();
                mapper.saveModel(new File(testDir, "example.xml"), model);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        {
            try {
                XmiToDeploymentMapper mapper = new XmiToDeploymentMapper();
                ComponentPackageDescription model = mapper.loadModel(new File(testDir, "example.xml"));
                System.out.println(model);
                
                DeploymentToXmiMapper xmiMapper = new DeploymentToXmiMapper();
                xmiMapper.saveModel(new File(testDir, "example.tmp.xml"), model);
                
                // Check for implements association
                for(Iterator i=model.getImplementations().iterator(); i.hasNext();) {
                    PackagedComponentImplementation impl = (PackagedComponentImplementation)i.next();
                    ComponentInterfaceDescription cid = 
                        impl.getReferencedImplementation().getImplements();
                    
                    System.out.println("getRealizes=" + cid);
                }
                
            }
            catch(JDOMException e) {
                System.out.println(e.getMessage());
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private static ComponentPackageDescription instantiateDeploymentModel()
    {
        DeploymentFactory factory = DeploymentFactory.instance;
        
        ImplementationArtifactDescription implAD = 
            factory.createImplementationArtifactDescription();
        implAD.setLabel("l");
        implAD.setUUID("u");
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

        ComponentAssemblyDescription compAD = 
            factory.createComponentAssemblyDescription();
        compAD.getAssemblyArtifacts().add(compAAD);

        ComponentInterfaceDescription compID = 
            factory.createComponentInterfaceDescription();
        compID.setLabel("");
        compID.setUUID("");
        compID.setSpecificType("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedTypes().add("IDL:wamas/stocktake/MainHome:1.0");
        compID.getSupportedTypes().add("IDL:wamas/stocktake/Main:1.0");
        compID.getIdlFiles().add("wamas/stocktake/MainHome.idl");
        compID.getIdlFiles().add("wamas/stocktake/Main.idl");
        
        ComponentImplementationDescription compImplDesc = 
            factory.createComponentImplementationDescription();
        compImplDesc.setLabel("");
        compImplDesc.setUUID("");
        compImplDesc.setMonolithicImpl(monoID);
        compImplDesc.setAssemblyImpl(compAD);
        compImplDesc.setImplements(compID);

        PackagedComponentImplementation packCompImpl = 
            factory.createPackagedComponentImplementation();
        packCompImpl.setName("myMainHome");
        packCompImpl.setReferencedImplementation(compImplDesc);

        ComponentPackageDescription compPackageDescription = 
            factory.createComponentPackageDescription();
        compPackageDescription.setLabel("");
        compPackageDescription.setUUID("");
        compPackageDescription.getImplementations().add(packCompImpl);
        compPackageDescription.setRealizes(compID);

        return compPackageDescription;
    }
}
