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
 * Association implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AssociationImp extends GeneralizableElementImp implements MofAssociation
{
    AssociationImp( AssociationXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((AssociationXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((AssociationXmi)xmi_).name_; }


    /// implements {@link MofAssociation#isDerived}
    public boolean isDerived()
    {
        // TODO
        return false;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
