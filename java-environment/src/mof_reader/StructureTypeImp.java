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

import java.util.Collection;
import java.util.List;

import mof_xmi_parser.DTD_Container;


/**
 * StructureType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class StructureTypeImp extends GeneralizableElementImp implements MofStructureType
{
    StructureTypeImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
