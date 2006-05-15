package Components.ccm.local;

/***
 * The SessionComponent is a callback interface implemented by a session CORBA
 * component. It provides operations for disassociating a context with the
 * component and to manage servant lifetime for a session component.
 * CCM Specification 4-28
 */
public interface SessionComponent
	extends EnterpriseComponent
{
    /*
     * The set_session_context operation is used to set the SessionContext
     * of the component. The container calls this operation after a component
     * instance has been created.
     */
    void set_session_context(SessionContext ctx)
    	throws CCMException;

    /*
     * The ccm_activate operation is called by the container to notify a
     * session component that is has been made active.
     */
    void ccm_activate()
      	throws CCMException;

    /*
     * The ccm_passivate operation is called by the container to notify a
     * session component that it has been made inactive.
     */
    void ccm_passivate()
      	throws CCMException;

    /*
     * The void ccm_remove operation is called by the container when the
     * servant is about to be destroyed.
     */
    void ccm_remove()
      throws CCMException;
}
