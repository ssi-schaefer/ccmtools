/* CCM Tools : User Interface Library
 * Leif Johnson <leif@ambient.2y.net>
 * copyright (c) 2002, 2003 Salomon Automation
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

package ccmtools.UI;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class ConsoleEnvironmentImpl
    implements Environment
{
    private Map params;

    public ConsoleEnvironmentImpl(Map defines)
    {
        params = new Hashtable(defines);

        StringBuffer defs = new StringBuffer("");
        for (Iterator i = defines.keySet().iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String val = (String) defines.get(key);
            defs.append(" -D" + key);
            if (! val.equals("")) defs.append("=" + val);
        }

        addParameter("define", defs.toString());
    }

    /**
     * Get the parameters that this Environment implementation has collected.
     * These parameters are generally used to fill out variables in a generated
     * environment file.
     *
     * @return a map containing environment variables for the current user
     *         interface environment.
     */
    public Map getParameters()
    {
        return params;
    }

    /**
     * Add a parameter to the user interface environment. If the given parameter
     * exists, replace it. Call this function with the optional third argument
     * to control replacement behavior.
     *
     * @param key the key of the environment parameter.
     * @param value the value associated with this key.
     */
    public void addParameter(Object key, Object value)
    {
        addParameter(key, value, true);
    }

    /**
     * Add a parameter to the user interface environment.
     *
     * @param key the key of the environment parameter.
     * @param value the value associated with this key.
     * @param force if true, will always set the given key to the given value.
     *        Otherwise this function will not override an existing value.
     */
    public void addParameter(Object key, Object value, boolean force)
    {
        if (force || (! params.containsKey(key)))
            params.put(key, value);
    }
}

