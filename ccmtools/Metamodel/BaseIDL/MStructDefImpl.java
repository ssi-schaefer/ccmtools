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

import java.util.List;
import java.util.ArrayList;

import org.omg.CORBA.TypeCode;

public class MStructDefImpl
    implements MStructDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_STRUCT;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;

    private boolean isDefinedInOriginalFile;

    private TypeCode typeCode_;
    private List MemberList_ = null;
    private MContainer Contains;

    public MStructDefImpl()
    {
	MemberList_ = new ArrayList();
    }

    // override toString()
    public String toString()
    {
	return "MStructDef: "+ identifier;
    }


    //----------------------------------------------------------------
    // implementation of attribute access
    //----------------------------------------------------------------

    // read-only attribute definitionKind:ccmtools.Metamodel.BaseIDL.MDefinitionKind
    public MDefinitionKind getDefinitionKind()  {return definitionKind;}

    // attribute typeCode:TypeCode
    public TypeCode getTypeCode()               {return typeCode_;}
    public void setTypeCode(TypeCode __arg)     {typeCode_ = __arg;}

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

    // attribute isDefinedInOriginalFile:boolean
    public boolean isDefinedInOriginalFile()            {return isDefinedInOriginalFile;}
    public void setDefinedInOriginalFile(boolean __arg) {isDefinedInOriginalFile = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // aggregation: direct role: [1] --> opposite role: member[*]
    public List getMembers()                       {return MemberList_;}
    public void setMembers(List __arg)
    {
	if(__arg != null) MemberList_ = new ArrayList(__arg);
	else              MemberList_ = __arg;
    }
    public void addMember(MFieldDef __arg)         {MemberList_.add(__arg);}
    public void removeMember(MFieldDef __arg)      {MemberList_.remove(__arg);}

    public void addMember(int __pos, MFieldDef __arg) {MemberList_.add(__pos, __arg);}
    public void removeMember(int __pos)               {MemberList_.remove(__pos);}
    public void setMember(int __pos, MFieldDef __arg) {MemberList_.set(__pos, __arg);}
    public MFieldDef getMember(int __pos)             {return (MFieldDef)MemberList_.get(__pos);}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}
}

