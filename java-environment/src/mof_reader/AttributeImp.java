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
import mof_xmi_parser.model.MAttribute_isDerived;


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

    String getXmiScope()
    { return ((AttributeXmi)xmi_).scope_; }

    String getXmiVisibility()
    { return ((AttributeXmi)xmi_).visibility_; }

    String getXmiIsChangeable()
    { return ((AttributeXmi)xmi_).isChangeable_; }

    String getXmiMultiplicity()
    { return ((AttributeXmi)xmi_).multiplicity_; }


    private String isDerived_;


    /// implements {@link MofAttribute#isDerived}
    public boolean isDerived()
    {
        if( isDerived_==null )
        {
            isDerived_ = ((AttributeXmi)xmi_).isDerived_;
            if( isDerived_==null )
            {
                isDerived_ = getBooleanFromChild(MAttribute_isDerived.xmlName__);
            }
        }
        return isDerived_.equalsIgnoreCase("true");
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginAttribute(this);
        handler.endModelElement(this);
    }
}
