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

public interface MComponentDef
    extends MInterfaceDef
{
    // composition: direct role: component[1] <-> opposite role: facet[*]
    Set getFacets();
    void setFacets(Set __arg);
    void addFacet(MProvidesDef __arg);
    void removeFacet(MProvidesDef __arg);

    // composition: direct role: component[1] <-> opposite role: receptacle[*]
    Set getReceptacles();
    void setReceptacles(Set __arg);
    void addReceptacle(MUsesDef __arg);
    void removeReceptacle(MUsesDef __arg);

    // composition: direct role: component[*] --> opposite role: supports[*]
    Set getSupportss();
    void setSupportss(Set __arg);
    void addSupports(MSupportsDef __arg);
    void removeSupports(MSupportsDef __arg);

    // composition: direct role: component[1] --> opposite role: emits[*]
    Set getEmitss();
    void setEmitss(Set __arg);
    void addEmits(MEmitsDef __arg);
    void removeEmits(MEmitsDef __arg);

    // composition: direct role: component[1] --> opposite role: publishes[*]
    Set getPublishess();
    void setPublishess(Set __arg);
    void addPublishes(MPublishesDef __arg);
    void removePublishes(MPublishesDef __arg);

     // composition: direct role: component[1] --> opposite role: consumes[*]
    Set getConsumess();
    void setConsumess(Set __arg);
    void addConsumes(MConsumesDef __arg);
    void removeConsumes(MConsumesDef __arg);

    // we have extended the CCM MOF specification to ease the navigation from
    // component to its home(s) ; our implementation generally encourages 1-1
    // relations between homes and components.
    //
    // association: direct role: component[1] <--> oposite role: is managed by [*]
    Set getHomes();
    void setHomes(Set __arg);
    void addHome(MHomeDef __arg);
    void removeHome(MHomeDef __arg);
}

