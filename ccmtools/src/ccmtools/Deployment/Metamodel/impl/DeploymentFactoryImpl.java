package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.ComponentPortDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;

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