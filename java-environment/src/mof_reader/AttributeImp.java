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
 * Attribute implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AttributeImp extends StructuralFeatureImp implements MofAttribute
{
    AttributeImp( AttributeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((AttributeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((AttributeXmi)xmi_).name_; }


    /// implements {@link MofAttribute#isDerived}
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
