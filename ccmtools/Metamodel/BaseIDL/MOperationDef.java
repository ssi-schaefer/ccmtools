/* CCM Tools : CCM Metamodel Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * Copyright (C) 2002, 2003 Salomon Automation
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

package ccmtools.Metamodel.BaseIDL;

import java.util.Set;
import java.util.List;

public interface MOperationDef
    extends MContained, MTyped
{
    // attribute contexts:string
    String getContexts();
    void setContexts(String __arg);

    // attribute isOneway:boolean
    boolean isOneway();
    void setOneway(boolean __arg);

    // aggregation: direct role: operation[0..1] <>- opposite role: parameter[*]
    List getParameters();
    void setParameters(List __arg);
    void addParameter(MParameterDef __arg);
    void removeParameter(MParameterDef __arg);

    // association: direct role: [*] --> opposite role: exceptionDef[*]
    Set getExceptionDefs();
    void setExceptionDefs(Set __arg);
    void addExceptionDef(MExceptionDef __arg);
    void removeExceptionDef(MExceptionDef __arg);
}

