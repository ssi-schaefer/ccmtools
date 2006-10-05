/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : Remote C++ Code Generator 
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

#ifndef __PRIMITIVE_CONVERTER_H__
#define __PRIMITIVE_CONVERTER_H__

#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif 

#ifdef HAVE_MICO  

#include <CORBA.h>
#include <string>

#include <wx/utils/debug.h>

using namespace wx::utils;

namespace ccm {
namespace remote {
    
    //==========================================================================
    // Convert basic types from C++ to CORBA 
    //==========================================================================
    
    inline void convertToCorba(const bool& in, CORBA::Boolean& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Boolean)");
	out = (CORBA::Boolean)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Boolean = " << out);
    }

    inline void convertToCorba(const char& in, CORBA::Char& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Char)");
	out = (CORBA::Char)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Char = " << out);
    }

    inline void convertToCorba(const double& in, CORBA::Double& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Double)");
	out = (CORBA::Double)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Double = " << out);
    }

    inline void convertToCorba(const float& in, CORBA::Float& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Float)");
	out = (CORBA::Float)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Float = " << out);
    }

    inline void convertToCorba(const long& in, CORBA::Long& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Long)");
	out = (CORBA::Long)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Long = " << out);
    }

    inline void convertToCorba(const unsigned char& in, CORBA::Octet& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Octet)");
	out = (CORBA::Octet)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Octet = " << out);
    }

    inline void convertToCorba(const short& in, CORBA::Short& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::Short)");
	out = (CORBA::Short)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::Short = " << out);	
    }

    inline void convertToCorba(const std::string& in, char*& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(char*)");
	const char* s = in.c_str();
	out = CORBA::string_dup(s);
	LDEBUGNL(CCM_CONTAINER, "char* = " << out);
    }

    inline void convertToCorba(const unsigned long& in, CORBA::ULong& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::ULong)");
	out = (CORBA::ULong)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::ULong = " << out);
    }

    inline void convertToCorba(const unsigned short& in, CORBA::UShort& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertToCorba(CORBA::UShort)");
	out = (CORBA::UShort)in;
	LDEBUGNL(CCM_CONTAINER, "CORBA::UShort = " << out);	
    }
    
    
    //==========================================================================
    // Convert basic types from CORBA to C++ 
    //==========================================================================

    inline void convertFromCorba(const CORBA::Boolean& in, bool& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Boolean)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Boolean = " << in);
	out = (bool)in;
    }

    inline void convertFromCorba(const CORBA::Char& in, char& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Char)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Char = " << in);
	out = (char)in;
    }

    inline void convertFromCorba(const CORBA::Double& in, double& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Double)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Double = " << in);
	out = (double)in;
    }

    inline void convertFromCorba(const CORBA::Float& in, float& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Float)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Float = " << in);
	out = (float)in;
    }

    inline void convertFromCorba(const CORBA::Long& in, long& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Long)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Long = " << in);
	out = (long)in;
    }

    inline void convertFromCorba(const CORBA::Octet& in, unsigned char& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Octet)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Octet = " << in);
	out = (unsigned char)in;
    }

    inline void convertFromCorba(const CORBA::Short& in, short& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::Short)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::Short = " << in);
	out = (short)in;
    }

    inline void convertFromCorba(const char* in, std::string& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(char*)");
	LDEBUGNL(CCM_CONTAINER, "char* = " << in);
	out = (std::string)in;	
    }

    inline void convertFromCorba(char*& in, std::string& out)  // inout string
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(char*&)");
	LDEBUGNL(CCM_CONTAINER, "char*& = " << in);
	out = (std::string)in;	
    }

    inline void convertFromCorba(const CORBA::ULong& in, unsigned long& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba(CORBA::ULong)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::ULong = " << in);
	out = (unsigned long)in;	
    }

    inline void convertFromCorba(const CORBA::UShort& in, unsigned short& out)
    {
	LDEBUGNL(CCM_CONTAINER, "convertFromCorba( CORBA::UShort)");
	LDEBUGNL(CCM_CONTAINER, "CORBA::UShort = " << in);
	out = (unsigned short)in;
    }
    
} // /namespace remote
} // /namespace ccm

#endif // HAVE_MICO
#endif // __PRIMITIVE_CONVERTER_H__
