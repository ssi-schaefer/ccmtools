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
import ccmtools.uml_parser.uml.MMultiplicityRange_lower;
import ccmtools.uml_parser.uml.MMultiplicityRange_upper;


/**
Multiplicity-range of UML elements. <br>Children:
<ul>
<li>{@link ccmtools.uml_parser.uml.MMultiplicityRange_lower}</li>
<li>{@link ccmtools.uml_parser.uml.MMultiplicityRange_upper}</li>
</ul>
The range value '*' will be converted to '-1'.

@author Robert Lechner (rlechner@gmx.at)
@version January 2004
*/
class UmlMultiplicityRange extends ccmtools.uml_parser.uml.MMultiplicityRange implements Worker
{
    private String id_;


    UmlMultiplicityRange( Attributes attrs )
    {
        super(attrs);
        id_ = xmi_id_;
        if( id_==null )
        {
            id_ = Main.makeId();
        }
        if( lower_!=null && lower_.equals("*") )
        {
            lower_ = "-1";
        }
        if( upper_!=null && upper_.equals("*") )
        {
            upper_ = "-1";
        }
    }


    /**
     * Default range: lower_='1' upper_='1'
     */
    UmlMultiplicityRange()
    {
        super(null);
        id_ = Main.makeId();
        lower_ = upper_ = "1";
    }


	public String getName()
	{
	    return null;
	}


    public String getId()
    {
        return id_;
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
	        Main.logChild("UmlMultiplicityRange",obj);
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


    public String getLower() throws IllegalStateException
    {
        if( lower_==null )
        {
            java.util.Vector v = findChildren(MMultiplicityRange_lower.xmlName__);
            if( v.size()>0 )
            {
                lower_ = ((MMultiplicityRange_lower)v.get(0)).get(0).toString().trim();
                if( lower_.equals("*") )
                {
                    lower_ = "-1";
                }
            }
            else
            {
                lower_ = "0";
            }
        }
        return lower_;
    }


    public String getUpper() throws IllegalStateException
    {
        if( upper_==null )
        {
            java.util.Vector v = findChildren(MMultiplicityRange_upper.xmlName__);
            if( v.size()>0 )
            {
                upper_ = ((MMultiplicityRange_upper)v.get(0)).get(0).toString().trim();
                if( upper_.equals("*") )
                {
                    upper_ = "-1";
                }
            }
            else
            {
                upper_ = "-1";
            }
        }
        return upper_;
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
