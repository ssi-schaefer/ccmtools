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

import java.util.Vector;
import org.xml.sax.Attributes;
import ccmtools.uml_parser.uml.MDependency_supplier;
import ccmtools.uml_parser.uml.MModelElement;


/**
Dependency between UML classes. <br>Children:
<ul>
<li>{@link ccmtools.uml_parser.uml.MDependency_client}</li>
<li>{@link ccmtools.uml_parser.uml.MDependency_supplier}</li>
</ul>

@author Robert Lechner (rlechner@gmx.at)
@version January 2004
*/
class UmlDependency extends ccmtools.uml_parser.uml.MDependency implements Worker
{
    private String id_;


    UmlDependency( Attributes attrs )
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
	        Main.logChild("UmlDependency",obj);
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
	    Object parent = getSupplier(main);
        if( parent!=null && (parent instanceof IdlContainer) )
        {
            return ((IdlContainer)parent).updateDependencyOrder(number, main);
        }
	    return number;
	}

	public int getDependencyNumber()
	{
	    return 0;
	}


	Object getSupplier( Main main )
	{
	    if( xmi_idref_!=null )
	    {
            Object obj = main.workers_.get(xmi_idref_);
            if( obj==null )
            {
                return null;
            }
            return ((UmlDependency)obj).getSupplier(main);
	    }
	    if( supplier_==null )
	    {
	        Vector v1 = findChildren(MDependency_supplier.xmlName__);
	        if( v1.size()<1 )
	        {
	            return null;
	        }
            MDependency_supplier p1 = (MDependency_supplier)v1.get(0);
            Vector v2 = p1.findChildren(MModelElement.xmlName__);
            if( v2.size()<1 )
            {
                return null;
            }
            MModelElement e1 = (MModelElement)v2.get(0);
            supplier_ = e1.xmi_idref_;
            if( supplier_==null )
            {
                return null;
            }
	    }
	    return main.workers_.get(supplier_);
	}
}
