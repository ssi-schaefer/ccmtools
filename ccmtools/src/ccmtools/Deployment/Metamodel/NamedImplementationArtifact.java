package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.utils.ModelElement;


public interface NamedImplementationArtifact
    extends ModelElement
{
    public String ELEMENT_NAME = "NamedImplementationArtifact";
    
    public abstract String getName();

    public abstract void setName(String name);

    public abstract ImplementationArtifactDescription getReferenceArtifact();

    public abstract void setReferencedArtifact(ImplementationArtifactDescription referenceArtifact);

}