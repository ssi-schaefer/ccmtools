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
 * A sub-expression.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdName extends DtdContent
{
    // XML-name of the sub-expression
    private String name_;


    /**
     * Creates the sub-expression.
     *
     * @param name  XML-name of the sub-expression
     */
	public DtdName( String name )
	{
        super();
        name_ = name;
	}


	/**
	 * Returns the XML-name of the sub-expression.
	 */
	public String getName()
	{
	    return name_;
	}


    protected String makeText( short priority )
    {
        if( priority>PRIORITY_NAME )
        {
            return "("+name_+")";
        }
        return name_;
    }


    /**
     * Adds the name of this sub-expression.
     */
    public void collectElementNames( java.util.Set names )
    {
        names.add(name_);
    }


    /**
     * Returns a copy with optionally_="".
     */
    public DtdContent getCopyNotOptionally()
    {
        return new DtdName(name_);
    }
}
