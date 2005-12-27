package ccmtools.Deployment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ccmtools.Constants;
import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.ComponentPortDescription;
import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.ModelElement;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;


public class DeploymentToXmiMapper
{    
    private final Namespace xmlnsDeployment = 
        Namespace.getNamespace("Deployment", "http://www.omg.org/Deployment");  
    
    private final Namespace xmlnsXmi = 
        Namespace.getNamespace("xmi", "http://www.omg.org/XMI");  
    
    private final Namespace xmlnsXsi = 
        Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");  
    
        
    public void saveModel(File file, ComponentPackageDescription model) 
        throws IOException
    {
        Document doc = transformToDoc(model);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        outputter.output(doc, out);
    }

    public String modelToString(ComponentPackageDescription model)
    {
        Document doc = transformToDoc(model);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        return outputter.outputString(doc);
    }
    
    public Document transformToDoc(ComponentPackageDescription in)
    {
        Element xmi = new Element("XMI", xmlnsXmi);
        xmi.setAttribute("version", "2.1", xmlnsXmi);
        xmi.addNamespaceDeclaration(xmlnsDeployment);
        xmi.addNamespaceDeclaration(xmlnsXmi);
//        xmi.addNamespaceDeclaration(xmlnsXsi);

        Element documentation = new Element("Documentation", xmlnsXmi);
        documentation.setAttribute("exporter", Constants.PACKAGE, xmlnsXmi);
        documentation.setAttribute("exporterVersion", Constants.VERSION, xmlnsXmi);
        xmi.addContent(documentation);
        
        String name = ComponentPackageDescription.ELEMENT_NAME;  
        xmi.addContent(transformToXmlElement(name, in, xmlnsDeployment));
        Document doc = new Document(xmi);
        return doc;
    }
    
    
    public Element transformToXmlElement(String name, String value)
    {
        Element out = new Element(name);
        out.setText(value);
        return out;
    }

        
    public Element transformToXmlElement(String name, ComponentInterfaceDescription in, Namespace ns)
    {
        Element out = new Element(name,ns); 
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        out.addContent(transformToXmlElement("label", in.getLabel()));
        out.addContent(transformToXmlElement("UUID", in.getUUID()));
        out.addContent(transformToXmlElement("specificType", in.getSpecificType()));
        
        for(Iterator i = in.getSupportedType().iterator(); i.hasNext();) {
            String type = (String)i.next();
            out.addContent(transformToXmlElement("supportedType", type));
        }
        for(Iterator i = in.getIdlFile().iterator(); i.hasNext();) {
            String type = (String)i.next();
            out.addContent(transformToXmlElement("idlFile", type));
        }     
        for(Iterator i = in.getPort().iterator(); i.hasNext();) {
            ComponentPortDescription port = (ComponentPortDescription)i.next();
            out.addContent(transformToXmlElement("port", port, ns));
        }  
        return out;
    }
    
    
    public Element transformToXmlProxy(String name, ComponentInterfaceDescription in)
    {
        Element out = new Element(name);
        out.setAttribute("idref", getId(in), xmlnsXmi);
        return out;
    }
    
    
    public Element transformToXmlElement(String name, ComponentPortDescription in, Namespace ns)
    {
        Element out = new Element(name, ns);
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        
        out.addContent(transformToXmlElement("name", in.getName()));
        out.addContent(transformToXmlElement("specificType", in.getSpecificType()));
        for(Iterator i = in.getSupportedType().iterator(); i.hasNext();) {
            String type = (String)i.next();
            out.addContent(transformToXmlElement("supportedType", type));
        }
        out.addContent(transformToXmlElement("provider", Boolean.toString(in.isProvider())));
        out.addContent(transformToXmlElement("exclusiveProvider", Boolean.toString(in.isExclusiveProvider())));
        out.addContent(transformToXmlElement("exclusiveUser", Boolean.toString(in.isExclusiveUser())));
        out.addContent(transformToXmlElement("optional", Boolean.toString(in.isOptional())));
        out.addContent(transformToXmlElement("kind", in.getKind().toString()));
        return out;
    }
        

