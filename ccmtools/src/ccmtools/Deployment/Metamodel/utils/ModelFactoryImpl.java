package ccmtools.Deployment.Metamodel.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;

import ccmtools.CcmtoolsException;


public class ModelFactoryImpl
    implements ModelFactory
{
    public ModelElement createModelElement(String name)
    {
        return new ModelElementImpl(name);
    }

    public ModelElement create(String name, Attributes attrs)
    {
        ModelElement element = createModelElement(name);
        element.setElementAttributes(attrs);
        return element;
    }
    
    
    /**
     * Load a model from an XML file.
     */
    public ModelElement loadXml(File file)
        throws CcmtoolsException
    {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
//            factory.setValidating(true);
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler((ModelFactory) this);
            saxParser.parse(file, handler);
            return handler.getRootElement();
        }
        catch(Exception e) {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    /**
     * Save a model to an XML file. 
     */
    public void saveXml(File file , ModelElement root, ModelDocumentType dtd)
        throws CcmtoolsException
    {       
        try {
            ModelDocument doc = new ModelDocumentImpl(root, dtd);
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(doc.toXml());
            out.close();
        }
        catch(Exception e) {
            throw new CcmtoolsException(e.getMessage());
        }
    }
    
    public void saveXml(File file , ModelElement root)
        throws CcmtoolsException
    {        
        saveXml(file , root, null);
    }
}
