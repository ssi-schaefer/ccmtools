package ccmtools.deployment.metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.deployment.metamodel.MonolithicImplementationDescription;


class MonolithicImplementationDescriptionImpl
    extends ModelElementImpl implements MonolithicImplementationDescription
{
    private List primaryArtifact = new ArrayList();
        
    
    public MonolithicImplementationDescriptionImpl()
    {
        super();
    }
    
    public List getPrimaryArtifacts()
    {
        return primaryArtifact;
    }
}
