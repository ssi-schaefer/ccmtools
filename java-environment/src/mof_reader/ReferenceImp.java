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
    ReferenceImp( ReferenceXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ReferenceXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ReferenceXmi)xmi_).name_; }

    String getXmiScope()
    { return ((AttributeXmi)xmi_).scope_; }

    String getXmiVisibility()
    { return ((AttributeXmi)xmi_).visibility_; }

    String getXmiIsChangeable()
    { return ((AttributeXmi)xmi_).isChangeable_; }

    String getXmiMultiplicity()
    { return ((AttributeXmi)xmi_).multiplicity_; }


    /// implements {@link MofReference#getReferencedEnd}
    public MofAssociationEnd getReferencedEnd()
    {
        // TODO
        throw new RuntimeException("not implemented");
    }

    /// implements {@link MofReference#getExposedEnd}
    public MofAssociationEnd getExposedEnd()
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
        throw new RuntimeException("not implemented");
    }
}
