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
 * Package implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class PackageImp extends GeneralizableElementImp implements MofPackage
{
    PackageImp( PackageXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((PackageXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((PackageXmi)xmi_).name_; }

    String getXmiAbstract()
    { return ((PackageXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((PackageXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((PackageXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((PackageXmi)xmi_).visibility_; }

    
    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginPackage(this);
        handler.endModelElement(this);
    }

}
