package ccm.local.Components;

/**
 * The Assembly interface represents an assembly instantion. It is used to
 * build up and tear down component assemblies.
 *      
 * CCM Specification 6-73
 */
public interface Assembly
{
    /**
     * Creates required component servers, creates required containers, installs
     * required component homes, instantiates components, configures and 
     * interconnects them according to the assembly descriptor.
     */
    void build()
        throws CreateFailure;

    /**
     * Removes all connections between components and destroys all components,
     * homes, containers, and component servers that were created by the build 
     * operation.
     */
    void tear_down()
        throws RemoveFailure;

    /**
     * Returns whether the assembly is active or inactive.
     */
    AssemblyState get_state();


    /**
     * Build a component assembly based on a given facade component.
     *  
     * Note: This is an CCM extension to support nested components.
     */
    void build(CCMObject facadeComponent)
        throws CreateFailure;

    /**
     * Call configuration_complete on every component instance in the assembly.
     *
     * Note: This is an CCM extension to support nested components.
     */
    void configuration_complete();
}
