package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class MonolithicImplementationDescriptionImpl
    extends ModelElementImpl implements MonolithicImplementationDescription
{
    private List primaryArtifact = new ArrayList();
        
    
    public MonolithicImplementationDescriptionImpl()
    {
        super();
    }
    
    public List getPrimaryArtifact()
    {
        return primaryArtifact;
    }

    
    public void addElement(ModelElement element)
    {
        if(element instanceof NamedImplementationArtifact) {
            getPrimaryArtifact().add((NamedImplementationArtifact)element);
        }
    }
       
    public void addAttribute(String name, String value)
    {
        // No Attributes
    }
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tab(indent)).append("<");
        buffer.append(MonolithicImplementationDescription.ELEMENT_NAME);
        buffer.append(">\n");
        
        if(getPrimaryArtifact() != null) {
            for(Iterator i = getPrimaryArtifact().iterator(); i.hasNext();) {
                NamedImplementationArtifact artifact = 
                    (NamedImplementationArtifact) i.next();
                buffer.append(artifact.toXml(indent + 1));
            }
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(MonolithicImplementationDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
