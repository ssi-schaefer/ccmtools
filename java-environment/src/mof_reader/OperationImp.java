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

import java.util.Vector;
import java.util.List;

import mof_xmi_parser.DTD_Container;
import mof_xmi_parser.model.MOperation_exceptions;


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

    String getXmiScope()
    { return ((OperationXmi)xmi_).scope_; }

    String getXmiVisibility()
    { return ((OperationXmi)xmi_).visibility_; }


    private String isQuery_;
    private Vector exceptions_;


    /// implements {@link MofOperation#isQuery}
    public boolean isQuery()
    {
        if( isQuery_==null )
        {
            isQuery_ = ((OperationXmi)xmi_).isQuery_;
            if( isQuery_==null )
            {
                isQuery_ = "false";
            }
        }
        return isQuery_.equalsIgnoreCase("true");
    }


    /// implements {@link MofOperation#getExceptions}
    public List getExceptions()
    {
        if( exceptions_==null )
        {
            exceptions_ = convertXmiContainerToMof(MOperation_exceptions.xmlName__, ExceptionXmi.xmlName__);
        }
        return exceptions_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginOperation(this);
        handler.endModelElement(this);
    }
}
