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
 * StructuralFeature
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofStructuralFeature extends MofFeature, MofTypedElement
{
    /**
     *
     */
    public boolean isChangeable();

    /**
     *
     */
    public MofMultiplicityType getMultiplicity();

}
