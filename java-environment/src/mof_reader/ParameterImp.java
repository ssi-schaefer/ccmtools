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


    /// implements {@link MofParameter#getDirection}
    public MofDirectionKind getDirection()
    {
        // TODO
        return null;
    }

    /// implements {@link MofParameter#getMultiplicity}
    public MofMultiplicityType getMultiplicity()
    {
        // TODO
        return null;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
