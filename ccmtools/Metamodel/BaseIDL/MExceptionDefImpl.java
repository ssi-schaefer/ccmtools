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

import org.omg.CORBA.TypeCode;

public class MExceptionDefImpl
    implements MExceptionDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_EXCEPTION;

    // private MFieldDef members_;
    private TypeCode typeCode_;
    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;
    private String sourceFile;

    private Set MemberSet_;
    private MContainer Contains;

    public MExceptionDefImpl()
    {
	MemberSet_ = new HashSet();
        sourceFile = new String("");
    }

    // override toString()
    public String toString()
    {
	return "MExceptionDef: "+ identifier + MemberSet_.toString();
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

    // aggregation: direct role: exception[0..1] <>- opposite role: member[*]
    public Set getMembers()                     {return MemberSet_;}
    public void setMembers(Set __arg)           {MemberSet_ = new HashSet(__arg);}
    public void addMember(MFieldDef __arg)      {MemberSet_.add(__arg);}
    public void removeMember(MFieldDef __arg)   {MemberSet_.remove(__arg);}

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains;}
    public void setDefinedIn(MContainer __arg)  {Contains = __arg;}
}
