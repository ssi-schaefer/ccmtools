package ccmtools.Deployment.Metamodel.impl;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ccmtools.Deployment.Metamodel.ComponentPackageDescription;
import ccmtools.Deployment.Metamodel.DeploymentFactory;
import ccmtools.Deployment.Metamodel.utils.ModelElement;


public class SaxHandlerImpl
    extends DefaultHandler
{
    private ComponentPackageDescription root;
    private ModelElement currentElement;
    private Stack parentElements;
    private String currentText;
    
    public ComponentPackageDescription getRootElement()
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
            currentElement = DeploymentFactory.instance.create(qName, attrs); 
            root = (ComponentPackageDescription)currentElement; 
        } 
        else {
            // add new model element
            parentElements.push(currentElement);
            currentElement = DeploymentFactory.instance.create(qName, attrs); 
        }
    }

    public void endElement(String _n, String _s, String qName)
        throws SAXException
    {
        if(!parentElements.empty()) {
            currentElement.setText(currentText);
            ModelElement result = currentElement;
            currentElement = (ModelElement)parentElements.pop();
            currentElement.addElement(result);
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
        // TODO: Error handling
        System.out.println("warning(): " + exception.getMessage());
    }
    
    public void error (SAXParseException exception)
        throws SAXException
    {
        // TODO: Error handling
        System.out.println("error(): " + exception.getMessage());
    }
    
    public void fatalError (SAXParseException exception)
        throws SAXException
    {
        // TODO: Error handling
        System.out.println("fatalError(): " + exception.getMessage());
    }
    
    // Helper methods ---------------------------------
    private String normalizeText(String text)
    {
        // TODO: trim string
        return text;
    }
}
