/* CCM Tools : CCM Metamodel Library
 * Egon Teiniker <egon.teiniker@tugraz.at>
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

package ccmtools.Metamodel.BaseIDL;

import java.util.List;
import java.util.ArrayList;

import org.omg.CORBA.TypeCode;

public class MUnionDefImpl
    implements MUnionDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_UNION;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;
    private String sourceFile;

    private TypeCode typeCode_;
    private MIDLType DiscriminatedBy_;
    private List UnionMemberList_;
    private MContainer Contains;

    public MUnionDefImpl()
    {
        typeCode_ = null;
        DiscriminatedBy_ = null;
        Contains = null;
	UnionMemberList_ = new ArrayList();
        sourceFile = "";
    }

    // override toString()
    public String toString()
    {
	String tmp = "MUnionDef: "+ identifier;
        if (UnionMemberList_.size() > 0) tmp += " " + UnionMemberList_;
        return tmp;
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

    // attribute sourceFile:String
    public String getSourceFile()               {return sourceFile;}
    public void setSourceFile(String __arg)     {sourceFile = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // association: direct role [*] --> opposite role: discriminatorType[1]
    public MIDLType getDiscriminatorType()                   {return DiscriminatedBy_;}
    public void setDiscriminatorType(MIDLType __arg)         {DiscriminatedBy_ = __arg;}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()                         {return Contains;}
    public void setDefinedIn(MContainer __arg)               {Contains = __arg;}

    // aggregation: direct role: union[0..1] <-> opposite role: unionMember[*]
    public List getUnionMembers()                            {return UnionMemberList_;}
    public void setUnionMembers(List __arg)                  {UnionMemberList_ = new ArrayList(__arg);}
    public void addUnionMember(MUnionFieldDef __arg)         {UnionMemberList_.add(__arg);}
    public void removeUnionMember(MUnionFieldDef __arg)      {UnionMemberList_.remove(__arg);}

    public void addUnionMember(int __pos, MUnionFieldDef __arg) {UnionMemberList_.add(__pos, __arg);}
    public void removeUnionMember(int __pos)                    {UnionMemberList_.remove(__pos);}
    public void setUnionMember(int __pos, MUnionFieldDef __arg) {UnionMemberList_.set(__pos, __arg);}
    public MUnionFieldDef getUnionMember(int __pos)             {return (MUnionFieldDef)UnionMemberList_.get(__pos);}
}

