package ccmtools.generator.deployment.metamodel.impl;

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

public class DeploymentFactoryImpl
    implements DeploymentFactory
{        
    public ComponentPackageDescription createComponentPackageDescription()
    {
        return new ComponentPackageDescriptionImpl();
    }
    
    public ComponentInterfaceDescription createComponentInterfaceDescription()
    {
        return new ComponentInterfaceDescriptionImpl();
    }
    
    public PackagedComponentImplementation createPackagedComponentImplementation()
    {
        return new PackagedComponentImplementationImpl();
    }
    
    public ComponentImplementationDescription createComponentImplementationDescription()
    {
        return new ComponentImplementationDescriptionImpl();
    }
    
    public ComponentAssemblyArtifactDescription createComponentAssemblyArtifactDescription()
    {
        return new ComponentAssemblyArtifactDescriptionImpl();
    }
    
    public MonolithicImplementationDescription createMonolithicImplementationDescription()
    {
        return new MonolithicImplementationDescriptionImpl();
    }
    
    public NamedImplementationArtifact createNamedImplementationArtifact()
    {
        return new NamedImplementationArtifactImpl();
    }
    
    public ImplementationArtifactDescription createImplementationArtifactDescription()
    {
        return new ImplementationArtifactDescriptionImpl();
    }
    
    public ComponentPortDescription createComponentPortDescription()
    {
        return new ComponentPortDescriptionImpl();
    }
}