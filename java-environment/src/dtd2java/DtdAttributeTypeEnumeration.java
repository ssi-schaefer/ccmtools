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

package dtd2java;


/**
 * Attribute type 'enumeration'.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdAttributeTypeEnumeration extends DtdAttributeType
{
    // the enumeration
    private DtdEnumeration enumeration_;
    
    // true => "NOTATION"
    private boolean isNotation_;
    
    
    /**
     * Creates the attribute type 'enumeration'.
     *
     * @param enum  the enumeration
     * @param notation  true: the enumeration is of kind "NOTATION"
     */
	public DtdAttributeTypeEnumeration( DtdEnumeration enum, boolean notation )
	{
		super(null);
		enumeration_ = enum;
		isNotation_ = notation;
	}


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        if( isNotation_ )
        {
            return "NOTATION "+enumeration_.text();
        }
        return enumeration_.text();
    }
}
