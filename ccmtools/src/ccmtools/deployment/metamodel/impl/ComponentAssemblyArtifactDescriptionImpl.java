package ccmtools.deployment.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.deployment.metamodel.ComponentAssemblyArtifactDescription;


class ComponentAssemblyArtifactDescriptionImpl
    extends ModelElementImpl implements ComponentAssemblyArtifactDescription
{
    private String label;
    private String UUID;
    private String spectifcType;
    private List location = new ArrayList();
    
    public ComponentAssemblyArtifactDescriptionImpl()
    {
        this(null, null, null);
    }
    
    public ComponentAssemblyArtifactDescriptionImpl(String label, String uuid, String type)
    {
        super();
        this.label = label;
        spectifcType = type;
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
        
    public String getSpectifcType()
    {
        return spectifcType;
    }

    public void setSpecifcType(String spectifcType)
    {
        this.spectifcType = spectifcType;
    }

    public List getLocations()
    {
        return location;
    }
}
