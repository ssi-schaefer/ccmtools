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
 * Reference
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofReference extends MofStructuralFeature
{
    /**
     *
     */
    public MofAssociationEnd getReferencedEnd();

    /**
     *
     */
    public MofAssociationEnd getExposedEnd();

}
