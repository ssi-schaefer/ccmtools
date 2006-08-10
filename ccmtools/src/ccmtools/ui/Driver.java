/* CCM Tools : Code Generator Library
 * Leif Johnson <leif@ambient.2y.net>
 * Copyright (C) 2002, 2003 Salomon Automation
 *
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

package ccmtools.ui;

import java.util.Map;
import java.util.Set;

public interface Driver
{
    final long M_NONE              = 0;
    final long M_NODE_TRACE        = 0x0001;
    final long M_NODE_DATA         = 0x0002;
    final long M_VARIABLES         = 0x0004;
    final long M_TEMPLATE          = 0x0008;
    final long M_OUTPUT_VARIABLES  = 0x0010;
    final long M_CURRENT_VARIABLES = 0x0020;
    final long M_OUTPUT_FILE       = 0x0040;
    final long M_MESSAGE           = 0x0080;
    final long M_PREFIX            = 0x0100;
    final long M_PREFIX_INDENT     = 0x0200;

    // additional debug flags available.
    final long M_UNUSED4           = 0x0400;
    final long M_UNUSED5           = 0x0800;
    final long M_UNUSED6           = 0x1000;
    final long M_UNUSED7           = 0x2000;
    final long M_UNUSED8           = 0x4000;
    final long M_UNUSED9           = 0x8000;
    
    
    // Handle logging
    
    void nodeStart(Object node, String scope_id);
    void nodeEnd(Object node, String scope_id);
    void nodeData(Object node, String name, Object value);

    void templateContents(String template);
    void outputVariables(Map variables);
    void currentVariables(Set variables);


    // Handle user output

    void println(String str);
    void printMessage(String msg);
    void printError(String err);
}

