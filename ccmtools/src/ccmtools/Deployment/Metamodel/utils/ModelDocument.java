package ccmtools.Deployment.Metamodel.utils;


public interface ModelDocument
    extends ModelElement
{
    public abstract ModelDocumentType getDocType();
    public abstract void setDocType(ModelDocumentType docType);

    public abstract ModelElement getRoot();
    public abstract void setRoot(ModelElement root);
}
