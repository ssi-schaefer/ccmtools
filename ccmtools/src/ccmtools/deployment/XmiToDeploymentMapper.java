package ccmtools.deployment;

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

import ccmtools.deployment.metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.deployment.metamodel.ComponentPackageDescription;
import ccmtools.deployment.metamodel.ComponentPortDescription;
import ccmtools.deployment.metamodel.DeploymentFactory;
import ccmtools.deployment.metamodel.ImplementationArtifactDescription;
import ccmtools.deployment.metamodel.ModelElement;
import ccmtools.deployment.metamodel.MonolithicImplementationDescription;
import ccmtools.deployment.metamodel.NamedImplementationArtifact;
import ccmtools.deployment.metamodel.PackagedComponentImplementation;
import ccmtools.deployment.metamodel.impl.CCMComponentPortKind;



public class XmiToDeploymentMapper
{
    private DeploymentFactory factory = DeploymentFactory.instance;

    private final Namespace xmlnsDeployment = 
        Namespace.getNamespace("Deployment", "http://www.omg.org/Deployment");

    private final Namespace xmlnsXmi = 
        Namespace.getNamespace("xmi", "http://www.omg.org/XMI");  
    

    /** Map to hold all objects by their xmi.id values */
    private Map ObjectMap = new HashMap();

    
    public ComponentPackageDescription loadModel(File file)
        throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(file);
        Element xmi = doc.getRootElement();
        ComponentPackageDescription model = null;
        if(xmi != null) {
            String name = ComponentPackageDescription.ELEMENT_NAME;
            Element root = xmi.getChild(name, xmlnsDeployment);
            if(root != null) {
                model = transformToComponentPackageDescription(root);
            }             
        }
        return model;
    }

    
    public ComponentPackageDescription transformToComponentPackageDescription(Element in)
    {
        ComponentPackageDescription out = factory.createComponentPackageDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(eName.equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(eName.equals("realizes")) {
                out.setRealizes(transformToComponentInterfaceDescription(element));
            }
            else if(eName.equals("implementation")) {
                out.getImplementations().add(transformToPackagedComponentImplementation(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public ComponentInterfaceDescription transformToComponentInterfaceDescription(Element in)
    {
        ComponentInterfaceDescription out = factory.createComponentInterfaceDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(eName.equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(eName.equals("specificType")) {
                out.setSpecificType(element.getTextNormalize());
            }
            else if(eName.equals("supportedType")) {
                out.getSupportedType().add(element.getTextNormalize());
            }
            else if(eName.equals("idlFile")) {
                out.getIdlFile().add(element.getText());
            }
            else if(eName.equals("port")) {
                out.getPort().add(transformToComponentPortDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public ComponentPortDescription transformToComponentPortDescription(Element in)
    {
        ComponentPortDescription out = factory.createComponentPortDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("name")) {
                out.setName(element.getTextNormalize());
            }
            else if(eName.equals("specificType")) {
                out.setSpecificType(element.getTextNormalize());
            }
            else if(eName.equals("supportedType")) {
                out.getSupportedType().add(element.getTextNormalize());
            }
            else if(eName.equals("provider")) {
                Boolean value = new Boolean(element.getTextNormalize());                
                out.setProvider(value.booleanValue());
            }
            else if(eName.equals("exclusiveProvider")) {
                Boolean value = new Boolean(element.getTextNormalize());   
                out.setExclusiveProvider(value.booleanValue());
            }
            else if(eName.equals("exclusiveUser")) {
                Boolean value = new Boolean(element.getTextNormalize());  
                out.setExclusiveUser(value.booleanValue());
            }
            else if(eName.equals("optional")) {
                Boolean value = new Boolean(element.getTextNormalize());  
                out.setOptional(value.booleanValue());
            }
            else if(eName.equals("kind")) {
                out.setKind(transformToCCMComponentPortKind(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }
    
    public CCMComponentPortKind transformToCCMComponentPortKind(Element in)
    {
        String value = in.getTextNormalize();        
        if(value.equals("Facet")) {
            return CCMComponentPortKind.Facet;
        }
        else if(value.equals("SimplexReceptacle")) {
            return CCMComponentPortKind.SimplexReceptacle;
        }
        else if(value.equals("MultiplexReceptacle")) {
            return CCMComponentPortKind.MultiplexReceptacle;
        }
        else if(value.equals("EventEmitter")) {
            return CCMComponentPortKind.EventEmitter;
        }
        else if(value.equals("EventPublisher")) {
            return CCMComponentPortKind.EventPublisher;
        }
        else if(value.equals("EventConsumer")) {
            return CCMComponentPortKind.EventConsumer;
        }
        else {
            // invalid enumeration value
            return null;
        }
    }
    
    public PackagedComponentImplementation 
        transformToPackagedComponentImplementation(Element in)
    {
        PackagedComponentImplementation out = factory.createPackagedComponentImplementation();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("name")) {
                out.setName(element.getTextNormalize());
            }
            else if(eName.equals("referencedImplementation")) {
                out.setReferencedImplementation(transformToComponentImplementationDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public ComponentImplementationDescription transformToComponentImplementationDescription(Element in)
    {
        ComponentImplementationDescription out = factory.createComponentImplementationDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(eName.equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(eName.equals("monolithicImpl")) {
                out.setMonolithicImpl(transformToMonolithicImplementationDescription(element));
            }
            else if(eName.equals("assemblyImpl")) {
                out.setAssemblyImpl(transformToComponentAssemblyArtifactDescription(element));
            }
            else if(eName.equals("implements")) {
                String idref = element.getAttributeValue("idref", xmlnsXmi);
                ComponentInterfaceDescription realizes = 
                    (ComponentInterfaceDescription) getObjectFromMap(idref);
                out.setImplements(realizes);
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public ComponentAssemblyArtifactDescription transformToComponentAssemblyArtifactDescription(Element in)
    {
        ComponentAssemblyArtifactDescription out = factory.createComponentAssemblyArtifactDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(eName.equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(eName.equals("specificType")) {
                out.setSpecifcType(element.getTextNormalize());
            }
            else if(eName.equals("location")) {
                out.getLocations().add(element.getTextNormalize());
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public MonolithicImplementationDescription transformToMonolithicImplementationDescription(Element in)
    {
        MonolithicImplementationDescription out = factory.createMonolithicImplementationDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("primaryArtifact")) {
                out.getPrimaryArtifacts().add(transformToNamedImplementationArtifact(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public NamedImplementationArtifact transformToNamedImplementationArtifact(Element in)
    {
        NamedImplementationArtifact out = factory.createNamedImplementationArtifact();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("name")) {
                out.setName(element.getTextNormalize());
            }
            else if(eName.equals("referencedArtifact")) {
                out.setReferencedArtifact(transformToImplementationArtifactDescription(element));
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    public ImplementationArtifactDescription transformToImplementationArtifactDescription(Element in)
    {
        ImplementationArtifactDescription out = factory.createImplementationArtifactDescription();
        for(Iterator i = in.getChildren().iterator(); i.hasNext();) {
            Element element = (Element) i.next();
            String eName = element.getName();
            if(eName.equals("label")) {
                out.setLabel(element.getTextNormalize());
            }
            else if(eName.equals("UUID")) {
                out.setUUID(element.getTextNormalize());
            }
            else if(eName.equals("location")) {
                out.getLocations().add(element.getText());
            }
            else {
                // Ignore all other child elements
            }
        }
        addObjectToMap(in.getAttributeValue("id",xmlnsXmi), out);
        return out;
    }

    // Helper methods -----------------------------------------------

    private ModelElement getObjectFromMap(String refid)
    {
        return (ModelElement) ObjectMap.get(refid);
    }

    private void addObjectToMap(String id, ModelElement obj)
    {
        ObjectMap.put(id, obj);
    }
}
