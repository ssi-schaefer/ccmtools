package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.impl.DeploymentFactoryImpl;


public interface DeploymentFactory
{
    /** Singleton */
    DeploymentFactory instance = new DeploymentFactoryImpl();
 
    ComponentPackageDescription createComponentPackageDescription();
    ComponentInterfaceDescription createComponentInterfaceDescription();
    PackagedComponentImplementation createPackagedComponentImplementation();
    ComponentImplementationDescription createComponentImplementationDescription();
    ComponentAssemblyArtifactDescription createComponentAssemblyArtifactDescription();
    MonolithicImplementationDescription createMonolithicImplementationDescription();
    NamedImplementationArtifact createNamedImplementationArtifact();
    ImplementationArtifactDescription createImplementationArtifactDescription();
    ComponentPortDescription createComponentPortDescription();
}
