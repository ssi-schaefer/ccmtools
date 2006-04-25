package ccm.local.Components;

/***
 * All interfaces for components types are derived from CCMObject.
 * CCM Specification 1-52
 * Light Weight CCM 4.1.11.1
 ***/
public interface CCMObject
	extends Navigation, Receptacles
{
    /*
     * The get_ccm_home() operation returns a CCMHome reference to the
     * home which manages this component.
     */
    CCMHome get_ccm_home();

    /*
     * This operation is called by a configurator to indicate that the
     * initial component configuration has completed.
     * If the component determines that it is not sufficiently configured
     * to allow normal client access, it raises the InvalidConfiguration
     * exception.
     */
    void configuration_complete()
        throws InvalidConfiguration;

    /*
     * This operation is used to delete a component.
     * Application failures during remove may raise the RemoveFailure
     * exception.
     */
    void remove()
        throws RemoveFailure;	
}
