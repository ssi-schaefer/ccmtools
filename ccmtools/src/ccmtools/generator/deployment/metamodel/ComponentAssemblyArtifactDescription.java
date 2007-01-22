package ccmtools.generator.deployment.metamodel;

import java.util.List;


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