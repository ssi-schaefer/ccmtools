package ccmtools.Deployment.Metamodel;

import ccmtools.Deployment.Metamodel.utils.ModelElement;


public interface ComponentImplementationDescription
    extends ModelElement
{
    String ELEMENT_NAME = "ComponentImplementationDescription";

    String getUUID();
    void setUUID(String uuid);

    String getLabel();
    void setLabel(String label);

    ComponentAssemblyDescription getAssemblyImpl();
    void setAssemblyImpl(ComponentAssemblyDescription assemblyImpl);

    MonolithicImplementationDescription getMonolithicImpl();
    void setMonolithicImpl(MonolithicImplementationDescription monolithicImpl);

    void setImplements(ComponentInterfaceDescription cid);
    ComponentInterfaceDescription getImplements();
}