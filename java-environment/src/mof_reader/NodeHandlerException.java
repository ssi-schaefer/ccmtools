/*  MOF reader
 *
 *  2004 by Research & Development, Salomon Automation <www.salomon.at>
 *
 *  Robert Lechner  <robert.lechner@salomon.at>
 *
 *
 *  $Id$
 *
 */

package mof_reader;


/**
 * Exceptions for the node handler.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class NodeHandlerException extends java.lang.Exception
{
    public NodeHandlerException()
    {
        super("node handler exception");
    }

    public NodeHandlerException( String message )
    {
        super(message);
    }

    public NodeHandlerException( Throwable cause )
    {
        super("node handler exception", cause);
    }

    public NodeHandlerException( String message, Throwable cause )
    {
        super(message, cause);
    }
}

