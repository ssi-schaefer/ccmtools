package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface MonolithicImplementationDescription
    extends ModelElement
{
    String ELEMENT_NAME = "MonolithicImplementationDescription";
    
    List getPrimaryArtifacts();
}