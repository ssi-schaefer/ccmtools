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
 * Operation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofOperation extends MofBehavioralFeature
{
    /**
     *
     */
    public boolean isQuery();

    /**
     * returns a list of {@link MofException}
     */
    public List getExceptions();
}
