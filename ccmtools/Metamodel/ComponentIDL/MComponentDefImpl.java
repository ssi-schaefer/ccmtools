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
import java.util.HashSet;
import java.util.Iterator;

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MDefinitionKind;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MModuleDef;
import ccmtools.Metamodel.BaseIDL.MValueDef;

public class MComponentDefImpl
    implements MComponentDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_COMPONENT;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;

    private boolean isAbstract;
    private boolean isLocal;
    private boolean isForwardDeclaration = false;
    private boolean isDefinedInOriginalFile;

    private Set HomeSet_;
    private Set FacetSet_;
    private Set ReceptacleSet_;
    private Set SupportsSet_;
    private Set EmitsSet_;
    private Set PublishesSet_;
    private Set ConsumesSet_;
    private MIDLType TypedBy_;
    private MContainer Contains;
    private Set ContainsSet;
    private Set InterfaceDerivedFromSet;

    public MComponentDefImpl()
    {
	HomeSet_ = new HashSet();
	FacetSet_ = new HashSet();
	ReceptacleSet_ = new HashSet();
	SupportsSet_ = new HashSet();
	EmitsSet_ = new HashSet();
	PublishesSet_ = new HashSet();
	ConsumesSet_ = new HashSet();
	ContainsSet = new HashSet();
	InterfaceDerivedFromSet = new HashSet();
    }

    // override toString()
    public String toString()
    {
	return "MComponentDef: "+ identifier;
    }

    //----------------------------------------------------------------
    // implementation of attribute access
    //----------------------------------------------------------------

    // read-only attribute definitionKind:ccmtools.Metamodel.BaseIDL.MDefinitionKind
    public MDefinitionKind getDefinitionKind()  {return definitionKind;}

    // attribute absoluteName:String
    public String getAbsoluteName()             {return absoluteName;}
    public void setAbsoluteName(String __arg)   {absoluteName = __arg;}

    // attribute identifier:String
    public String getIdentifier()               {return identifier;}
    public void setIdentifier(String __arg)     {identifier = __arg;}

    // attribute repositoryId:String
    public String getRepositoryId()             {return repositoryId;}
    public void setRepositoryId(String __arg)   {repositoryId = __arg;}

    // attribute version:String
    public String getVersion()                  {return version;}
    public void setVersion(String __arg)        {version = __arg;}

    // attribute isAbstract:boolean
    public boolean isAbstract()                 {return isAbstract;}
    public void setAbstract(boolean __arg)      {isAbstract = __arg;}

    // attribute isLocal:boolean
    public boolean isLocal()                    {return isLocal;}
    public void setLocal(boolean __arg)         {isLocal = __arg;}

    // attribute isDefinedInOriginalFile:boolean
    public boolean isDefinedInOriginalFile()            {return isDefinedInOriginalFile;}
    public void setDefinedInOriginalFile(boolean __arg) {isDefinedInOriginalFile = __arg;}

    // attribute isForwardDeclaration:boolean
    public boolean isForwardDeclaration()            {return isForwardDeclaration;}
    public void setForwardDeclaration(boolean __arg) {isForwardDeclaration = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // association: direct role: [*] --> opposite role: idlType[1]
    public MIDLType getIdlType()                {return TypedBy_;}
    public void setIdlType(MIDLType __arg)      {TypedBy_ = __arg;}

    // composition: direct role: component[1] <-> opposite role: facet[*]
    public Set getFacets()                      {return FacetSet_;}
    public void setFacets(Set __arg)            {FacetSet_ = new HashSet(__arg);}
    public void addFacet(MProvidesDef __arg)    {FacetSet_.add(__arg);}
    public void removeFacet(MProvidesDef __arg) {FacetSet_.remove(__arg);}

    // composition: direct role: component[1] <-> opposite role: receptacle[*]
    public Set getReceptacles()                  {return ReceptacleSet_;}
    public void setReceptacles(Set __arg)        {ReceptacleSet_ = new HashSet(__arg);}
    public void addReceptacle(MUsesDef __arg)    {ReceptacleSet_.add(__arg);}
    public void removeReceptacle(MUsesDef __arg) {ReceptacleSet_.remove(__arg);}

    // association: direct role: component[*] --> opposite role: supports[*]
    public Set getSupportss()                      {return SupportsSet_;}
    public void setSupportss(Set __arg)            {SupportsSet_ = new HashSet(__arg);}
    public void addSupports(MSupportsDef __arg)    {SupportsSet_.add(__arg);}
    public void removeSupports(MSupportsDef __arg) {SupportsSet_.remove(__arg);}

    // composition: direct role: component[1] --> opposite role: emits[*]
    public Set getEmitss()                      {return EmitsSet_;}
    public void setEmitss(Set __arg)            {EmitsSet_ = new HashSet(__arg);}
    public void addEmits(MEmitsDef __arg)       {EmitsSet_.add(__arg);}
    public void removeEmits(MEmitsDef __arg)    {EmitsSet_.remove(__arg);}

    // composition: direct role: component[1] --> opposite role: publishes[*]
    public Set getPublishess()                       {return PublishesSet_;}
    public void setPublishess(Set __arg)             {PublishesSet_ = new HashSet(__arg);}
    public void addPublishes(MPublishesDef __arg)    {PublishesSet_.add(__arg);}
    public void removePublishes(MPublishesDef __arg) {PublishesSet_.remove(__arg);}

     // composition: direct role: component[1] --> opposite role: consumes[*]
    public Set getConsumess()                      {return ConsumesSet_;}
    public void setConsumess(Set __arg)            {ConsumesSet_ = new HashSet(__arg);}
    public void addConsumes(MConsumesDef __arg)    {ConsumesSet_.add(__arg);}
    public void removeConsumes(MConsumesDef __arg) {ConsumesSet_.remove(__arg);}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}

    // assocation: direct role: definedIn[0..1] <-> oposide role: contents[*]
    public Set getContentss()                    {return ContainsSet;}
    public void setContentss(Set __arg)          {ContainsSet = (__arg != null) ? new HashSet(__arg) : null;}
    public void addContents(MContained __arg)    {ContainsSet.add(__arg);}
    public void removeContents(MContained __arg) {ContainsSet.remove(__arg);}

    // association: direct role: [*] --> opposite role: base[*]
    public Set getBases()                       {return InterfaceDerivedFromSet;}
    public void setBases(Set __arg)             {InterfaceDerivedFromSet = new HashSet(__arg);}
    public void addBase(MInterfaceDef __arg)    {InterfaceDerivedFromSet.add(__arg);}
    public void removeBase(MInterfaceDef __arg) {InterfaceDerivedFromSet.remove(__arg);}

    // we have extended the CCM MOF specification to ease the navigation from
    // component to its home(s) ; our implementation generally encourages 1-1
    // relations between homes and components.
    //
    // association: direct role: component[1] <--> oposite role: is managed by [*]
    public Set getHomes()                       {return HomeSet_;}
    public void setHomes(Set __arg)             {HomeSet_ = new HashSet(__arg);}
    public void addHome(MHomeDef __arg)         {HomeSet_.add(__arg);}
    public void removeHome(MHomeDef __arg)      {HomeSet_.remove(__arg);}

    //----------------------------------------------------------------
    // implementation of operations
    //----------------------------------------------------------------

    public MContained getFilteredContents(MDefinitionKind limitToType,
                                          boolean includeInherited)
    {
	return null;
    }

    // a helper function for the lookupName function below.
    private MContainer checkContainer(MContained elem,
                                      boolean excludeInherited)
    {
        if ((elem instanceof MContainer) &&
            ((! excludeInherited) ||
             ((! ((elem instanceof MModuleDef) ||
                  (elem instanceof MInterfaceDef) ||
                  (elem instanceof MValueDef)))))) {
            return (MContainer) elem;
        }

        return null;
    }

    // a helper function for the lookupName function below.
    private MContained searchSet(Set set, String searchName,
                                 long levelsToSearch,
                                 MDefinitionKind limitToType,
                                 boolean excludeInherited)
    {
        MContained elem;
        Iterator it = set.iterator();

        while (it.hasNext()) {
            elem = (MContained) it.next();

            if ((limitToType == null)
                || (elem.getDefinitionKind() == limitToType)) {

                if (searchName.equals(elem.getIdentifier()))
                    return elem;

                MContained subelem = null;
                MContainer container = checkContainer(elem, excludeInherited);

                if (container != null) {
                    subelem = container.lookupName(searchName,
                                                   levelsToSearch - 1,
                                                   limitToType,
                                                   excludeInherited);
                    if (subelem != null)
                        return subelem;
                }
            }
        }

        return null;
    }

    /**
     * Look up a descendant node of this node using the given name, using the
     * entire subgraph of the current element as a search space.
     *
     * @param searchName a string containing the name (identifier) of the
     *        desired metamodel element.
     * @return the element specified, or null if no such element was located.
     */
    public MContained lookup(String searchName)
    {
        return lookupName(searchName, 4294967296L, (MDefinitionKind) null, false);
    }

    /**
     * Look up a descendant node of this node using the given name, search
     * depth, and type limits.
     *
     * @param searchName a string containing the name (identifier) of the
     *        desired metamodel element.
     * @param levelsToSearch the number of levels in the metamodel graph to
     *        search. If this is negative the function will return immediately.
     * @param limitToType the desired type of the object. This can speed up the
     *        search by excluding checks for irrelevant element types.
     * @param excludeInherited will only search for elements contained in
     *        elements of the current node type.
     * @return the element specified, or null if no such element was located.
     */
    public MContained lookupName(String searchName, long levelsToSearch,
                                 MDefinitionKind limitToType,
                                 boolean excludeInherited)
    {
        if (levelsToSearch < 0) { return null; }

        MContained elem = null;

        elem = searchSet(ContainsSet, searchName, levelsToSearch,limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(FacetSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(ReceptacleSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(SupportsSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(EmitsSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(PublishesSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(ConsumesSet_, searchName, levelsToSearch, limitToType, excludeInherited);

        if (elem != null) return elem;

	return null;
    }
}

