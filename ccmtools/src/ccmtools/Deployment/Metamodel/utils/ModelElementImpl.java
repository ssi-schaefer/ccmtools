package ccmtools.Deployment.Metamodel.utils;


public class ModelElementImpl
    implements ModelElement
{
    private static long _nextId = 0;
    private final long _id = _nextId++;
    
    public String getModelElementId()
    {
        return Long.toString(_id);
    }
}
