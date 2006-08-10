package ccmtools.deployment.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.deployment.metamodel.ComponentInterfaceDescription;


class ComponentInterfaceDescriptionImpl
    extends ModelElementImpl implements ComponentInterfaceDescription
{
    private String label;
    private String UUID;
    private String specificType;
    private List supportedType = new ArrayList();
    private List idlFile = new ArrayList();
    private List port = new ArrayList();
    
    
    public ComponentInterfaceDescriptionImpl()
    {
        this(null, null, null);
    }
    
    public ComponentInterfaceDescriptionImpl(String label, String uuid, String type)
    {
        super();
        this.label = label;
        specificType = type;
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
    
    public String getSpecificType()
    {
        return specificType;
    }
    
    public void setSpecificType(String specificType)
    {
        this.specificType = specificType;
    }

    public List getSupportedType()
    {
        return supportedType;
    }
    
    public List getIdlFile()
    {
        return idlFile;
    }
    
    public List getPort()
    {
        return port;
    }
}
