package ccmtools.generator.deployment.metamodel.impl;

import ccmtools.generator.deployment.metamodel.ImplementationArtifactDescription;
import ccmtools.generator.deployment.metamodel.NamedImplementationArtifact;


class NamedImplementationArtifactImpl
    extends ModelElementImpl implements NamedImplementationArtifact
{
    private String name;
    private ImplementationArtifactDescription referenceArtifact;
    
    public NamedImplementationArtifactImpl()
    {
        this(null);
    }
    
    public NamedImplementationArtifactImpl(String name)
    {
        super();
        this.name = name;
    }

    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ImplementationArtifactDescription getReferenceArtifact()
    {
        return referenceArtifact;
    }

    public void setReferencedArtifact(ImplementationArtifactDescription referenceArtifact)
    {
        this.referenceArtifact = referenceArtifact;
    }
}
