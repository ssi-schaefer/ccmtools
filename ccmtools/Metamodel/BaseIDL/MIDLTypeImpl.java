/* CCM Tools : CCM Metamodel Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003 Salomon Automation
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

package ccmtools.Metamodel.BaseIDL;

import org.omg.CORBA.TypeCode;

public class MIDLTypeImpl
    implements MIDLType
{
    private TypeCode typeCode_;

    // override toString()
    public String toString()
    {
	return "MIDLType( typeCode): " + typeCode_.toString();
    }


    //----------------------------------------------------------------
    // implementation of attribute access
    //----------------------------------------------------------------

    // attribute typeCode:TypeCode
    public TypeCode getTypeCode()              {return typeCode_;}
    public void setTypeCode(TypeCode __arg)    {typeCode_ = __arg;}
}
