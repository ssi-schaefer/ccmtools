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


/**
The name of a model element. <br>Children:
<ul>
<li>{@link java.lang.String}</li>
</ul>

@author Robert Lechner (rlechner@gmx.at)
@version January 2004
*/
class UmlModelElementName extends ccmtools.uml_parser.uml.MModelElement_name implements Worker
{
    private String id_;
    private String name_;


    UmlModelElementName( Attributes attrs )
    {
        super(attrs);
        id_ = Main.makeId();
    }


    public String getId()
    {
        return id_;
    }


	public String getName()
	{
	    if( name_==null )
	    {
	        if( size()>0 )
	        {
	            name_ = get(0).toString();
	        }
	    }
	    return name_;
	}


    /**
     * Puts all children of type 'Worker' into the map.
     * The key value is the unique identifier.
     */
	public void collectWorkers( java.util.HashMap map )
	{
	    int s = size();
	    for( int index=0; index<s; index++ )
	    {
	        Object obj = get(index);
	        if( obj instanceof Worker )
	        {
	            ((Worker)obj).collectWorkers(map);
	        }
	        Main.logChild("UmlModelElementName",obj);
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


	public int createDependencyOrder( int number, Main main )
	{
	    return number;
	}

	public int getDependencyNumber()
	{
	    return 0;
	}
}
