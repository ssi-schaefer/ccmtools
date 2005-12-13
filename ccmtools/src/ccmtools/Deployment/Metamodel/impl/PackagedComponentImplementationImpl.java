package ccmtools.Deployment.Metamodel.impl;

import ccmtools.Deployment.Metamodel.ComponentImplementationDescription;
import ccmtools.Deployment.Metamodel.PackagedComponentImplementation;


public class PackagedComponentImplementationImpl
    extends ModelElementImpl implements PackagedComponentImplementation
{
    private String name;
    private ComponentImplementationDescription referencedImplementation; 
    
    
    public PackagedComponentImplementationImpl()
    {
        this(null);
    }
        
    public PackagedComponentImplementationImpl(String name)
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

    public ComponentImplementationDescription getReferencedImplementation()
    {
        return referencedImplementation;
    }

    public void setReferencedImplementation(ComponentImplementationDescription referencedImplementation)
    {
        this.referencedImplementation = referencedImplementation;
    }
}
