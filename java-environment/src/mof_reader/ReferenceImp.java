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
 * Reference implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ReferenceImp extends StructuralFeatureImp implements MofReference
{
    ReferenceImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofReference#getReferencedEnd}
    public MofAssociationEnd getReferencedEnd()
    {
        // TODO
        return null;
    }

    /// implements {@link MofReference#getExposedEnd}
    public MofAssociationEnd getExposedEnd()
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
