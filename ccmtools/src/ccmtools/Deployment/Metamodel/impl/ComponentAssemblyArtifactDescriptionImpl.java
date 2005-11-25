package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;
import ccmtools.utils.Text;


public class ComponentAssemblyArtifactDescriptionImpl
    extends ModelElementImpl implements ComponentAssemblyArtifactDescription
{
    private String label;
    private String UUID;
    private String spectifcType;
    private List location = new ArrayList();
    
    public ComponentAssemblyArtifactDescriptionImpl()
    {
        this(null, null, null);
    }
    
    public ComponentAssemblyArtifactDescriptionImpl(String label, String uuid, String type)
    {
        super();
        this.label = label;
        spectifcType = type;
        UUID = uuid;
        setElementName(ComponentAssemblyArtifactDescription.ELEMENT_NAME);
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
        
    public String getSpectifcType()
    {
        return spectifcType;
    }

    public void setSpectifcType(String spectifcType)
    {
        this.spectifcType = spectifcType;
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
        else if(name.equals("specificType")) {
            setSpectifcType(value);
        }
    }
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(indent)).append("<");
        buffer.append(ComponentAssemblyArtifactDescription.ELEMENT_NAME);
        if(getLabel() != null) {
            buffer.append(" label=\"").append(getLabel()).append("\"");
        }
        if(getUUID() != null) {
            buffer.append(" UUID=\"").append(getUUID()).append("\"");
        }
        if(getSpectifcType() != null) {
            buffer.append(" specificType=\"").append(getSpectifcType()).append("\"");
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
        buffer.append(ComponentAssemblyArtifactDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
