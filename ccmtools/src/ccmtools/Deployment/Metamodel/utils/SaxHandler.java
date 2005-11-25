package ccmtools.Deployment.Metamodel.utils;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler
    extends DefaultHandler
{
    private ModelFactory factory;
    private ModelElement root;
    private ModelElement currentElement;
    private Stack parentElements;
    private String currentText;

    
    public SaxHandler(ModelFactory factory)
    {
        this.factory = factory;
    }

    public SaxHandler()
    {
        this(ModelFactory.instance);
    }
    
    public ModelElement getRootElement()
    {
        return root;
    }

    // ContentHandler methods --------------------------- 
    
    public void startDocument() 
        throws SAXException
    {
        root = null;     
        currentText = null;
        currentElement = null;
        parentElements = new Stack();
    }

    public void endDocument() 
        throws SAXException
    {
        if(!parentElements.empty()) {
            throw new SAXException("parentElements stack is not empty!");
        }
    }

    
    public void startElement(String _n, String _l, String qName, Attributes attrs) 
        throws SAXException
    {
        if(root == null) {
            // Set root model element 
            currentElement = factory.create(qName, attrs); 
            root = currentElement; 
        } 
        else {
            // add new model element
            parentElements.push(currentElement);
            currentElement = factory.create(qName, attrs); 
        }
    }

    public void endElement(String _n, String _s, String qName)
        throws SAXException
    {
        if(!parentElements.empty()) {
            currentElement.setElementText(currentText);
            ModelElement result = currentElement;
            currentElement = (ModelElement)parentElements.pop();
            currentElement.addElementChild(result);
          }
    }

    public void characters(char buf[], int offset, int len) throws SAXException
    {
          currentText = normalizeText(new String(buf, offset, len));
    }
    
    
    // ErrorHandler methods -----------------------------------
    
    public void warning (SAXParseException exception)
        throws SAXException
    {
        // TODO: Logging
        System.out.println("XML parser warning: " + exception.getMessage());
    }
    
    public void error (SAXParseException exception)
        throws SAXException
    {
        // TODO: Logging
        System.out.println("XML parser error: " + exception.getMessage());
    }
    
    public void fatalError (SAXParseException exception)
        throws SAXException
    {
        // TODO: Logging
        System.out.println("XML parser fatal error: " + exception.getMessage());
    }
    
    // Helper methods ---------------------------------
    
    private String normalizeText(String text)
    {
        if(text != null) {
            return text.trim();
        }
        else {
            return "";
        }
    }
}
