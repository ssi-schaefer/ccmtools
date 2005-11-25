package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ImplementationArtifactDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ImplementationArtifactDescription";    

    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract String getUUID();

    public abstract void setUUID(String uuid);

    public abstract List getLocation();

}