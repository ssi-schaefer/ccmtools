package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ImplementationArtifactDescription;
import ccmtools.Deployment.Metamodel.NamedImplementationArtifact;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class NamedImplementationArtifactImpl
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
