/* CCM Tools : CCM Metamodel Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

package ccmtools.Metamodel.ComponentIDL;

import java.util.Set;

import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MValueDef;

public interface MHomeDef
    extends MInterfaceDef
{
    // association: direct role: [*] --> opposite role: supports[*]
    Set getSupportss();
    void setSupportss(Set __arg);
    void addSupports(MSupportsDef __arg);
    void removeSupports(MSupportsDef __arg);

    // association: direct role: home[*] --> opposite role: component[1]
    MComponentDef getComponent();
    void setComponent(MComponentDef __arg);

    // association: direct role: home[*] --> opposite role: primary_key[0..1]
    MValueDef getPrimary_Key();
    void setPrimary_Key(MValueDef __arg);

    // association: direct role: home [*] <-> opposite role: factory[*]
    Set getFactories();
    void setFactories(Set __arg);
    void addFactory(MFactoryDef __arg);
    void removeFactory(MFactoryDef __arg);

    // association: direct role: home [*] <-> opposite role: finder[*]
    Set getFinders();
    void setFinders(Set __arg);
    void addFinder(MFinderDef __arg);
    void removeFinder(MFinderDef __arg);
}

