package ccm.local.Components;

/***
 * The KeylessCCMHome interface is inherited by the implicit home interface.
 *
 * CCM Specification  1-42
 *
 * Extension to CCM-Spec: CCMException to create_component()
 ***/
public interface KeylessCCMHome
{
    CCMObject create_component()
    	throws CCMException, CreateFailure;
}
