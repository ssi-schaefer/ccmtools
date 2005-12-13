package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.Deployment.Metamodel.MonolithicImplementationDescription;


public class MonolithicImplementationDescriptionImpl
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
