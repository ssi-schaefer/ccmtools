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
 * Feature
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofFeature extends MofModelElement
{
    /**
     * returns the scope kind
     */
    public MofScopeKind getScope();

    /**
     * returns the visibility kind
     */
    public MofVisibilityKind getVisibility();

}
