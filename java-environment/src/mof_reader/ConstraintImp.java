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
    public MofEvaluationKind getEvaluationPolicy()
    {
        if( evaluation_==null )
        {
            try
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
                }
            }
            catch( IllegalArgumentException e )
            {
                e.printStackTrace();
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
                Vector ch = xmi_.findChildren(MConstraint_expression.xmlName__);
                if( ch.size()>=1 )
                {
                    MConstraint_expression expr = (MConstraint_expression)ch.get(0);
                    if( expr.size()>=1 )
                    {
                        expression_ = expr.get(0).toString();
                    }
                }
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
                Vector ch = xmi_.findChildren(MConstraint_language.xmlName__);
                if( ch.size()>=1 )
                {
                    MConstraint_language expr = (MConstraint_language)ch.get(0);
                    if( expr.size()>=1 )
                    {
                        language_ = expr.get(0).toString();
                    }
                }
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
        // TODO
    }
}
