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

package ccmtools.Metamodel.BaseIDL;

import java.util.Set;
import java.util.HashSet;

public class MAttributeDefImpl
    implements MAttributeDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_ATTRIBUTE;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;

    private boolean isReadonly_;

    private MContainer Contains;
    private MIDLType TypedBy_;
    private Set GetRaisesSet_;
    private Set SetRaisesSet_;

    public MAttributeDefImpl()
    {
	GetRaisesSet_ = new HashSet();
	SetRaisesSet_ = new HashSet();
    }

    // override toString()
    public String toString()
    {
	return "MAttributeDef: "+ identifier;
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

    // attribute isReadonly:boolean
    public boolean isReadonly()                 {return isReadonly_;}
    public void setReadonly(boolean __arg)      {isReadonly_ = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}

    // association: direct role: [*] --> opposite role: idlType[1]
    public MIDLType getIdlType()                {return TypedBy_;}
    public void setIdlType(MIDLType __arg)      {TypedBy_ = __arg;}

    // assocation: direct role: [*] --> oposide role: getException[*]
    public Set getGetExceptions()                       {return (Set)GetRaisesSet_;}
    public void setGetExceptions(Set __arg)             {GetRaisesSet_ = new HashSet(__arg);}
    public void addGetException(MExceptionDef __arg)    {GetRaisesSet_.add(__arg);}
    public void removeGetException(MExceptionDef __arg) {GetRaisesSet_.remove(__arg);}

    // association: direct role: [*] --> opposite role: setException[*]
    public Set getSetExceptions()                       {return (Set)SetRaisesSet_;}
    public void setSetExceptions(Set __arg)             {SetRaisesSet_ = new HashSet(__arg);}
    public void addSetException(MExceptionDef __arg)    {SetRaisesSet_.add(__arg);}
    public void removeSetException(MExceptionDef __arg) {SetRaisesSet_.remove(__arg);}
}

