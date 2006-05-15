package Components.ccm.local;

/***
 * Clients can use the HomeFinder interface to obtain homes for particular
 * component types, of particularly homes, or homes that are bound to
 * specific names in a naming service.
 * CCM Spec. 1-42
 ***/
public interface HomeFinder
	extends HomeRegistration
{
    CCMHome find_home_by_component_type(String repid)
        throws HomeNotFound;

    CCMHome find_home_by_name(String name)
        throws HomeNotFound;

    CCMHome find_home_by_type(String repid)
        throws HomeNotFound;
}
