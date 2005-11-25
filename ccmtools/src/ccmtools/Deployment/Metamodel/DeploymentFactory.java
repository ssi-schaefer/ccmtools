package ccmtools.Deployment.Metamodel;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import ccmtools.Deployment.Metamodel.impl.DeploymentFactoryImpl;
import ccmtools.Deployment.Metamodel.utils.ModelElement;


public interface DeploymentFactory
{
    /** Singleton */
    DeploymentFactory instance = new DeploymentFactoryImpl();
    
    ComponentPackageDescription loadXml(File file)
        throws ParserConfigurationException, SAXException, IOException;

    void saveXml(File file, ComponentPackageDescription cpd)
        throws IOException;
    
    ModelElement create(String name, Attributes attrs);

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
