package ccmtools.Deployment.Metamodel.utils;

import org.xml.sax.Attributes;



public class ModelElementImpl
    implements ModelElement
{
    private String name;
    private String text;
    
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
        this.name = name;
        this.text = text;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }

    
    public void addElement(ModelElement element)
    {
        // No element
    }
       
    public void addAttribute(String name, String value)
    {
        // No attribute
    }
    
    public void addAttributes(Attributes attrs)
    {
        for(int i=0; i<attrs.getLength(); i++) {
            addAttribute(attrs.getQName(i), attrs.getValue(i));
        }
    }

 
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        if(getName() != null) {
            buffer.append(tab(indent));
            buffer.append("<").append(getName()).append(">");
            buffer.append(text);
            buffer.append(tab(indent));
            buffer.append("</").append(getName()).append(">\n");
        }
        return buffer.toString();
    }    
    
    public String toXml()
    {
        return toXml(0);
    }
 
    public String toString()
    {
        return toXml();
    }

    protected String tab(int n)
    {
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<n; i++)
            buffer.append("   ");
        return buffer.toString();
    }
}
