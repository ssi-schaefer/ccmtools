package Components;

/***
 * The SessionContext is an internal interface which provides a component
 * instance with access to the container-provided runtime services. The
 * SessionContext enables the component to simply obtain all the references
 * it may require to implement its behavior.
 * CCM Spec. 4-27
 */
public interface SessionContext
	extends CCMContext
{
    /**
     * The get_CCM_object operation is used to get the reference used to
     * invoke the component (component reference or facet reference. If this
     * operation is issued outside of the scope of a callback operation, the
     * IllegalState exception is returned.
     */
    Object get_CCM_object()
		throws IllegalState;
}
