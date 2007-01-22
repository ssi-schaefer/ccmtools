package ccmtools.generator.deployment.metamodel;

import ccmtools.generator.deployment.metamodel.impl.DeploymentFactoryImpl;


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
