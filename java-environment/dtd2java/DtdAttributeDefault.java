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
 * The default value of an attribute.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdAttributeDefault
{
    /**
     * For attributes which are required (=> no need for a default value).
     */
    public static final DtdAttributeDefault REQUIRED = new DtdAttributeDefault("#REQUIRED",null);

    /**
     * For attributes which are optional (=> no need for a default value).
     */
    public static final DtdAttributeDefault IMPLIED = new DtdAttributeDefault("#IMPLIED",null);


    // type ("#REQUIRED", "#IMPLIED", "#FIXED" or null)
    private String type_;

    // value (or null)
    private String value_;


    private DtdAttributeDefault( String type, String value )
    {
        type_ = type;
        value_ = value;
    }


    /**
     * Returns the value (or null).
     */
    public String getValue()
    {
        return value_;
    }


    /**
     * Creates a normal default value.
     */
    public static DtdAttributeDefault createDefault( String value )
    {
        return new DtdAttributeDefault(null,value);
    }


    /**
     * Creates a 'fixed' value.
     */
    public static DtdAttributeDefault createFixed( String value )
    {
        return new DtdAttributeDefault("#FIXED",value);
    }


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        if( type_==null )
        {
            return DtdFile.makeString(value_);
        }
        if( value_==null )
        {
            return type_;
        }
        return type_+" "+DtdFile.makeString(value_);
    }
}
