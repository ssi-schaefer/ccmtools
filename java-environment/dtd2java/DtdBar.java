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


/**
 * A content of kind "(expr1 | expr2)".
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public class DtdBar extends DtdContent
{
    private DtdContent parameter1_;
    private DtdContent parameter2_;


    /**
     * Creates a content of kind "(expr1 | expr2)".
     */
	public DtdBar( DtdContent expr1, DtdContent expr2 )
	{
        super();
        parameter1_ = expr1;
        parameter2_ = expr2;
	}


    /**
     * Returns a copy with optionally_="".
     */
    public DtdContent getCopyNotOptionally()
    {
        return new DtdBar(parameter1_, parameter2_);
    }


    protected String makeText( short priority )
    {
        if( priority>PRIORITY_BAR )
        {
            return "("+parameter1_.text(PRIORITY_BAR)+"|"+parameter2_.text(PRIORITY_BAR)+")";
        }
        return parameter1_.text(PRIORITY_BAR)+"|"+parameter2_.text(PRIORITY_BAR);
    }


    /**
     * Collects the names of the sub-expressions.
     */
    public void collectElementNames( java.util.Set names )
    {
        parameter1_.collectElementNames(names);
        parameter2_.collectElementNames(names);
    }


    /**
     * Collects all sub-expressions and constants.
     */
    public void expand( java.util.Vector buffer )
    {
        parameter1_.expand(buffer);
        parameter2_.expand(buffer);
    }
}
