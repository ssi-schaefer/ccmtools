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

import mof_xmi_parser.DTD_Creator;
import mof_xmi_parser.DTD_Container;
import org.xml.sax.Attributes;


/**
 * The factory for all implementation classes.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class XmiFactory extends DTD_Creator
{
    /**
     * extends {@link mof_xmi_parser.DTD_Creator#create}
     */
    public DTD_Container create( String qName, Attributes attrs )
    {
        // TODO

        return super.create(qName, attrs);
    }
}
