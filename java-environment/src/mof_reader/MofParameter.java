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
 * Parameter
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofParameter extends MofTypedElement
{
    /**
     *
     */
    public MofDirectionKind getDirection();

    /**
     *
     */
    public MofMultiplicityType getMultiplicity();

}
