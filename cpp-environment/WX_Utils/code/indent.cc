// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "indent.h"

namespace WX {
namespace Utils {

std::ostream& indent(std::ostream& o, int indent) {
   while (indent--)
      o << ' ';
   return o;
}

} // /namespace
} // /namespace
