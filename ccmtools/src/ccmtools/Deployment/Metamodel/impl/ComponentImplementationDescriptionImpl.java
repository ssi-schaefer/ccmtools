package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ComponentAssemblyDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class ComponentImplementationDescriptionImpl
    extends ModelElementImpl implements ComponentImplementationDescription
{
    private String label;
    private String UUID;
    private ComponentAssemblyDescription assemblyImpl;
    private MonolithicImplementationDescription monolithicImpl;
    
    public ComponentImplementationDescriptionImpl()
    {
        this(null,null);
    }
    
    public ComponentImplementationDescriptionImpl(String label, String uuid)
    {
        super();
        this.label = label;
        UUID = uuid;
    }

    public String getUUID()
    {
        return UUID;
    }
    
    public void setUUID(String uuid)
    {
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
    
    public ComponentAssemblyDescription getAssemblyImpl()
    {
        return assemblyImpl;
    }

    public void setAssemblyImpl(ComponentAssemblyDescription assemblyImpl)
    {
        this.assemblyImpl = assemblyImpl;
    }

    public MonolithicImplementationDescription getMonolithicImpl()
    {
        return monolithicImpl;
    }

    public void setMonolithicImpl(MonolithicImplementationDescription monolithicImpl)
    {
        this.monolithicImpl = monolithicImpl;
    }

    
    public void addElement(ModelElement element)
    {
        if(element instanceof ComponentAssemblyDescription) {
            setAssemblyImpl((ComponentAssemblyDescription)element);
        }
        else if(element instanceof MonolithicImplementationDescription) {
            setMonolithicImpl((MonolithicImplementationDescription)element);
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
        buffer.append(ComponentImplementationDescription.ELEMENT_NAME);
        if(getLabel() != null)
            buffer.append(" label=\"").append(getLabel()).append("\"");
        if(getUUID() != null)
        buffer.append(" UUID=\"").append(getUUID()).append("\"");
        buffer.append(">\n");
        
        if(getAssemblyImpl() != null) {
            buffer.append(getAssemblyImpl().toXml(indent+1));
        }
        if(getMonolithicImpl() != null) {
            buffer.append(getMonolithicImpl().toXml(indent+1));
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(ComponentImplementationDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
