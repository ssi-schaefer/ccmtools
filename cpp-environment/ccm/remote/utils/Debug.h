/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : CCM_Local/utils
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

#ifndef __CCM__REMOTE__DEBUG__H__
#define __CCM__REMOTE__DEBUG__H__

#ifdef WXDEBUG

#ifdef HAVE_MICO  

#include <CORBA.h>
#include <string>

#include <ccm/local/Debug.h>

namespace ccm {
namespace remote {

    /**
     * Convert CORBA basic types into string representations which can
     * be used for debug issues.
     **/

    // Mico mapps CORBA::Boolean to the same native type than CORBA::Octet

    inline 
    std::string 
    ccmDebug(const CORBA::Boolean& in, bool b, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	if(in == true) {
	    os << "true:CORBA::Boolean";
	}
	else {
	    os << "false:CORBA::Boolean";
	}
	return os.str();
    } 

    inline 
    std::string 
    ccmDebug(const CORBA::Char& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << (int)in << ":CORBA::Char";
	return os.str();
    }


    inline 
    std::string 
    ccmDebug(const CORBA::Double& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::Double";
	return os.str();
    }


    inline 
    std::string 
    ccmDebug(const CORBA::Float& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::Float";
	return os.str();
    }


    inline 
    std::string 
    ccmDebug(const CORBA::Long& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::Long";
	return os.str();
    }


    inline 
    std::string 
    ccmDebug(const CORBA::Octet& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << (int)in << ":CORBA::Octet";
	return os.str();   
    }


    inline 
    std::string 
    ccmDebug(const CORBA::Short& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::Short";
	return os.str();   
    }


    inline 
    std::string 
    ccmDebug(const char* in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << "\"" << in << "\"" << ":char*";
	return os.str();   
    }


    inline 
    std::string 
    ccmDebug(const CORBA::ULong& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::ULong";
	return os.str();   
    }


    inline 
    std::string 
    ccmDebug(const CORBA::UShort& in, int indent = 0)
    {
	std::ostringstream os;
	os << ccm::local::doIndent(indent);
	os << in << ":CORBA::UShort";
	return os.str();   
    }

} // /namespace remote
} // /namespace ccm

#endif // WXDEBUG

#endif // HAVE_MICO

#endif // __CCM__REMOTE__DEBUG__H__
