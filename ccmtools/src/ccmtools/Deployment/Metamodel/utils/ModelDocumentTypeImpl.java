package ccmtools.Deployment.Metamodel.utils;

public class ModelDocumentTypeImpl
extends ModelElementImpl implements ModelDocumentType
{
    protected String elementName;
    protected String systemID;
    protected String publicID;
    
    public ModelDocumentTypeImpl(String elementName, String publicID, String systemID) 
    {
        super();
        setElementName(elementName);
        setPublicID(publicID);
        setSystemID(systemID);
    }
    
    public ModelDocumentTypeImpl(String elementName, String systemID)
    {
        this(elementName, null, systemID);
    }

    public String getElementName()
    {
        return elementName;
    }

    public void setElementName(String elementName)
    {
        this.elementName = elementName;
    }

    public String getPublicID()
    {
        return publicID;
    }

    public void setPublicID(String publicID)
    {
        this.publicID = publicID;
    }

    public String getSystemID()
    {
        return systemID;
    }

    public void setSystemID(String systemID)
    {
        this.systemID = systemID;
    }
    
    
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE ");
        if(getElementName() != null) {
            buffer.append(getElementName());
            if(getSystemID() != null) {
                buffer.append(" SYSTEM \"").append(getSystemID());
                buffer.append("\">\n");
            }
        }
        return buffer.toString();
    }
}
