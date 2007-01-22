package ccmtools.generator.deployment.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.generator.deployment.metamodel.ComponentPortDescription;


class ComponentPortDescriptionImpl
    extends ModelElementImpl implements ComponentPortDescription 
{
    private String name;
    private String specificType;
    private List supportedType = new ArrayList();
    private boolean provider;
    private boolean exclusiveProvider;
    private boolean exclusiveUser;
    private boolean optional;
    private CCMComponentPortKind kind;
    
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    
    public boolean isProvider()
    {
        return provider;
    }
    
    public void setProvider(boolean provider)
    {
        this.provider = provider;
    }
    
    
    public boolean isExclusiveProvider()
    {
        return exclusiveProvider;
    }
    
    public void setExclusiveProvider(boolean exclusiveProvider)
    {
        this.exclusiveProvider = exclusiveProvider;
    }
    
    
    public boolean isExclusiveUser()
    {
        return exclusiveUser;
    }
    
    public void setExclusiveUser(boolean exclusiveUser)
    {
        this.exclusiveUser = exclusiveUser;
    }
    
    
    public boolean isOptional()
    {
        return optional;
    }
    
    public void setOptional(boolean optional)
    {
        this.optional = optional;
    }
    
    
    public CCMComponentPortKind getKind()
    {
        return kind;
    }
    
    public void setKind(CCMComponentPortKind kind)
    {
        this.kind = kind;
    }
}
