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

/* 
 * This code is based on the MicoCCM implementation of a CCM session container.
 */

#include <WX/Utils/debug.h>

#include "PrimitiveConverter.h" 

using namespace std;
using namespace WX::Utils;

//============================================================================
// Convert basic types from C++ to CORBA 
//============================================================================

void 
CCM::convertToCorba(const bool& in, CORBA::Boolean& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Boolean)");
    out = (CORBA::Boolean)in;
}

void 
CCM::convertToCorba(const char& in, CORBA::Char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Char)");
    out = (CORBA::Char)in;
}

void 
CCM::convertToCorba(const double& in, CORBA::Double& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Double)");
    out = (CORBA::Double)in;
}

void 
CCM::convertToCorba(const float& in, CORBA::Float& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Float)");
    out = (CORBA::Float)in;
}

void 
CCM::convertToCorba(const long& in, CORBA::Long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Long)");
    out = (CORBA::Long)in;
}

void 
CCM::convertToCorba(const unsigned char& in, CORBA::Octet& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Octet)");
    out = (CORBA::Octet)in;
}

void 
CCM::convertToCorba(const short& in, CORBA::Short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::Short)");
    out = (CORBA::Short)in;
}

void 
CCM::convertToCorba(const std::string& in, char*& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(char*)");
    const char* s = in.c_str();
    out = CORBA::string_dup(s);
}

void 
CCM::convertToCorba(const unsigned long& in, CORBA::ULong& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::ULong)");
    out = (CORBA::ULong)in;
}

void 
CCM::convertToCorba(const unsigned short& in, CORBA::UShort& out)
{
    LDEBUGNL(CCM_REMOTE, "convertToCorba(CORBA::UShort)");
    out = (CORBA::UShort)in;
}



//============================================================================
// Convert CORBA to C++ types
//============================================================================

void 
CCM::convertFromCorba(const CORBA::Boolean& in, bool& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Boolean)");
    out = (bool)in;
}

void 
CCM::convertFromCorba(const CORBA::Char& in, char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Char)");
    out = (char)in;
}

void 
CCM::convertFromCorba(const CORBA::Double& in, double& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Double)");
    out = (double)in;
}

void 
CCM::convertFromCorba(const CORBA::Float& in, float& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Float)");
    out = (float)in;
}

void 
CCM::convertFromCorba(const CORBA::Long& in, long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Long)");
    out = (long)in;
}

void 
CCM::convertFromCorba(const CORBA::Octet& in, unsigned char& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Octet)");
    out = (unsigned char)in;
}

void 
CCM::convertFromCorba(const CORBA::Short& in, short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::Short)");
    out = (short)in;
}

void 
CCM::convertFromCorba(const char*& in, std::string& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba()");
    out = (std::string)in;
}

void 
CCM::convertFromCorba(char*& in, std::string& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba()");
    out = (std::string)in;
}

void 
CCM::convertFromCorba(const CORBA::ULong& in, unsigned long& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba(CORBA::ULong)");
    out = (unsigned long)in;
}  

void 
CCM::convertFromCorba(const CORBA::UShort& in, unsigned short& out)
{
    LDEBUGNL(CCM_REMOTE, "convertFromCorba( CORBA::UShort)");
    out = (unsigned short)in;
}
