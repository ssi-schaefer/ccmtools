package ccmtools.generator.deployment.metamodel;

import java.util.List;


public interface ComponentPackageDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentPackageDescription";
    
    String getLabel();
    void setLabel(String label);

    String getUUID();
    void setUUID(String uuid);

    ComponentInterfaceDescription getRealizes();
    void setRealizes(ComponentInterfaceDescription realizes);

    List getImplementations();
}