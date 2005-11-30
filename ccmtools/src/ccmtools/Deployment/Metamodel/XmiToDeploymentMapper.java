package ccmtools.Deployment.Metamodel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import ccmtools.Deployment.Metamodel.utils.ModelElement;


public class XmiToDeploymentMapper
{
    private DeploymentFactory factory = DeploymentFactory.instance;
    private final Namespace xmlns = 
        Namespace.getNamespace("Deployment", "http://www.omg.org/Deployment");  
    
    /** Map to hold all objects by their xmi.id values */
    private Map ObjectMap = new HashMap();
        
    public ComponentPackageDescription loadModel(File file) 
        throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(file);
        Element xmi = doc.getRootElement();
        if(xmi == null) return null;
        Element xmiContent = xmi.getChild("XMI.content");
        if(xmi == null) return null;
        Element root = xmiContent.getChild(ComponentPackageDescription.ELEMENT_NAME, xmlns);
        if(root == null) return null;        
        ComponentPackageDescription model = transformToComponentPackageDescription(root);
        return model;
    }
    
    public ComponentPackageDescription 
        transformToComponentPackageDescription(Element in)
    {
        ComponentPackageDescription out = factory.createComponentPackageDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(element.getName().equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(element.getName().equals(ComponentInterfaceDescription.ELEMENT_NAME)) {
                out.setRealizes(transformToComponentInterfaceDescription(element));
            }
            else if(element.getName().equals(PackagedComponentImplementation.ELEMENT_NAME)) {
                out.getImplementations().add(transformToPackagedComponentImplementation(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
   
    public ComponentInterfaceDescription 
        transformToComponentInterfaceDescription(Element in)
    {
        ComponentInterfaceDescription out = factory.createComponentInterfaceDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(element.getName().equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(element.getName().equals("specificType")) {
                out.setSpecificType(element.getTextNormalize());
            }
            else if(element.getName().equals("SupportedType")) {
                out.getSupportedTypes().add(element.getTextNormalize());
            }
            else if(element.getName().equals("IdlFile")) {
                out.getIdlFiles().add(element.getText());
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
       
    public PackagedComponentImplementation 
        transformToPackagedComponentImplementation(Element in)
    {
        PackagedComponentImplementation out = factory.createPackagedComponentImplementation();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("name")) {
                out.setName(element.getTextNormalize());
            }
            else if(element.getName().equals(ComponentImplementationDescription.ELEMENT_NAME)) {
                out.setReferencedImplementation(transformToComponentImplementationDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public ComponentImplementationDescription 
        transformToComponentImplementationDescription(Element in)
    {
        ComponentImplementationDescription out = factory.createComponentImplementationDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(element.getName().equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(element.getName().equals(ComponentAssemblyDescription.ELEMENT_NAME)) {
                out.setAssemblyImpl(transformToComponentAssemblyDescription(element));
            }
            else if(element.getName().equals(MonolithicImplementationDescription.ELEMENT_NAME)) {
                out.setMonolithicImpl(transformToMonolithicImplementationDescription(element));
            }            
            else if(element.getName().equals("implements")) {
                Element ref = element.getChild(ComponentInterfaceDescription.ELEMENT_NAME, xmlns);
                if(ref != null) {
                    String idref = ref.getAttributeValue("xmi.idref");
                    ComponentInterfaceDescription realizes = 
                        (ComponentInterfaceDescription)getObjectFromMap(idref);
                    out.setImplements(realizes);
                }
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public ComponentAssemblyDescription 
        transformToComponentAssemblyDescription(Element in)
    {
        ComponentAssemblyDescription out = factory.createComponentAssemblyDescription();
        
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals(ComponentAssemblyArtifactDescription.ELEMENT_NAME)) {
                out.getAssemblyArtifacts().add(transformToComponentAssemblyArtifactDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public ComponentAssemblyArtifactDescription 
        transformToComponentAssemblyArtifactDescription(Element in)
    {
        ComponentAssemblyArtifactDescription out = factory.createComponentAssemblyArtifactDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(element.getName().equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(element.getName().equals("SpecificType")) {
                out.setSpecifcType(element.getTextNormalize());
            }
            else if(element.getName().equals("Location")) {
                out.getLocations().add(element.getTextNormalize());
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public MonolithicImplementationDescription 
        transformToMonolithicImplementationDescription(Element in)
    {
        MonolithicImplementationDescription out = factory.createMonolithicImplementationDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals(NamedImplementationArtifact.ELEMENT_NAME)) {
                out.getPrimaryArtifacts().add(transformToNamedImplementationArtifact(element));
            }
            else {
                // Ignore all other child elements
            }
        }        
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public NamedImplementationArtifact 
        transformToNamedImplementationArtifact(Element in)
    {
        NamedImplementationArtifact out = factory.createNamedImplementationArtifact();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("name")) {
                out.setName(element.getTextNormalize());
            }
            else if(element.getName().equals(ImplementationArtifactDescription.ELEMENT_NAME)) {
                out.setReferencedArtifact(transformToImplementationArtifactDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }
    
    public ImplementationArtifactDescription 
        transformToImplementationArtifactDescription(Element in)
    {
        ImplementationArtifactDescription out = factory.createImplementationArtifactDescription();
        for(Iterator i=in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element)i.next();
            if(element.getName().equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(element.getName().equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(element.getName().equals("Location")) {
                out.getLocations().add(element.getText());
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("xmi.id"), out);
        return out;
    }

    
    // Helper methods -----------------------------------------------
    
    private ModelElement getObjectFromMap(String refid)
    {
        return (ModelElement)ObjectMap.get(refid);
    }
    
    private void addObjectToMap(String id, ModelElement obj)
    {
        ObjectMap.put(id, obj);
    }
}
