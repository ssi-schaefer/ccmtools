/*
 * CCM Tools : C++ Code Generator Library 
 * Egon Teiniker <egon.teiniker@salomon.at> 
 * Copyright (C) 2002 - 2005 Salomon Automation
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

package ccmtools.CppGenerator.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import ccmtools.CppGenerator.SourceConstants;
import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MOperationDef;
import ccmtools.Metamodel.BaseIDL.MParameterDef;
import ccmtools.utils.Text;

public class LocalHelper
{    
    public String getPmmHack(List scope, MContained node)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("#ifdef HAVE_CONFIG_H\n");
        buffer.append("#  include <config.h>\n");
        buffer.append("#endif\n\n");
        buffer.append("#ifdef USING_CONFIX \n");
        buffer.append("#include <");
        buffer.append(Text.join(SourceConstants.fileSeparator, scope));
        buffer.append(".h> \n");
        buffer.append("#else \n");
        buffer.append("#include <");
        buffer.append(node.getIdentifier());
        buffer.append(".h> \n");
        buffer.append("#endif \n");
        return buffer.toString();
    }
    
    
    public String getCcmToolsVersion()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("CCM Tools version ");
        buffer.append(ccmtools.Constants.VERSION);
        return buffer.toString();
    }
    
    public String getCcmGeneratorTimeStamp() 
    {
        StringBuffer buffer = new StringBuffer();
        Calendar now = Calendar.getInstance();
        buffer.append(now.getTime());
        return buffer.toString();
    }
    
    public String getCcmGeneratorUser() 
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(System.getProperty("user.name"));
        return buffer.toString();
    }
    
    public String getOperationResult(MOperationDef op, String langType)
    {
        StringBuffer buffer = new StringBuffer();
        if(!langType.equals("void")) {
            buffer.append(Text.TAB).append("return result;");
        }
        return buffer.toString();
    }
   
    public String getOperationReturn(String langType)
    {
        if(!langType.equals("void"))
            return "return ";
        else	
            return "";
    }
    
    public String getOperationDelegation(MOperationDef op, String langType,
                                         String target)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(Text.TAB);
        if(!langType.equals("void")) {
            buffer.append(langType);
            buffer.append(" result = ");
        }
        buffer.append(target).append("->");
        buffer.append(op.getIdentifier()).append("(");
        List parameterList = new ArrayList();
        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
            MParameterDef p = (MParameterDef) params.next();
            parameterList.add(p.getIdentifier());
        }
        buffer.append(Text.join(",", parameterList)).append(");");
        return buffer.toString();
    }    
}
