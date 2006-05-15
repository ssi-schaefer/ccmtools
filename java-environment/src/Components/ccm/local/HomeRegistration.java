package Components.ccm.local;

/***
 * The HomeRegistration is an internal interface that may be used by the
 * CORBA component to register its home so it can be located by a
 * HomeFinder
 * CCM Specification 4-34
 */
public interface HomeRegistration
{
    /**
     * The register_home operation is used to register a component home
     * with the HomeFinder so it can by located by a component client.
     */
    void register_home(CCMHome home_ref, String home_name);

    /**
     * The unregister_home operation is used to remove a component home
     * from the HomeFinder.
     */
    void unregister_home(CCMHome home_ref);

    /**
     * This unregister_home operation is used to remove a component home
     * (defined by the home_name) from the HomeFinder.
     * Note: this method is NOT defined in the CCM specification!!
     */
    void unregister_home(String home_name);
}
