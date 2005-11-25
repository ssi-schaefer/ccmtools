package ccmtools.Deployment.Metamodel.utils;


public interface ModelDocumentType
    extends ModelElement
{
    public abstract String getElementName();
    public abstract void setElementName(String elementName);

    public abstract String getPublicID();
    public abstract void setPublicID(String publicID);

    public abstract String getSystemID();
    public abstract void setSystemID(String systemID);
}
