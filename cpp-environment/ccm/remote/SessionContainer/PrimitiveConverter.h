/* -*- mode: C++; c-basic-offset: 4 -*-
 *
 * CCM Tools : Remote C++ Code Generator 
 * Egon Teiniker <egon.teiniker@tugraz.at>
 * copyright (c) 2002, 2003 Salomon Automation
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

namespace ccmtools {
namespace remote {
    
    //==========================================================================
    // Convert basic types from C++ to CORBA 
    //==========================================================================
    
    inline void convertToCorba(const bool& in, CORBA::Boolean& out)
    {
		out = (CORBA::Boolean)in;
    }

    inline void convertToCorba(const char& in, CORBA::Char& out)
    {
		out = (CORBA::Char)in;
    }

    inline void convertToCorba(const double& in, CORBA::Double& out)
    {
		out = (CORBA::Double)in;
    }

    inline void convertToCorba(const float& in, CORBA::Float& out)
    {
		out = (CORBA::Float)in;
    }

    inline void convertToCorba(const long& in, CORBA::Long& out)
    {
		out = (CORBA::Long)in;
    }

    inline void convertToCorba(const unsigned char& in, CORBA::Octet& out)
    {
		out = (CORBA::Octet)in;
    }

    inline void convertToCorba(const short& in, CORBA::Short& out)
    {
		out = (CORBA::Short)in;
    }

    inline void convertToCorba(const std::string& in, char*& out)
    {
		const char* s = in.c_str();
		out = CORBA::string_dup(s);
    }

    inline void convertToCorba(const unsigned long& in, CORBA::ULong& out)
    {
		out = (CORBA::ULong)in;
	}

    inline void convertToCorba(const unsigned short& in, CORBA::UShort& out)
    {
		out = (CORBA::UShort)in;
    }
    
    
    //==========================================================================
    // Convert basic types from CORBA to C++ 
    //==========================================================================

    inline void convertFromCorba(const CORBA::Boolean& in, bool& out)
    {
		out = (bool)in;
    }

    inline void convertFromCorba(const CORBA::Char& in, char& out)
    {
		out = (char)in;
    }

    inline void convertFromCorba(const CORBA::Double& in, double& out)
    {
		out = (double)in;
    }

    inline void convertFromCorba(const CORBA::Float& in, float& out)
    {
		out = (float)in;
    }

    inline void convertFromCorba(const CORBA::Long& in, long& out)
    {
		out = (long)in;
    }

    inline void convertFromCorba(const CORBA::Octet& in, unsigned char& out)
    {
		out = (unsigned char)in;
    }

    inline void convertFromCorba(const CORBA::Short& in, short& out)
    {
		out = (short)in;
    }

    inline void convertFromCorba(const char* in, std::string& out)
    {
		out = (std::string)in;	
    }

    inline void convertFromCorba(char*& in, std::string& out)  // inout string
    {
		out = (std::string)in;	
    }

    inline void convertFromCorba(const CORBA::ULong& in, unsigned long& out)
    {
		out = (unsigned long)in;	
    }

    inline void convertFromCorba(const CORBA::UShort& in, unsigned short& out)
    {
		out = (unsigned short)in;
    }
    
} // /namespace remote
} // /namespace ccmtools

#endif // HAVE_MICO
#endif // __PRIMITIVE_CONVERTER_H__
