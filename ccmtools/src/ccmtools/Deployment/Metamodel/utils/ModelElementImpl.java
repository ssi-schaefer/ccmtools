package ccmtools.Deployment.Metamodel.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import ccmtools.utils.Text;

public class ModelElementImpl
    implements ModelElement
{
    private String elementName;
    private Map elementAttributes;
    private String elementText;
    private List elementChildren;
    
    
    public ModelElementImpl()
    {
        this(null, null);
    }
    
    public ModelElementImpl(String name)
    {
        this(name, null);
    }
        
    public ModelElementImpl(String name, String text)
    {        
        super();
        this.elementName = name;
        this.elementText = text;
        elementAttributes = new HashMap();
        elementChildren = new ArrayList();
    }
    
    
    public String getElementName()
    {
        return elementName;
    }

    public void setElementName(String name)
    {
        this.elementName = name;
    }
   
    
    public String getElementText()
    {
        return elementText;
    }
    
    public void setElementText(String text)
    {
        this.elementText = text;
    }

       
    public List getElementChildren()
    {
        return elementChildren;
    }
    
    public void addElementChild(ModelElement element)
    {
        getElementChildren().add(element);
    }

    public void setElementChildren(List children)
    {
        this.elementChildren = children;
    }
    
    
    public Map getElementAttributes()
    {
        return elementAttributes;
    }
    
    public void addElementAttribute(String name, String value)
    {
        getElementAttributes().put(name, value);
    }
    
    public void setElementAttributes(Attributes attrs)
    {
        for(int i=0; i<attrs.getLength(); i++) {
            addElementAttribute(attrs.getQName(i), attrs.getValue(i));
        }
    }

    
    public String toXml()
    {
        return toXml(0);
    }
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        if(getElementName() != null) {
            buffer.append(Text.tab(indent));
            buffer.append("<").append(getElementName());
            
            for(Iterator i=getElementAttributes().keySet().iterator(); i.hasNext();) {    
                String key = (String)i.next();
                String value = (String)getElementAttributes().get(key);
                buffer.append(" ").append(key).append("=\"");
                buffer.append(value).append("\"");
            }
            
            buffer.append(">");
            if(getElementText() != null && getElementText().length() > 0) {
                buffer.append(getElementText());
            }
            else {
                buffer.append("\n");                
            }
                
            for(Iterator i=getElementChildren().iterator(); i.hasNext();) {    
                ModelElement m = (ModelElement)i.next();
                buffer.append(m.toXml(indent+1));
            }
            
            if(getElementText() == null || getElementText().length() == 0) {
                buffer.append(Text.tab(indent));
            }
            buffer.append("</").append(getElementName()).append(">\n");
        }
        return buffer.toString();
    }    
    
    public String toString()
    {
        return toXml();
    }
}
