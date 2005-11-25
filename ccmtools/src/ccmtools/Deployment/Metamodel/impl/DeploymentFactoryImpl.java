package ccmtools.Deployment.Metamodel.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
import ccmtools.Deployment.Metamodel.utils.ModelDocument;
import ccmtools.Deployment.Metamodel.utils.ModelDocumentImpl;
import ccmtools.Deployment.Metamodel.utils.ModelDocumentType;
import ccmtools.Deployment.Metamodel.utils.ModelDocumentTypeImpl;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;

public class DeploymentFactoryImpl
    implements DeploymentFactory
{
    /**
     * Load a model from an XML file.
     */
    public ComponentPackageDescription loadXml(File file)
        throws ParserConfigurationException, SAXException, IOException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        SaxHandlerImpl handler = new SaxHandlerImpl();
        saxParser.parse(file, handler);
        return handler.getRootElement();
    }

    
    /**
     * Save a model to an XML file. 
     */
    public void saveXml(File file ,ComponentPackageDescription cpd)
        throws IOException
    {
        ModelDocumentType docType =  
            new ModelDocumentTypeImpl(ComponentPackageDescription.ELEMENT_NAME, 
                                      "deployment.dtd");
        ModelDocument doc = new ModelDocumentImpl(cpd, docType);
        String xml = doc.toXml();
        
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(xml);
        out.close();
    }
    
        
    public ModelElement create(String name, Attributes attrs)
    {
        ModelElement element = null;

        if(name.equals(ComponentPackageDescription.ELEMENT_NAME)) {
            element = createComponentPackageDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(ComponentInterfaceDescription.ELEMENT_NAME)) {
            element = createComponentInterfaceDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(PackagedComponentImplementation.ELEMENT_NAME)) {
            element = createPackagedComponentImplementation();
            element.addAttributes(attrs);
        }
        else if(name.equals(ComponentImplementationDescription.ELEMENT_NAME)) {
            element = createComponentImplementationDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(ComponentAssemblyDescription.ELEMENT_NAME)) {
            element = createComponentAssemblyDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(ComponentAssemblyArtifactDescription.ELEMENT_NAME)) {
            element = createComponentAssemblyArtifactDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(MonolithicImplementationDescription.ELEMENT_NAME)) {
            element = createMonolithicImplementationDescription();
            element.addAttributes(attrs);
        }
        else if(name.equals(NamedImplementationArtifact.ELEMENT_NAME)) {
            element = new NamedImplementationArtifactImpl();
            element.addAttributes(attrs);
        }
        else if(name.equals(ImplementationArtifactDescription.ELEMENT_NAME)) {
            element = new ImplementationArtifactDescriptionImpl();
            element.addAttributes(attrs);
        }
        else { // in the default case...
            element = createModelElement();
            element.setName(name);
            element.addAttributes(attrs);
        }
        return element;
    }

    
    
    public ModelElement createModelElement()
    {
        return new ModelElementImpl();
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