package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class NamedImplementationArtifactImpl
    extends ModelElementImpl implements NamedImplementationArtifact
{
    private String name;
    private ImplementationArtifactDescription referenceArtifact;
    
    public NamedImplementationArtifactImpl()
    {
        this(null);
    }
    
    public NamedImplementationArtifactImpl(String name)
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

    public ImplementationArtifactDescription getReferenceArtifact()
    {
        return referenceArtifact;
    }

    public void setReferencedArtifact(ImplementationArtifactDescription referenceArtifact)
    {
        this.referenceArtifact = referenceArtifact;
    }

    
    public void addElement(ModelElement element)
    {
        if(element instanceof ImplementationArtifactDescription) {
            setReferencedArtifact((ImplementationArtifactDescription)element);
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
        buffer.append(NamedImplementationArtifact.ELEMENT_NAME);
        if(getName() != null) {
            buffer.append(" name=\"").append(getName()).append("\"");
        }
        buffer.append(">\n");
        
        if(getReferenceArtifact() != null) {
            buffer.append(getReferenceArtifact().toXml(indent+1));
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(NamedImplementationArtifact.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
