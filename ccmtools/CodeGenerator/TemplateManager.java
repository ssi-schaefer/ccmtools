/* CCM Tools : Code Generator Library
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

package ccmtools.CodeGenerator;

import java.util.Set;

public interface TemplateManager
{
    Set getVariables(String node_type);
    Template getTemplate(String node_type, String scope_id);
    Template getRawTemplate(String node_type);
}

