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

import mof_xmi_parser.DTD_Container;


/**
 * EvaluationKind
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public final class MofEvaluationKind
{
    private String enumeration_kind_;

    private MofEvaluationKind( String kind )
    {
        enumeration_kind_ = kind;
    }

    public String toString()
    {
        return enumeration_kind_;
    }


    /**
     *  immediate
     */
    public static final MofEvaluationKind IMMEDIATE = new MofEvaluationKind("immediate");

    /**
     *  deferred
     */
    public static final MofEvaluationKind DEFERRED = new MofEvaluationKind("deferred");


    /**
     * Returns {@link IMMEDIATE} or {@link DEFERRED}.
     *
     * @throws IllegalArgumentException  unknown evaluation kind
     */
    public static MofEvaluationKind create( String evaluation ) throws IllegalArgumentException
    {
        if( evaluation==null )
        {
            throw new IllegalArgumentException("no evaluation kind");
        }
        if( evaluation.equalsIgnoreCase("immediate") )
        {
            return IMMEDIATE;
        }
        if( evaluation.equalsIgnoreCase("deferred") )
        {
            return DEFERRED;
        }
        throw new IllegalArgumentException("unknown evaluation kind: "+evaluation);
    }
}
