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
import mof_xmi_parser.model.MConstant_value;


/**
 * Constant implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ConstantImp extends TypedElementImp implements MofConstant
{
    ConstantImp( ConstantXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ConstantXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ConstantXmi)xmi_).name_; }


    private String value_;


    /// implements {@link MofConstant#getValue}
    public String getValue()
    {
        if( value_==null )
        {
            value_ = ((ConstantXmi)xmi_).value_;
            if( value_==null )
            {
                value_ = getTextFromChild(MConstant_value.xmlName__);
            }
        }
        return value_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
