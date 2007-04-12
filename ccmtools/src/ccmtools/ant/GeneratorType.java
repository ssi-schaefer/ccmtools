/* CCM Tools : ccmtools ant tasks
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

import org.apache.tools.ant.types.EnumeratedAttribute;

public class GeneratorType
    extends EnumeratedAttribute
{

    public String[] getValues()
    {
        return new String[]  
        {
                // ccmtools.parser.idl.metamodel
                "model.validator",
                "model.parser",
                
                // ccmtools.generator.idl
                "idl3",
                "idl3.mirror",
                "idl2",
                
                // ccmtools.generator.java
                "java.iface",
                "java.local",
                "java.app",
                "java.remote",
                "java.clientlib",
                
                // ccmtools.generator.deployment
                "descriptor"
        };        
    }
}
