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
 * Base class of all content declarations.
 *
 * @author Robert Lechner (rlechner@gmx.at)
 * @version November 2003
 */
public abstract class DtdContent
{
    /**
     * priority of {@link DtdComma}
     */
    public static final short PRIORITY_COMMA  = 1;

    /**
     * priority of {@link DtdBar}
     */
    public static final short PRIORITY_BAR    = 2;

    /**
     * priority of "#PCDATA"
     */
    public static final short PRIORITY_PCDATA = 3;

    /**
     * priority of {@link DtdName}
     */
    public static final short PRIORITY_NAME   = 3;

    /**
     * priority of the start node
     */
    public static final short PRIORITY_START  = 10;

    /**
     * priority of {@link DtdConstant#ANY}
     */
    public static final short PRIORITY_ANY    = 100;

    /**
     * priority of {@link DtdConstant#EMPTY}
     */
    public static final short PRIORITY_EMPTY  = 100;


    /**
     * "", "?", "+" or "*"
     */
    protected String optionally_;


    protected DtdContent()
    {
        optionally_ = "";
    }


    /**
     * Returns true if optionally_="*" or optionally_="+".
     */
    public boolean isCollection()
    {
        return optionally_.equals("*") || optionally_.equals("+");
    }


    /**
     * Returns false if optionally_="".
     */
    public boolean isOptionally()
    {
        return optionally_.length()>0;
    }


    /**
     * Returns "", "?", "+" or "*".
     */
    public String getOptionallyKey()
    {
        return optionally_;
    }


    public void mergeOptionally( String opt )
    {
        if( opt==null || opt.length()==0 )
        {
            return;
        }
        if( optionally_.length()==0 )
        {
            optionally_ = opt;
        }
        else if( !optionally_.equals(opt) )
        {
            optionally_ = "*";
        }
    }


    /**
     * Returns the XML-code.
     */
    public String text()
    {
        return text(PRIORITY_START);
    }


    protected String text( short priority )
    {
        if( optionally_.length()==0 )
        {
            return makeText(priority);
        }
        else
        {
            return "("+makeText((short)0)+")"+optionally_;
        }
    }


    /**
     * Collects all sub-expressions and constants.
     */
    public void expand( java.util.Vector buffer )
    {
        buffer.add(this);
    }


    protected abstract String makeText( short priority );

    /**
     * Collects the names of the sub-expressions.
     */
    public abstract void collectElementNames( java.util.Set names );

    /**
     * Returns a copy with optionally_="".
     */
    public abstract DtdContent getCopyNotOptionally();
}
