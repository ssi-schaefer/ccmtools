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
 * Exception implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ExceptionImp extends BehavioralFeatureImp implements MofException
{
    ExceptionImp( ExceptionXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ExceptionXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ExceptionXmi)xmi_).name_; }

    String getXmiScope()
    { return ((ExceptionXmi)xmi_).scope_; }

    String getXmiVisibility()
    { return ((ExceptionXmi)xmi_).visibility_; }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }

}
