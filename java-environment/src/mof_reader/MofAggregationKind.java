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
import mof_xmi_parser.model.MAssociationEnd_aggregation;


/**
 * AggregationKind
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public final class MofAggregationKind
{
    private String enumeration_kind_;

    private MofAggregationKind( String kind )
    {
        enumeration_kind_ = kind;
    }

    public String toString()
    {
        return enumeration_kind_;
    }


    /**
     *  none
     */
    public static final MofAggregationKind NONE = new MofAggregationKind("none");

    /**
     *  shared
     */
    public static final MofAggregationKind SHARED = new MofAggregationKind("shared");

    /**
     *  composite
     */
    public static final MofAggregationKind COMPOSITE = new MofAggregationKind("composite");


    static MofAggregationKind create( MAssociationEnd_aggregation aggregation )
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    /**
     * Returns {@link NONE}, {@link SHARED} or {@link COMPOSITE}.
     *
     * @throws IllegalArgumentException  unknown aggregation kind
     */
    public static MofAggregationKind create( String aggregation ) throws IllegalArgumentException
    {
        if( aggregation.equalsIgnoreCase("none") )
        {
            return NONE;
        }
        if( aggregation.equalsIgnoreCase("shared") )
        {
            return SHARED;
        }
        if( aggregation.equalsIgnoreCase("composite") )
        {
            return COMPOSITE;
        }
        throw new IllegalArgumentException("unknown aggregation kind: "+aggregation);
    }
}
