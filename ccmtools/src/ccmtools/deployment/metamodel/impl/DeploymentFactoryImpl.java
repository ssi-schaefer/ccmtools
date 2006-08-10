package ccmtools.deployment.metamodel.impl;

import ccmtools.deployment.metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.deployment.metamodel.ComponentPackageDescription;
import ccmtools.deployment.metamodel.ComponentPortDescription;
import ccmtools.deployment.metamodel.DeploymentFactory;
import ccmtools.deployment.metamodel.ImplementationArtifactDescription;
import ccmtools.deployment.metamodel.MonolithicImplementationDescription;
import ccmtools.deployment.metamodel.NamedImplementationArtifact;
import ccmtools.deployment.metamodel.PackagedComponentImplementation;

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