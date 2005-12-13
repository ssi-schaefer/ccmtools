package ccmtools.Deployment.Metamodel;



public interface ComponentImplementationDescription
    extends ModelElement
{
    String ELEMENT_NAME = "ComponentImplementationDescription";

    String getUUID();
    void setUUID(String uuid);

    String getLabel();
    void setLabel(String label);

    ComponentAssemblyArtifactDescription getAssemblyImpl();
    void setAssemblyImpl(ComponentAssemblyArtifactDescription assemblyImpl);

    MonolithicImplementationDescription getMonolithicImpl();
    void setMonolithicImpl(MonolithicImplementationDescription monolithicImpl);

    void setImplements(ComponentInterfaceDescription cid);
    ComponentInterfaceDescription getImplements();
}