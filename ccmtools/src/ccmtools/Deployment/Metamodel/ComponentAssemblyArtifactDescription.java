package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ComponentAssemblyArtifactDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentAssemblyArtifactDescription";
    
    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract String getUUID();

    public abstract void setUUID(String uuid);

    public abstract String getSpectifcType();

    public abstract void setSpectifcType(String spectifcType);

    public abstract List getLocation();

}