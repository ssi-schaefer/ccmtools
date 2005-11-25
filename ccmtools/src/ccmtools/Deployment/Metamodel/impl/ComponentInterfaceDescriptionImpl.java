package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElement;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;
import ccmtools.utils.Text;


public class ComponentInterfaceDescriptionImpl
    extends ModelElementImpl implements ComponentInterfaceDescription
{
    private String label;
    private String UUID;
    private String specificType;
    private List supportedType = new ArrayList();
    private List idlFile = new ArrayList();
    
    public ComponentInterfaceDescriptionImpl()
    {
        this(null, null, null);
    }
    
    public ComponentInterfaceDescriptionImpl(String label, String uuid, String type)
    {
        super();
        this.label = label;
        specificType = type;
        UUID = uuid;
        setElementName(ComponentInterfaceDescription.ELEMENT_NAME);
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
    
    public String getSpecificType()
    {
        return specificType;
    }

    public void setSpecificType(String specificType)
    {
        this.specificType = specificType;
    }

    public List getIdlFile()
    {
        return idlFile;
    }

    public List getSupportedType()
    {
        return supportedType;
    }

    
    public void addElementChild(ModelElement element)
    {
        if(element instanceof ModelElement) {
            // Default case
            // Handle XML elements which are used for sequences attributes
            ModelElement model = (ModelElement)element;
            String name = model.getElementName();
            String text = model.getElementText();
            if(name.equals("SupportedType")) {
                getSupportedType().add(text);
            }
            else if(name.equals("IdlFile")) {
                getIdlFile().add(text);
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
            setSpecificType(value);
        }
    }
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.tab(indent)).append("<");
        buffer.append(ComponentInterfaceDescription.ELEMENT_NAME);
        if(getLabel() != null) {
            buffer.append(" label=\"").append(getLabel()).append("\"");
        }
        if(getUUID() != null) {
            buffer.append(" UUID=\"").append(getUUID()).append("\"");
        }
        if(getSpecificType() != null) {
            buffer.append(" specificType=\"").append(getSpecificType()).append("\"");
        }
        buffer.append(">\n");
        
        if(getSupportedType() != null) {
            for(Iterator i = getSupportedType().iterator(); i.hasNext();) {
                String text = (String) i.next();
                buffer.append(Text.tab(indent + 1)).append("<SupportedType>");
                buffer.append(text);
                buffer.append("</SupportedType>\n");
            }
        }
        
        if(getIdlFile() != null) {
            for(Iterator i = getIdlFile().iterator(); i.hasNext();) {
                String text = (String) i.next();
                buffer.append(Text.tab(indent + 1)).append("<IdlFile>");
                buffer.append(text);
                buffer.append("</IdlFile>\n");
            }
        }
        
        buffer.append(Text.tab(indent)).append("</");
        buffer.append(ComponentInterfaceDescription.ELEMENT_NAME);
        buffer.append(">\n");
        return buffer.toString();
    }
}
