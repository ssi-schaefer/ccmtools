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


    /**
     * Returns {@link CLASSIFIER} or {@link INSTANCE}.
     *
     * @throws IllegalArgumentException  unknown scope kind
     */
    public static MofScopeKind create( String scope ) throws IllegalArgumentException
    {
        if( scope==null )
        {
            throw new IllegalArgumentException("no scope kind");
        }
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
