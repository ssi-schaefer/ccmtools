/* CCM Tools : ant tasks
 * Egon Teiniker <egon.teiniker@fh-joanneum.at>
 * Copyright (C) 2002 - 2007 ccmtools.sourceforge.net
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

package ccmtools.ant;

import org.apache.tools.ant.types.Path;

/**
 * This class implements the nested <include> element of the <ccmtools>
 * ant task.
 */
public class IncludePath
{   
    /** 
     * Attribute: path 
     * Specifies a Path object which is used to define include paths for a
     * ccmtools generator call.
     */
    private Path path;
    public void setPath(Path path)
    {
        this.path = path;
    }

    /**
     * Getter method that returns all stored include paths as a string array.
     */
    public String[] getPaths()
    {
        return path.list();
    }
    
            
    public String toString()
    {
        return path.toString();
    }
}
