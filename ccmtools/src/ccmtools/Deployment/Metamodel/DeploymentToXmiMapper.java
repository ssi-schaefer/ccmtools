package ccmtools.Deployment.Metamodel;

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


public class DeploymentToXmiMapper
{    
    private final Namespace xmlns = 
        Namespace.getNamespace("Deployment", "http://www.omg.org/Deployment");  
    
    public void saveModel(File file, ComponentPackageDescription model) 
        throws IOException
    {
        Document doc = transformToDoc(model);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        outputter.output(doc, out);
    }

    public Document transformToDoc(ComponentPackageDescription in)
    {
        Element xmiExporter = new Element("XMI.exporter");
        xmiExporter.setText(Constants.PACKAGE);
        Element xmiExporterVersion = new Element("xmi.exporterVersion");
        xmiExporterVersion.setText(Constants.VERSION);

        Element xmiDocumentation = new Element("XMI.documentation");
        xmiDocumentation.addContent(xmiExporter);
        xmiDocumentation.addContent(xmiExporterVersion);
        
        Element xmiMetamodel = new Element("XMI.metamodel");
        xmiMetamodel.setAttribute("xmi.name", "Deployment");
        xmiMetamodel.setAttribute("xmi.version", "0.0");        
        
        Element xmiHeader = new Element("XMI.header");
        xmiHeader.addContent(xmiDocumentation);
        xmiHeader.addContent(xmiMetamodel);
                
        Element xmiContent = new Element("XMI.content");        
        xmiContent.addContent(transformToXmi(in));
        
        Element xmi = new Element("XMI");
        xmi.setAttribute("xmi.version", "1.2");
        xmi.setAttribute("timestamp","");
        xmi.addNamespaceDeclaration(xmlns);
        xmi.addContent(xmiHeader);
        xmi.addContent(xmiContent);

//        DocType type = new DocType(ComponentPackageDescription.ELEMENT_NAME,
//                                   "deployment.dtd");        
        Document doc = new Document(xmi);
        return doc;
    }
    
    
    public Element transformToXmi(ComponentPackageDescription in)
    {
        Element out = new Element(ComponentPackageDescription.ELEMENT_NAME,xmlns);                                  
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"label", in.getLabel());
        addAttributeElement(out,"UUID", in.getUUID());
        Element iface = transformToXml(in.getRealizes());
        out.addContent(iface);        
        for(Iterator i = in.getImplementations().iterator(); i.hasNext();) {
            PackagedComponentImplementation impl = (PackagedComponentImplementation)i.next();
            Element child = transformToXml(impl);
            out.addContent(child);
        }        
        return out;
    }
    
    public Element transformToXml(ComponentInterfaceDescription in)
    {
        Element out = new Element(ComponentInterfaceDescription.ELEMENT_NAME,xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"label", in.getLabel());
        addAttributeElement(out,"UUID", in.getUUID());
        addAttributeElement(out,"specificType", in.getSpecificType());
        for(Iterator i = in.getSupportedTypes().iterator(); i.hasNext();) {
            String type = (String)i.next();
            addAttributeElement(out,"SupportedType", type);
        }
        for(Iterator i = in.getIdlFiles().iterator(); i.hasNext();) {
            String type = (String)i.next();
            addAttributeElement(out, "IdlFile", type);
        }        
        return out;
    }
    
    public Element transformToXml(PackagedComponentImplementation in)
    {
        Element out = new Element(PackagedComponentImplementation.ELEMENT_NAME, xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"name", in.getName());
        Element child = transformToXml(in.getReferencedImplementation()); 
        out.addContent(child);        
        return out;
    }
    
    public Element transformToXml(ComponentImplementationDescription in)
    {
        Element out = new Element(ComponentImplementationDescription.ELEMENT_NAME, xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"label", in.getLabel());
        addAttributeElement(out,"UUID", in.getUUID());
        
        Element Reference = new Element("implements");
        Element Implements = new Element(ComponentInterfaceDescription.ELEMENT_NAME, xmlns);                                    
        String idref = in.getImplements().getModelElementId();
        Implements.setAttribute("xmi.idref", idref);
        Reference.addContent(Implements);
        out.addContent(Reference);
        
        Element assembly = transformToXml(in.getAssemblyImpl()); 
        out.addContent(assembly);
        
        Element impl = transformToXml(in.getMonolithicImpl()); 
        out.addContent(impl);
        return out;
    }
        
    public Element transformToXml(ComponentAssemblyDescription in)
    {
        Element out = new Element(ComponentAssemblyDescription.ELEMENT_NAME, xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        for(Iterator i = in.getAssemblyArtifacts().iterator(); i.hasNext();) {
            ComponentAssemblyArtifactDescription artifact = 
                (ComponentAssemblyArtifactDescription)i.next();
            Element child = transformToXml(artifact);
            out.addContent(child);
        }
        return out;
    }
    
    public Element transformToXml(ComponentAssemblyArtifactDescription in)
    {
        Element out = new Element(ComponentAssemblyArtifactDescription.ELEMENT_NAME, xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"label", in.getLabel());
        addAttributeElement(out,"UUID", in.getUUID());
        addAttributeElement(out,"SpecificType", in.getSpectifcType());
        for(Iterator i = in.getLocations().iterator(); i.hasNext();) {
            String location = (String)i.next();
            addAttributeElement(out, "Location", location);
        }
        return out;
    }
    
    public Element transformToXml(MonolithicImplementationDescription in)
    {
        Element out = new Element(MonolithicImplementationDescription.ELEMENT_NAME, xmlns);                                   
        out.setAttribute("xmi.id", in.getModelElementId());
        for(Iterator i = in.getPrimaryArtifacts().iterator(); i.hasNext();) {
            NamedImplementationArtifact artifact = (NamedImplementationArtifact)i.next();
            Element child = transformToXml(artifact);
            out.addContent(child);
        }
        return out;
    }
    
    public Element transformToXml(NamedImplementationArtifact in)
    {
        Element out = new Element(NamedImplementationArtifact.ELEMENT_NAME, xmlns); 
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"name", in.getName());
        Element child = transformToXml(in.getReferenceArtifact()); 
        out.addContent(child);
        return out;
    }
    
    public Element transformToXml(ImplementationArtifactDescription in)
    {
        Element out = new Element(ImplementationArtifactDescription.ELEMENT_NAME, xmlns); 
        out.setAttribute("xmi.id", in.getModelElementId());
        addAttributeElement(out,"label", in.getLabel());
        addAttributeElement(out,"UUID", in.getUUID());        
        for(Iterator i = in.getLocations().iterator(); i.hasNext();) {
            String location = (String)i.next();
            addAttributeElement(out, "Location", location);
        }
        return out;
    }

    // Helper methods -----------------------------------------------
    
    private void addAttributeElement(Element e, String name, String value)
    {
        if(value != null) {            
            Element child = new Element(name);
            child.setText(value);
            e.addContent(child);
        }
    }
}
