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
 * The type of an attribute.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdAttributeType
{
    /**
     * XML-type "CDATA"
     */
    public static final DtdAttributeType CDATA = new DtdAttributeType("CDATA");

    /**
     * XML-type "ENTITY"
     */
    public static final DtdAttributeType ENTITY = new DtdAttributeType("ENTITY");

    /**
     * XML-type "ENTITIES"
     */
    public static final DtdAttributeType ENTITIES = new DtdAttributeType("ENTITIES");

    /**
     * XML-type "ID"
     */
    public static final DtdAttributeType ID = new DtdAttributeType("ID");

    /**
     * XML-type "IDREF"
     */
    public static final DtdAttributeType IDREF = new DtdAttributeType("IDREF");

    /**
     * XML-type "IDREFS"
     */
    public static final DtdAttributeType IDREFS = new DtdAttributeType("IDREFS");

    /**
     * XML-type "NMTOKEN"
     */
    public static final DtdAttributeType NMTOKEN = new DtdAttributeType("NMTOKEN");

    /**
     * XML-type "NMTOKENS"
     */
    public static final DtdAttributeType NMTOKENS = new DtdAttributeType("NMTOKENS");


    /**
     * The XML-attribute-type.
     */
    protected String name_;


    /**
     * Creates an attribute type.
     *
     * @param name  the XML-attribute-type
     */
    protected DtdAttributeType( String name )
    {
        name_ = name;
    }


    /**
     * Returns the XML-attribute-type.
     */
    public String text()
    {
        return name_;
    }
}
