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
    StructureTypeImp( StructureTypeXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((StructureTypeXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((StructureTypeXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((StructureTypeXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((StructureTypeXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((StructureTypeXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((StructureTypeXmi)xmi_).visibility_; }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginStructureType(this);
        handler.endModelElement(this);
    }
}
