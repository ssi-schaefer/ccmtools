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

import java.util.Collection;
import java.util.List;


/**
 * ModelElement
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofModelElement
{
    /**
     * Calls the node-handler.
     *
     * @param handler  the node-handler
     *
     * @throws NodeHandlerException  only the node-handler throws this exception
     */
    public void process( NodeHandler handler ) throws NodeHandlerException;

    /**
     * returns an annotation
     */
    public String getAnnotation();

    /**
     * returns the name of this element
     */
    public String getName();

    /**
     * returns the qualified name, a non-empty list of {@link java.lang.String}
     */
    public List getQualifiedName();

    /**
     * returns a collection of {@link MofModelElement} which this element depends on
     */
    public Collection getProviders();

    /**
     * returns the parent namespace or null
     */
    public MofNamespace getContainer();

    /**
     * returns a collection of {@link MofConstraint}
     */
    public Collection getConstraints();

    /**
     * returns a list of {@link MofTag}
     */
    public List getTags();

}
