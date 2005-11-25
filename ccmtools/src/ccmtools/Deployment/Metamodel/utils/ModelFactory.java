package ccmtools.Deployment.Metamodel.utils;

import java.io.File;

import org.xml.sax.Attributes;

import ccmtools.CcmtoolsException;


public interface ModelFactory
{
    /** Singleton */
    ModelFactory instance = new ModelFactoryImpl();
    
    ModelElement loadXml(File file)
        throws CcmtoolsException;

    void saveXml(File file, ModelElement root, ModelDocumentType dtd)
        throws CcmtoolsException;
    
    void saveXml(File file, ModelElement root)
        throws CcmtoolsException;

    ModelElement createModelElement(String name);
    ModelElement create(String name, Attributes attrs);    
}
