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
import mof_xmi_parser.model.MAssociation_isDerived;


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

    String getXmiAbstract()
    { return ((AssociationXmi)xmi_).isAbstract_; }

    String getXmiLeaf()
    { return ((AssociationXmi)xmi_).isLeaf_; }

    String getXmiRoot()
    { return ((AssociationXmi)xmi_).isRoot_; }

    String getXmiVisibility()
    { return ((AssociationXmi)xmi_).visibility_; }


    private String isDerived_;


    /// implements {@link MofAssociation#isDerived}
    public boolean isDerived()
    {
        if( isDerived_==null )
        {
            isDerived_ = ((AssociationXmi)xmi_).isDerived_;
            if( isDerived_==null )
            {
                isDerived_ = getBooleanFromChild(MAssociation_isDerived.xmlName__);
            }
        }
        return isDerived_.equalsIgnoreCase("true");
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginAssociation(this);
        handler.endModelElement(this);
    }
}
