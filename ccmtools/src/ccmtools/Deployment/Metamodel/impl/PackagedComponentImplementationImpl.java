package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class PackagedComponentImplementationImpl
    extends ModelElementImpl implements PackagedComponentImplementation
{
    private String name;
    private ComponentImplementationDescription referencedImplementation; 
    
    
    public PackagedComponentImplementationImpl()
    {
        this(null);
    }
        
    public PackagedComponentImplementationImpl(String name)
    {
        super();
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }

    public ComponentImplementationDescription getReferencedImplementation()
    {
        return referencedImplementation;
    }

    public void setReferencedImplementation(ComponentImplementationDescription referencedImplementation)
    {
        this.referencedImplementation = referencedImplementation;
    }

    
    public void addElement(ModelElement element)
    {
        if(element instanceof ComponentImplementationDescription) {
            setReferencedImplementation((ComponentImplementationDescription)element);
        }
    }
       
    public void addAttribute(String name, String value)
    {
        if(name.equals("name")) {
            setName(value);
        }
    }
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tab(indent)).append("<");
        buffer.append(PackagedComponentImplementation.ELEMENT_NAME);
        if(getName() != null) {
            buffer.append(" name=\"").append(getName()).append("\"");
        }
        buffer.append(">\n");
        
        if(getReferencedImplementation() != null) {
            buffer.append(getReferencedImplementation().toXml(indent+1));
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(PackagedComponentImplementation.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }    
}