    public Element transformToXmlElement(String name, ComponentPackageDescription in, Namespace ns)
    {
        Element out = new Element(name,ns);
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        
        out.addContent(transformToXmlElement("label", in.getLabel()));
        out.addContent(transformToXmlElement("UUID", in.getUUID()));
        
        out.addContent(transformToXmlElement("realizes", in.getRealizes(), null));
        
        for(Iterator i = in.getImplementations().iterator(); i.hasNext();) {
            PackagedComponentImplementation impl = (PackagedComponentImplementation)i.next();
            Element child = transformToXmlElement("implementation", impl, null);
            out.addContent(child);
        }        
        return out;
    }
        
    
    public Element transformToXmlElement(String name, PackagedComponentImplementation in, Namespace ns)
    {
        Element out = new Element(name, ns);                                   
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        out.addContent(transformToXmlElement("name", in.getName()));
        out.addContent(transformToXmlElement("referencedImplementation", 
                                             in.getReferencedImplementation(),
                                             null));        
        return out;
    }
    
    
    public Element transformToXmlElement(String name, ComponentImplementationDescription in, Namespace ns)
    {
        Element out = new Element(name, ns);
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        
        out.addContent(transformToXmlElement("label", in.getLabel()));
        out.addContent(transformToXmlElement("UUID", in.getUUID()));
        out.addContent(transformToXmlProxy("implements", in.getImplements()));
                
        Element assembly = transformToXmlElement("assemblyImpl", in.getAssemblyImpl(), null); 
        out.addContent(assembly);
        
        Element impl = transformToXmlElement("monolithicImpl", in.getMonolithicImpl(), null); 
        out.addContent(impl);
        return out;
    }
    
    public Element transformToXmlProxy(String name, ComponentImplementationDescription in)
    {
        Element out = new Element("referencedImplementation");
        out.setAttribute("idref", getId(in), xmlnsXmi);
        return out;
    }
    
    
    public Element transformToXmlElement(String name, ComponentAssemblyArtifactDescription in, Namespace ns)
    {
        Element out = new Element(name, ns);                                   
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        
        out.addContent(transformToXmlElement("label", in.getLabel()));
        out.addContent(transformToXmlElement("UUID", in.getUUID()));
        out.addContent(transformToXmlElement("specificType", in.getSpectifcType()));
        
        for(Iterator i = in.getLocations().iterator(); i.hasNext();) {
            String location = (String)i.next();
            out.addContent(transformToXmlElement("location", location));
        }
        return out;
    }
    
    
    public Element transformToXmlElement(String name, MonolithicImplementationDescription in, Namespace ns)
    {
        Element out = new Element(name, ns);                                   
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        for(Iterator i = in.getPrimaryArtifacts().iterator(); i.hasNext();) {
            NamedImplementationArtifact artifact = (NamedImplementationArtifact)i.next();
            Element child = transformToXml("primaryArtifact", artifact, null);
            out.addContent(child);
        }
        return out;
    }
    
    
    public Element transformToXml(String name, NamedImplementationArtifact in, Namespace ns)
    {
        Element out = new Element(name, ns); 
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        out.addContent(transformToXmlElement("name", in.getName()));

        Element child = transformToXmlElement("referencedArtifact", 
                                            in.getReferenceArtifact(),
                                            null); 
        out.addContent(child);
        return out;
    }
    
    
    public Element transformToXmlElement(String name, ImplementationArtifactDescription in, Namespace ns)
    {
        Element out = new Element(name, ns); 
        if(in == null) return out;
        
        out.setAttribute("id", getId(in), xmlnsXmi);
        out.addContent(transformToXmlElement("label", in.getLabel()));
        out.addContent(transformToXmlElement("UUID", in.getUUID()));
        for(Iterator i = in.getLocations().iterator(); i.hasNext();) {
            String location = (String)i.next();
            out.addContent(transformToXmlElement("location", location));
        }
        return out;
    }
    
    public Element transformToXmlProxy(String name, ImplementationArtifactDescription in)
    {
        Element out = new Element(name);
        out.setAttribute("idref", getId(in), xmlnsXmi);
        return out;
    }
    
    
    // Helper methods -----------------------------------------------
 
    private String getId(ModelElement in)
    {
        return "id" + in.hashCode();
    }
}
