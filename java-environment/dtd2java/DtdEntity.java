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
 * A DTD entity declaration.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdEntity
{
    // XML-name
    private String name_;

    // XML-code
    private String value_;

    // "NDATA" value (or null)
    private String ndata_;

    // true: parameter entity ("%")
    private boolean isParameterEntity_;

    // true: system entity ("SYSTEM")
    private boolean isSystemEntity_;


    /**
     * Creates an entity declaration.
     *
     * @param name  XML-name of the entity
     * @param value  XML-code
     * @param parameter  true: entity is of kind "%"
     * @param system  true: entity is of kind "SYSTEM"
     * @param ndata  "NDATA" value (or null)
     */
    public DtdEntity( String name, String value, boolean parameter, boolean system, String ndata )
    {
        name_ = name;
        value_ = value;
        ndata_ = ndata;
        isParameterEntity_ = parameter;
        isSystemEntity_ = system;
    }


    /**
     * Returns the XML-name.
     */
	public String getName()
	{
		return name_;
	}


    /**
     * Returns the XML-code.
     */
	public String getValue()
	{
		return value_;
	}


    /**
     * Writes the XML-code and a newline.
     */
    public void write( java.io.Writer w ) throws java.io.IOException
    {
        w.write("<!ENTITY ");
        if( isParameterEntity_ )
        {
            w.write("% ");
        }
        w.write(name_+" ");
        if( isSystemEntity_ )
        {
            w.write("SYSTEM ");
        }
        w.write(DtdFile.makeString(value_)+" ");
        if( ndata_!=null )
        {
            w.write("NDATA "+ndata_+" ");
        }
        w.write(">\n\n");
    }
}
