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

import java.util.Vector;


/**
 * An enumeration.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdEnumeration
{
    /**
     * stores instances of {@link java.lang.String}
     */
    private Vector values_;


    /**
     * Creates an enumeration.
     *
     * @param values  an array of instances of {@link java.lang.String}
     */
	public DtdEnumeration( Vector values )
	{
	    values_ = values;
	}


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        String result = "(";
        int size = values_.size();
        for( int index=0; index<size; index++ )
        {
            if( index>0 )
            {
                result += " | ";
            }
            result += (String)values_.get(index);
        }
        return result+")";
    }
}
