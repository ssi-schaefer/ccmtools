package ccmtools.Deployment.Metamodel.impl;

import java.util.ArrayList;
import java.util.List;

import ccmtools.Deployment.Metamodel.ComponentAssemblyDescription;
import ccmtools.Deployment.Metamodel.utils.ModelElementImpl;


public class ComponentAssemblyDescriptionImpl
    extends ModelElementImpl implements ComponentAssemblyDescription
{
    private List assemblyArtifact = new ArrayList();
    
    public ComponentAssemblyDescriptionImpl()
    {
        super();
    }
    
    
    public List getAssemblyArtifacts()
    {
        return assemblyArtifact;
    }
}
