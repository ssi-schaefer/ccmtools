/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : ccm/local/utils
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002 - 2005 Salomon Automation
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

#ifndef __CCM__LOCAL__TOSTRING__H__
#define __CCM__LOCAL__TOSTRING__H__

#ifdef WXDEBUG

#include <string>
#include <iostream>
#include <sstream>
#include <WX/Utils/value.h>
#include <WX/Utils/smartptr.h>

namespace ccm {
namespace local {

    /**
     * Create a string with n*TAB spaces that can be used as
     * indent for debug messages.
     */
    inline 
    std::string 
    doIndent(int n)
    {
	const std::string TAB = "    "; 
	std::string space = "";

	for(int i=0;i<n; i++) {
	    space += TAB;
	}
	return space;
    }

    /**
     * Convert C++ basic types into string representations which can
     * be used for debug issues.
     **/
    
    inline 
    std::string 
    ccmDebug(const bool& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	if(in == true) {
	    os << "true:boolean";
	}
	else {
	    os << "false:boolean";
	}
	return os.str();
    } 
    
    inline 
    std::string 
    ccmDebug(const char& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << (int)in << ":char";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const double& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":double";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const float& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":float";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const long& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":long";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const unsigned char& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << (int)in << ":unsigned char";
	return os.str();   
    }
    
    inline 
    std::string 
    ccmDebug(const short& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":short";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const std::string& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << "\"" << in << "\"" << ":string";
	return os.str();  
    }
    
    inline 
    std::string 
    ccmDebug(const unsigned long& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":unsigned long";
	return os.str();         
    }
    
    inline 
    std::string 
    ccmDebug(const unsigned short& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << in << ":unsigned short";
	return os.str();         
    }
    
    inline 
    std::string 
    ccmDebug(const wchar_t& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << (int)in << ":wchar_t";
	return os.str();
    }
    
    inline 
    std::string 
    ccmDebug(const std::wstring& in, int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << ":wstring"; // TODO !!!!!!
	return os.str();
    }

    inline 
    std::string 
    ccmDebug(const WX::Utils::SmartPtr<WX::Utils::Value>& in, 
	     int indent = 0)
    {
	std::ostringstream os;
	os << doIndent(indent);
	os << ":WX::Utils::SmartPtr<WX::Utils::Value>"; // TODO !!!!!!
	return os.str();
    }
    
} // /namespace local
} // /namespace ccm

#endif // WXDEBUG

#endif // __CCM__LOCAL__TOSTRING__H__
