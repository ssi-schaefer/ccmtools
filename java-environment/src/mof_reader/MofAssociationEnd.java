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
 * AssociationEnd
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofAssociationEnd extends MofTypedElement
{
    /**
     *
     */
    public MofAggregationKind getAggregation();

    /**
     *
     */
    public boolean isChangeable();

    /**
     *
     */
    public boolean isNavigable();

    /**
     *
     */
    public MofMultiplicityType getMultiplicity();

}
