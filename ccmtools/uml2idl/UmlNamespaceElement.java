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
import java.util.Vector;


/**
Container for children of {@link UmlModel} and {@link UmlPackage}. <br>Children:
<ul>
<li>{@link UmlPackage}</li>
<li>{@link UmlClass}</li>
<li>{@link UmlDataType}</li>
<li>{@link UmlConstraint}</li>
<li>{@link UmlTagDefinition}</li>
<li>{@link UmlStereotype}</li>
<li>{@link UmlAssociation}</li>
<li>{@link UmlDependency}</li>
</ul>

@author Robert Lechner (rlechner@gmx.at)
@version January 2004
*/
class UmlNamespaceElement extends ccmtools.uml_parser.uml.MNamespace_ownedElement implements Worker
{
    private String id_;
    private Vector myWorkers_ = new Vector();


    UmlNamespaceElement( Attributes attrs )
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
	    return null;
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
	            myWorkers_.add(obj);
	        }
	        Main.logChild("UmlNamespaceElement",obj);
	    }
	}


	public void makeConnections( Main main, Worker parent )
	{
	    int s = myWorkers_.size();
	    for( int index=0; index<s; index++ )
	    {
	        ((Worker)myWorkers_.get(index)).makeConnections(main, parent);
	    }
	}


	public String getIdlCode( Main main, String prefix )
	{
	    StringBuffer code = new StringBuffer();
	    int s = myWorkers_.size();
	    for( int index=0; index<s; index++ )
	    {
	        code.append( ((Worker)myWorkers_.get(index)).getIdlCode(main, prefix) );
	    }
	    return code.toString();
	}


    private int dependencyNumber_=-1;

	public int createDependencyOrder( int number, Main main )
	{
	    if( dependencyNumber_>0 )
	    {
	        return number;
	    }
	    int s = myWorkers_.size();
	    for( int index=0; index<s; index++ )
	    {
	        number = ((Worker)myWorkers_.get(index)).createDependencyOrder(number, main);
	    }
	    Main.sort(myWorkers_);
	    dependencyNumber_ = number;
	    return number+1;
	}

	public int getDependencyNumber()
	{
	    return dependencyNumber_;
	}
}
