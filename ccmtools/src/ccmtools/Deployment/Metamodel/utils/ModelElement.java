package ccmtools.Deployment.Metamodel.utils;

import org.xml.sax.Attributes;

public interface ModelElement
{
    public abstract String getName();
    public abstract void setName(String name);

    public abstract String getText();
    public abstract void setText(String text);
    
    public abstract void addElement(ModelElement element);

    public abstract void addAttribute(String name, String value);
    public abstract void addAttributes(Attributes attrs);

    public abstract String toXml();
    public abstract String toXml(int indent);
}