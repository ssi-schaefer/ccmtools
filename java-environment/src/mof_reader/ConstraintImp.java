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
import mof_xmi_parser.model.MConstraint_evaluationPolicy;
import mof_xmi_parser.model.MConstraint_expression;
import mof_xmi_parser.model.MConstraint_language;


/**
 * Constraint implementation
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
class ConstraintImp extends ModelElementImp implements MofConstraint
{
    ConstraintImp( ConstraintXmi xmi, MofModelElement parent )
    {
        super(xmi, parent);
    }

    String getXmiAnnotation()
    { return ((ConstraintXmi)xmi_).annotation_; }

    String getXmiName()
    { return ((ConstraintXmi)xmi_).name_; }


    private MofEvaluationKind evaluation_;
    private String expression_;
    private String language_;


    /// implements {@link MofConstraint#getEvaluationPolicy}
    public MofEvaluationKind getEvaluationPolicy() throws IllegalArgumentException
    {
        if( evaluation_==null )
        {
            String ep = ((ConstraintXmi)xmi_).evaluationPolicy_;
            if( ep!=null )
            {
                evaluation_ = MofEvaluationKind.create(ep);
            }
            else
            {
                Vector children = xmi_.findChildren(MConstraint_evaluationPolicy.xmlName__);
                if( children.size()>=1 )
                {
                    MConstraint_evaluationPolicy p = (MConstraint_evaluationPolicy)children.get(0);
                    evaluation_ = MofEvaluationKind.create(p.xmi_value_);
                }
                else
                {
                    throw new IllegalArgumentException("no evaluation policy");
                }
            }
        }
        return evaluation_;
    }


    /// implements {@link MofConstraint#getExpression}
    public String getExpression()
    {
        if( expression_==null )
        {
            expression_ = ((ConstraintXmi)xmi_).expression_;
            if( expression_==null )
            {
                expression_ = getTextFromChild(MConstraint_expression.xmlName__);
            }
        }
        return expression_;
    }


    /// implements {@link MofConstraint#getLanguage}
    public String getLanguage()
    {
        if( language_==null )
        {
            language_ = ((ConstraintXmi)xmi_).language_;
            if( language_==null )
            {
                language_ = getTextFromChild(MConstraint_language.xmlName__);
            }
        }
        return language_;
    }


    /// implements {@link MofConstraint#getConstrainedElement}
    public MofModelElement getConstrainedElement()
    {
        return parent_;
    }


    /// implements {@link MofModelElement#process}
    public void process( NodeHandler handler ) throws NodeHandlerException
    {
        handler.beginConstraint(this);
        handler.endModelElement(this);
    }
}
