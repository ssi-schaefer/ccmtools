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
 * Common interface of all root elements.
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
interface Worker
{
    /**
     * register all core elements
     */
    public void register( java.util.Map map );

    /**
     * create the model graph
     */
    public void process( Model model );
}
