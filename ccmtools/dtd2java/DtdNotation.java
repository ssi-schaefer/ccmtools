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


/**
 * A notation declaration.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdNotation
{
    private String name_;
    private String value_;


    public DtdNotation( String name, String value )
    {
        name_ = name;
        value_ = value;
    }


    public String getName()
    {
        return name_;
    }


    public String getValue()
    {
        return value_;
    }


    /**
     * Writes the XML-code and a newline.
     */
    public void write( java.io.Writer w ) throws java.io.IOException
    {
        w.write("<!NOTATION "+name_+" SYSTEM ");
        w.write(DtdFile.makeString(value_));
        w.write(" >\n\n");
    }
}
