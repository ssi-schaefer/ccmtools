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
 * PrimitiveType implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class PrimitiveTypeImp extends GeneralizableElementImp implements MofPrimitiveType
{
    PrimitiveTypeImp( PrimitiveTypeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((PrimitiveTypeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((PrimitiveTypeXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((PrimitiveTypeXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((PrimitiveTypeXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((PrimitiveTypeXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((PrimitiveTypeXmi)xmi_).visibility_; }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginPrimitiveType(this);
        handler.endModelElement(this);
    }

}
