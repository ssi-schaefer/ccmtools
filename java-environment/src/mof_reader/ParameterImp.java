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
import mof_xmi_parser.model.MParameter_direction;
import mof_xmi_parser.model.MParameter_multiplicity;


/**
 * Parameter implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ParameterImp extends TypedElementImp implements MofParameter
{
    ParameterImp( ParameterXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ParameterXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ParameterXmi)xmi_).name_; }


    private MofDirectionKind direction_;
    private MofMultiplicityType multiplicity_;


    /// implements {@link MofParameter#getDirection}
    public MofDirectionKind getDirection() throws IllegalArgumentException
    {
        if( direction_==null )
        {
            String dir = ((ParameterXmi)xmi_).direction_;
            if( dir!=null )
            {
                direction_ = MofDirectionKind.create(dir);
            }
            else
            {
                Vector children = xmi_.findChildren(MParameter_direction.xmlName__);
                if( children.size()>=1 )
                {
                    MParameter_direction p = (MParameter_direction)children.get(0);
                    direction_ = MofDirectionKind.create(p.xmi_value_);
                }
                else
                {
                    throw new IllegalArgumentException("no direction");
                }
            }
        }
        return direction_;
    }

    /// implements {@link MofParameter#getMultiplicity}
    public MofMultiplicityType getMultiplicity() throws NumberFormatException
    {
        if( multiplicity_==null )
        {
            String m = ((ParameterXmi)xmi_).multiplicity_;
            if( m!=null )
            {
                multiplicity_ = new MofMultiplicityType(m);
            }
            else
            {
                Vector children = xmi_.findChildren(MParameter_multiplicity.xmlName__);
                if( children.size()>=1 )
                {
                    MParameter_multiplicity p = (MParameter_multiplicity)children.get(0);
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
        handler.beginParameter(this);
        handler.endModelElement(this);
    }
}
