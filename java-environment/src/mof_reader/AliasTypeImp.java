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
 * AliasType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AliasTypeImp extends GeneralizableElementImp implements MofAliasType
{
    AliasTypeImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofTypedElement#getType}
    public MofClassifier getType()
    {
        // TODO
        return null;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
