package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;

public class ComponentPackageDescriptionImpl
    extends ModelElementImpl implements ComponentPackageDescription
{
    private String label;
    private String UUID;
    
    private ComponentInterfaceDescription realizes;
    private List implementation = new ArrayList();
    
    
    public ComponentPackageDescriptionImpl()
    {
        this(null, null);
    }
    
    public ComponentPackageDescriptionImpl(String label, String uuid)
    {
        super();
        this.label = label;
        UUID = uuid;
    }

    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getUUID()
    {
        return UUID;
    }
    
    public void setUUID(String uuid)
    {
        UUID = uuid;
    }
    
    public ComponentInterfaceDescription getRealizes()
    {
        return realizes;
    }
    
    public void setRealizes(ComponentInterfaceDescription realizes)
    {
        this.realizes = realizes;
    }
    
    public List getImplementation()
    {
        return implementation;
    }

    
    public void addElement(ModelElement element)
    {
        if(element instanceof ComponentInterfaceDescription) {
            setRealizes((ComponentInterfaceDescription)element);
        }
        else if(element instanceof PackagedComponentImplementation) {
            getImplementation().add((PackagedComponentImplementation)element);
        }
    }
       
    public void addAttribute(String name, String value)
    {
        if(name.equals("label")) {
            setLabel(value);
        }
        else if(name.equals("UUID")) {
            setUUID(value);
        }
    }
            
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tab(indent)).append("<");
        buffer.append(ComponentPackageDescription.ELEMENT_NAME);
        if(getLabel() != null) {
            buffer.append(" label=\"").append(getLabel()).append("\"");
        }
        if(getUUID() != null) {
            buffer.append(" UUID=\"").append(getUUID()).append("\"");
        }
        buffer.append(">\n");
        
        if(getRealizes() != null) {
            buffer.append(getRealizes().toXml(indent+1));
        }
        
        if(getImplementation() != null) {
            for(Iterator i = getImplementation().iterator(); i.hasNext();) {
                PackagedComponentImplementation pci = 
                    (PackagedComponentImplementation) i.next();
                buffer.append(pci.toXml(indent + 1));
            }
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(ComponentPackageDescription.ELEMENT_NAME);
        buffer.append(">\n\n");
        return buffer.toString();
    }
}
