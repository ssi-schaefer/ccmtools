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
 * DirectionKind
 *
 * @author Robert Lechner (robert.lechner@salomon.at)
 * @version $Date$
 */
public final class MofDirectionKind
{
    private String enumeration_kind_;

    private MofDirectionKind( String kind )
    {
        enumeration_kind_ = kind;
    }

    public String toString()
    {
        return enumeration_kind_;
    }


    /**
     *  in_dir
     */
    public static final MofDirectionKind IN = new MofDirectionKind("in_dir");

    /**
     *  out_dir
     */
    public static final MofDirectionKind OUT = new MofDirectionKind("out_dir");

    /**
     *  inout_dir
     */
    public static final MofDirectionKind INOUT = new MofDirectionKind("inout_dir");

    /**
     *  return_dir
     */
    public static final MofDirectionKind RETURN = new MofDirectionKind("return_dir");


    /**
     * Returns {@link IN}, {@link OUT}, {@link INOUT} or {@link RETURN}.
     *
     * @throws IllegalArgumentException  unknown direction kind
     */
    public static MofDirectionKind create( String direction ) throws IllegalArgumentException
    {
        if( direction==null )
        {
            throw new IllegalArgumentException("no direction kind");
        }
        if( direction.equalsIgnoreCase("in_dir") || direction.equalsIgnoreCase("in") )
        {
            return IN;
        }
        if( direction.equalsIgnoreCase("out_dir") || direction.equalsIgnoreCase("out") )
        {
            return OUT;
        }
        if( direction.equalsIgnoreCase("inout_dir") || direction.equalsIgnoreCase("inout") )
        {
            return INOUT;
        }
        if( direction.equalsIgnoreCase("return_dir") || direction.equalsIgnoreCase("return") )
        {
            return RETURN;
        }
        throw new IllegalArgumentException("unknown direction kind: "+direction);
    }
}
