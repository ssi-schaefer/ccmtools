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
 * Tag
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofTag extends MofModelElement
{
    /**
     * returns the id of this tag
     */
    public String getTagId();

    /**
     * returns a list of {@link java.lang.String}
     */
    public List getValues();

    /**
     * returns the owner of this tag
     */
    public MofModelElement getModelElement();

}
