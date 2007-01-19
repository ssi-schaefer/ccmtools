#ifndef wx_utils_types_value_out_h
#define wx_utils_types_value_out_h

#include "Value.h"
#include <iosfwd>

namespace wamas {
namespace platform {
namespace utils {


void output(std::ostream&, const SmartPtr<Value>&);


inline std::ostream& operator<<(std::ostream& o, const SmartPtr<Value>& v)
{
    output(o, v);
    return o;
}


} // /namespace
} // /namespace
}// /namespace

#endif
