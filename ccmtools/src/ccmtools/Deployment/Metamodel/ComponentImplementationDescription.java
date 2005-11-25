package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.utils.ModelElement;


public interface ComponentImplementationDescription
    extends ModelElement
{
    public String ELEMENT_NAME = "ComponentImplementationDescription";

    public abstract String getUUID();

    public abstract void setUUID(String uuid);

    public abstract String getLabel();

    public abstract void setLabel(String label);

    public abstract ComponentAssemblyDescription getAssemblyImpl();

    public abstract void setAssemblyImpl(
                                         ComponentAssemblyDescription assemblyImpl);

    public abstract MonolithicImplementationDescription getMonolithicImpl();

    public abstract void setMonolithicImpl(MonolithicImplementationDescription monolithicImpl);

}