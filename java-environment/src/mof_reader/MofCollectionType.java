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
 * CollectionType
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofCollectionType extends MofDataType, MofTypedElement
{
    /**
     *
     */
    public MofMultiplicityType getMultiplicity() throws NumberFormatException;
}
