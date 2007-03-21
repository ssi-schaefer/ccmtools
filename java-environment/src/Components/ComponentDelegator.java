/*
 * Created on Mar 20, 2007
 * 
 * R&D Salomon Automation (http://www.salomon.at)
 * 
 * Robert Lechner (robert.lechner@salomon.at)
 * 
 * $Id$
 */
package Components;

/**
 * If the implementation class of the component implements this interface, the local component
 * adapter delegates to it.
 */
public interface ComponentDelegator
{
    /**
     * provides a facet
     * 
     * @param name name of the facet
     * @return facet adapter (or null if the facet is not supported)
     * @throws InvalidName if a facet of that name is not known
     */
    Object provide( String name ) throws InvalidName;

    /**
     * connect a facet into a receptacle
     * 
     * @param name name of the receptacle
     * @param connection the facet
     * @return cookie for multiple receptacles
     */
    Cookie connect( String name, Object connection ) throws InvalidName, InvalidConnection,
            AlreadyConnected, ExceededConnectionLimit;

    /**
     * disconnects from a receptacle
     * 
     * @param name name of the receptacle
     * @param ck cookie for multiple receptacles; null for single receptacles
     */
    void disconnect( String name, Cookie ck ) throws InvalidName, InvalidConnection,
            CookieRequired, NoConnection;
}
