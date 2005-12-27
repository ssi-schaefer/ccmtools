package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;


class ImplementationArtifactDescriptionImpl
    extends ModelElementImpl implements ImplementationArtifactDescription
{
    private String label;
    private String UUID;
    private List location = new ArrayList();
    
    public ImplementationArtifactDescriptionImpl()
    {
        this(null,null);
    }
    
    public ImplementationArtifactDescriptionImpl(String label, String uuid)
    {
        super();
        this.label = label;
        UUID = uuid;
    }

    
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getUUID()
    {
        return UUID;
    }

    public void setUUID(String uuid)
    {
        UUID = uuid;
    }

    public List getLocations()
    {
        return location;
    }
}
