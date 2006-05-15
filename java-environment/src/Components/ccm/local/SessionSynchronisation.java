package Components.ccm.local;

/***
 * The SessionSynchronisation interface is a callback interface that may be
 * optionally be implemented by the session component. It permits the
 * component to be notified of transaction boundaries by the container.
 * CCM Specification 4-29
 */
public interface SessionSynchronisation
{
	void after_begin()
		throws CCMException;

	void before_completion()
		throws CCMException;

	void after_completion(boolean committed)
		throws CCMException;
}
