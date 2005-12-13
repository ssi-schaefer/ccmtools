package ccmtools.Deployment.Metamodel;



public interface NamedImplementationArtifact
    extends ModelElement
{
    String ELEMENT_NAME = "NamedImplementationArtifact";
    
    String getName();
    void setName(String name);

    ImplementationArtifactDescription getReferenceArtifact();
    void setReferencedArtifact(ImplementationArtifactDescription referenceArtifact);
}