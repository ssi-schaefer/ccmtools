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
 * EnumerationType
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofEnumerationType extends MofDataType
{
    /**
     * returns a non-empty list of {@link java.lang.String}
     */
    public List getLabels();
}
