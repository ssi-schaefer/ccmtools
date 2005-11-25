package ccmtools.Deployment.Metamodel.utils;

import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

public interface ModelElement
{
    String getElementName();
    void setElementName(String name);

    String getElementText();
    void setElementText(String text);

    List getElementChildren();
    void addElementChild(ModelElement element);
    void setElementChildren(List children);
    
    Map getElementAttributes();
    void addElementAttribute(String name, String value);
    void setElementAttributes(Attributes attrs);

    String toXml();
    String toXml(int indent);
}