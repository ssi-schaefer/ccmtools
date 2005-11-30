package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ImplementationArtifactDescription
    extends ModelElement
{
    String ELEMENT_NAME = "ImplementationArtifactDescription";    

    String getLabel();
    void setLabel(String label);

    String getUUID();
    void setUUID(String uuid);

    List getLocations();
}