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

import java.util.List;


/**
 * Namespace
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofNamespace extends MofModelElement
{
    /**
     * returns a list of {@link MofModelElement}
     */
    public List getContainedElements();

    /**
     * Calls {@link MofModelElement#process} for each child.
     *
     * @param handler  the node-handler
     *
     * @throws NodeHandlerException  only the node-handler throws this exception
     */
    public void processContainedElements( NodeHandler handler ) throws NodeHandlerException;
}
