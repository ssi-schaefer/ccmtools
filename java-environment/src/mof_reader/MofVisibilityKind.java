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
 * VisibilityKind
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public final class MofVisibilityKind
{
    private String enumeration_kind_;

    private MofVisibilityKind( String kind )
    {
        enumeration_kind_ = kind;
    }

    public String toString()
    {
        return enumeration_kind_;
    }


    /**
     *  public_vis
     */
    public static final MofVisibilityKind PUBLIC = new MofVisibilityKind("public_vis");

    /**
     *  private_vis
     */
    public static final MofVisibilityKind PRIVATE = new MofVisibilityKind("private_vis");

    /**
     *  protected_vis
     */
    public static final MofVisibilityKind PROTECTED = new MofVisibilityKind("protected_vis");


    /**
     * Returns {@link PUBLIC}, {@link PRIVATE} or {@link PROTECTED}.
     *
     * @throws IllegalArgumentException  unknown visibility kind
     */
    public static MofVisibilityKind create( String visibility ) throws IllegalArgumentException
    {
        if( visibility==null )
        {
            throw new IllegalArgumentException("no visibility kind");
        }
        if( visibility.equalsIgnoreCase("public_vis") || visibility.equalsIgnoreCase("public") )
        {
            return PUBLIC;
        }
        if( visibility.equalsIgnoreCase("private_vis") || visibility.equalsIgnoreCase("private") )
        {
            return PRIVATE;
        }
        if( visibility.equalsIgnoreCase("protected_vis") || visibility.equalsIgnoreCase("protected") )
        {
            return PROTECTED;
        }
        throw new IllegalArgumentException("unknown visibility kind: "+visibility);
    }
}
