package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ComponentPackageDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentPackageDescription";
    
    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract String getUUID();

    public abstract void setUUID(String uuid);

    public abstract ComponentInterfaceDescription getRealizes();

    public abstract void setRealizes(ComponentInterfaceDescription realizes);

    public abstract List getImplementation();

}