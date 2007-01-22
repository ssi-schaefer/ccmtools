package ccmtools.generator.deployment.metamodel.impl;

import ccmtools.generator.deployment.metamodel.ComponentImplementationDescription;
import ccmtools.generator.deployment.metamodel.PackagedComponentImplementation;


class PackagedComponentImplementationImpl
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
