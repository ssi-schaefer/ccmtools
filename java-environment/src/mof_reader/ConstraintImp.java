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
 * Constraint implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ConstraintImp extends ModelElementImp implements MofConstraint
{
    ConstraintImp( DTD_Container xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }


    /// implements {@link MofConstraint#getEvaluationPolicy}
    public MofEvaluationKind getEvaluationPolicy()
    {
        // TODO
        return null;
    }


    /// implements {@link MofConstraint#getExpression}
    public String getExpression()
    {
        // TODO
        return null;
    }


    /// implements {@link MofConstraint#getLanguage}
    public String getLanguage()
    {
        // TODO
        return null;
    }


    /// implements {@link MofConstraint#getConstrainedElement}
    public MofModelElement getConstrainedElement()
    {
        return parent_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        // TODO
    }
}
