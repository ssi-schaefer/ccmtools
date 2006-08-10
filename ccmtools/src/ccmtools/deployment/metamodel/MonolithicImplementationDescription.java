package ccmtools.deployment.metamodel;

import java.util.List;


public interface MonolithicImplementationDescription
    extends ModelElement
{
    String ELEMENT_NAME = "MonolithicImplementationDescription";
    
    List getPrimaryArtifacts();
}