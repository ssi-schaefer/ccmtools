package ccm.local.Components;

/***
 * The Receptacles interface provides generic operations for connecting
 * to a component's receptacles. The CCMObject interface is derived from
 * Receptacles.
 * CCM Specification 1-18
 * Light Weight CCM 4.1.5.3
 ***/
public interface Receptacles
{
    /*
     * The connect() operation connects the object reference specified by
     * the connection parameter to the receptacle specified by the name
     * parameter on the target component.
     * multiplex receptacle: the operation returns a cookie value that can
     * be used subsequently to disconnect the object reference.
     * simplex receptacle: the return value is a nil.
     */
    Cookie connect(String name, Object connection)
      throws InvalidName, InvalidConnection, AlreadyConnected, ExceededConnectionLimit;

    /*
     * Simplex receptacle: the operation will disassociate any object
     * reference currently connected to the receptacle - the cookie
     * parameter is ignored.
     * multiplex receptacle: the operation disassociates the object reference
     * associated with the cookie value from the receptacle.
     */
    void disconnect(String name, Cookie ck)
      throws InvalidName, InvalidConnection, CookieRequired, NoConnection;
}
