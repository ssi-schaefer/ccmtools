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
 * The attribute of a XML element.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdAttribute
{
    // XML-name
    private String name_;
    
    // type
    private DtdAttributeType type_;
    
    // default value
    private DtdAttributeDefault default_;

    /**
     * Java variable name.
     */
    String javaName_;


    /**
     * Creates a XML attribute.
     *
     * @param name  the XML-name of the attribute
     * @param type  the type of the attribute
     * @param def   the default value of the attribute
     */
    public DtdAttribute( String name, DtdAttributeType type, DtdAttributeDefault def )
    {
        name_ = name;
        type_ = type;
        default_ = def;
    }


    /**
     * Returns the XML-name.
     */
    public String getName()
    {
        return name_;
    }


    /**
     * Returns the default value (or null).
     */
    public String getDefaultValue()
    {
        return default_.getValue();
    }


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        return name_+" "+type_.text()+" "+default_.text();
    }


    /**
     * Writes the XML-code and a newline.
     */
    public void write( java.io.Writer w ) throws java.io.IOException
    {
        w.write(text()+"\n");
    }
}
