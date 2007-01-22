package ccmtools.generator.deployment.metamodel;

import java.util.List;

import ccmtools.generator.deployment.metamodel.impl.CCMComponentPortKind;

public interface ComponentPortDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentPortDescription";
    
    String getName();
    void setName(String name);
    
    String getSpecificType();
    void setSpecificType(String specificType);
    
    List getSupportedType();
    
    boolean isProvider();
    void setProvider(boolean provider);
    
    boolean isExclusiveProvider();
    void setExclusiveProvider(boolean exclusiveProvider);

    boolean isExclusiveUser();
    void setExclusiveUser(boolean exclusiveUser);
    
    boolean isOptional();
    void setOptional(boolean optional);

    CCMComponentPortKind getKind();
    void setKind(CCMComponentPortKind kind);
}