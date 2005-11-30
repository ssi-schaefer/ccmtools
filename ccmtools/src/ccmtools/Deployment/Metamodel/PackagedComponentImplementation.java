package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.utils.ModelElement;


public interface PackagedComponentImplementation
    extends ModelElement
{
    String ELEMENT_NAME = "PackagedComponentImplementation";
    
    String getName();
    void setName(String name);

    ComponentImplementationDescription getReferencedImplementation();    
    void setReferencedImplementation(ComponentImplementationDescription referencedImplementation);
}