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
import java.util.Vector;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MAssociationEnd_multiplicity;
import mof_xmi_parser.model.MAssociationEnd_aggregation;
import mof_xmi_parser.model.MAssociationEnd_isChangeable;
import mof_xmi_parser.model.MAssociationEnd_isNavigable;


/**
 * AssociationEnd implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class AssociationEndImp extends TypedElementImp implements MofAssociationEnd
{
    AssociationEndImp( AssociationEndXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((AssociationEndXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((AssociationEndXmi)xmi_).name_; }


    private MofMultiplicityType multiplicity_;
    private MofAggregationKind aggregation_;
    private String isChangeable_;
    private String isNavigable_;


    /// implements {@link MofAssociationEnd#getAggregation}
    public MofAggregationKind getAggregation() throws IllegalArgumentException
    {
        if( aggregation_==null )
        {
            String text = ((AssociationEndXmi)xmi_).aggregation_;
            if( text!=null )
            {
                aggregation_ = MofAggregationKind.create(text);
            }
            else
            {
                Vector children = xmi_.findChildren(MAssociationEnd_aggregation.xmlName__);
                if( children.size()>=1 )
                {
                    MAssociationEnd_aggregation v = (MAssociationEnd_aggregation)children.get(0);
                    aggregation_ = MofAggregationKind.create(v.xmi_value_);
                }
                else
                {
                    throw new IllegalArgumentException("no aggregation");
                }
            }
        }
        return aggregation_;
    }


    /// implements {@link MofAssociationEnd#isChangeable}
    public boolean isChangeable()
    {
        if( isChangeable_==null )
        {
            isChangeable_ = ((AssociationEndXmi)xmi_).isChangeable_;
            if( isChangeable_==null )
            {
                isChangeable_ = getBooleanFromChild(MAssociationEnd_isChangeable.xmlName__);
            }
        }
        return isChangeable_.equalsIgnoreCase("true");
    }


    /// implements {@link MofAssociationEnd#isNavigable}
    public boolean isNavigable()
    {
        if( isNavigable_==null )
        {
            isNavigable_ = ((AssociationEndXmi)xmi_).isNavigable_;
            if( isNavigable_==null )
            {
                isNavigable_ = getBooleanFromChild(MAssociationEnd_isNavigable.xmlName__);
            }
        }
        return isNavigable_.equalsIgnoreCase("true");
    }


    /// implements {@link MofAssociationEnd#getMultiplicity}
    public MofMultiplicityType getMultiplicity() throws NumberFormatException
    {
        if( multiplicity_==null )
        {
            String m = ((AssociationEndXmi)xmi_).multiplicity_;
            if( m!=null )
            {
                multiplicity_ = new MofMultiplicityType(m);
            }
            else
            {
                Vector children = xmi_.findChildren(MAssociationEnd_multiplicity.xmlName__);
                if( children.size()>=1 )
                {
                    MAssociationEnd_multiplicity p = (MAssociationEnd_multiplicity)children.get(0);
                    multiplicity_ = new MofMultiplicityType(p);
                }
                else
                {
                    throw new NumberFormatException("no multiplicity");
                }
            }
        }
        return multiplicity_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginAssociationEnd(this);
        handler.endModelElement(this);
    }
}
