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
     * register all core elements and set the parent
     */
    public void register( java.util.Map map, Worker parent );

    /**
     * create the model graph
     */
    public void process( Model model );

    /**
     * return the associated MOF-element
     */
    public MofModelElement mof();


    /**
     * 'AssocioationImp': move 'MofAssocioationEnd' to 'MofNamespace'
     * others: call 'moveAssociationEnds' for all children
     */
    public void moveAssociationEnds();

}
