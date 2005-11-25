package ccmtools.Deployment.Metamodel;

import java.util.List;

import ccmtools.Deployment.Metamodel.utils.ModelElement;

public interface ComponentAssemblyDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentAssemblyDescription";
    
    public abstract List getAssemblyArtifact();

}