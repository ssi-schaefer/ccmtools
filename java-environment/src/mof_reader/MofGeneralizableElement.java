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
 * GeneralizableElement
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofGeneralizableElement extends MofNamespace
{
    /**
     *
     */
    public boolean isAbstract();

    /**
     *
     */
    public boolean isLeaf();

    /**
     *
     */
    public boolean isRoot();

    /**
     * returns the visibility kind
     */
    public MofVisibilityKind getVisibility();

    /**
     * returns the supertypes of this element (a list of {@link MofGeneralizableElement})
     */
    public List getSupertypes();

}
