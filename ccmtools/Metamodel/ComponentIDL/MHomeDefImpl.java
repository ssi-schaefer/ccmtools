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

public class MHomeDefImpl
    implements MHomeDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_HOME;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;

    private boolean isAbstract;
    private boolean isLocal;
    private boolean isForwardDeclaration = false;
    private boolean isDefinedInOriginalFile;

    private Set SupportsSet_;
    private Set FactorySet_;
    private Set FinderSet_;
    private MComponentDef Component_;
    private MValueDef PrimaryKey_;
    private MContainer Contains;
    private MIDLType TypedBy_;
    private Set ContainsSet;
    private Set InterfaceDerivedFromSet;

    public MHomeDefImpl()
    {
	 SupportsSet_= new HashSet();
	 FactorySet_= new HashSet();
	 FinderSet_ = new HashSet();
	 ContainsSet = new HashSet();
	 InterfaceDerivedFromSet = new HashSet();
    }

    // override toString()
    public String toString()
    {
	return "MHomeDef: "+ identifier;
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

    // association: direct role: [*] --> opposite role: supports[*]
    public Set getSupportss()                      {return (Set)SupportsSet_;}
    public void setSupportss(Set __arg)            {SupportsSet_ = new HashSet(__arg);}
    public void addSupports(MSupportsDef __arg)    {SupportsSet_.add(__arg);}
    public void removeSupports(MSupportsDef __arg) {SupportsSet_.remove(__arg);}

    // association: direct role: home[*] --> opposite role: component[1]
    public MComponentDef getComponent()           {return Component_;}
    public void setComponent(MComponentDef __arg) {Component_ = __arg;}

    // association: direct role: home[*] --> opposite role: primary_key[0..1]
    public MValueDef getPrimary_Key()           {return PrimaryKey_;}
    public void setPrimary_Key(MValueDef __arg) {PrimaryKey_ = __arg;}

    // association: direct role: home [*] <-> opposite role: factory[*]
    public Set getFactories()                    {return (Set)FactorySet_;}
    public void setFactories(Set __arg)          {FactorySet_ = new HashSet(__arg);}
    public void addFactory(MFactoryDef __arg)    {FactorySet_.add(__arg);}
    public void removeFactory(MFactoryDef __arg) {FactorySet_.remove(__arg);}

    // association: direct role: home [*] <-> opposite role: finder[*]
    public Set getFinders()                     {return (Set) FinderSet_;}
    public void setFinders(Set __arg)           {FinderSet_ = new HashSet();}
    public void addFinder(MFinderDef __arg)     {FinderSet_.add(__arg);}
    public void removeFinder(MFinderDef __arg)  {FinderSet_.remove(__arg);}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}

    // assocation: direct role: definedIn[0..1] <-> oposide role: contents[*]
    public Set getContentss()                    {return (Set)ContainsSet;}
    public void setContentss(Set __arg)          {ContainsSet = new HashSet(__arg);}
    public void addContents(MContained __arg)    {ContainsSet.add(__arg);}
    public void removeContents(MContained __arg) {ContainsSet.remove(__arg);}

    // association: direct role: [*] --> opposite role: base[*]
    public Set getBases()                       {return (Set) InterfaceDerivedFromSet;}
    public void setBases(Set __arg)             {InterfaceDerivedFromSet = new HashSet(__arg);}
    public void addBase(MInterfaceDef __arg)    {InterfaceDerivedFromSet.add(__arg);}
    public void removeBase(MInterfaceDef __arg) {InterfaceDerivedFromSet.remove(__arg);}

    //----------------------------------------------------------------
    // implementation of operations
    //----------------------------------------------------------------

    public MContained getFilteredContents(MDefinitionKind limitToType,
                                          boolean includeInherited)
    {
	//	assert false : "Not implemented!";
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

        elem = searchSet(ContainsSet, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(SupportsSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(FactorySet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchSet(FinderSet_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;

	return null;
    }
}



