/* DTD parser
 *
 * 2003 by Robert Lechner (rlechner@gmx.at)
 *
 * $Id$
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ccmtools.dtd2java;


/**
 * A content of kind "ANY", "EMPTY" or "#PCDATA".
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdConstant extends DtdContent
{
    /**
     * Content kind "ANY".
     */
    public static final DtdConstant ANY = new DtdConstant("ANY", PRIORITY_ANY);

    /**
     * Content kind "EMPTY".
     */
    public static final DtdConstant EMPTY = new DtdConstant("EMPTY", PRIORITY_EMPTY);


    /**
     * Creates a content of kind "#PCDATA".
     */
    public static DtdConstant getPCDATA()
    {
        return new DtdConstant("#PCDATA", PRIORITY_PCDATA);
    }


    // "ANY", "EMPTY" or "#PCDATA"
    private String value_;

    // PRIORITY_ANY, PRIORITY_EMPTY or PRIORITY_PCDATA
    private short priority_;


    private DtdConstant( String value, short priority )
    {
        super();
        value_ = value;
        priority_ = priority;
    }


    /**
     * Returns a copy with optionally_="".
     */
    public DtdContent getCopyNotOptionally()
    {
        return new DtdConstant(value_, priority_);
    }


    /**
     * Returns "ANY", "EMPTY" or "#PCDATA".
     */
    public String getName()
    {
        return value_;
    }


    public boolean equals( Object ref )
    {
        if( ref==this )
        {
            return true;
        }
        if( ref!=null && (ref instanceof DtdConstant) )
        {
            return ((DtdConstant)ref).value_.equals(value_);
        }
        return false;
    }


    protected String makeText( short priority )
    {
        if( priority>priority_ )
        {
            return "("+value_+")";
        }
        return value_;
    }


    public void collectElementNames( java.util.Set names )
    {
        // nothing to do
    }
}
