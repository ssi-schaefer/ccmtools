/* DTD code generator
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

import java.util.Vector;


/**
 * Stores XML-name and Java-Class of DTD-elements.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
class ClassTable
{
    private Vector keys_ = new Vector();
    private Vector values_ = new Vector();


    ClassTable()
    {
    }


    void put( String key, DtdClass value )
    {
        keys_.add(key);
        values_.add(value);
    }


    DtdClass get( String key )
    {
        int size = keys_.size();
        for( int index=0; index<size(); index++ )
        {
            if( ((String)keys_.get(index)).equals(key) )
            {
                return (DtdClass)values_.get(index);
            }
        }
        return null;
    }


    int size()
    {
        return keys_.size();
    }


    String key( int index )
    {
        return (String)keys_.get(index);
    }


    DtdClass value( int index )
    {
        return (DtdClass)values_.get(index);
    }
}
