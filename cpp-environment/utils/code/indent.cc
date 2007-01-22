// -*- mode: C++; c-basic-offset: 3 -*-
// 
// $Id$
//
#include "indent.h"

namespace wamas {
namespace platform {
namespace utils {

std::ostream& indent(std::ostream& o, int indent) {
   while (indent--)
      o << ' ';
   return o;
}

} // /namespace
} // /namespace
} // /namespace
