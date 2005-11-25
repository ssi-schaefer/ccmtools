package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;
import ccmtools.utils.Text;


public class ImplementationArtifactDescriptionImpl
    extends ModelElementImpl implements ImplementationArtifactDescription
{
    private String label;
    private String UUID;
    private List location = new ArrayList();
    
    public ImplementationArtifactDescriptionImpl()
    {
        this(null,null);
    }
    
    public ImplementationArtifactDescriptionImpl(String label, String uuid)
    {
        super();
        this.label = label;
        UUID = uuid;
        setElementName(ImplementationArtifactDescription.ELEMENT_NAME);
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

    public List getLocation()
    {
        return location;
    }

    
    public void addElementChild(ModelElement element)
    {
        if(element instanceof ModelElement) {
            // Default case
            // Handle XML elements which are used for sequences attributes
            ModelElement model = (ModelElement) element;
            String name = model.getElementName();
            String text = model.getElementText();
            if(name.equals("Location")) {
                getLocation().add(text);
            }
        }
    }
       
    public void addElementAttribute(String name, String value)
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
        buffer.append(Text.tab(indent)).append("<");
        buffer.append(ImplementationArtifactDescription.ELEMENT_NAME);
        if(getLabel() != null) {
            buffer.append(" label=\"").append(getLabel()).append("\"");
        }
        if(getUUID() != null) {
            buffer.append(" UUID=\"").append(getUUID()).append("\"");
        }
        buffer.append(">\n");
        
        if(getLocation() != null) {
            for(Iterator i = getLocation().iterator(); i.hasNext();) {
                String text = (String) i.next();
                buffer.append(Text.tab(indent + 1)).append("<Location>");
                buffer.append(text);
                buffer.append("</Location>\n");
            }
        }
        
        buffer.append(Text.tab(indent)).append("</");
        buffer.append(ImplementationArtifactDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
