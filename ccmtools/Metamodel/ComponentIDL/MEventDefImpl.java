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

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MContainer;
import ccmtools.Metamodel.BaseIDL.MDefinitionKind;
import ccmtools.Metamodel.BaseIDL.MInterfaceDef;
import ccmtools.Metamodel.BaseIDL.MModuleDef;
import ccmtools.Metamodel.BaseIDL.MValueDef;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import org.omg.CORBA.TypeCode;

public class MEventDefImpl
    implements MEventDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_EVENT;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;
    private String sourceFile;

    private TypeCode TypeCode;

    private boolean isAbstract;
    private boolean isCustom;
    private boolean isTruncatable;

    private MContainer Contains;
    private Set ContainsSet;
    private Set AbstractDerivedFromSet;
    private MValueDef ValueDerivedFrom;
    private MInterfaceDef interfaceDef;

    public MEventDefImpl()
    {
	ContainsSet = new HashSet();
	AbstractDerivedFromSet = new HashSet();
        sourceFile = new String("");
    }

    // override toString()
    public String toString()
    {
	return "MValueDef: "+ identifier;
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

    // attribute isCustom:boolean
    public boolean isCustom()                   {return isCustom;}
    public void setCustom(boolean __arg)        {isCustom = __arg;}

    // attribute isTruncatable:boolean
    public boolean isTruncatable()              {return isTruncatable;}
    public void setTruncatable(boolean __arg)   {isTruncatable = __arg;}

    // attribute typeCode:TypeCode
    public TypeCode getTypeCode()               {return TypeCode;}
    public void setTypeCode(TypeCode __arg)     {TypeCode = __arg;}

    // attribute sourceFile:String
    public String getSourceFile()               {return sourceFile;}
    public void setSourceFile(String __arg)     {sourceFile = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}

    // assocation: direct role: definedIn[0..1] <-> oposide role: contents[*]
    public Set getContentss()                    {return ContainsSet;}
    public void setContentss(Set __arg)          {ContainsSet = (__arg != null) ? new HashSet(__arg) : null;}
    public void addContents(MContained __arg)    {ContainsSet.add(__arg);}
    public void removeContents(MContained __arg) {ContainsSet.remove(__arg);}

    // association: direct role: [*] --> opposite role: abstractBase[*]
    public Set getAbstractBases()                   {return AbstractDerivedFromSet;}
    public void setAbstractBases(Set __arg)         {AbstractDerivedFromSet = new HashSet(__arg);}
    public void addAbstractBase(MValueDef __arg)    {AbstractDerivedFromSet.add(__arg);}
    public void removeAbstractBase(MValueDef __arg) {AbstractDerivedFromSet.remove(__arg);}

    // association: direct role: [*] --> opposite role: base[0..1]
    public MValueDef getBase()                  {return ValueDerivedFrom;}
    public void setBase(MValueDef __arg)        {ValueDerivedFrom = __arg ;}

    // association: direct role: [*] --> opposite role: interfaceDef[0..1]
    public MInterfaceDef getInterfaceDef()           {return interfaceDef;}
    public void setInterfaceDef(MInterfaceDef __arg) {interfaceDef = __arg ;}

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

        MContained elem;
        Iterator it = ContainsSet.iterator();

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
}

