package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ComponentInterfaceDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentInterfaceDescription";
    
    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract String getUUID();

    public abstract void setUUID(String uuid);

    public abstract String getSpecificType();

    public abstract void setSpecificType(String specificType);

    public abstract List getIdlFile();

    public abstract List getSupportedType();

}