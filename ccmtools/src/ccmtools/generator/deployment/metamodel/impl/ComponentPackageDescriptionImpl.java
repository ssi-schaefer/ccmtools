package ccmtools.generator.deployment.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.deployment.metamodel.ComponentInterfaceDescription;
import ccmtools.generator.deployment.metamodel.ComponentPackageDescription;

class ComponentPackageDescriptionImpl
    extends ModelElementImpl implements ComponentPackageDescription
{
    private String label;
    private String UUID;
    
    private ComponentInterfaceDescription realizes;
    private List implementation = new ArrayList();
    
    
    public ComponentPackageDescriptionImpl()
    {
        this(null, null);
    }
    
    public ComponentPackageDescriptionImpl(String label, String uuid)
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
    
    public ComponentInterfaceDescription getRealizes()
    {
        return realizes;
    }
    
    public void setRealizes(ComponentInterfaceDescription realizes)
    {
        this.realizes = realizes;
    }
    
    public List getImplementations()
    {
        return implementation;
    }
}
