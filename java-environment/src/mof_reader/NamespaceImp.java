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
import mof_xmi_parser.DTD_Container;


/**
 * Namespace implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
abstract class NamespaceImp extends ModelElementImp implements MofNamespace
{
    NamespaceImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofNamespace#getContainedElements}
    public List getContainedElements()
    {
        // TODO
        return null;
    }

}
