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
 * only used in {@link GeneralizableElementXmi#register}
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class GenElementRefImp extends GeneralizableElementImp
{
    GenElementRefImp( GeneralizableElementXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((GeneralizableElementXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((GeneralizableElementXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((GeneralizableElementXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((GeneralizableElementXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((GeneralizableElementXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((GeneralizableElementXmi)xmi_).visibility_; }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        throw new NodeHandlerException("GenElementRefImp.process(NodeHandler) may not be called");
    }
}
