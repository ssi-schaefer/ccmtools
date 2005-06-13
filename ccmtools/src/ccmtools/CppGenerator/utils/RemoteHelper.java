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
import java.util.List;

import ccmtools.Metamodel.BaseIDL.MContained;
import ccmtools.Metamodel.BaseIDL.MIDLType;
import ccmtools.Metamodel.BaseIDL.MPrimitiveDef;
import ccmtools.Metamodel.BaseIDL.MStringDef;
import ccmtools.utils.Text;


public class RemoteHelper
{
   
    public String convertFromCorbaDeclaration(String corbaName, String cppName)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("void convertFromCorba(const ");
        buffer.append(corbaName);
        buffer.append("& in, ");
        buffer.append(cppName);
        buffer.append("& out);");
        return buffer.toString();
    }
    
    public String convertToCorbaDeclaration(String cppName, String corbaName)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("void convertToCorba(const ");
        buffer.append(cppName);
        buffer.append("& in, ");
        buffer.append(corbaName);
        buffer.append("& out);");
        return buffer.toString();
    }   
    
    
    public String getInOutValue(MIDLType singleIdlType) 
    {
        String inOutValue;
        if (singleIdlType instanceof MStringDef) {
            inOutValue = "out[i].inout()";
        }
        else {
            inOutValue = "out[i]";
        }
        return inOutValue;
    }
   
    
    public String getConvertFromCorbaImpl(String stubName,
                                          String localName,
                                          String convertAlias) 
    {
        StringBuffer buffer = new StringBuffer();
        List code = new ArrayList();
        buffer.append("void").append(Text.NL);
        buffer.append("convertFromCorba(const ").append(stubName); 
        buffer.append("& in, ").append(localName).append("& out)");
        buffer.append(Text.NL);
        buffer.append("{").append(Text.NL);
        buffer.append("    LDEBUGNL(CCM_REMOTE,\" convertFromCorba(");
        buffer.append(stubName).append(")\");").append(Text.NL);
        buffer.append("    LDEBUGNL(CCM_REMOTE, in);").append(Text.NL);
        buffer.append(convertAlias).append(Text.NL);
    	buffer.append("}").append(Text.NL);
    	return buffer.toString();
	}
   
    
    public String getConvertToCorbaImpl(String localName,String stubName,
                                        String convertAlias) 
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("void").append(Text.NL);
        buffer.append("convertToCorba(const ").append(localName).append("& in, "); 
        buffer.append(stubName).append("& out)").append(Text.NL);
        buffer.append("{").append(Text.NL);
        buffer.append("    LDEBUGNL(CCM_REMOTE,\" convertToCorba(");
        buffer.append(stubName).append(")\");");
        buffer.append(convertAlias).append(Text.NL);
    	buffer.append("    LDEBUGNL(CCM_REMOTE, out);").append(Text.NL);
    	buffer.append("}").append(Text.NL);
    	return buffer.toString();
    }
    
    public String getOutputCorbaTypeDeclaration(String stubName) 
    {
        StringBuffer buffer = new StringBuffer();	
        buffer.append("std::ostream& operator<<(std::ostream& o, const ");
    	buffer.append(stubName).append("& value);").append(Text.NL);
        return buffer.toString();
    }
    
    public String getOutputCorbaTypeImpl(String stubName, String outType) 
    {
        StringBuffer buffer = new StringBuffer();		
        buffer.append("std::ostream&").append(Text.NL);
        buffer.append("operator<<(std::ostream& o, const "); 
        buffer.append(stubName).append("& value)").append(Text.NL);
        buffer.append("{").append(Text.NL);
        buffer.append(outType).append(Text.NL);
        buffer.append("    return o;").append(Text.NL);
        buffer.append("}").append(Text.NL);
        return buffer.toString();
    }
    
    public String getSingleValue(List localNamespace, 
                                 MIDLType singleIdlType, String langType)
    {
        StringBuffer buffer = new StringBuffer();
        if (singleIdlType instanceof MPrimitiveDef 
                || singleIdlType instanceof MStringDef) {
            buffer.append(langType);
        }
        else {
            buffer.append(Text.join("::", localNamespace)).append("::"); 
            buffer.append(langType);
        }
        return buffer.toString();
    }
    
    public String getCORBASequenceConverterInclude(MContained singleContained)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("#include \"");
        buffer.append(singleContained.getIdentifier());
        buffer.append("_remote.h\"");
        return buffer.toString();
    }
    
    public String getConvertAliasFromCORBA(String singleValue) 
    {
        List code = new ArrayList();
        code.add("    out.clear();");
        code.add("    out.reserve(in.length());");
        code.add("    for(unsigned long i=0; i < in.length();i++) {");
        code.add("        " + singleValue + " singleValue;");
        code.add("        convertFromCorba(in[i], singleValue);");
        code.add("        out.push_back(singleValue);");
        code.add("    }");
        return Text.join("\n", code);
    }
    
    public String getConvertAliasToCORBA(String singleValue, String inOutValue) 
    {
        List code = new ArrayList();
        code.add("    out.length(in.size());");
        code.add("    for(unsigned long i=0; i < in.size(); i++) {");
        code.add("        " + singleValue + " singleValue = in[i];");
        code.add("        convertToCorba(singleValue, " + inOutValue + ");");
        code.add("    }");
        return Text.join("\n", code);
    }
    
    public String getOutputCppType() 
    {
        List code = new ArrayList();
        code.add("    o << \"[ \";");
        code.add("    for(unsigned long i=0; i < value.size(); i++) {");
        code.add("        if(i) o << \",\";");
        code.add("        o << value[i];");
        code.add("    }");
        code.add("    o << \" ]\";");
        return Text.join("\n", code);
    }

    public String getOutputCORBAType(String namespace, String identifier)
    {
        List code = new ArrayList();
        code.add("    o << endl;");
        code.add("    o << \"sequence " + namespace
                + identifier + " [ \" << endl;");
        code.add("    for(unsigned long i=0; i < value.length();i++) {");
        code.add("        o << value[i] << endl;");
        code.add("    }");
        code.add("    o << \"]\";");
        return Text.join("\n", code);
    }
}

