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
 * Operation implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class OperationImp extends BehavioralFeatureImp implements MofOperation
{
    OperationImp( OperationXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((OperationXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((OperationXmi)xmi_).name_; }


    /// implements {@link MofOperation#isQuery}
    public boolean isQuery()
    {
        // TODO
        return false;
    }

    /// implements {@link MofOperation#getExceptions}
    public List getExceptions()
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
