/* UML to IDL/OCL converter
 *
 * 2004 by Robert Lechner (rlechner@gmx.at)
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

package ccmtools.uml2idl;

import org.xml.sax.Attributes;
import ccmtools.uml_parser.uml.MTaggedValue_dataValue;


/**
Container for tagged values. <br>Children:
<ul>
<li>{@link UmlModelElementName}</li>
<li>{@link ccmtools.uml_parser.uml.MTaggedValue_dataValue}</li>
</ul>

@author Robert Lechner (rlechner@gmx.at)
@version January 2004
*/
class UmlTaggedValue extends ccmtools.uml_parser.uml.MTaggedValue implements Worker
{
    private String id_;


    UmlTaggedValue( Attributes attrs )
    {
        super(attrs);
        id_ = xmi_id_;
        if( id_==null )
        {
            id_ = Main.makeId();
        }
    }


    public String getId()
    {
        return id_;
    }


	public String getName()
	{
        if( name_==null )
        {
            name_ = Main.makeModelElementName(this);
        }
	    return name_;
	}


	public void collectWorkers( java.util.HashMap map )
	{
	    map.put(id_, this);
	    int s = size();
	    for( int index=0; index<s; index++ )
	    {
	        Object obj = get(index);
	        if( obj instanceof Worker )
	        {
	            ((Worker)obj).collectWorkers(map);
	        }
	        Main.logChild("UmlTaggedValue",obj);
	    }
	}


	public void makeConnections( Main main, Worker parent )
	{
	    // nothing to do
	}


	public String getIdlCode( Main main, String prefix )
	{
	    return "";
	}


    public String getOclCode( Main main )
    {
        return "";
    }


	void addValue( java.util.HashMap map )
	{
        java.util.Vector values = findChildren(MTaggedValue_dataValue.xmlName__);
        if( values.size()>0 )
        {
            MTaggedValue_dataValue v3 = (MTaggedValue_dataValue)values.get(0);
            if( v3.size()>0 )
            {
                map.put( getName(), v3.get(0).toString() );
            }
        }
	}


	public int createDependencyOrder( int number, Main main )
	{
	    return number;
	}

	public int getDependencyNumber()
	{
	    return 0;
	}
}
