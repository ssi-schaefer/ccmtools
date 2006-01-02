package ccm.local.Components;

/***
 * The CCMHome interface is interited by the explicit home interface.
 *
 * CCM Specification 1-41
 * Light Weight CCM 4.1.7.5
 * Extension to CCM-Spec: CCMException to remove_component()
 */
public interface CCMHome
{
	void remove_component(CCMObject component)
    	throws CCMException, RemoveFailure;
}
