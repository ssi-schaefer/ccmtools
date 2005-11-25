package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface MonolithicImplementationDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "MonolithicImplementationDescription";
    
    public abstract List getPrimaryArtifact();

}