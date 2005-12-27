package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ComponentAssemblyArtifactDescription;
import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.ComponentInterfaceDescription;
import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;


class ComponentImplementationDescriptionImpl
    extends ModelElementImpl implements ComponentImplementationDescription
{
    private String label;
    private String UUID;
    private ComponentAssemblyArtifactDescription assemblyImpl;
    private MonolithicImplementationDescription monolithicImpl;
    private ComponentInterfaceDescription interfaceDescription;
    
    public ComponentImplementationDescriptionImpl()
    {
        this(null,null);
    }
    
    public ComponentImplementationDescriptionImpl(String label, String uuid)
    {
        super();
        this.label = label;
        UUID = uuid;
    }

    public String getUUID()
    {
        return UUID;
    }
    
    public void setUUID(String uuid)
    {
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
    
    public ComponentAssemblyArtifactDescription getAssemblyImpl()
    {
        return assemblyImpl;
    }

    public void setAssemblyImpl(ComponentAssemblyArtifactDescription assemblyImpl)
    {
        this.assemblyImpl = assemblyImpl;
    }

    public MonolithicImplementationDescription getMonolithicImpl()
    {
        return monolithicImpl;
    }

    public void setMonolithicImpl(MonolithicImplementationDescription monolithicImpl)
    {
        this.monolithicImpl = monolithicImpl;
    }

    
    public void setImplements(ComponentInterfaceDescription cid)
    {
        this.interfaceDescription = cid;
    }
    
    public ComponentInterfaceDescription getImplements()
    {
        return interfaceDescription;
    }
}
