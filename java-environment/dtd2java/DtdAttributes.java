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

import java.util.Vector;


/**
 * The attribute list of a XML element.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdAttributes
{
    // XML-name
    private String name_;

    // stores instances of {@link DtdAttribute}
    private Vector attributes_;


    /**
     * Creates a list of attributes.
     *
     * @param name  the XML-name of the attribute list
     * @param attributes  an array of instances of {@link DtdAttribute}
     */
    public DtdAttributes( String name, Vector attributes )
    {
        name_ = name;
        attributes_ = attributes;
    }


    /**
     * Returns the number of attributes.
     */
    public int size()
    {
        return attributes_.size();
    }


    /**
     * Returns one attribute.
     */
    public DtdAttribute get( int index ) throws ArrayIndexOutOfBoundsException
    {
        return (DtdAttribute)attributes_.get(index);
    }


    /**
     * Merges this list with another one.
     */
    public void merge( DtdAttributes attr )
    {
        attributes_.addAll(attr.attributes_);
    }


    /**
     * Returns the XML-name of the attribute list.
     */
    public String getName()
    {
        return name_;
    }


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        String result = "<!ATTLIST "+name_+"\n";
        int size = attributes_.size();
        for( int index=0; index<size; index++ )
        {
            DtdAttribute attr = (DtdAttribute)attributes_.get(index);
            result += "\t"+attr.text()+"\n";
        }
        return result+">\n";
    }


    /**
     * Writes the XML-code and a newline.
     */
    public void write( java.io.Writer w ) throws java.io.IOException
    {
        w.write("<!ATTLIST "+name_+"\n");
        int size = attributes_.size();
        for( int index=0; index<size; index++ )
        {
            DtdAttribute attr = (DtdAttribute)attributes_.get(index);
            w.write("\t");
            attr.write(w);
        }
        w.write(">\n\n");
    }
}
