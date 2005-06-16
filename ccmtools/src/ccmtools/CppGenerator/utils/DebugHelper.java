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



public class DebugHelper
{
//    public String getDebugInclude()
//    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("#ifdef WXDEBUG").append(Text.NL);
//        buffer.append("#include <CCM_Local/Debug.h>").append(Text.NL);
//        buffer.append("#endif // WXDEBUG").append(Text.NL);
//        return buffer.toString();
//    }

//    public String getDebugSequence(List baseNamespace, String sequenceName, 
//                                MIDLType singleIdlType)
//    {
//        StringBuffer buffer = new StringBuffer(); 	
//        buffer.append("#ifdef WXDEBUG").append(Text.NL);
//        buffer.append("inline").append(Text.NL);
//        buffer.append("std::string").append(Text.NL); 
//        buffer.append("ccmDebug(const ").append(sequenceName).append("& in, int indent = 0)").append(Text.NL);
//        buffer.append("{").append(Text.NL);
//        buffer.append(Text.TAB).append("std::ostringstream os;").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) << \"sequence ")
//        	.append(sequenceName).append("\" << endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) << \"[\" << std::endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("for(unsigned int i=0; i<in.size(); i++) {")
//        	.append(Text.NL);
//        buffer.append(Text.tab(2)).append("os << ");
//        buffer.append(Scope.getDebugNamespace(baseNamespace,singleIdlType));
//        buffer.append("ccmDebug(in[i], indent+1) << std::endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("}").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) << \"]\";").append(Text.NL);
//        buffer.append(Text.TAB).append("return os.str();").append(Text.NL);
//        buffer.append("}").append(Text.NL);
//        buffer.append("#endif").append(Text.NL);
//        return buffer.toString();
//    }
//    
//    public String getDebugArray(List baseNamespace, String sequenceName, 
//                                   MIDLType singleIdlType)
//    {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append("#ifdef WXDEBUG").append(Text.NL);
//        buffer.append("inline").append(Text.NL);
//        buffer.append("std::string").append(Text.NL); 
//        buffer.append("ccmDebug(const ").append(sequenceName).append("& in, int indent = 0)").append(Text.NL);
//        buffer.append("{").append(Text.NL);
//        buffer.append(Text.TAB).append("std::ostringstream os;").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) << \"array ")
//        	.append(sequenceName).append("\" << endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) <<  \"[\" << std::endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("for(unsigned int i=0; i<in.size(); i++) {")
//        	.append(Text.NL);
//        buffer.append(Text.tab(2)).append("os << ");
//        buffer.append(Scope.getDebugNamespace(baseNamespace,singleIdlType));            
//        buffer.append("ccmDebug(in[i], indent+1) << std::endl;").append(Text.NL);
//        buffer.append(Text.TAB).append("}").append(Text.NL);
//        buffer.append(Text.TAB).append("os << doIndent(indent) << \"]\";").append(Text.NL);
//        buffer.append(Text.TAB).append("return os.str();").append(Text.NL);
//        buffer.append("}").append(Text.NL);
//        buffer.append("#endif").append(Text.NL);
//        return buffer.toString();
//    }
//        
//    public String getDebugEnum(MEnumDef enum)
//    {
//        StringBuffer buffer = new StringBuffer();
//        for(Iterator i = enum.getMembers().iterator(); i.hasNext();) {
//            String member = (String)i.next(); 
//            buffer.append(Text.TAB).append("case ").append(member).append(": ")
//            	.append(Text.NL);
//            buffer.append(Text.tab(2)).append("os << \"").append(member)
//            	.append("\";").append(Text.NL);
//            buffer.append(Text.tab(2)).append("break;").append(Text.NL);
//        }
//        return buffer.toString();        
//    }
//    
//    public String getDebugOperationInParameter(List baseNamespace, MOperationDef op)
//    {
//        StringBuffer buffer = new StringBuffer();
//        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
//            MParameterDef p = (MParameterDef) params.next();
//            MIDLType idlType = ((MTyped) p).getIdlType();
//            MParameterMode direction = p.getDirection();
//            if(direction == MParameterMode.PARAM_IN) {
//                buffer.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"IN ");
//                buffer.append(p.getIdentifier()).append(" = \" << ");
//                buffer.append(Scope.getDebugNamespace(baseNamespace,idlType));
//                buffer.append("ccmDebug(").append(p.getIdentifier()).append(")");
//                buffer.append(");");
//                buffer.append(Text.NL);
//            }
//            else if(direction == MParameterMode.PARAM_INOUT) {
//                buffer.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"INOUT ");
//                buffer.append(p.getIdentifier()).append(" = \" << ");
//                buffer.append(Scope.getDebugNamespace(baseNamespace,idlType));
//                buffer.append("ccmDebug(").append(p.getIdentifier()).append(")");
//                buffer.append(");");
//                buffer.append(Text.NL);
//            }
//        }
//        return buffer.toString();
//    }
//   
//    public String getDebugOperationOutParameter(List baseNamespace, MOperationDef op)
//    {
//        StringBuffer buffer = new StringBuffer();
//        for(Iterator params = op.getParameters().iterator(); params.hasNext();) {
//            MParameterDef p = (MParameterDef) params.next();
//            MIDLType idlType = ((MTyped) p).getIdlType();
//            MParameterMode direction = p.getDirection();
//            if(direction == MParameterMode.PARAM_OUT) {
//                buffer.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"OUT ");
//                buffer.append(p.getIdentifier()).append(" = \" << ");
//                buffer.append(Scope.getDebugNamespace(baseNamespace,idlType));
//                buffer.append("ccmDebug(").append(p.getIdentifier()).append("));");
//            }
//        }
//        return buffer.toString();
//    }
//        
//    public String getDebugOperationResult(List baseNamespace, MOperationDef op, 
//                                          String langType)
//    {
//        MIDLType idlType = op.getIdlType();
//        StringBuffer buffer = new StringBuffer();
//        if(!langType.equals("void")) {
//            buffer.append(Text.TAB).append("LDEBUGNL(CCM_LOCAL, \"result = \" << ");
//            buffer.append(Scope.getDebugNamespace(baseNamespace, idlType));
//            buffer.append("ccmDebug(").append("result").append(")").append(");");
//        }
//        return buffer.toString();
//    }
}
