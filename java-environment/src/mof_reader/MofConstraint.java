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


/**
 * Constraint
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public interface MofConstraint extends MofModelElement
{
    /**
     * ?
     */
    public MofEvaluationKind getEvaluationPolicy();

    /**
     * returns the expression of this constraint
     */
    public String getExpression();

    /**
     * returns the language of the expression
     */
    public String getLanguage();

    /**
     * returns the owner of this constraint
     */
    public MofModelElement getConstrainedElement();

}
