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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private String sourceFile;

    private boolean isAbstract;
    private boolean isLocal;
    private boolean isForwardDeclaration;

    private List SupportsList_;
    private List FactoryList_;
    private List FinderList_;
    private MComponentDef Component_;
    private MValueDef PrimaryKey_;
    private MContainer Contains;
    private MIDLType TypedBy_;
    private List ContainsList;
    private List InterfaceDerivedFromList;

    public MHomeDefImpl()
    {
        isForwardDeclaration = false;
	SupportsList_= new ArrayList();
	FactoryList_= new ArrayList();
	FinderList_ = new ArrayList();
	ContainsList = new ArrayList();
	InterfaceDerivedFromList = new ArrayList();
        sourceFile = new String("");
    }

    // override toString()
    public String toString()
    {
	String tmp = "MHomeDef: "+ identifier;
	if (ContainsList.size() > 0)
            tmp  += " " + ContainsList;
        if (InterfaceDerivedFromList.size() > 0)
            tmp += " (bases: " + InterfaceDerivedFromList + ")";
        if (! sourceFile.equals(""))
            tmp += " (defined in '"+ sourceFile + "')";
	return tmp;
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

    // attribute sourceFile:String
    public String getSourceFile()               {return sourceFile;}
    public void setSourceFile(String __arg)     {sourceFile = __arg;}

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
    public List getSupportss()                     {return SupportsList_;}
    public void setSupportss(List __arg)           {SupportsList_ = new ArrayList(__arg);}
    public void addSupports(MSupportsDef __arg)    {SupportsList_.add(__arg);}
    public void removeSupports(MSupportsDef __arg) {SupportsList_.remove(__arg);}

    // association: direct role: home[*] --> opposite role: component[1]
    public MComponentDef getComponent()           {return Component_;}
    public void setComponent(MComponentDef __arg) {Component_ = __arg;}

    // association: direct role: home[*] --> opposite role: primary_key[0..1]
    public MValueDef getPrimary_Key()           {return PrimaryKey_;}
    public void setPrimary_Key(MValueDef __arg) {PrimaryKey_ = __arg;}

    // association: direct role: home [*] <-> opposite role: factory[*]
    public List getFactories()                   {return FactoryList_;}
    public void setFactories(List __arg)         {FactoryList_ = new ArrayList(__arg);}
    public void addFactory(MFactoryDef __arg)    {FactoryList_.add(__arg);}
    public void removeFactory(MFactoryDef __arg) {FactoryList_.remove(__arg);}

    // association: direct role: home [*] <-> opposite role: finder[*]
    public List getFinders()                    {return FinderList_;}
    public void setFinders(List __arg)          {FinderList_ = new ArrayList();}
    public void addFinder(MFinderDef __arg)     {FinderList_.add(__arg);}
    public void removeFinder(MFinderDef __arg)  {FinderList_.remove(__arg);}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}

    // assocation: direct role: definedIn[0..1] <-> oposide role: contents[*]
    public List getContentss()                   {return ContainsList;}
    public void setContentss(List __arg)         {ContainsList = new ArrayList(__arg);}
    public void addContents(MContained __arg)    {ContainsList.add(__arg);}
    public void removeContents(MContained __arg) {ContainsList.remove(__arg);}

    // association: direct role: [*] --> opposite role: base[*]
    public List getBases()                      {return InterfaceDerivedFromList;}
    public void setBases(List __arg)            {InterfaceDerivedFromList = new ArrayList(__arg);}
    public void addBase(MInterfaceDef __arg)    {InterfaceDerivedFromList.add(__arg);}
    public void removeBase(MInterfaceDef __arg) {InterfaceDerivedFromList.remove(__arg);}

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
    private MContained searchBin(List bin, String searchName,
                                 long levelsToSearch,
                                 MDefinitionKind limitToType,
                                 boolean excludeInherited)
    {
        MContained elem;
        Iterator it = bin.iterator();

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

        elem = searchBin(ContainsList, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchBin(SupportsList_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchBin(FactoryList_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;
        elem = searchBin(FinderList_, searchName, levelsToSearch, limitToType, excludeInherited);
        if (elem != null) return elem;

	return null;
    }
}



