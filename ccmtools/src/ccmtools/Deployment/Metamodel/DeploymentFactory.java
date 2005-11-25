package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.impl.DeploymentFactoryImpl;
import ccmtools.Deployment.Metamodel.utils.ModelFactory;


public interface DeploymentFactory
    extends ModelFactory
{
    /** Singleton */
    DeploymentFactory instance = new DeploymentFactoryImpl();
 
    ComponentPackageDescription createComponentPackageDescription();
    ComponentInterfaceDescription createComponentInterfaceDescription();
    PackagedComponentImplementation createPackagedComponentImplementation();
    ComponentImplementationDescription createComponentImplementationDescription();
    ComponentAssemblyDescription createComponentAssemblyDescription();
    ComponentAssemblyArtifactDescription createComponentAssemblyArtifactDescription();
    MonolithicImplementationDescription createMonolithicImplementationDescription();
    NamedImplementationArtifact createNamedImplementationArtifact();
    ImplementationArtifactDescription createImplementationArtifactDescription();
}
