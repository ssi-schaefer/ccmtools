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

package ccmtools.metamodel.BaseIDL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MAttributeDefImpl
    implements MAttributeDef
{
    private final static MDefinitionKind definitionKind =
        MDefinitionKind.DK_ATTRIBUTE;

    private String absoluteName;
    private String identifier;
    private String repositoryId;
    private String version;
    private String sourceFile;

    private boolean isReadonly_;

    private MContainer Contains;
    private MIDLType TypedBy_;
    private List GetRaisesSet_;
    private List SetRaisesSet_;

    public MAttributeDefImpl()
    {
        GetRaisesSet_ = new ArrayList();
        SetRaisesSet_ = new ArrayList();
        Contains = null;
        TypedBy_ = null;
        sourceFile = "";
        isReadonly_ = false;
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

    // attribute sourceFile:String
    public String getSourceFile()               {return sourceFile;}
    public void setSourceFile(String __arg)     {sourceFile = __arg;}

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
    public List getGetExceptions()                       {return GetRaisesSet_;}
    public void setGetExceptions(List __arg)             {GetRaisesSet_ = new ArrayList(__arg);}
    public void addGetException(MExceptionDef __arg)    {GetRaisesSet_.add(__arg);}
    public void removeGetException(MExceptionDef __arg) {GetRaisesSet_.remove(__arg);}

    // association: direct role: [*] --> opposite role: setException[*]
    public List getSetExceptions()                       {return SetRaisesSet_;}
    public void setSetExceptions(List __arg)             {SetRaisesSet_ = new ArrayList(__arg);}
    public void addSetException(MExceptionDef __arg)    {SetRaisesSet_.add(__arg);}
    public void removeSetException(MExceptionDef __arg) {SetRaisesSet_.remove(__arg);}
}

