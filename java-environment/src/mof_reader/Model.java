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

import mof_xmi_parser.DTD_Container;


/**
 * The MOF model.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public class Model
{
    public Model( DTD_Container root )
    {
        // TODO
        System.out.println("root=="+root.getClass().getName());
    }
}
