package ccmtools.Deployment.Metamodel;

import java.util.List;


public interface ComponentInterfaceDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentInterfaceDescription";
    
    String getLabel();
    void setLabel(String label);

    String getUUID();
    void setUUID(String uuid);

    String getSpecificType();
    void setSpecificType(String specificType);
    
    List getSupportedType();

    List getIdlFile();
    
    List getPort();
}