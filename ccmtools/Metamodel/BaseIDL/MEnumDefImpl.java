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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.omg.CORBA.TypeCode;

public class MEnumDefImpl
    implements MEnumDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_ENUM;

    private String absoluteName_;
    private String identifier_;
    private String repositoryId_;
    private String version_;
    private String sourceFile;

    private MContainer Contains_;
    private List MemberList_;
    private TypeCode typeCode_;

    public MEnumDefImpl()
    {
	MemberList_ = new ArrayList();
        sourceFile = new String("");
    }

    public String toString()
    {
	StringBuffer buffer = new StringBuffer("MEnumDef: ");
        buffer.append(identifier_ + " { ");

	Iterator it = MemberList_.iterator();
	while(it.hasNext()) {
	    buffer.append(it.next() + " ");
	}
	buffer.append("};");
	return buffer.toString();
    }


    //----------------------------------------------------------------
    // implementation of attribute access
    //----------------------------------------------------------------

    public List getMembers()                       { return MemberList_; }
    public void setMembers(List __arg)             { MemberList_ = new ArrayList(__arg);}
    public void addMember(String __arg)            { MemberList_.add(__arg);}
    public void removeMember(String __arg)         { MemberList_.remove(__arg);}

    public void addMember(int __pos, String __arg) { MemberList_.add(__pos, __arg); }
    public void removeMember(int __pos)            { MemberList_.remove(__pos); }
    public void setMember(int __pos, String __arg) { MemberList_.set(__pos, __arg); }
    public String getMember(int __pos)             { return (String)MemberList_.get(__pos); }

    // read-only attribute definitionKind:ccmtools.Metamodel.BaseIDL.MDefinitionKind
    public MDefinitionKind getDefinitionKind()  {return definitionKind;}

    // attribute typeCode:TypeCode
    public TypeCode getTypeCode()               {return typeCode_;}
    public void setTypeCode(TypeCode __arg)     {typeCode_ = __arg;}

    // attribute absoluteName:String
    public String getAbsoluteName()             {return absoluteName_;}
    public void setAbsoluteName(String __arg)   {absoluteName_ = __arg;}

    // attribute identifier:String
    public String getIdentifier()               {return identifier_;}
    public void setIdentifier(String __arg)     {identifier_ = __arg;}

    // attribute repositoryId:String
    public String getRepositoryId()             {return repositoryId_;}
    public void setRepositoryId(String __arg)   {repositoryId_ = __arg;}

    // attribute version:String
    public String getVersion()                  {return version_;}
    public void setVersion(String __arg)        {version_ = __arg;}

    // attribute sourceFile:String
    public String getSourceFile()               {return sourceFile;}
    public void setSourceFile(String __arg)     {sourceFile = __arg;}

    //----------------------------------------------------------------
    // implementation of navigation
    //----------------------------------------------------------------

    // association: direct role: contants[*] <-> opposite role: definedIn[0..1]
    public MContainer getDefinedIn()            {return Contains_;}
    public void setDefinedIn(MContainer __arg)  {Contains_ = __arg;}
}

