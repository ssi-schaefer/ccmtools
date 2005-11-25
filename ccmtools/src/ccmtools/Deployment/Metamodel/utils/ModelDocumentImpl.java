package ccmtools.Deployment.Metamodel.utils;


public class ModelDocumentImpl
    extends ModelElementImpl 
    implements ModelDocument
{
    protected ModelDocumentType dtd;
    protected ModelElement root;
    
    public ModelDocumentImpl(ModelElement root, ModelDocumentType dtd)
    {
        super();
        setRoot(root);
        setDocType(dtd);
    }
    
    public ModelDocumentImpl(ModelElement root)
    {
        this(root,null);
    }

    public ModelDocumentType getDocType()
    {
        return dtd;
    }

    public void setDocType(ModelDocumentType dtd)
    {
        this.dtd = dtd;
    }

    public ModelElement getRoot()
    {
        return root;
    }

    public void setRoot(ModelElement root)
    {
        this.root = root;
    }
           
    public String toXml(int indent)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        if(getDocType() != null) {
            buffer.append(getDocType().toXml());
        }
        buffer.append("\n");
        buffer.append(getRoot());
        return buffer.toString();
    }
}
