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
 * Import
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofImport extends MofModelElement
{
    /**
     * ?
     */
    public boolean isClustered();

    /**
     * returns the visibility kind
     */
    public MofVisibilityKind getVisibility() throws IllegalArgumentException;

    /**
     * returns the imported namespace
     */
    public MofNamespace getImported();

}
