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
import mof_xmi_parser.model.MFeature_scope;


/**
 * ScopeKind
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public final class MofScopeKind
{
    private String enumeration_kind_;

    private MofScopeKind( String kind )
    {
        enumeration_kind_ = kind;
    }

    public String toString()
    {
        return enumeration_kind_;
    }


    /**
     *  classifier_level
     */
    public static final MofScopeKind CLASSIFIER = new MofScopeKind("classifier_level");

    /**
     *  instance_level
     */
    public static final MofScopeKind INSTANCE = new MofScopeKind("instance_level");


    static MofScopeKind create( MFeature_scope scope )
    {
        // TODO
        throw new RuntimeException("not implemented");
    }


    /**
     * Returns {@link CLASSIFIER} or {@link INSTANCE}.
     *
     * @throws IllegalArgumentException  unknown scope kind
     */
    public static MofScopeKind create( String scope ) throws IllegalArgumentException
    {
        if( scope.equalsIgnoreCase("classifier_level") || scope.equalsIgnoreCase("classifier") )
        {
            return CLASSIFIER;
        }
        if( scope.equalsIgnoreCase("instance_level") || scope.equalsIgnoreCase("instance") )
        {
            return INSTANCE;
        }
        throw new IllegalArgumentException("unknown scope kind: "+scope);
    }
}
