package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ComponentAssemblyArtifactDescription
    extends ModelElement
{
    String ELEMENT_NAME = "ComponentAssemblyArtifactDescription";
    
    String getLabel();
    void setLabel(String label);

    String getUUID();
    void setUUID(String uuid);

    String getSpectifcType();
    void setSpecifcType(String spectifcType);

    public abstract List getLocations();
}