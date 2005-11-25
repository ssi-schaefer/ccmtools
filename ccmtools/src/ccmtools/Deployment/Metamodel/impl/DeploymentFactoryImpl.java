package ccmtools.Deployment.Metamodel.impl;

import java.io.File;

import org.xml.sax.Attributes;

import ccmtools.CcmtoolsException;
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
import ccmtools.Deployment.Metamodel.utils.ModelDocumentType;
import ccmtools.Deployment.Metamodel.utils.ModelDocumentTypeImpl;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelFactoryImpl;

public class DeploymentFactoryImpl
    extends ModelFactoryImpl implements DeploymentFactory
{        
    /**
     * Save a model to an XML file. 
     */
    public void saveXml(File file , ModelElement root)
        throws CcmtoolsException
    {
        ModelDocumentType docType =  
            new ModelDocumentTypeImpl(root.getElementName(),"deployment.dtd");
        saveXml(file, root, docType);
    }
    
        
    public ModelElement create(String name, Attributes attrs)
    {
        ModelElement element = null;

        if(name.equals(ComponentPackageDescription.ELEMENT_NAME)) {
            element = createComponentPackageDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(ComponentInterfaceDescription.ELEMENT_NAME)) {
            element = createComponentInterfaceDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(PackagedComponentImplementation.ELEMENT_NAME)) {
            element = createPackagedComponentImplementation();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(ComponentImplementationDescription.ELEMENT_NAME)) {
            element = createComponentImplementationDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(ComponentAssemblyDescription.ELEMENT_NAME)) {
            element = createComponentAssemblyDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(ComponentAssemblyArtifactDescription.ELEMENT_NAME)) {
            element = createComponentAssemblyArtifactDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(MonolithicImplementationDescription.ELEMENT_NAME)) {
            element = createMonolithicImplementationDescription();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(NamedImplementationArtifact.ELEMENT_NAME)) {
            element = new NamedImplementationArtifactImpl();
            element.setElementAttributes(attrs);
        }
        else if(name.equals(ImplementationArtifactDescription.ELEMENT_NAME)) {
            element = new ImplementationArtifactDescriptionImpl();
            element.setElementAttributes(attrs);
        }
        else { 
            // in the default case...
            element = super.create(name, attrs);
        }
        return element;
    }

    
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
    
    public ComponentAssemblyDescription createComponentAssemblyDescription()
    {
        return new ComponentAssemblyDescriptionImpl();
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
}