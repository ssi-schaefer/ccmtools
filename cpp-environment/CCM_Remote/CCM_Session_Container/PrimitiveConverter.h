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

#include <CORBA.h>
#include <string>

namespace CCM {
  
  //============================================================================
  // Convert basic types from C++ to CORBA 
  //============================================================================

  void convertToCorba(const bool&          , CORBA::Boolean&);
  void convertToCorba(const char&          , CORBA::Char&);
  void convertToCorba(const double&        , CORBA::Double&); 
  void convertToCorba(const float&         , CORBA::Float&);
  void convertToCorba(const long&          , CORBA::Long&);
  void convertToCorba(const unsigned char& , CORBA::Octet&);
  void convertToCorba(const short&         , CORBA::Short&);
  void convertToCorba(const std::string&   , char*& );
  void convertToCorba(const unsigned long& , CORBA::ULong&);
  void convertToCorba(const unsigned short&, CORBA::UShort&);


  //============================================================================
  // Convert basic types from CORBA to C++ 
  //============================================================================

  void convertFromCorba(const CORBA::Boolean&, bool&);
  void convertFromCorba(const CORBA::Char&   , char&);
  void convertFromCorba(const CORBA::Double& , double&);
  void convertFromCorba(const CORBA::Float&  , float&);
  void convertFromCorba(const CORBA::Long&   , long&);
  void convertFromCorba(const CORBA::Octet&  , unsigned char&);
  void convertFromCorba(const CORBA::Short&  , short&);
  void convertFromCorba(const char*&         , std::string&);
  void convertFromCorba(char*&               , std::string&);
  void convertFromCorba(const CORBA::ULong&  , unsigned long&);  
  void convertFromCorba(const CORBA::UShort& , unsigned short&);

} // /namespace CCM

#endif
