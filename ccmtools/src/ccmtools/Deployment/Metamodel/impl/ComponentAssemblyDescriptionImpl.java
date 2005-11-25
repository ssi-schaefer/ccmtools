package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentAssemblyDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class ComponentAssemblyDescriptionImpl
    extends ModelElementImpl implements ComponentAssemblyDescription
{
    private List assemblyArtifact = new ArrayList();
    
    public ComponentAssemblyDescriptionImpl()
    {
        super();
    }
    
    
    public List getAssemblyArtifact()
    {
        return assemblyArtifact;
    }

   
    public void addElement(ModelElement element)
    {
        if(element instanceof ComponentAssemblyArtifactDescription) {
            getAssemblyArtifact().add(
                ((ComponentAssemblyArtifactDescription)element));
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
        buffer.append(ComponentAssemblyDescription.ELEMENT_NAME);
        buffer.append(">\n");
        
        if(getAssemblyArtifact() != null) {
            for(Iterator i=getAssemblyArtifact().iterator(); i.hasNext();) {
                ComponentAssemblyArtifactDescription artifact = 
                    (ComponentAssemblyArtifactDescription)i.next();
                buffer.append(artifact.toXml(indent+1));
            }
        }
        
        buffer.append(tab(indent)).append("</");
        buffer.append(ComponentAssemblyDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }    
}
